package cn.forbearance.mysql.protocol.packets;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cristina
 */
public class RowDataPacket extends Packet {

    private final int fieldCount;
    private final List<byte[]> fieldValues;
    private final List<String> fieldValuesStrs;

    public RowDataPacket(int fieldCount) {
        this.fieldCount = fieldCount;
        this.fieldValues = new ArrayList<byte[]>(fieldCount);
        this.fieldValuesStrs = new ArrayList<>(fieldCount);
    }

    public void read(BinaryPacket bin) {
        packetLength = bin.packetLength;
        packetId = bin.packetId;
        Message mm = new Message(bin.getBody());

        do {
            byte[] bytes = mm.readBytesWithLength();
            fieldValues.add(bytes);
            fieldValuesStrs.add(new String(bytes, StandardCharsets.UTF_8));
        } while (mm.getPosition() < bin.getBody().length);
    }

    public int getFieldCount() {
        return fieldCount;
    }

    public List<byte[]> getFieldValues() {
        return fieldValues;
    }

    public List<String> getFieldValuesStrs() {
        return fieldValuesStrs;
    }
}
