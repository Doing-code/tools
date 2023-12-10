package cn.forbearance.mysql.binlog;

/**
 * @author cristina
 * @see <a href="https://dev.mysql.com/doc/dev/mysql-server/latest/namespacemysql_1_1binlog_1_1event.html#a4a991abea842d4e50cbee0e490c28cee">
 * Log_event_type
 * </a>
 */
public interface EventType {

    public final byte UNKNOWN_EVENT = 0x00;

    public final byte START_EVENT_V3 = 0x01;

    /**
     * STATEMENT	基于 SQL 语句的日志记录，记录的是 SQL 语句，对数据进行修改的 SQL 都会记录在日志文件中。
     * <p>
     * MIXED	混合STATEMENT、ROW，默认采用STATEMENT，在某些特殊情况会自动切换为ROW进行记录
     */
    public final byte QUERY_EVENT = 0x02;

    public final byte STOP_EVENT = 0x03;

    public final byte ROTATE_EVENT = 0x04;

    public final byte INTVAR_EVENT = 0x05;

    public final byte LOAD_EVENT = 0x06;

    public final byte SLAVE_EVENT = 0x07;

    public final byte CREATE_FILE_EVENT = 0x08;

    public final byte APPEND_BLOCK_EVENT = 0x09;

    public final byte EXEC_LOAD_EVENT = 0x0a;

    public final byte DELETE_FILE_EVENT = 0x0b;

    public final byte NEW_LOAD_EVENT = 0x0c;

    public final byte RAND_EVENT = 0x0d;

    public final byte USER_VAR_EVENT = 0x0e;

    /**
     * 用于描述二进制日志的格式，它通常是二进制日志的第一个事件.
     * <p>
     * 用于标识二进制日志的格式信息，包括文件版本、创建时间、以及在事件中使用的各种数据格式。
     */
    public final byte FORMAT_DESCRIPTION_EVENT = 0x0f;

    public final byte XID_EVENT = 0x10;

    public final byte BEGIN_LOAD_QUERY_EVENT = 0x11;

    public final byte EXECUTE_LOAD_QUERY_EVENT = 0x12;

    public final byte TABLE_MAP_EVENT = 0x13;

    public final byte WRITE_ROWS_EVENT_V0 = 0x14;

    public final byte UPDATE_ROWS_EVENT_V0 = 0x15;

    public final byte DELETE_ROWS_EVENT_V0 = 0x16;

    public final byte WRITE_ROWS_EVENT_V1 = 0x17;

    public final byte UPDATE_ROWS_EVENT_V1 = 0x18;

    public final byte DELETE_ROWS_EVENT_V1 = 0x19;

    public final byte INCIDENT_EVENT = 0x1a;

    public final byte HEARTBEAT_EVENT = 0x1b;

    public final byte IGNORABLE_EVENT = 0x1c;

    /**
     * ROW	基于行的日志记录，记录的是每一行的数据变更。（默认），比如一个update影响了5行，那么就记录5条
     */
    public final byte ROWS_QUERY_EVENT = 0x1d;

    public final byte WRITE_ROWS_EVENT_V2 = 0x1e;

    public final byte UPDATE_ROWS_EVENT_V2 = 0x1f;

    public final byte DELETE_ROWS_EVENT_V2 = 0x20;

    public final byte GTID_EVENT = 0x21;

    public final byte ANONYMOUS_GTID_EVENT = 0x22;

    public final byte PREVIOUS_GTIDS_EVENT = 0x23;
}
