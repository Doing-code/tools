package cn.forbearance.service;

import cn.forbearance.utils.SyncResponseUtil;
import io.netty.channel.Channel;

import java.util.Collection;
import java.util.List;
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

        return execute(new ValueDeserializingRedisCallback(key) {
            @Override
            protected Object inRedis(RedisConnection connection) {
                Channel channel = connection.getChannel();
                // 业务逻辑
                channel.writeAndFlush("get " + key);
                LinkedBlockingDeque<Object> deque = SyncResponseUtil.SYNC_RESULT.get(channel.id());
                Object res = null;
                try {
                    res = deque.poll(3, TimeUnit.MINUTES);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return res;
            }
        });
    }

    @Override
    public List<V> multiGet(Collection<K> keys) {
        return null;
    }

    @Override
    public void set(K key, V value) {
        execute(new ValueDeserializingRedisCallback(key) {
            @Override
            protected Object inRedis(RedisConnection connection) {
                // 业务逻辑
                Channel channel = connection.getChannel();
                channel.writeAndFlush("set " + " " + key + " " + value);
                try {
                    SyncResponseUtil.SYNC_RESULT.get(channel.id()).poll(3, TimeUnit.MINUTES);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    @Override
    public void set(K key, V value, long timeout, TimeUnit unit) {

    }
}
