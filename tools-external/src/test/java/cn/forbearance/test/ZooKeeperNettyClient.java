package cn.forbearance.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.apache.zookeeper.ClientCnxnSocketNetty;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.nio.charset.StandardCharsets;

public class ZooKeeperNettyClient {

    private static final String ZOOKEEPER_SERVER = "localhost:2181";
    private static final int ZOOKEEPER_SESSION_TIMEOUT = 5000000;

    public static void main(String[] args) throws Exception {
        System.setProperty("zookeeper.clientCnxnSocket", ClientCnxnSocketNetty.class.getName());

        // 创建一个与 ZooKeeper 服务器的连接
        ZooKeeper zooKeeper = new ZooKeeper(ZOOKEEPER_SERVER, ZOOKEEPER_SESSION_TIMEOUT, null);

        System.out.println(zooKeeper.getChildren("/", false));

    }
}
