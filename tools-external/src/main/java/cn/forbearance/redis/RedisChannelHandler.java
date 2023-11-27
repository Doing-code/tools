package cn.forbearance.redis;

import io.netty.channel.Channel;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.redis.RedisArrayAggregator;
import io.netty.handler.codec.redis.RedisBulkStringAggregator;
import io.netty.handler.codec.redis.RedisDecoder;
import io.netty.handler.codec.redis.RedisEncoder;

/**
 * @author cristina
 */
public class RedisChannelHandler implements ChannelPoolHandler {

    @Override
    public void channelReleased(Channel ch) throws Exception {
    }

    @Override
    public void channelAcquired(Channel ch) throws Exception {
    }

    @Override
    public void channelCreated(Channel ch) throws Exception {
        SocketChannel channel = (SocketChannel) ch;
        channel.config().setKeepAlive(true);
        channel.config().setTcpNoDelay(true);

        ch.pipeline().addLast(new RedisDecoder());
        ch.pipeline().addLast(new RedisBulkStringAggregator());
        ch.pipeline().addLast(new RedisArrayAggregator());
        ch.pipeline().addLast(new RedisEncoder());
        ch.pipeline().addLast(new RedisCommandHandler());
    }

}
