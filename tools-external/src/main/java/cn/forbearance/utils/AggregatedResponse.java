package cn.forbearance.utils;

import cn.forbearance.domain.Cursor;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.redis.*;
import io.netty.util.CharsetUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cristina
 */
public class AggregatedResponse {

    public static Object aggregatedRedisResponse(RedisMessage msg) {
        if (msg instanceof SimpleStringRedisMessage) {
            return ((SimpleStringRedisMessage) msg).content();
        } else if (msg instanceof ErrorRedisMessage) {
            return ((ErrorRedisMessage) msg).content();
        } else if (msg instanceof IntegerRedisMessage) {
            return ((IntegerRedisMessage) msg).value();
        } else if (msg instanceof FullBulkStringRedisMessage) {
            return getString((FullBulkStringRedisMessage) msg);
        } else if (msg instanceof ArrayRedisMessage) {
            return msg;
        }
        throw new CodecException("unknown message type: " + msg);
    }

    public static Cursor<Object> parseScan(ArrayRedisMessage message) {
        RedisMessage cursor0 = message.children().remove(0);
        Object position = AggregatedResponse.aggregatedRedisResponse(cursor0);

        ArrayRedisMessage arrayRedisMessage = (ArrayRedisMessage) message.children().get(message.children().size() - 1);

        List<Object> ele = new ArrayList<>();
        for (RedisMessage child : arrayRedisMessage.children()) {
            ele.add(AggregatedResponse.aggregatedRedisResponse(child));
        }
        return new Cursor<>((String) position, ele);
    }

    private static String getString(FullBulkStringRedisMessage msg) {
        if (msg.isNull()) {
            return "(null)";
        }
        return msg.content().toString(CharsetUtil.UTF_8);
    }
}
