package cn.forbearance.redis;

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
import org.jetbrains.annotations.Nullable;
import org.springframework.web.server.ServerErrorException;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

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

    private static final Map<InetSocketAddress, FixedChannelPool> CONNECTION_POOLS = new ConcurrentHashMap<>(16);

    private static final Map<String, InetSocketAddress> pools = new ConcurrentHashMap<>(16);

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
    }

    public Channel getConnection(InetSocketAddress address) throws ServerErrorException {
        return getChannel(address);
    }

    public Channel getConnection(String localhost, int port) throws ServerErrorException {
        InetSocketAddress address = getInetSocketAddress(localhost, port);

        return getChannel(address);
    }

    @Nullable
    private Channel getChannel(InetSocketAddress address) {
        Channel channel = null;
        try {
            FixedChannelPool pool = getFixedChannelPool(address);
            Future<Channel> future = pool.acquire();
            channel = future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return channel;
    }

    public static void release(Channel ch, String localhost, int port) {
        if (Objects.isNull(ch)) {
            return;
        }
        ch.flush();
        CONNECTION_POOLS.get(getInetSocketAddress(localhost, port)).release(ch);
    }

    public static InetSocketAddress getInetSocketAddress(String localhost, int port) {
        return pools.computeIfAbsent(localhost + ":" + port, v -> new InetSocketAddress(localhost, port));
    }

    public FixedChannelPool getFixedChannelPool(InetSocketAddress address) {
        return CONNECTION_POOLS.computeIfAbsent(address, v -> {
            return new FixedChannelPool(bootstrap.remoteAddress(address), new RedisChannelHandler(), 200, Integer.MAX_VALUE);
        });
    }

}
