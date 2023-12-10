package cn.forbearance.test;


import cn.forbearance.mysql.MysqlConnection;
import cn.forbearance.mysql.MysqlDataSource;
import cn.forbearance.mysql.executor.QueryExecutor;
import cn.forbearance.mysql.protocol.packets.BinaryPacket;
import cn.forbearance.mysql.protocol.packets.Packet;
import cn.forbearance.mysql.protocol.packets.ResultSetPacket;
import cn.forbearance.mysql.protocol.packets.request.CommandPacket;
import cn.forbearance.utils.ErrorPacketException;
import com.alibaba.fastjson.JSONObject;

/**
 * @author cristina
 */
public class TestMySQL {

    public static void main(String[] args) {
        MysqlDataSource dataSource = new MysqlDataSource("127.0.0.1", 3306, "root", "root");
        dataSource.start();
        ResultSetPacket query = null;
        try {
            MysqlConnection conn = dataSource.getConnection();
            QueryExecutor queryExecutor = conn.getQueryExecutor();

            CommandPacket commandPacket = new CommandPacket(Packet.COM_INIT_DB, "austin".getBytes());

            commandPacket.write(conn.getChannel());

            BinaryPacket bin = new BinaryPacket();
            bin.read(conn.getChannel());

            if (bin.getBody()[0] < 0) {
                ErrorPacketException.handleFailure(bin);
            }

            query = queryExecutor.query("SELECT * FROM channel_account;");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String json = JSONObject.toJSONString(query);

        System.out.println(json);
    }
}
