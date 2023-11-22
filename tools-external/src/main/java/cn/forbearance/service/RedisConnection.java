package cn.forbearance.service;

import cn.forbearance.domain.Cursor;
import cn.forbearance.domain.RedisServer;
import cn.forbearance.utils.connection.RedisCommandHandler;
import io.netty.channel.Channel;
import io.netty.util.concurrent.DefaultPromise;

/**
 * @author cristina
 */
public interface RedisConnection {

    /**
     * 连接
     *
     * @return
     */
    Channel getConnection();

    /**
     * release
     *
     * @param ch
     * @param localhost
     * @param port
     */
    void release(Channel ch, String localhost, int port);

    /**
     * channel
     *
     * @return
     */
    Channel channel();

    /**
     * set
     *
     * @param server
     */
    void setRedisServer(RedisServer server);

    /**
     * set执行 handler
     *
     * @return
     */
    void setHandler(RedisCommandHandler handler);

    /**
     * 获取执行 handler
     *
     * @return
     */
    RedisCommandHandler getHandler();

    /**
     * set同步结果 DefaultPromise
     *
     * @return
     */
    void setPromise(DefaultPromise<Object> promise);

    /**
     * 获取同步结果 DefaultPromise
     *
     * @return
     */
    DefaultPromise<Object> getPromise();

    /**
     * 查询 key, 默认查询所有 key
     *
     * @param pattern
     * @return
     */
    Cursor<Object> scan(String pattern);

    /**
     * 分页查询 key
     *
     * @param pattern  匹配 key
     * @param position limit
     * @param count    count
     * @return
     */
    Cursor<Object> scan(String pattern, int position, int count);

    /**
     * get key
     *
     * @param key
     * @return
     */
    Object get(Object key);

    /**
     * set key
     *
     * @param key
     * @param value
     */
    void set(Object key, Object value);
}
