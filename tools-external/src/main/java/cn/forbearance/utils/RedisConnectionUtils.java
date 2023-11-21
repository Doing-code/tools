package cn.forbearance.utils;

import cn.forbearance.service.RedisConnection;
import cn.forbearance.service.RedisConnectionFactory;

/**
 * @author cristina
 */
public class RedisConnectionUtils {

    public static RedisConnection getConnection(RedisConnectionFactory factory) {
        return fetchConnection(factory);
    }

    private static RedisConnection fetchConnection(RedisConnectionFactory factory) {
        return factory.getConnection();
    }
}
