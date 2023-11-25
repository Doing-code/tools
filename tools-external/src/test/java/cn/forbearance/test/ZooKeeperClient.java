package cn.forbearance.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

/**
 * @author cristina
 */
public class ZooKeeperClient {

    private static final String ZOOKEEPER_SERVER = "127.0.0.1";

    private static final int ZOOKEEPER_PORT = 2181;

    public void connect() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(1024 * 2, 1024 * 10, 1024 * 1024))
                .handler(new ChannelDuplexHandler() {

                    @Override
                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                        ctx.write("ls /", promise);
                    }

                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        System.out.println("Received message from ZooKeeper server: " + msg);
                    }

                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                        ctx.close();
                        cause.printStackTrace();
                    }
                });

        Channel channel = bootstrap.connect(ZOOKEEPER_SERVER, ZOOKEEPER_PORT).sync().channel();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter command (list, exit): ");
            String command = scanner.nextLine();

            if (command.equals("exit")) {
                break;
            } else if (command.equals("ls")) {
                // Send a request to list the root node
                channel.writeAndFlush("ls /");
            } else {
                // Handle other commands as needed
                channel.writeAndFlush(command);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new ZooKeeperClient().connect();
    }
}
