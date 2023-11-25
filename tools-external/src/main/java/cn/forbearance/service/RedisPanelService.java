package cn.forbearance.service;

import cn.forbearance.domain.Cursor;
import cn.forbearance.domain.RedisInfo;

/**
 * TODO 暂时只实现 string 的获取，后续优化为 策略模式获取 string、list、set、hash、zset 等数据类型.
 *
 * @author cristina
 */
public interface RedisPanelService {

    /**
     * 查询所有 key, 默认分页查询
     *
     * @param server 连接哪个 redis 服务
     * @return
     */
    Cursor<Object> queryAllKeys(RedisInfo server);

    /**
     * 根据 key 获取 value
     *
     * @param server
     * @return
     */
    String getValue(RedisInfo server);

    /**
     * 根据 key 设置 value
     *
     * @param server
     */
    void setValue(RedisInfo server);

    /**
     * 根据 key 删除 value
     *
     * @param server
     */
    void delete(RedisInfo server);
}
