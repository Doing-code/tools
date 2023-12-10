package cn.forbearance.mysql.binlog.packet.data;

import cn.forbearance.mysql.binlog.ChecksumEnum;
import cn.forbearance.mysql.binlog.EventType;
import cn.forbearance.mysql.binlog.packet.EventPacket;
import cn.forbearance.mysql.protocol.packets.Message;
import lombok.ToString;

/**
 * 描述二进制日志的格式
 * <p>
 * see: https://dev.mysql.com/doc/dev/mysql-server/latest/classmysql_1_1binlog_1_1event_1_1Format__description__event.html
 *
 * @author cristina
 */
@ToString
public class FormatDescEventPacket implements EventPacket {

    private long created;
    private int binlogVersion;
    private String serverVersion;

    /**
     * 事件 header 的长度
     */
    private long eventHeaderLen;

    /**
     * 事件的固定数据部分的长度
     */
    private byte postHeaderLen;

    private ChecksumEnum checksumEnum;

    @Override
    public void read(byte[] data) {
        Message msg = new Message(data);
        binlogVersion = msg.readUb2();
        serverVersion = new String(msg.readBytes(50));
        created = msg.readUb4();
        eventHeaderLen = msg.read();
        // 跳过 FORMAT_DESCRIPTION_EVENT 的事件头
        msg.move((int) EventType.FORMAT_DESCRIPTION_EVENT - 1);
        postHeaderLen = msg.read();
        int checkSumBlockLength = data.length - postHeaderLen;

        ChecksumEnum checksumType;
        if (checkSumBlockLength > 0) {
            msg.move(msg.getLength() - msg.getPosition() - checkSumBlockLength);
            int read = msg.read();
            checksumType = ChecksumEnum.ordinal(read);
            this.checksumEnum = checksumType;
        }
    }
}
