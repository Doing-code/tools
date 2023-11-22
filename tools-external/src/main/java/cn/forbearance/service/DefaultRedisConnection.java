package cn.forbearance.service;

import cn.forbearance.domain.Cursor;
import cn.forbearance.domain.RedisServer;
import cn.forbearance.utils.AggregatedResponse;
import cn.forbearance.utils.connection.NettyClientPool;
import cn.forbearance.utils.connection.RedisCommandHandler;
import io.netty.channel.Channel;
import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.util.concurrent.DefaultPromise;

import java.util.Objects;

/**
 * @author cristina
 */
public class DefaultRedisConnection implements RedisConnection {

    private RedisServer server;
    private RedisCommandHandler handler;
    private DefaultPromise<Object> promise;
    private Channel channel;

    public DefaultRedisConnection() {
    }

    @Override
    public Channel getConnection() {
        channel = NettyClientPool.getInstance().getConnection(server.getIp(), server.getPort());
        return channel;
    }

    @Override
    public void release(Channel ch, String localhost, int port) {
        NettyClientPool.release(ch, localhost, port);
    }

    @Override
    public Channel channel() {
        return channel;
    }

    @Override
    public void setRedisServer(RedisServer server) {
        this.server = server;
    }

    @Override
    public void setHandler(RedisCommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void setPromise(DefaultPromise<Object> promise) {
        this.promise = promise;
    }

    @Override
    public RedisCommandHandler getHandler() {
        return handler;
    }

    @Override
    public DefaultPromise<Object> getPromise() {
        return promise;
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

            getHandler().sendCommand(channel(), command.toString(), getPromise());

            Object msg = getPromise().get();

            return AggregatedResponse.parseScan((ArrayRedisMessage) msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object get(Object key) {
        try {
            getHandler().sendCommand(channel(), "get " + key, getPromise());
            return getPromise().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void set(Object key, Object value) {
        getHandler().sendCommand(channel(), "set " + key + " " + value, getPromise());
        try {
            getPromise().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
