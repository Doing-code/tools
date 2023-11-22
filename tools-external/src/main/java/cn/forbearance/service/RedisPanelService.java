package cn.forbearance.service;

import cn.forbearance.domain.Cursor;
import cn.forbearance.domain.RedisServer;

import java.util.List;

/**
 * TODO 暂时只实现 string 的获取，后续优化为 策略模式获取 string、list、set、hash、zset 等数据类型.
 *
 * @author cristina
 */
public interface RedisPanelService {

    /**
     * 查询所有 key, 默认分页查询
     *
     * @param pattern  需要匹配的 key，null 则查询全部
     * @param position limit
     * @param count    count
     * @param server   连接哪个 redis 服务
     * @return
     */
    Cursor<Object> queryAllKeys(String pattern, int position, int count, RedisServer server);

    /**
     * 根据 key 获取 value
     *
     * @param key
     * @param server
     * @return
     */
    String getValue(String key, RedisServer server);

    /**
     * 根据 key 设置 value
     *
     * @param key
     * @param value
     * @param server
     */
    void setValue(String key, String value, RedisServer server);

    /**
     * 根据 key 删除 value
     *
     * @param key
     * @param server
     */
    void delete(String key, RedisServer server);
}
