package cn.forbearance.service.redis;

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
