package cn.forbearance.service;

/**
 * @author cristina
 */
public class DefaultRedisConnectionFactory implements RedisConnectionFactory {

    public DefaultRedisConnectionFactory() {
    }

    @Override
    public RedisConnection getConnection() {
        return new DefaultRedisConnection();
    }
}
