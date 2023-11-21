package cn.forbearance.service;

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
