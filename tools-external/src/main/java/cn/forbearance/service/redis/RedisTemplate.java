package cn.forbearance.service.redis;

import cn.forbearance.domain.Cursor;
import cn.forbearance.domain.RedisInfo;
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
    public <T> T execute(RedisCallback<T> action, RedisInfo server) {
        return execute(action, server, true);
    }

    @Override
    public DataType type(K key, RedisInfo server) {
        return null;
    }

    @Override
    public Boolean expire(K key, long timeout, TimeUnit unit, RedisInfo server) {
        return null;
    }

    @Override
    public Long getExpire(K key, TimeUnit timeUnit, RedisInfo server) {
        return null;
    }

    @Override
    public Boolean delete(K key, RedisInfo server) {
        return null;
    }

    @Override
    public Long delete(Collection<K> keys, RedisInfo server) {
        return null;
    }

    @Override
    public Cursor<V> scan(String pattern, RedisInfo server) {
        return scan(pattern, 0, Integer.MAX_VALUE, server);
    }

    @Override
    public Cursor<V> scan(String pattern, int position, int count, RedisInfo server) {
        return (Cursor<V>) execute(connection -> connection.scan(pattern, position, count), server);
    }

    @Override
    public Object hGet(String key, Object hashKey, RedisInfo server) {
        return null;
    }

    @Override
    public Map<Object, Object> entries(String key, RedisInfo server) {
        return null;
    }

    @Override
    public void hmSet(String key, Map<String, Object> map, RedisInfo server) {

    }

    @Override
    public boolean hmSet(String key, Map<String, Object> map, long time, RedisInfo server) {
        return false;
    }

    @Override
    public boolean hSet(String key, String item, Object value, RedisInfo server) {
        return false;
    }

    @Override
    public Long hDel(String key, RedisInfo server, Object... item) {
        return null;
    }

    @Override
    public Boolean hasKey(String key, Object hashKey, RedisInfo server) {
        return null;
    }

    @Override
    public List<V> range(String key, long start, long end, RedisInfo server) {
        return null;
    }

    @Override
    public Long lGetListSize(String key, RedisInfo server) {
        return null;
    }

    @Override
    public Object lGetIndex(String key, long index, RedisInfo server) {
        return null;
    }

    @Override
    public Long rightPush(String key, Object value, RedisInfo server) {
        return null;
    }

    @Override
    public Long rightPushAll(K key, Collection<Object> values, RedisInfo server) {
        return null;
    }

    @Override
    public void lUpdateIndex(String key, long index, Object value, RedisInfo server) {

    }

    @Override
    public Long lRemove(String key, long count, Object value, RedisInfo server) {
        return null;
    }

    @Override
    public Set<Object> members(String key, RedisInfo server) {
        return null;
    }

    @Override
    public Boolean isMember(String key, Object value, RedisInfo server) {
        return null;
    }

    @Override
    public Long add(String key, RedisInfo server, Object... values) {
        return null;
    }

    @Override
    public Long size(String key, RedisInfo server) {
        return null;
    }

    @Override
    public Long remove(String key, RedisInfo server, Object... values) {
        return null;
    }

    @Override
    public ValueOperations<K, V> opsForValue() {
        return valueOps;
    }

    /*public <T> T execute(RedisCallback<T> action, RedisServer server) {
        return execute(action, server, true);
    }*/

    public <T> T execute(RedisCallback<T> action, RedisInfo server, boolean pipeline) {
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

    protected RedisConnection preProcessConnection(RedisConnection connection, RedisInfo server) {
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
