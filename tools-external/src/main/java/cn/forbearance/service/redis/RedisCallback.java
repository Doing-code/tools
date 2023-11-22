package cn.forbearance.service.redis;

import org.springframework.dao.DataAccessException;
import org.springframework.lang.Nullable;

/**
 * @author cristina
 */
public interface RedisCallback<T> {

    /**
     * 执行命令
     *
     * @param connection
     * @return
     * @throws DataAccessException
     */
    @Nullable
    T doInRedis(RedisConnection connection) throws DataAccessException;
}
