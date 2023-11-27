package cn.forbearance.test;

import cn.forbearance.domain.RedisInfo;
import cn.forbearance.redis.RedisTemplate;

/**
 * @author cristina
 */
public class TestApplication {

    public static void main(String[] args) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();

        RedisInfo server = new RedisInfo("127.0.0.1", 6379);
//        System.out.println("scan > " + redisTemplate.scan(null, server));
//        System.out.println("get > " + redisTemplate.opsForValue().get("name", server));
//        redisTemplate.opsForValue().set("username", "Tommy", server);
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                System.out.println("get > " + redisTemplate.opsForValue().get("username", server));
            }).start();
        }
    }
}
