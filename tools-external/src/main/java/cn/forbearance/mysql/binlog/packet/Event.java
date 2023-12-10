package cn.forbearance.mysql.binlog.packet;

import cn.forbearance.mysql.binlog.DecoderFactory;
import cn.forbearance.mysql.binlog.codec.EventDecoder;
import cn.forbearance.mysql.protocol.packets.BinaryPacket;
import cn.forbearance.mysql.protocol.packets.Message;

import java.util.Objects;

/**
 * @author cristina
 */
public class Event {

    private EventHeader header;

    private EventPacket packet;

    public Event() {
    }

    public Event(EventHeader header, EventPacket packet) {
        this.header = header;
        this.packet = packet;
    }

    public void read(BinaryPacket bin) {
        Message msg = new Message(bin.getBody());
        msg.read();
        if (Objects.isNull(header))
            header = new EventHeaderV4();
        header.read(msg);
        EventDecoder<?> decoder = DecoderFactory.getDecoder(header.eventType);
        if (Objects.isNull(decoder)) {
            throw new RuntimeException(String.format("cannot find this event type %s", header.eventType));
        }

        // master data decode.
        packet = decoder.decode(msg.readBytes());
    }

    public EventHeader getHeader() {
        return header;
    }

    public void setHeader(EventHeader header) {
        this.header = header;
    }

    public EventPacket getPacket() {
        return packet;
    }

    public void setPacket(EventPacket packet) {
        this.packet = packet;
    }
}
