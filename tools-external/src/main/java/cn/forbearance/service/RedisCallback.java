package cn.forbearance.service;

import org.springframework.dao.DataAccessException;
import org.springframework.lang.Nullable;

/**
 * @author cristina
 */
public interface RedisCallback<T> {

    @Nullable
    T doInRedis(RedisConnection connection) throws DataAccessException;
}
