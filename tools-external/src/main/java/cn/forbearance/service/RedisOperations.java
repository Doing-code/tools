package cn.forbearance.service;

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

    // 通用命令

    /**
     * 判断 key 的类型
     *
     * @param key 键
     * @return
     */
    DataType type(K key);

    /**
     * 指定缓存失效时间
     *
     * @param key     键
     * @param timeout 失效时间
     * @param unit    时间单位
     * @return
     */
    Boolean expire(K key, long timeout, TimeUnit unit);

    /**
     * 根据 key 获取过期时间
     *
     * @param key      键
     * @param timeUnit 返回的时间单位
     * @return
     */
    Long getExpire(K key, final TimeUnit timeUnit);

    /**
     * 删除一个 key
     *
     * @param key 键
     * @return
     */
    Boolean delete(K key);

    /**
     * 删除多个 key
     *
     * @param keys 键
     * @return
     */
    Long delete(Collection<K> keys);

    /**
     * 获取 key
     *
     * @param pattern
     * @return
     */
    Collection<V> scan(String pattern);

    // string

    // hash

    /**
     * HashGet
     *
     * @param key     key
     * @param hashKey 项
     * @return
     */
    Object hGet(String key, Object hashKey);

    /**
     * 获取 key 对应的所有键值
     *
     * @param key 键
     * @return
     */
    Map<Object, Object> entries(String key);

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     */
    void hmSet(String key, Map<String, Object> map);

    /**
     * HashSet 并设置有效时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 失效时间
     * @return
     */
    boolean hmSet(String key, Map<String, Object> map, long time);

    /**
     * 向一张 hash 表中放入数据, 如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return
     */
    boolean hSet(String key, String item, Object value);

    /**
     * 删除 hash 表中的值
     *
     * @param key  键
     * @param item 项，可以传入多个
     * @return
     */
    Long hDel(String key, Object... item);

    /**
     * 判断 hash 表中是否有该项的值
     *
     * @param key     键
     * @param hashKey 项
     * @return
     */
    Boolean hasKey(String key, Object hashKey);

    // list

    /**
     * 获取 list 缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 （0 到 -1 代表所有值）
     * @return
     */
    List<V> range(String key, long start, long end);

    /**
     * 获取 list 缓存的长度
     *
     * @param key 键
     * @return
     */
    Long lGetListSize(String key);

    /**
     * 通过索引 获取 list 中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    Object lGetIndex(String key, long index);

    /**
     * 将 list 放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    Long rightPush(String key, Object value);

    /**
     * 将 list 放入缓存
     *
     * @param key    键
     * @param values 值
     * @return
     */
    Long rightPushAll(K key, Collection<Object> values);

    /**
     * 根据索引修改 list 中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     */
    void lUpdateIndex(String key, long index, Object value);

    /**
     * 移除 count 个值为value
     *
     * @param key   键
     * @param count 移除的数量
     * @param value 值
     * @return
     */
    Long lRemove(String key, long count, Object value);

    // set

    /**
     * 根据 key 获取 Set 中的所有值
     *
     * @param key 键
     * @return
     */
    Set<Object> members(String key);

    /**
     * 根据value从一个set中查询, 是否存在
     *
     * @param key   键
     * @param value 值
     * @return
     */
    Boolean isMember(String key, Object value);

    /**
     * 将数据放入 set 缓存
     *
     * @param key    键
     * @param values 值，可以是多个
     * @return
     */
    Long add(String key, Object... values);

    /**
     * 获取 set 缓存的长度
     *
     * @param key 键
     * @return
     */
    Long size(String key);

    /**
     * 将 values 从 set 中移除
     *
     * @param key    键
     * @param values 值，可以有多个
     * @return
     */
    Long remove(String key, Object... values);

    // zset

    /**
     * string
     *
     * @return
     */
    ValueOperations<K, V> opsForValue();

}
