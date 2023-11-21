package cn.forbearance.service;

import cn.forbearance.domain.Cursor;
import io.netty.channel.Channel;

/**
 * @author cristina
 */
public interface RedisConnection {

    /**
     * 获取通信 channel
     *
     * @return
     */
    Channel getChannel();

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
     * @param pattern
     * @param position
     * @param count
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
