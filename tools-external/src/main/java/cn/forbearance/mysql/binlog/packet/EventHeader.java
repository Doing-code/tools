package cn.forbearance.mysql.binlog.packet;

import cn.forbearance.mysql.protocol.packets.Message;

/**
 * @author cristina
 */
public abstract class EventHeader {

    protected byte eventType;

    /**
     * event data
     *
     * @param msg
     */
    protected abstract void read(Message msg);
}
