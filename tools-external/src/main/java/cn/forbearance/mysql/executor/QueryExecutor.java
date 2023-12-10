package cn.forbearance.mysql.executor;

import cn.forbearance.mysql.MysqlConnection;
import cn.forbearance.mysql.protocol.packets.*;
import cn.forbearance.mysql.protocol.packets.request.CommandPacket;
import cn.forbearance.utils.ErrorPacketException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询执行器
 *
 * @author cristina
 */
public class QueryExecutor extends MysqlExecutor {

    public QueryExecutor(MysqlConnection conn) {
        super(conn);
    }

    /**
     * 当在arg传入 show variables like 'binlog_format'，对于查询命令而言，会返回Result Set消息
     * <p>
     * Result set消息分为五个部分，四种报文，分别为 ResultSetHeaderPacket、FieldPacket、RowPacket、EOFPacket
     * <p>
     * 接收顺序如下
     * <pre>
     *     ResultSetHeaderPacket
     *              ↓
     *         FieldPacket
     *              ↓
     *             EOF
     *              ↓
     *          RowPacket
     *              ↓
     *             EOF
     * </pre>
     * 看body的第一个字节的取值在哪一个范围就是什么类型的报文，其中Result Set Header、Field、RowData三种报文取值范围一致，只能按照发送的顺序来看具体是哪一种报文。
     *
     * @param cmd
     * @return
     * @throws Exception
     */
    public ResultSetPacket query(String cmd) throws Exception {
        if (!connection.isStart()) {
            throw new Exception("connection not open");
        }
        CommandPacket commandPacket = new CommandPacket();
        commandPacket.setCommand(Packet.COM_QUERY);
        commandPacket.setArg(cmd.getBytes());
        commandPacket.write(connection.getChannel());

        // 包含有关结果集的元信息的数据包
        BinaryPacket receive = receive();
        if (receive.getBody()[0] < 0) {
            ErrorPacketException.handleFailure(receive);
        }
        ResultSetHeaderPacket resultSetHeaderPacket = new ResultSetHeaderPacket();
        resultSetHeaderPacket.read(receive);

        // 包含字段信息的数据包
        List<FieldPacket> fieldPackets = new ArrayList<>();
        for (int i = 0; i < resultSetHeaderPacket.getFieldCount(); i++) {
            FieldPacket fieldPacket = new FieldPacket();
            fieldPacket.read(receive());
            fieldPackets.add(fieldPacket);
        }

        // 表示字段结束的数据包
        readEofPacket();

        // 包含查询结果中行的数据包
        List<RowDataPacket> rowDatas = new ArrayList<>();
        while (true) {
            BinaryPacket rowBin = receive();
            if (rowBin.getBody()[0] == EofPacket.FIELD_COUNT) {
                break;
            }

            RowDataPacket rowDataPacket = new RowDataPacket(resultSetHeaderPacket.getFieldCount());
            rowDataPacket.read(rowBin);
            rowDatas.add(rowDataPacket);
        }

        // 封装结果集
        ResultSetPacket resultSetPacket = new ResultSetPacket();
        resultSetPacket.setFieldDescriptors(fieldPackets);
        for (RowDataPacket rowDataPacket : rowDatas) {
            resultSetPacket.getFieldValues().add(rowDataPacket.getFieldValuesStrs());
        }
        return resultSetPacket;
    }

    private boolean readEofPacket() throws IOException {
        BinaryPacket receive = receive();
        EofPacket eofPacket = new EofPacket();
        eofPacket.read(receive);
        if (receive.getBody()[0] != -2) {
            throw new IOException("EOF Packet is expected, but packet with field_count=" + receive.getBody()[0] + " is found.");
        }
        return (eofPacket.getStatus() & 0x0008) != 0;
    }
}
