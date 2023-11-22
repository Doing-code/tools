package cn.forbearance.service.redis;

import cn.forbearance.domain.Cursor;
import cn.forbearance.domain.RedisServer;
import cn.forbearance.utils.AggregatedResponse;
import cn.forbearance.utils.connection.NettyClientPool;
import cn.forbearance.utils.connection.RedisCommandHandler;
import cn.hutool.core.util.StrUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.util.concurrent.DefaultPromise;

import java.util.concurrent.TimeUnit;

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
            command.append("scan ").append(position);

            if (StrUtil.isNotEmpty(pattern)) {
                command.append(" match ").append(pattern);
            }

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

    @Override
    public void setEx(Object key, Object value, long timeout, TimeUnit unit) {
        getHandler().sendCommand(channel(), "set " + key + " " + value + " ex " + timeout, getPromise());
        try {
            getPromise().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Object key) {
        getHandler().sendCommand(channel(), "del " + key, getPromise());
        try {
            getPromise().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
