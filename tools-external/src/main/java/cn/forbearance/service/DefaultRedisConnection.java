package cn.forbearance.service;

import cn.forbearance.utils.connection.NettyClientPool;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;

/**
 * @author cristina
 */
public class DefaultRedisConnection implements RedisConnection {

    public DefaultRedisConnection() {
    }

    @Override
    public Channel getChannel() {
        return NettyClientPool.getInstance().getConnection(new InetSocketAddress("localhost", 6379));
    }
}
