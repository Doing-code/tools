package cn.forbearance.socket;

import cn.forbearance.service.impl.RedisCommandServiceImpl;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.*;

/**
 * @author cristina
 */
public class RedisClientService implements Callable<ChannelFuture> {

    private final CompletableFuture<Object> responseCallback;

    private ChannelFuture channelFuture;

    public RedisClientService() {
        this.responseCallback = new CompletableFuture<Object>();
    }

    public CompletableFuture<Object> getResponseCallback() {
        return responseCallback;
    }

    public ChannelFuture getChannelFuture() {
        return channelFuture;
    }

    @Override
    public ChannelFuture call() {
        Bootstrap client = new Bootstrap();
        client.group(new NioEventLoopGroup());
        client.channel(NioSocketChannel.class);
        ChannelFuture future = client.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) {
                ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) {
                        ByteBuf buf = (ByteBuf) msg;
                        responseCallback.complete(buf.toString(Charset.defaultCharset()));
                    }
                });
            }
        }).connect(new InetSocketAddress("localhost", 6379)).syncUninterruptibly();

        this.channelFuture = future;
        return future;
    }

    public static void main(String[] args) throws Exception {
        RedisClientService redisClientService = new RedisClientService();
        Future<ChannelFuture> future = Executors.newFixedThreadPool(2).submit(redisClientService);
        ChannelFuture channel = future.get();
        RedisCommandServiceImpl service = new RedisCommandServiceImpl(redisClientService);
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                System.out.println("name: " + service.get("name"));
            }).start();
        }
    }
}
