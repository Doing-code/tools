package cn.forbearance.redis;

/**
 * @author cristina
 */
public interface RedisConnectionFactory {

    /**
     * /
     *
     * @return
     */
    RedisConnection getConnection();
}
