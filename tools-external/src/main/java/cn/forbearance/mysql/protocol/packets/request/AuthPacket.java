package cn.forbearance.mysql.protocol.packets.request;

import cn.forbearance.mysql.protocol.socket.SocketChannel;
import cn.forbearance.utils.ByteUtil;
import cn.forbearance.utils.StreamUtil;

import java.io.IOException;

/**
 * 登录认证
 *
 * @author cristina
 */
public class AuthPacket {

    /**
     * 填充
     */
    private static final byte[] FILLER = new byte[23];

    private int packetLength;
    private byte packetId;
    private long clientFlags;
    private long maxPacketSize;
    private int charsetIndex;
    private String user;
    private byte[] password;
    private String database;

    public AuthPacket() {
    }

    public AuthPacket(int packetLength, byte packetId, long clientFlags, long maxPacketSize, int charsetIndex, String user, byte[] password, String database) {
        this.packetLength = packetLength;
        this.packetId = packetId;
        this.clientFlags = clientFlags;
        this.maxPacketSize = maxPacketSize;
        this.charsetIndex = charsetIndex;
        this.user = user;
        this.password = password;
        this.database = database;
    }

    static {
        byte[] version = "1.0.0".getBytes();
        byte[] header = ByteUtil.getBytesWithLength(version.length);
        if ((header.length + version.length) <= FILLER.length) {
            int index = 0;
            for (byte b : header) {
                FILLER[index++] = b;
            }
            for (byte b : version) {
                FILLER[index++] = b;
            }
        }
    }

    public void write(SocketChannel channel) throws IOException {
        StreamUtil.writeUb3(channel, getPacketLength());
        StreamUtil.write(channel, packetId);
        StreamUtil.writeUb4(channel, clientFlags);
        StreamUtil.writeUb4(channel, maxPacketSize);
        StreamUtil.write(channel, (byte) charsetIndex);
        channel.write(FILLER);

        if (user == null) {
            StreamUtil.write(channel, (byte) 0);
        } else {
            StreamUtil.writeWithNull(channel, user.getBytes());
        }
        if (password == null) {
            StreamUtil.write(channel, (byte) 0);
        } else {
            StreamUtil.writeWithLength(channel, password);
        }
        if (database == null) {
            StreamUtil.write(channel, (byte) 0);
        } else {
            StreamUtil.writeWithNull(channel, database.getBytes());
        }
    }

    public static byte[] getFiller() {
        return FILLER;
    }

    public int getPacketLength() {
        int size = 32;
        size += (user == null) ? 1 : user.length() + 1;
        size += (password == null) ? 1 : ByteUtil.getLengthWithBytes(password);
        size += (database == null) ? 1 : database.length() + 1;
        return size;
    }

    public byte getPacketId() {
        return packetId;
    }

    public long getClientFlags() {
        return clientFlags;
    }

    public long getMaxPacketSize() {
        return maxPacketSize;
    }

    public int getCharsetIndex() {
        return charsetIndex;
    }

    public String getUser() {
        return user;
    }

    public byte[] getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }

    public void setPacketLength(int packetLength) {
        this.packetLength = packetLength;
    }

    public void setPacketId(byte packetId) {
        this.packetId = packetId;
    }

    public void setClientFlags(long clientFlags) {
        this.clientFlags = clientFlags;
    }

    public void setMaxPacketSize(long maxPacketSize) {
        this.maxPacketSize = maxPacketSize;
    }

    public void setCharsetIndex(int charsetIndex) {
        this.charsetIndex = charsetIndex;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
