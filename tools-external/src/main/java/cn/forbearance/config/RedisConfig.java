package cn.forbearance.config;

import cn.forbearance.redis.RedisTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cristina
 */
@Configuration
public class RedisConfig {

    /**
     * TODO value 没有做序列化处理
     *
     * @return
     */
    @Bean(name = "customRedisTemplate")
    @ConditionalOnMissingBean(name = "customRedisTemplate")
    public RedisTemplate<Object, Object> customRedisTemplate() {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();

        return template;
    }
}
