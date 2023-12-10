package cn.forbearance.mysql.protocol.packets;

/**
 * @author cristina
 */
public class EofPacket extends Packet {

    public static final byte FIELD_COUNT = (byte) 0xfe;

    private byte fieldCount = FIELD_COUNT;
    private int warningCount;
    private int status = 2;

    public void read(BinaryPacket bin) {
        Message msg = new Message(bin.getBody());
        fieldCount = msg.read();
        warningCount = msg.readUb2();
        status = msg.readUb2();
    }

    public byte getFieldCount() {
        return fieldCount;
    }

    public void setFieldCount(byte fieldCount) {
        this.fieldCount = fieldCount;
    }

    public int getWarningCount() {
        return warningCount;
    }

    public void setWarningCount(int warningCount) {
        this.warningCount = warningCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
