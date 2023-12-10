package cn.forbearance.mysql.protocol.packets.request;

import cn.forbearance.mysql.protocol.packets.Packet;
import cn.forbearance.mysql.protocol.socket.SocketChannel;
import cn.forbearance.utils.StreamUtil;

import java.io.IOException;

/**
 * @author cristina
 */
public class Auth323Packet extends Packet {

    private byte[] seed;

    public void write(SocketChannel sc) throws IOException {
        StreamUtil.writeUb3(sc, getPacketLength());
        StreamUtil.write(sc, packetId);
        if (seed == null) {
            StreamUtil.write(sc, (byte) 0);
        } else {
            StreamUtil.writeWithNull(sc, seed);
        }
    }

    private int getPacketLength() {
        return seed == null ? 1 : seed.length + 1;
    }

    public void setPacketId(byte packetId) {
        this.packetId = packetId;
    }

    public void setSeed(byte[] seed) {
        this.seed = seed;
    }
}
