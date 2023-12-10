package cn.forbearance.mysql.binlog;

/**
 * @author cristina
 * @see <a href="https://dev.mysql.com/doc/refman/8.0/en/replication-options-binary-log.html#sysvar_binlog_checksum">
 * --binlog-checksum
 * </a>用于验证 binlog 内容的正确性.
 */
public enum ChecksumEnum {

    /**
     * --binlog-checksum Valid Values
     */
    NONE(1), CRC32(4);

    private final int length;

    private ChecksumEnum(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    private static final ChecksumEnum[] VALUES = values();

    public static ChecksumEnum ordinal(int ordinal) {
        return VALUES[ordinal];
    }
}
