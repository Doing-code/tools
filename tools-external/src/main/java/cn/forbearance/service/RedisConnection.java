package cn.forbearance.service;

import io.netty.channel.Channel;

/**
 * @author cristina
 */
public interface RedisConnection {

    /**
     * /
     * @return
     */
    Channel getChannel();
}
