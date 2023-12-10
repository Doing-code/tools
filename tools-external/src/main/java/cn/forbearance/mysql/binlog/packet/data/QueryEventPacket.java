package cn.forbearance.mysql.binlog.packet.data;

import cn.forbearance.mysql.binlog.BinlogDumpContext;
import cn.forbearance.mysql.binlog.ChecksumEnum;
import cn.forbearance.mysql.binlog.packet.EventPacket;
import cn.forbearance.mysql.protocol.packets.Message;
import lombok.ToString;

/**
 * 每个修改数据库的查询创建一个 Query_event. 当 binlog 格式设置的是 statement|mixed 时
 * <p>
 * see: https://dev.mysql.com/doc/dev/mysql-server/latest/classmysql_1_1binlog_1_1event_1_1Query__event.html
 *
 * @author cristina
 */
@ToString
public class QueryEventPacket implements EventPacket {

    private long threadId;

    private long queryExecTime;

    private int curDatabaseLength;

    private int errorCode;

    private int statusVarsLength;

    private byte[] statusVars;

    private String database;

    /**
     * The SQL query.
     */
    private String command;

    public QueryEventPacket() {
    }

    @Override
    public void read(byte[] data) {
        Message msg = new Message(data);
        threadId = msg.readUb4();
        queryExecTime = msg.readUb4();
        curDatabaseLength = msg.read();
        errorCode = msg.readUb2();
        statusVarsLength = msg.readUb2();
        statusVars = msg.readBytes(statusVarsLength);
        database = new String(msg.readBytes(curDatabaseLength));

        BinlogDumpContext context = BinlogDumpContext.getBinlogDumpContext();
        if (context.getChecksumType() == ChecksumEnum.CRC32) {
            command = new String(msg.readBytesWithCrc32());
        } else {
            command = new String(msg.readBytes());
        }
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public long getQueryExecTime() {
        return queryExecTime;
    }

    public void setQueryExecTime(long queryExecTime) {
        this.queryExecTime = queryExecTime;
    }

    public int getCurDatabaseLength() {
        return curDatabaseLength;
    }

    public void setCurDatabaseLength(int curDatabaseLength) {
        this.curDatabaseLength = curDatabaseLength;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getStatusVarsLength() {
        return statusVarsLength;
    }

    public void setStatusVarsLength(int statusVarsLength) {
        this.statusVarsLength = statusVarsLength;
    }

    public byte[] getStatusVars() {
        return statusVars;
    }

    public void setStatusVars(byte[] statusVars) {
        this.statusVars = statusVars;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
