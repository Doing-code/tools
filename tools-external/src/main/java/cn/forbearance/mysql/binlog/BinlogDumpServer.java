package cn.forbearance.mysql.binlog;

import cn.forbearance.mysql.AbstractBaseLifeCycle;
import cn.forbearance.mysql.MysqlDataSource;
import cn.forbearance.mysql.binlog.packet.Event;
import cn.forbearance.mysql.binlog.packet.EventHeader;
import cn.forbearance.mysql.binlog.packet.EventHeaderV4;
import cn.forbearance.mysql.executor.QueryExecutor;
import cn.forbearance.mysql.executor.UpdateExecutor;
import cn.forbearance.mysql.protocol.packets.*;
import cn.forbearance.mysql.protocol.packets.request.BinlogDumpPacket;
import cn.forbearance.mysql.protocol.packets.request.GtidSet;
import cn.forbearance.utils.ErrorPacketException;
import cn.hutool.core.lang.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * MySQL 主从复制是 从拉 策略，即master在修改binlog后，在master中会有一个线程异步的通知 slave 来拉取binlog.
 *
 * @author cristina
 */
public class BinlogDumpServer extends AbstractBaseLifeCycle {

    private static final Logger log = LoggerFactory.getLogger(BinlogDumpServer.class);

    /**
     * binlog file name
     */
    private String filename;

    /**
     * binlog position
     */
    private long pos;
    private final MysqlDataSource dataSource;

    /**
     * binlog 同步上下文
     */
    private final BinlogDumpContext context;

    private long lastHearBeat = 0;
    /**
     * 心跳监测
     */
    private Runnable heartBeatTimerTask = () -> {
        if ((System.currentTimeMillis() - lastHearBeat / 1000) > MASTER_HEARTBEAT_PERIOD_SECONDS) {
            log.error("master loss of the connection");
        } else {
            log.info("hearBeat check time, Last time [{}], Now time [{}]", lastHearBeat, System.currentTimeMillis());
        }
    };

    private ScheduledExecutorService timer;

    private LogPosition logPosition;

    /**
     * 主节点心跳周期（秒）
     */
    public static final int MASTER_HEARTBEAT_PERIOD_SECONDS = 15;

    /**
     * 监测间隔
     */
    public static final int DETECTING_INTERVAL_IN_SECONDS = 3;

    private final ThreadFactory threadFactory = r -> {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        thread.setName("hearBeat");
        return thread;
    };

    public BinlogDumpServer(String address, int port, String username, String password) {
        dataSource = new MysqlDataSource(address, port, username, password);
        registerListener();
        context = new BinlogDumpContext();
    }

