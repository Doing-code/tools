package cn.forbearance.mysql.binlog;

import cn.forbearance.mysql.protocol.packets.request.GtidSet;

/**
 * @author cristina
 */
public class BinlogDumpContext {

    private final static ThreadLocal<BinlogDumpContext> CONTEXT_THREAD_LOCAL = new ThreadLocal<BinlogDumpContext>();

    private ChecksumEnum checksumType;

    public BinlogDumpContext() {
        setBinlogDumpContext(this);
    }

    /**
     * 可能会包含额外的信息标识
     */
    private boolean mayContainExtraInformation = true;

    private GtidSet gtidSet;

    public static BinlogDumpContext getBinlogDumpContext() {
        return CONTEXT_THREAD_LOCAL.get();
    }

    public void setBinlogDumpContext(BinlogDumpContext context) {
        CONTEXT_THREAD_LOCAL.set(context);
    }

    public ChecksumEnum getChecksumType() {
        return checksumType;
    }

    public void setChecksumType(ChecksumEnum checksumType) {
        this.checksumType = checksumType;
    }

    public boolean isMayContainExtraInformation() {
        return mayContainExtraInformation;
    }

    public void setMayContainExtraInformation(boolean mayContainExtraInformation) {
        this.mayContainExtraInformation = mayContainExtraInformation;
    }

    public GtidSet getGtidSet() {
        return gtidSet;
    }

    public void setGtidSet(GtidSet gtidSet) {
        this.gtidSet = gtidSet;
    }
}
