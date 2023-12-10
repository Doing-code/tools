package cn.forbearance.mysql.executor;

import cn.forbearance.mysql.MysqlConnection;
import cn.forbearance.mysql.protocol.packets.BinaryPacket;
import cn.forbearance.mysql.protocol.packets.ErrorPacket;
import cn.forbearance.mysql.protocol.packets.OkPacket;
import cn.forbearance.mysql.protocol.packets.Packet;
import cn.forbearance.mysql.protocol.packets.request.CommandPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 更新执行器
 *
 * @author cristina
 */
public class UpdateExecutor extends MysqlExecutor {

    private static final Logger log = LoggerFactory.getLogger(UpdateExecutor.class);

    public UpdateExecutor(MysqlConnection conn) {
        super(conn);
    }

    /**
     * 更新
     *
     * @param update sql 语句
     * @return
     * @throws Exception
     */
    public OkPacket update(String update) throws Exception {
        if (!connection.isStart()) {
            throw new Exception("connection not open");
        }
        CommandPacket cmd = new CommandPacket();
        cmd.setCommand(Packet.COM_QUERY);
        cmd.setArg(update.getBytes());
        cmd.write(connection.getChannel());
        BinaryPacket receive = receive();

        if (receive.getBody()[0] < 0) {
            ErrorPacket packet = new ErrorPacket();
            packet.read(receive);
            throw new IOException(packet + "\n with command " + update);
        }

        OkPacket okPacket = new OkPacket();
        okPacket.read(receive);
        log.info(okPacket.toString());
        return okPacket;
    }
}
