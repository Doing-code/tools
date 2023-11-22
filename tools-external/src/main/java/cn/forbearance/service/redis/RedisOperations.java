package cn.forbearance.service.redis;

import cn.forbearance.domain.Cursor;
import cn.forbearance.domain.RedisServer;
import cn.forbearance.utils.enums.DataType;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * key（string、list、hash、zset、set ...） 增删改查
 *
 * @author cristina
 */
public interface RedisOperations<K, V> {

    /**
     * .
     *
     * @param action
     * @param <T>
     * @return
     */
    <T> T execute(RedisCallback<T> action, RedisServer server);

    // 通用命令

    /**
     * 判断 key 的类型
     *
     * @param key    键
     * @param server 用于动态连接Redis
     * @return
     */
    DataType type(K key, RedisServer server);

    /**
     * 指定缓存失效时间
     *
     * @param key     键
     * @param timeout 失效时间
     * @param unit    时间单位
     * @param server  用于动态连接Redis
     * @return
     */
    Boolean expire(K key, long timeout, TimeUnit unit, RedisServer server);

    /**
     * 根据 key 获取过期时间
     *
     * @param key      键
     * @param timeUnit 返回的时间单位
     * @param server   用于动态连接Redis
     * @return
     */
    Long getExpire(K key, final TimeUnit timeUnit, RedisServer server);

    /**
     * 删除一个 key
     *
     * @param key    键
     * @param server 用于动态连接Redis
     * @return
     */
    Boolean delete(K key, RedisServer server);

    /**
     * 删除多个 key
     *
     * @param keys   键
     * @param server 用于动态连接Redis
     * @return
     */
    Long delete(Collection<K> keys, RedisServer server);

    /**
     * 查询匹配 key, 默认查询所有 key
     *
     * @param pattern
     * @param server  用于动态连接Redis
     * @return
     */
    Cursor<V> scan(String pattern, RedisServer server);

    /**
     * 分页查询 key
     *
     * @param pattern
     * @param position
     * @param count
     * @param server   用于动态连接Redis
     * @return
     */
    Cursor<V> scan(String pattern, int position, int count, RedisServer server);

    // string

    // hash

    /**
     * HashGet
     *
     * @param key     key
     * @param hashKey 项
     * @param server  用于动态连接Redis
     * @return
     */
    Object hGet(String key, Object hashKey, RedisServer server);

    /**
     * 获取 key 对应的所有键值
     *
     * @param key    键
     * @param server 用于动态连接Redis
     * @return
     */
    Map<Object, Object> entries(String key, RedisServer server);

    /**
     * HashSet
     *
     * @param key    键
     * @param map    对应多个键值
     * @param server 用于动态连接Redis
     */
    void hmSet(String key, Map<String, Object> map, RedisServer server);

    /**
     * HashSet 并设置有效时间
     *
     * @param key    键
     * @param map    对应多个键值
     * @param time   失效时间
     * @param server 用于动态连接Redis
     * @return
     */
    boolean hmSet(String key, Map<String, Object> map, long time, RedisServer server);

    /**
     * 向一张 hash 表中放入数据, 如果不存在将创建
     *
     * @param key    键
     * @param item   项
     * @param value  值
     * @param server 用于动态连接Redis
     * @return
     */
    boolean hSet(String key, String item, Object value, RedisServer server);

    /**
     * 删除 hash 表中的值
     *
     * @param key    键
     * @param server 用于动态连接Redis
     * @param item   项，可以传入多个
     * @return
     */
    Long hDel(String key, RedisServer server, Object... item);

    /**
     * 判断 hash 表中是否有该项的值
     *
     * @param key     键
     * @param hashKey 项
     * @param server  用于动态连接Redis
     * @return
     */
    Boolean hasKey(String key, Object hashKey, RedisServer server);

    // list

    /**
     * 获取 list 缓存的内容
     *
     * @param key    键
     * @param start  开始
     * @param end    结束 （0 到 -1 代表所有值）
     * @param server 用于动态连接Redis
     * @return
     */
    List<V> range(String key, long start, long end, RedisServer server);

    /**
     * 获取 list 缓存的长度
     *
     * @param key    键
     * @param server 用于动态连接Redis
     * @return
     */
    Long lGetListSize(String key, RedisServer server);

    /**
     * 通过索引 获取 list 中的值
     *
     * @param key    键
     * @param index  索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @param server 用于动态连接Redis
     * @return
     */
    Object lGetIndex(String key, long index, RedisServer server);

    /**
     * 将 list 放入缓存
     *
     * @param key    键
     * @param value  值
     * @param server 用于动态连接Redis
     * @return
     */
    Long rightPush(String key, Object value, RedisServer server);

    /**
     * 将 list 放入缓存
     *
     * @param key    键
     * @param values 值
     * @param server 用于动态连接Redis
     * @return
     */
    Long rightPushAll(K key, Collection<Object> values, RedisServer server);

    /**
     * 根据索引修改 list 中的某条数据
     *
     * @param key    键
     * @param index  索引
     * @param value  值
     * @param server 用于动态连接Redis
     */
    void lUpdateIndex(String key, long index, Object value, RedisServer server);

    /**
     * 移除 count 个值为value
     *
     * @param key    键
     * @param count  移除的数量
     * @param value  值
     * @param server 用于动态连接Redis
     * @return
     */
    Long lRemove(String key, long count, Object value, RedisServer server);

    // set

    /**
     * 根据 key 获取 Set 中的所有值
     *
     * @param key    键
     * @param server 用于动态连接Redis
     * @return
     */
    Set<Object> members(String key, RedisServer server);

    /**
     * 根据value从一个set中查询, 是否存在
     *
     * @param key    键
     * @param value  值
     * @param server 用于动态连接Redis
     * @return
     */
    Boolean isMember(String key, Object value, RedisServer server);

    /**
     * 将数据放入 set 缓存
     *
     * @param key    键
     * @param server 用于动态连接Redis
     * @param values 值，可以是多个
     * @return
     */
    Long add(String key, RedisServer server, Object... values);

    /**
     * 获取 set 缓存的长度
     *
     * @param key    键
     * @param server 用于动态连接Redis
     * @return
     */
    Long size(String key, RedisServer server);

    /**
     * 将 values 从 set 中移除
     *
     * @param key    键
     * @param values 值，可以有多个
     * @param server 用于动态连接Redis
     * @return
     */
    Long remove(String key, RedisServer server, Object... values);

    // zset

    /**
     * string
     *
     * @return
     */
    ValueOperations<K, V> opsForValue();

}
