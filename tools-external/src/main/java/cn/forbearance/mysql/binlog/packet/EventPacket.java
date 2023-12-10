package cn.forbearance.mysql.binlog.packet;

/**
 * @author cristina
 */
public interface EventPacket {

    /**
     * event data
     *
     * @param data
     */
    void read(byte[] data);
}
