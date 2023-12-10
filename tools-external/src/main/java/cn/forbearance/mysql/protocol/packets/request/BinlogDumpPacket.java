package cn.forbearance.mysql.protocol.packets.request;

import cn.forbearance.mysql.protocol.packets.Packet;
import cn.forbearance.mysql.protocol.socket.SocketChannel;
import cn.forbearance.utils.StreamUtil;

import java.io.IOException;
import java.util.Objects;

/**
 * @author cristina
 */
public class BinlogDumpPacket extends Packet {

    public static final int BINLOG_DUMP_NON_BLOCK = 1;
    public static final int BINLOG_SEND_ANNOTATE_ROWS_EVENT = 2;

    private byte command = COM_BINLOG_DUMP;

    /**
     * The position within the binary log to start from - binlog 起始位置
     */
    private long pos;

    /**
     * slave server id，与 master 不相同即可
     */
    private int slaveServerId;

    /* Additional parameters */

    /**
     * Flags (usually set to 0) - binlog 标志位
     */
    private int binlogFlags = 0;

    /**
     * binlog filename - 从指定的二进制日志文件开始复制
     */
    private String filename;

    public void write(SocketChannel sc) throws IOException {
        StreamUtil.writeUb3(sc, getPacketLength());
        StreamUtil.write(sc, packetId);
        StreamUtil.write(sc, command);

        StreamUtil.writeUb4(sc, pos);
        StreamUtil.writeUb2(sc, binlogFlags);
        StreamUtil.writeUb4(sc, slaveServerId);
        if (Objects.nonNull(filename))
            StreamUtil.writeWithNull(sc, filename.getBytes());
    }

    public byte getCommand() {
        return command;
    }

    public void setCommand(byte command) {
        this.command = command;
    }

    public long getPos() {
        return pos;
    }

    public void setPos(long pos) {
        this.pos = pos;
    }

    public int getBinlogFlags() {
        return binlogFlags;
    }

    public void setBinlogFlags(int binlogFlags) {
        this.binlogFlags = binlogFlags;
    }

    public int getSlaveServerId() {
        return slaveServerId;
    }

    public void setSlaveServerId(int slaveServerId) {
        this.slaveServerId = slaveServerId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    protected int getPacketLength() {
        return 1 + 4 + 2 + 4 + (filename == null ? 0 : filename.getBytes().length);
    }

    public void setPacketId(byte packetId) {
        this.packetId = packetId;
    }
}
