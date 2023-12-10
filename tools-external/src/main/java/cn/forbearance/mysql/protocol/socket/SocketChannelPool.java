package cn.forbearance.mysql.protocol.socket;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author cristina
 */
public abstract class SocketChannelPool {

    public static SocketChannel open(InetSocketAddress address) throws IOException {
        return BioSocketChannelPool.open(address);
    }
}
