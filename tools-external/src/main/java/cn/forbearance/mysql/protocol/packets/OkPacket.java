package cn.forbearance.mysql.protocol.packets;

import cn.forbearance.utils.ByteUtil;

import java.util.Arrays;

/**
 * 客户端的命令执行正确时，服务器会返回OK响应报文.
 *
 * @author cristina
 */
public class OkPacket extends Packet {

    public static final byte FIELD_COUNT = 0x00;

    /**
     * 字段数量
     */
    private byte fieldCount = FIELD_COUNT;

    /**
     * 受影响的行
     */
    private long affectedRows;
    private long insertId;
    private int serverStatus;
    private int warningCount;
    private byte[] message;

    public void read(BinaryPacket bin) {
        Message msg = new Message(bin.getBody());
        this.fieldCount = msg.read();
        this.affectedRows = ByteUtil.byte2Long(msg.readBytesWithLength());
        this.insertId = ByteUtil.byte2Long(msg.readBytesWithLength());
        this.serverStatus = msg.readUb2();
        this.warningCount = msg.readUb2();
        this.message = msg.readBytes();
    }

    public static byte getFieldCount() {
        return FIELD_COUNT;
    }

    public void setFieldCount(byte fieldCount) {
        this.fieldCount = fieldCount;
    }

    public long getAffectedRows() {
        return affectedRows;
    }

    public void setAffectedRows(long affectedRows) {
        this.affectedRows = affectedRows;
    }

    public long getInsertId() {
        return insertId;
    }

    public void setInsertId(long insertId) {
        this.insertId = insertId;
    }

    public int getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(int serverStatus) {
        this.serverStatus = serverStatus;
    }

    public int getWarningCount() {
        return warningCount;
    }

    public void setWarningCount(int warningCount) {
        this.warningCount = warningCount;
    }

    public byte[] getMessage() {
        return message;
    }

    public void setMessage(byte[] message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "OkPacket{" +
                "fieldCount=" + fieldCount +
                ", affectedRows=" + affectedRows +
                ", insertId=" + insertId +
                ", serverStatus=" + serverStatus +
                ", warningCount=" + warningCount +
                ", message=" + Arrays.toString(message) +
                '}';
    }
}
