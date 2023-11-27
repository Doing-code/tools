package cn.forbearance.service;

import cn.forbearance.domain.ZkNodeChildren;
import cn.forbearance.domain.ZookeeperInfo;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * <pre>
 *     TODO
 *      1. 目前采用`org.apache.curator`提供的 API，但后期会修改为自己开发的Zookeeper客户端。
 *      2. 难点是 zookeeper 的编解码比较复杂，没有那么多经历投入
 *      3. 暂仅支持查询
 *      4. 深入源码发现，curator底层还是借助了 Zookeeper 官方提供的 API 能力.
 * </pre>
 *
 * @author cristina
 */
public interface ZookeeperPanelService {

    /**
     * 获取 path 的子节点
     *
     * @param zkInfo
     * @return
     */
    List<ZkNodeChildren> getChildren(ZookeeperInfo zkInfo);

    /**
     * 获取 path 节点值
     *
     * @param zkInfo
     * @return
     */
    String getData(ZookeeperInfo zkInfo);

    /**
     * 获取 path 的详细属性. eg czxid、mzxid ...
     *
     * @param zkInfo
     * @return
     */
    Stat getStat(ZookeeperInfo zkInfo);
}
