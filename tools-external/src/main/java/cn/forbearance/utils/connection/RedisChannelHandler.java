package cn.forbearance.utils.connection;

import cn.forbearance.utils.AggregatedResponse;
import cn.forbearance.utils.SyncResponseUtil;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.redis.*;
import io.netty.util.ReferenceCountUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cristina
 */
public class RedisChannelHandler implements ChannelPoolHandler {

    @Override
    public void channelReleased(Channel ch) throws Exception {
    }

    @Override
    public void channelAcquired(Channel ch) throws Exception {
    }

    @Override
    public void channelCreated(Channel ch) throws Exception {
        SocketChannel channel = (SocketChannel) ch;
        channel.config().setKeepAlive(true);
        channel.config().setTcpNoDelay(true);

        ch.pipeline().addLast(new RedisDecoder());
        ch.pipeline().addLast(new RedisBulkStringAggregator());
        ch.pipeline().addLast(new RedisArrayAggregator());
        ch.pipeline().addLast(new RedisEncoder());
        ch.pipeline().addLast(new ChannelDuplexHandler() {

            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                String[] commands = ((String) msg).split("\\s+");
                List<RedisMessage> children = new ArrayList<>(commands.length);
                for (String cmdString : commands) {
                    children.add(new FullBulkStringRedisMessage(ByteBufUtil.writeUtf8(ctx.alloc(), cmdString)));
                }
                RedisMessage request = new ArrayRedisMessage(children);
                ctx.write(request, promise);
            }

            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) {
                RedisMessage buf = (RedisMessage) msg;
                // 同步获取到 response 放入阻塞队列.
                SyncResponseUtil.SYNC_RESULT.get(ctx.channel().id()).add(AggregatedResponse.aggregatedRedisResponse(buf));

                ReferenceCountUtil.release(buf);
            }

            @Override
            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                ctx.close();
            }
        });
    }

}
