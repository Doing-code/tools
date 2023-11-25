package cn.forbearance.service.redis;

import cn.forbearance.domain.RedisInfo;

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
    public V get(Object key, RedisInfo server) {

        return execute(new CommonOperationsRedisCallback(key) {
            @Override
            protected Object inRedis(RedisConnection connection) {
                return connection.get(key);
            }
        }, server);
    }

    @Override
    public List<V> multiGet(Collection<K> keys, RedisInfo server) {
        return null;
    }

    @Override
    public void set(K key, V value, RedisInfo server) {
        execute(new CommonOperationsRedisCallback(key) {
            @Override
            protected Object inRedis(RedisConnection connection) {
                connection.set(key, value);
                return null;
            }
        }, server);
    }

    @Override
    public void set(K key, V value, long timeout, TimeUnit unit, RedisInfo server) {
        execute(new CommonOperationsRedisCallback(key) {
            @Override
            protected Object inRedis(RedisConnection connection) {
                connection.setEx(key, value, timeout, unit);
                return null;
            }
        }, server);
    }

    @Override
    public void delete(K key, RedisInfo server) {
        execute(new CommonOperationsRedisCallback(key) {
            @Override
            protected Object inRedis(RedisConnection connection) {
                connection.delete(key);
                return null;
            }
        }, server);
    }
}
