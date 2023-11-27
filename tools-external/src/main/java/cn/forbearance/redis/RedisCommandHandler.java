package cn.forbearance.redis;

import cn.forbearance.utils.AggregatedResponse;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.FullBulkStringRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import io.netty.util.concurrent.DefaultPromise;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cristina
 */
public class RedisCommandHandler extends ChannelDuplexHandler {

    private DefaultPromise<Object> promise;

    public void sendCommand(Channel channel, Object msg, DefaultPromise<Object> promise) {
        this.promise = promise;
        channel.writeAndFlush(msg);
    }

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

        promise.setSuccess(AggregatedResponse.aggregatedRedisResponse(buf));
//        ReferenceCountUtil.release(buf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
        cause.printStackTrace();
    }

}
