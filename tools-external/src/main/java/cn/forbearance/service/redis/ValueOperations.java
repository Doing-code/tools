package cn.forbearance.service.redis;

import cn.forbearance.domain.RedisInfo;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author cristina
 */
public interface ValueOperations<K, V> {

    /**
     * 获取 value
     *
     * @param key    键
     * @param server 指定 server 连接
     * @return
     */
    V get(Object key, RedisInfo server);

    /**
     * 批量获取
     *
     * @param keys   键
     * @param server 指定 server 连接
     * @return
     */
    List<V> multiGet(Collection<K> keys, RedisInfo server);

    /**
     * 添加 key
     *
     * @param key    键
     * @param value  值
     * @param server 指定 server 连接
     */
    void set(K key, V value, RedisInfo server);

    /**
     * 添加 key，并设置有效时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 失效时间
     * @param unit    时间单位
     * @param server  指定 server 连接
     */
    void set(K key, V value, long timeout, TimeUnit unit, RedisInfo server);

    /**
     * 删除 key
     *
     * @param key
     * @param server
     */
    void delete(K key, RedisInfo server);
}
