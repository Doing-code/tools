package cn.forbearance.utils;

import cn.forbearance.domain.RedisServer;
import cn.forbearance.service.redis.RedisConnection;
import cn.forbearance.service.redis.RedisConnectionFactory;
import org.springframework.lang.Nullable;

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

    public static void releaseConnection(@Nullable RedisConnection conn, RedisConnectionFactory factory,
                                         RedisServer server) {
        if (conn == null) {
            return;
        }
        conn.release(conn.channel(), server.getIp(), server.getPort());
    }

}
