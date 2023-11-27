package cn.forbearance.service;

import cn.hutool.core.lang.Assert;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.ClientCnxnSocketNetty;
import org.apache.zookeeper.client.ZKClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会话管理
 *
 * @author cristina
 */
@Component("zkConnectedService")
@Configuration
public class ZookeeperConnectedService {

    static {
        System.setProperty(ZKClientConfig.ZOOKEEPER_CLIENT_CNXN_SOCKET, ClientCnxnSocketNetty.class.getName());
    }

    public ZookeeperConnectedService() {
    }

    private static final Map<String, CuratorFramework> CONNECTED = new ConcurrentHashMap<>(16);
    ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);

    public CuratorFramework getConnection(String address) {
        Assert.notBlank(address, "zookeeper server address not null");

        return CONNECTED.computeIfAbsent(address, v -> {
            CuratorFramework zkClient = CuratorFrameworkFactory.builder()
                    .connectString(address)
                    .sessionTimeoutMs(10000)
                    .connectionTimeoutMs(10000)
                    .retryPolicy(retryPolicy)
                    .build();

            zkClient.getConnectionStateListenable().addListener(new ConnectionStateListenerImpl());
            // 创建 session 链接
            zkClient.start();
            try {
                zkClient.blockUntilConnected();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return zkClient;
        });
    }

    /**
     * 会话过期重连
     * 实现 ConnectionStateListener 接口
     */
    static class ConnectionStateListenerImpl implements ConnectionStateListener {

        @Override
        public void stateChanged(CuratorFramework client, ConnectionState newState) {
            // processing service
        }
    }
}
