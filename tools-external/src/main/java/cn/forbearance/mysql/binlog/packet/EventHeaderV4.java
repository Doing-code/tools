package cn.forbearance.mysql.binlog.packet;

import cn.forbearance.mysql.protocol.packets.Message;

/**
 * @author cristina
 */
public class EventHeaderV4 extends EventHeader {

    private long timestamp;
    private long serverId;
    private long eventSize;

    /**
     * if binlog-version > 1
     */
    private long nextLogPos;
    private int  flags;

    private int checksumAlg;
    private long crc;
    private String logFileName;

    @Override
    protected void read(Message msg) {
        this.timestamp = msg.readUb4() * 1000;
        this.eventType = msg.read();
        this.serverId = msg.readUb4();
        this.eventSize = msg.readUb4();
        this.nextLogPos = msg.readUb4();
        this.flags = msg.readUb2();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getServerId() {
        return serverId;
    }

    public void setServerId(long serverId) {
        this.serverId = serverId;
    }

    public long getEventSize() {
        return eventSize;
    }

    public void setEventSize(long eventSize) {
        this.eventSize = eventSize;
    }

    public long getNextLogPos() {
        return nextLogPos;
    }

    public void setNextLogPos(long nextLogPos) {
        this.nextLogPos = nextLogPos;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getChecksumAlg() {
        return checksumAlg;
    }

    public void setChecksumAlg(int checksumAlg) {
        this.checksumAlg = checksumAlg;
    }

    public long getCrc() {
        return crc;
    }

    public void setCrc(long crc) {
        this.crc = crc;
    }

    public String getLogFileName() {
        return logFileName;
    }

    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }
}
