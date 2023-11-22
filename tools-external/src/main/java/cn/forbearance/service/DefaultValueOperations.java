package cn.forbearance.service;

import cn.forbearance.domain.RedisServer;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author cristina
 */
public class DefaultValueOperations<K, V> extends AbstractOperations<K, V> implements ValueOperations<K, V> {

    /**
     * TODO 需要解耦
     */
    public DefaultValueOperations(RedisTemplate<K, V> template) {
        super(template);
    }

    @Override
    public V get(Object key, RedisServer server) {

        return execute(new CommonOperationsRedisCallback(key) {
            @Override
            protected Object inRedis(RedisConnection connection) {
                return connection.get(key);
            }
        }, server);
    }

    @Override
    public List<V> multiGet(Collection<K> keys, RedisServer server) {
        return null;
    }

    @Override
    public void set(K key, V value, RedisServer server) {
        execute(new CommonOperationsRedisCallback(key) {
            @Override
            protected Object inRedis(RedisConnection connection) {
                connection.set(key, value);
                return null;
            }
        }, server);
    }

    @Override
    public void set(K key, V value, long timeout, TimeUnit unit, RedisServer server) {

    }
}
