package cn.forbearance.mysql.binlog.packet.data;

import cn.forbearance.mysql.binlog.packet.EventPacket;
import cn.forbearance.mysql.protocol.packets.Message;
import lombok.ToString;

/**
 * see: https://dev.mysql.com/doc/dev/mysql-server/latest/classmysql_1_1binlog_1_1event_1_1Rows__query__event.html
 * <p>
 * 基于行的日志记录，记录的是每一行的数据变更，比如一个update影响了5行，那么就记录5条.
 *
 * @author cristina
 */
@ToString
public class RowsQueryEventPacket implements EventPacket {

    private String rowsQuery;

    @Override
    public void read(byte[] data) {
        Message msg = new Message(data);
        int len = msg.read();
        rowsQuery = new String(msg.readBytes(len));
    }

    public String getRowsQuery() {
        return rowsQuery;
    }

    public void setRowsQuery(String rowsQuery) {
        this.rowsQuery = rowsQuery;
    }
}
