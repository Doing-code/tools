package cn.forbearance.test;

import cn.forbearance.service.ZookeeperConnectedService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author cristina
 */
public class TestCuratorFramework {

    public static void main(String[] args) throws Exception {

        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);

        CuratorFramework zkClient = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(300000)
                .connectionTimeoutMs(500000)
                .retryPolicy(retryPolicy)
                .build();

        // 创建 session 链接
        zkClient.start();

        System.out.println(zkClient.getChildren().forPath("/"));

//        System.in.read();
    }
}
