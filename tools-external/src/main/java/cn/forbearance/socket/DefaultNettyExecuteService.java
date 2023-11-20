package cn.forbearance.socket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author cristina
 */
public class DefaultNettyExecuteService {

    private final ChannelFuture redisChannelFuture;

    private final CompletableFuture<Object> responseCallback;

    public DefaultNettyExecuteService(ChannelFuture redisChannelFuture, CompletableFuture<Object> responseCallback) {
        this.redisChannelFuture = redisChannelFuture;
        this.responseCallback = responseCallback;
    }

    /**
     * 回车换行
     */
    final byte[] LINE = {13, 10};

    public Object strGet(Object key) {
        ByteBuf buf = redisChannelFuture.channel().alloc().buffer();
        // 三个元素
        buf.writeBytes("*2".getBytes());
        buf.writeBytes(LINE);
        // 长度
        buf.writeBytes("$3".getBytes());
        buf.writeBytes(LINE);
        // 命令
        buf.writeBytes("get".getBytes());
        buf.writeBytes(LINE);
        buf.writeBytes("$4".getBytes());
        buf.writeBytes(LINE);
        buf.writeBytes(String.valueOf(key).getBytes());
        buf.writeBytes(LINE);
        redisChannelFuture.channel().writeAndFlush(buf);

        AtomicReference<Object> res = new AtomicReference<>();
        responseCallback.whenComplete((result, throwable) -> {
            if (throwable == null) {
                res.set(result.toString().split("\r\n")[1]);
            } else {
                throwable.printStackTrace();
            }
        });

        responseCallback.join();

        return res.get();
    }
}