    @Override
    protected void doStart() {
        dataSource.start();

        if (Objects.isNull(filename))
            getFilenameAndPos();

        // 在dump开始前更新 session 的配置.
        settingBeforeDump();

        /*
            mysql5.6之后，支持在binlog对象中增加checksum信息，比如CRC32协议.
                其原理主要是在原先binlog的末尾新增了4个byte，写入一个crc32的校验值.
            mysql5.6.6之后默认就会开启checksum.
         */
        ChecksumEnum checksumEnum = null;
        try {
            checksumEnum = fetchBinlogChecksum();
            context.setGtidSet(new GtidSet(fetchGtid()));

            if (checksumEnum != ChecksumEnum.NONE) {
                confirmSupportOfChecksum(checksumEnum);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        context.setChecksumType(checksumEnum);
        // 心跳检测
        setHearBeat();

        try {
            binlogDump();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doStop() {
        lastHearBeat = 0L;
        if (timer != null) {
            timer.shutdown();
        }
        heartBeatTimerTask = null;
        dataSource.stop();
    }

    private void getFilenameAndPos() {
        if (Objects.nonNull(logPosition)) {
            return;
        }

        try {
            ResultSetPacket result = query("show master status");
            List<String> status = result.getFieldValues().get(0);
            filename = status.get(0);
            pos = Long.parseLong(status.get(1));
            log.info("get binlog name is {}, pos is {}", filename, pos);

            logPosition = new LogPosition(filename, pos, dataSource.getConnection().getThreadId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void settingBeforeDump() {
        // set wait_timeout=28800 默认 28800 8小时 连接超时
        // set net_write_timeout=1800 用于控制MySQL服务器等待客户端发出网络数据时的超时时间
        // set @slave_uuid=uuid()
        // SET @master_heartbeat_period=000
    }

    /**
     * 获取 binlog 校验和
     *
     * @return
     */
    private ChecksumEnum fetchBinlogChecksum() {
        ResultSetPacket result = query("select @@global.binlog_checksum");
        List<List<String>> binlogChecksum = result.getFieldValues();
        if (binlogChecksum.isEmpty()) {
            return ChecksumEnum.NONE;
        }
        return ChecksumEnum.valueOf(binlogChecksum.get(0).get(0).toUpperCase());
    }

    /**
     * 获取 gtid，默认是没有配置的.
     *
     * @return
     */
    private String fetchGtid() {
        ResultSetPacket result = query("show global variables like 'gtid_purged'");
        if (result.getFieldValues().isEmpty()) {
            return "";
        }
        return result.getFieldValues().get(0).get(1);
    }

    private void confirmSupportOfChecksum(ChecksumEnum checksum) {
        update("set @master_binlog_checksum= @@global.binlog_checksum");
    }

    private void setHearBeat() {
        if (Objects.isNull(timer)) {
            timer = new ScheduledThreadPoolExecutor(1, threadFactory);
        }
        timer.scheduleAtFixedRate(heartBeatTimerTask, DETECTING_INTERVAL_IN_SECONDS, DETECTING_INTERVAL_IN_SECONDS, TimeUnit.SECONDS);
    }

    private void binlogDump() throws Exception {
        BinlogDumpPacket packet = new BinlogDumpPacket();
        packet.setFilename(filename);
        packet.setPos(pos);
        packet.setSlaveServerId(4);
        packet.setPacketId((byte) 0);
        packet.setBinlogFlags(packet.getBinlogFlags() | BinlogDumpPacket.BINLOG_SEND_ANNOTATE_ROWS_EVENT);
        packet.write(dataSource.getConnection().getChannel());

        while (running) {
            BinaryPacket receive = receive();
            byte flag = receive.getBody()[0];
            switch (flag) {
                case ErrorPacket.FIELD_COUNT:
                    ErrorPacketException.handleFailure(receive);
                    break;
                case EofPacket.FIELD_COUNT:
                    return;
                default:
                    Event event = readEvent(receive);
                    EventHeaderV4 headerV4 = (EventHeaderV4) event.getHeader();
                    if (Objects.nonNull(headerV4)) {
                        logPosition.setPosition(headerV4.getNextLogPos());
                    }
                    break;
            }
        }
    }

    private ResultSetPacket query(String sql) {
        try {
            return getQueryExecutor().query(sql);
        } catch (Exception e) {
            log.error(sql);
            throw new RuntimeException(e.getMessage());
        }
    }

    private OkPacket update(String sql) {
        try {
            UpdateExecutor updateExecutor = dataSource.getConnection().getUpdateExecutor();
            return updateExecutor.update(sql);
        } catch (Exception e) {
            log.error(sql);
            throw new RuntimeException(e.getMessage());
        }
    }

    private QueryExecutor getQueryExecutor() throws IOException {
        Assert.isNull(dataSource, "dataSource is null.");

        return dataSource.getConnection().getQueryExecutor();
    }

    private BinaryPacket receive() throws Exception {
        return getQueryExecutor().receive();
    }

    private Event readEvent(BinaryPacket bin) {
        Event event = new Event();
        event.read(bin);
        lastHearBeat = System.currentTimeMillis();
        return event;
    }

    /**
     * 监听jvm关闭时执行的一些操作，实现优雅停机
     */
    private void registerListener() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("shutdown");
            stop();
        }));
    }
}
