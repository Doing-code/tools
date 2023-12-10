package cn.forbearance.mysql.protocol.packets;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cristina
 */
public class ResultSetPacket extends Packet {

    private SocketAddress sourceAddress;
    private List<FieldPacket> fieldDescriptors = new ArrayList<FieldPacket>();
    private List<List<String>> fieldValues = new ArrayList<List<String>>();

    public void setFieldDescriptors(List<FieldPacket> fieldDescriptors) {
        this.fieldDescriptors = fieldDescriptors;
    }

    public List<FieldPacket> getFieldDescriptors() {
        return fieldDescriptors;
    }

    public void setFieldValues(List<List<String>> fieldValues) {
        this.fieldValues = fieldValues;
    }

    public List<List<String>> getFieldValues() {
        return fieldValues;
    }

    public void setSourceAddress(SocketAddress sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public SocketAddress getSourceAddress() {
        return sourceAddress;
    }
}
