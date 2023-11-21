package cn.forbearance.service;

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
     * @param key 键
     * @return
     */
    V get(Object key);

    /**
     * 批量获取
     *
     * @param keys 键
     * @return
     */
    List<V> multiGet(Collection<K> keys);

    /**
     * 添加 key
     *
     * @param key   键
     * @param value 值
     */
    void set(K key, V value);

    /**
     * 添加 key，并设置有效时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 失效时间
     * @param unit    时间单位
     */
    void set(K key, V value, long timeout, TimeUnit unit);
}
