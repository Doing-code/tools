package cn.forbearance.utils.connection;

import cn.forbearance.service.RedisTemplate;
import cn.forbearance.utils.SyncResponseUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerErrorException;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Netty 连接池
 *
 * @author cristina
 */
@Slf4j
public class NettyClientPool {

    volatile private static NettyClientPool nettyClientPool;

    final EventLoopGroup work = new NioEventLoopGroup();

    final Bootstrap bootstrap = new Bootstrap();

    public static final ConcurrentHashMap<Long, CompletableFuture<String>> requestMap = new ConcurrentHashMap<>();

    volatile private static Map<InetSocketAddress, FixedChannelPool> pools = new ConcurrentHashMap<>(16);

    private NettyClientPool() {
        connection();
    }

    /**
     * Double Check Lock
     *
     * @return nettyClientPool
     */
    public static NettyClientPool getInstance() {
        if (nettyClientPool == null) {
            synchronized (NettyClientPool.class) {
                if (nettyClientPool == null) {
                    nettyClientPool = new NettyClientPool();
                }
            }
        }
        return nettyClientPool;
    }

    public void connection() {
        log.info("NettyClientPool build...");
        bootstrap.group(work)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.RCVBUF_ALLOCATOR,
                        new AdaptiveRecvByteBufAllocator(1024 * 2, 1024 * 10, 1024 * 1024));

        InetSocketAddress address = new InetSocketAddress("localhost", 6379);
        pools.put(address, new FixedChannelPool(bootstrap.remoteAddress(address), new RedisChannelHandler(), 10, 10));
    }

    public Channel getConnection(InetSocketAddress address) throws ServerErrorException {
        Channel channel = null;
        try {
            FixedChannelPool pool = pools.get(address);
            Future<Channel> future = pool.acquire();
            channel = future.get();

            SyncResponseUtil.SYNC_RESULT.computeIfAbsent(channel.id(), key -> new LinkedBlockingDeque<>());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return channel;
    }

    public static void main(String[] args) throws Exception {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();

//        System.out.println("> " + service.opsForValue().get("name"));
        System.out.println("> " + redisTemplate.scan(null));
//        System.out.println("> " + service.opsForValue().get("name"));
//        service.opsForValue().set("age", 0);
//        service.opsForValue().set("name", "jack");
//        System.out.println("> " + service.opsForValue().get("name"));
    }

}
