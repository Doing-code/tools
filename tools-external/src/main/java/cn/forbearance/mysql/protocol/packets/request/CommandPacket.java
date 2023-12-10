package cn.forbearance.mysql.protocol.packets.request;

import cn.forbearance.mysql.protocol.packets.Packet;
import cn.forbearance.mysql.protocol.socket.SocketChannel;
import cn.forbearance.utils.StreamUtil;

import java.io.IOException;

/**
 * 命令请求包
 *
 * @author cristina
 */
public class CommandPacket extends Packet {

    private byte command;
    private byte[] arg;

    public CommandPacket() {
    }

    public CommandPacket(byte command, byte[] arg) {
        this.command = command;
        this.arg = arg;
    }

    public void write(SocketChannel channel) throws IOException {
        StreamUtil.writeUb3(channel, getPacketLength());
        StreamUtil.write(channel, packetId);
        StreamUtil.write(channel, command);
        channel.write(arg);
    }

    protected int getPacketLength() {
        return 1 + arg.length;
    }

    public byte getCommand() {
        return command;
    }

    public void setCommand(byte command) {
        this.command = command;
    }

    public byte[] getArg() {
        return arg;
    }

    public void setArg(byte[] arg) {
        this.arg = arg;
    }
}
