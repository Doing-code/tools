package cn.forbearance.service.impl;

import cn.forbearance.domain.Cursor;
import cn.forbearance.domain.RedisInfo;
import cn.forbearance.service.RedisPanelService;
import cn.forbearance.redis.RedisTemplate;
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
    public Cursor<Object> queryAllKeys(RedisInfo server) {
        return customRedisTemplate.scan(server.getPatternKey(), server.getPosition(), server.getCount(), server);
    }

    @Override
    public String getValue(RedisInfo server) {
        return (String) customRedisTemplate.opsForValue().get(server.getKey(), server);
    }

    @Override
    public void setValue(RedisInfo server) {
        customRedisTemplate.opsForValue().set(server.getKey(), server.getValue(), server);
    }

    @Override
    public void delete(RedisInfo server) {
        customRedisTemplate.opsForValue().delete(server.getKey(), server);
    }
}
