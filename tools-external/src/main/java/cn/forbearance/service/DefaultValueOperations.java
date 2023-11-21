package cn.forbearance.service;

import cn.forbearance.domain.Cursor;
import cn.forbearance.utils.AggregatedResponse;
import cn.forbearance.utils.SyncResponseUtil;
import cn.forbearance.utils.connection.RedisCommandHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelProgressivePromise;
import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import io.netty.util.concurrent.DefaultPromise;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingDeque;
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
    public V get(Object key) {

        return execute(new CommonOperationsRedisCallback(key) {
            @Override
            protected Object inRedis(RedisConnection connection) {
                return connection.get(key);
            }
        });
    }

    @Override
    public List<V> multiGet(Collection<K> keys) {
        return null;
    }

    @Override
    public void set(K key, V value) {
        execute(new CommonOperationsRedisCallback(key) {
            @Override
            protected Object inRedis(RedisConnection connection) {
                connection.set(key, value);
                return null;
            }
        });
    }

    @Override
    public void set(K key, V value, long timeout, TimeUnit unit) {

    }
}
