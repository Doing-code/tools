package cn.forbearance.service;

import cn.forbearance.domain.RedisServer;
import org.springframework.lang.Nullable;

/**
 * @author cristina
 */
public class AbstractOperations<K, V> {

    abstract class CommonOperationsRedisCallback implements RedisCallback<V> {

        private Object key;

        public CommonOperationsRedisCallback(Object key) {
            this.key = key;
        }

        @Override
        public final V doInRedis(RedisConnection connection) {
            return (V) inRedis(connection);
        }

        /**
         * 执行命令
         *
         * @param connection
         * @return
         */
        @Nullable
        protected abstract Object inRedis(RedisConnection connection);
    }

    final RedisTemplate<K, V> template;

    AbstractOperations(RedisTemplate<K, V> template) {
        this.template = template;
    }

    <T> T execute(RedisCallback<T> callback, RedisServer server) {
        return template.execute(callback, server);
    }
}
