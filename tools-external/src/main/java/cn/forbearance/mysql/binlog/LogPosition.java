package cn.forbearance.mysql.binlog;

/**
 * show master status主从同步的起始位置
 *
 * 暂时不涉及到 binlog_do_DB、binlog_Ignore_DB、Executed_Gtid_Set
 *
 * @author cristina
 */
public class LogPosition {

    private String journalName;

    private Long position;

    private Long serverId;

    private String gtid;

    public LogPosition(String journalName, Long position, Long serverId) {
        this.journalName = journalName;
        this.position = position;
        this.serverId = serverId;
    }

    public String getJournalName() {
        return journalName;
    }

    public void setJournalName(String journalName) {
        this.journalName = journalName;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public String getGtid() {
        return gtid;
    }

    public void setGtid(String gtid) {
        this.gtid = gtid;
    }

    @Override
    public String toString() {
        return "LogPosition{" +
                "journalName='" + journalName + '\'' +
                ", position=" + position +
                ", serverId=" + serverId +
                ", gtid='" + gtid + '\'' +
                '}';
    }
}
