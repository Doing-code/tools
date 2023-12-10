package cn.forbearance.mysql.protocol.packets;

import cn.forbearance.mysql.protocol.socket.SocketChannel;
import cn.forbearance.utils.StreamUtil;

import java.io.IOException;

/**
 * BinaryPacket 通过存储消息头，序号和字节数组的消息体来存储整个报文. read
 *
 * @author cristina
 */
public class BinaryPacket extends Packet {

    private byte[] body;

    public byte[] getBody() {
        return body;
    }

    public void read(SocketChannel channel) throws IOException {
        packetLength = StreamUtil.readUb3(channel);
        packetId = StreamUtil.read(channel);
        byte[] bte = new byte[packetLength];
        StreamUtil.read(channel, bte, 0, packetLength);
        body = bte;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public byte getPacketId() {
        return this.packetId;
    }
}
