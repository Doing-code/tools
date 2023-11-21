package cn.forbearance.service;

import cn.forbearance.domain.Cursor;
import cn.forbearance.utils.RedisConnectionUtils;
import cn.forbearance.utils.SyncResponseUtil;
import cn.forbearance.utils.enums.DataType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Promise;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @author cristina
 */
public class RedisTemplate<K, V> implements RedisOperations<K, V> {

    private final ValueOperations<K, V> valueOps = new DefaultValueOperations<>(this);

    /**
     * TODO 需要将 Netty 连接优化为连接池
     */
    public RedisTemplate() {
    }

    @Override
    public <T> T execute(RedisCallback<T> action) {
        return execute(action, true);
    }

    @Override
    public DataType type(K key) {
        return null;
    }

    @Override
    public Boolean expire(K key, long timeout, TimeUnit unit) {
        return null;
    }

    @Override
    public Long getExpire(K key, TimeUnit timeUnit) {
        return null;
    }

    @Override
    public Boolean delete(K key) {
        return null;
    }

    @Override
    public Long delete(Collection<K> keys) {
        return null;
    }

    @Override
    public Cursor<V> scan(String pattern) {
        return (Cursor<V>) execute(connection -> connection.scan(pattern));
    }

    @Override
    public Object hGet(String key, Object hashKey) {
        return null;
    }

    @Override
    public Map<Object, Object> entries(String key) {
        return null;
    }

    @Override
    public void hmSet(String key, Map<String, Object> map) {

    }

    @Override
    public boolean hmSet(String key, Map<String, Object> map, long time) {
        return false;
    }

    @Override
    public boolean hSet(String key, String item, Object value) {
        return false;
    }

    @Override
    public Long hDel(String key, Object... item) {
        return null;
    }

    @Override
    public Boolean hasKey(String key, Object hashKey) {
        return null;
    }

    @Override
    public List<V> range(String key, long start, long end) {
        return null;
    }

    @Override
    public Long lGetListSize(String key) {
        return null;
    }

    @Override
    public Object lGetIndex(String key, long index) {
        return null;
    }

    @Override
    public Long rightPush(String key, Object value) {
        return null;
    }

    @Override
    public Long rightPushAll(K key, Collection<Object> values) {
        return null;
    }

    @Override
    public void lUpdateIndex(String key, long index, Object value) {

    }

    @Override
    public Long lRemove(String key, long count, Object value) {
        return null;
    }

    @Override
    public Set<Object> members(String key) {
        return null;
    }

    @Override
    public Boolean isMember(String key, Object value) {
        return null;
    }

    @Override
    public Long add(String key, Object... values) {
        return null;
    }

    @Override
    public Long size(String key) {
        return null;
    }

    @Override
    public Long remove(String key, Object... values) {
        return null;
    }

    @Override
    public ValueOperations<K, V> opsForValue() {
        return valueOps;
    }

    public <T> T execute(RedisCallback<T> action, boolean exposeConnection) {
        return execute(action, exposeConnection, false);
    }

    public <T> T execute(RedisCallback<T> action, boolean exposeConnection, boolean pipeline) {
        Assert.notNull(action, "Callback object must not be null");

        // 获取连接
        RedisConnectionFactory factory = new DefaultRedisConnectionFactory();
        RedisConnection conn = RedisConnectionUtils.getConnection(factory);

        try {

            RedisConnection connToUse = preProcessConnection(conn);

            // open pipeline

            T result = action.doInRedis(connToUse);

            // close pipeline

            return postProcessResult(result, connToUse);
        } finally {
//            RedisConnectionUtils.releaseConnection(conn, factory, enableTransactionSupport);
        }
    }

    protected RedisConnection preProcessConnection(RedisConnection connection) {
        return connection;
    }

    protected <T> T postProcessResult(@Nullable T result, RedisConnection conn) {
        return result;
    }
}
