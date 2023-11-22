package cn.forbearance.service;

import cn.forbearance.domain.Cursor;
import cn.forbearance.domain.RedisServer;
import cn.forbearance.utils.RedisConnectionUtils;
import cn.forbearance.utils.connection.RedisCommandHandler;
import cn.forbearance.utils.enums.DataType;
import io.netty.channel.Channel;
import io.netty.util.concurrent.DefaultPromise;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    public <T> T execute(RedisCallback<T> action, RedisServer server) {
        return execute(action, server, true);
    }

    @Override
    public DataType type(K key, RedisServer server) {
        return null;
    }

    @Override
    public Boolean expire(K key, long timeout, TimeUnit unit, RedisServer server) {
        return null;
    }

    @Override
    public Long getExpire(K key, TimeUnit timeUnit, RedisServer server) {
        return null;
    }

    @Override
    public Boolean delete(K key, RedisServer server) {
        return null;
    }

    @Override
    public Long delete(Collection<K> keys, RedisServer server) {
        return null;
    }

    @Override
    public Cursor<V> scan(String pattern, RedisServer server) {
        return scan(pattern, 0, Integer.MAX_VALUE, server);
    }

    @Override
    public Cursor<V> scan(String pattern, int position, int count, RedisServer server) {
        return (Cursor<V>) execute(connection -> connection.scan(pattern, position, count), server);
    }

    @Override
    public Object hGet(String key, Object hashKey, RedisServer server) {
        return null;
    }

    @Override
    public Map<Object, Object> entries(String key, RedisServer server) {
        return null;
    }

    @Override
    public void hmSet(String key, Map<String, Object> map, RedisServer server) {

    }

    @Override
    public boolean hmSet(String key, Map<String, Object> map, long time, RedisServer server) {
        return false;
    }

    @Override
    public boolean hSet(String key, String item, Object value, RedisServer server) {
        return false;
    }

    @Override
    public Long hDel(String key, RedisServer server, Object... item) {
        return null;
    }

    @Override
    public Boolean hasKey(String key, Object hashKey, RedisServer server) {
        return null;
    }

    @Override
    public List<V> range(String key, long start, long end, RedisServer server) {
        return null;
    }

    @Override
    public Long lGetListSize(String key, RedisServer server) {
        return null;
    }

    @Override
    public Object lGetIndex(String key, long index, RedisServer server) {
        return null;
    }

    @Override
    public Long rightPush(String key, Object value, RedisServer server) {
        return null;
    }

    @Override
    public Long rightPushAll(K key, Collection<Object> values, RedisServer server) {
        return null;
    }

    @Override
    public void lUpdateIndex(String key, long index, Object value, RedisServer server) {

    }

    @Override
    public Long lRemove(String key, long count, Object value, RedisServer server) {
        return null;
    }

    @Override
    public Set<Object> members(String key, RedisServer server) {
        return null;
    }

    @Override
    public Boolean isMember(String key, Object value, RedisServer server) {
        return null;
    }

    @Override
    public Long add(String key, RedisServer server, Object... values) {
        return null;
    }

    @Override
    public Long size(String key, RedisServer server) {
        return null;
    }

    @Override
    public Long remove(String key, RedisServer server, Object... values) {
        return null;
    }

    @Override
    public ValueOperations<K, V> opsForValue() {
        return valueOps;
    }

    /*public <T> T execute(RedisCallback<T> action, RedisServer server) {
        return execute(action, server, true);
    }*/

    public <T> T execute(RedisCallback<T> action, RedisServer server, boolean pipeline) {
        Assert.notNull(action, "Callback object must not be null");

        // 获取连接
        RedisConnectionFactory factory = new DefaultRedisConnectionFactory();
        RedisConnection conn = RedisConnectionUtils.getConnection(factory);

        try {

            RedisConnection connToUse = preProcessConnection(conn, server);

            // open pipeline

            T result = action.doInRedis(connToUse);

            // close pipeline

            return postProcessResult(result, connToUse);
        } finally {
            RedisConnectionUtils.releaseConnection(conn, factory, server);
        }
    }

    protected RedisConnection preProcessConnection(RedisConnection connection, RedisServer server) {
        connection.setRedisServer(server);
        Channel channel = connection.getConnection();
        connection.setPromise(new DefaultPromise<Object>(channel.eventLoop()));
        connection.setHandler(channel.pipeline().get(RedisCommandHandler.class));

        return connection;
    }

    protected <T> T postProcessResult(@Nullable T result, RedisConnection conn) {
        return result;
    }
}
