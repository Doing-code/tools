package cn.forbearance.service;

import cn.forbearance.domain.Cursor;
import cn.forbearance.utils.AggregatedResponse;
import cn.forbearance.utils.connection.NettyClientPool;
import cn.forbearance.utils.connection.RedisCommandHandler;
import io.netty.channel.Channel;
import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.util.concurrent.DefaultPromise;

import java.net.InetSocketAddress;
import java.util.Objects;

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

    @Override
    public Cursor<Object> scan(String pattern) {
        return scan(pattern, 0, Integer.MAX_VALUE);
    }

    @Override
    public Cursor<Object> scan(String pattern, int position, int count) {
        try {
            // scan 0 match key count 100
            StringBuilder command = new StringBuilder();
            command.append("scan");
            if (Objects.nonNull(pattern) && pattern.length() > 0) {
                command.append(" match ").append(pattern);
            }
            command.append(" ").append(position);
            command.append(" count ").append(count);
            Object msg = doExec(command.toString()).get();

            return AggregatedResponse.parseScan((ArrayRedisMessage) msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object get(Object key) {
        try {
            return doExec("get " + key).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void set(Object key, Object value) {
        Channel channel = getChannel();
        DefaultPromise<Object> promise = new DefaultPromise<Object>(channel.eventLoop());
        RedisCommandHandler handler = channel.pipeline().get(RedisCommandHandler.class);
        handler.sendCommand(channel, "set " + key + value, promise);
    }

    protected DefaultPromise<Object> doExec(String command) {
        Channel channel = getChannel();
        DefaultPromise<Object> promise = new DefaultPromise<Object>(channel.eventLoop());
        RedisCommandHandler handler = channel.pipeline().get(RedisCommandHandler.class);
        handler.sendCommand(channel, command, promise);

        return promise;
    }
}
