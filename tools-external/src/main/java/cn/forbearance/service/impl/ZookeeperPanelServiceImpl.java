package cn.forbearance.service.impl;

import cn.forbearance.domain.ZookeeperInfo;
import cn.forbearance.service.ZookeeperConnectedService;
import cn.forbearance.service.ZookeeperPanelService;
import lombok.RequiredArgsConstructor;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author cristina
 */
@Service("zkPanelService")
@RequiredArgsConstructor
public class ZookeeperPanelServiceImpl implements ZookeeperPanelService {

    private final ZookeeperConnectedService zkConnectedService;

    @Override
    public List<String> getChildren(ZookeeperInfo zkInfo) {
        try {
            return zkConnectedService.getConnection(zkInfo.getConnectString())
                    .getChildren()
                    .forPath(zkInfo.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getData(ZookeeperInfo zkInfo) {
        try {
            byte[] bytes = zkConnectedService.getConnection(zkInfo.getConnectString())
                    .getData()
                    .forPath(zkInfo.getPath());
            if (Objects.isNull(bytes)) {
                return null;
            }
            return new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Stat getStat(ZookeeperInfo zkInfo) {
        Stat stat = new Stat();
        try {
            zkConnectedService.getConnection(zkInfo.getConnectString())
                    .getData()
                    .storingStatIn(stat)
                    .forPath(zkInfo.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stat;
    }
}
