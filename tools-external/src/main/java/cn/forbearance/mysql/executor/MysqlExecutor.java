package cn.forbearance.mysql.executor;

import cn.forbearance.mysql.MysqlConnection;
import cn.forbearance.mysql.protocol.packets.BinaryPacket;

import java.io.IOException;

/**
 * @author cristina
 */
public abstract class MysqlExecutor {

    protected MysqlConnection connection;

    public MysqlExecutor(MysqlConnection conn) {
        this.connection = conn;
    }

    /**
     * receive() 用于接收二进制数据包. 与 MySQL 通信涉及到多个数据包的交互.
     * <p>
     * 接收来自 MySQL 服务器的各种响应数据包，包括但不限于：
     * <pre>
     * 1. Command Packet： 用于发送命令（例如查询）的数据包。
     * 2. Error Packet： 用于表示错误的数据包。
     * 3. ResultSet Header Packet： 包含有关结果集的元信息的数据包。
     * 4. Field Packets： 包含字段信息的数据包。
     * 5. Row Data Packets： 包含查询结果中行的数据包。
     * 6. EOF (End of Field) Packet： 表示字段结束的数据包。
     *
     * 比如在执行一个查询时，整个交互可能涉及多个数据包。每个数据包在二进制协议中都有特定的格式和目的。
     *
     * @return
     * @throws IOException
     */
    public BinaryPacket receive() throws IOException {
        BinaryPacket bin = new BinaryPacket();
        bin.read(connection.getChannel());
        return bin;
    }
}
