package cn.forbearance.service.impl;

import cn.forbearance.service.RedisCommandService;
import cn.forbearance.socket.DefaultNettyExecuteService;
import cn.forbearance.socket.RedisClientService;
import cn.forbearance.utils.enums.DataType;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author cristina
 */
public class RedisCommandServiceImpl extends DefaultNettyExecuteService implements RedisCommandService<Object, Object> {

    public RedisCommandServiceImpl(RedisClientService redisClientService) {
        super(redisClientService.getChannelFuture(), redisClientService.getResponseCallback());
    }

    @Override
    public DataType type(Object key) {
        return null;
    }

    @Override
    public Boolean expire(Object key, long timeout, TimeUnit unit) {
        return null;
    }

    @Override
    public Long getExpire(Object key, TimeUnit timeUnit) {
        return null;
    }

    @Override
    public Boolean delete(Object key) {
        return null;
    }

    @Override
    public Long delete(Collection<Object> keys) {
        return null;
    }

    @Override
    public String get(Object key) {
        return (String) this.strGet(key);
    }

    @Override
    public List<String> multiGet(Collection<String> keys) {
        return null;
    }

    @Override
    public void set(String key, String value) {

    }

    @Override
    public void set(String key, String value, long timeout, TimeUnit unit) {

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
    public List<Object> range(String key, long start, long end) {
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
    public Long rightPushAll(Object key, Collection<Object> values) {
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
}
