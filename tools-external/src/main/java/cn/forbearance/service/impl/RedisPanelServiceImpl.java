package cn.forbearance.service.impl;

import cn.forbearance.domain.Cursor;
import cn.forbearance.domain.RedisServer;
import cn.forbearance.service.RedisPanelService;
import cn.forbearance.service.redis.RedisTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author cristina
 */
@Service
@RequiredArgsConstructor
public class RedisPanelServiceImpl implements RedisPanelService {

    private final RedisTemplate<Object, Object> customRedisTemplate;

    @Override
    public Cursor<Object> queryAllKeys(String pattern, int position, int count, RedisServer server) {
        return customRedisTemplate.scan(pattern, position, count, server);
    }

    @Override
    public String getValue(String key, RedisServer server) {
        return (String) customRedisTemplate.opsForValue().get(key, server);
    }

    @Override
    public void setValue(String key, String value, RedisServer server) {
        customRedisTemplate.opsForValue().set(key, value, server);
    }

    @Override
    public void delete(String key, RedisServer server) {
        customRedisTemplate.opsForValue().delete(key, server);
    }
}
