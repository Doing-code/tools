package cn.forbearance.utils;

import io.netty.channel.ChannelId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author cristina
 */
public class SyncResponseUtil {

    public static final Map<ChannelId, LinkedBlockingDeque<Object>> SYNC_RESULT = new ConcurrentHashMap<>();
}
