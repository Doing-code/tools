package cn.forbearance.mysql.protocol.packets;

import java.util.Arrays;

/**
 * 请求执行发生异常的时候返回的报文
 *
 * @author cristina
 */
public class ErrorPacket extends Packet {

    public static final byte FIELD_COUNT = (byte) 0xff;
    private static final byte SQLSTATE_MARKER = (byte) '#';
    private static final byte[] DEFAULT_SQLSTATE = "HY000".getBytes();

    private byte fieldCount = FIELD_COUNT;
    private int errno;
    private byte mark = SQLSTATE_MARKER;
    private byte[] sqlState = DEFAULT_SQLSTATE;
    private byte[] message;

    public void read(BinaryPacket data) {
        packetLength = data.packetLength;
        packetId = data.packetId;
        Message msg = new Message(data.getBody());
        fieldCount = msg.read();
        errno = msg.readUb2();
        if (msg.hasRemaining() && (msg.read(msg.getPosition()) == SQLSTATE_MARKER)) {
            msg.read();
            sqlState = msg.readBytes(5);
        }
        message = msg.readBytes();
    }

    public static byte getSqlstateMarker() {
        return SQLSTATE_MARKER;
    }

    public static byte[] getDefaultSqlstate() {
        return DEFAULT_SQLSTATE;
    }

    public byte getFieldCount() {
        return fieldCount;
    }

    public void setFieldCount(byte fieldCount) {
        this.fieldCount = fieldCount;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public byte getMark() {
        return mark;
    }

    public void setMark(byte mark) {
        this.mark = mark;
    }

    public byte[] getSqlState() {
        return sqlState;
    }

    public void setSqlState(byte[] sqlState) {
        this.sqlState = sqlState;
    }

    public byte[] getMessage() {
        return message;
    }

    public void setMessage(byte[] message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorPacket{" +
                "fieldCount=" + fieldCount +
                ", errno=" + errno +
                ", mark=" + mark +
                ", sqlState=" + Arrays.toString(sqlState) +
                ", message=" + Arrays.toString(message) +
                '}';
    }
}
