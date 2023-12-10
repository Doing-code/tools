package cn.forbearance.mysql.binlog.codec;

import cn.forbearance.mysql.binlog.packet.EventPacket;

/**
 * @author cristina
 */
public interface EventDecoder<T extends EventPacket> {

    /**
     * 解码
     *
     * @param data
     * @return
     */
    T decode(byte[] data);
}
