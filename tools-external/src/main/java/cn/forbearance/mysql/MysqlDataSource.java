package cn.forbearance.mysql;

import cn.forbearance.mysql.protocol.packets.*;
import cn.forbearance.mysql.protocol.packets.request.Auth323Packet;
import cn.forbearance.mysql.protocol.packets.request.AuthPacket;
import cn.forbearance.mysql.protocol.socket.SocketChannel;
import cn.forbearance.mysql.protocol.socket.SocketChannelPool;
import cn.forbearance.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.NoSuchAlgorithmException;

/**
 * @author cristina
 */
public class MysqlDataSource extends AbstractBaseLifeCycle {

    private static final Logger log = LoggerFactory.getLogger(MysqlDataSource.class);

    private InetSocketAddress address;
    private String username;
    private String password;

    /**
     * 1024 表示 1KB，1024*1024 表示 1MB，1024 * 1024 * 16 表示 16MB
     */
    private static final long MAX_PACKET_SIZE = 1024 * 1024 * 16;

    private static final long CLIENT_FLAGS = getClientFlags();

    private MysqlConnection conn = null;

    public MysqlDataSource(String address, int port, String username, String password) {
        this.address = new InetSocketAddress(address, port);
        this.username = username;
        this.password = password;
    }

    public MysqlConnection getConnection() throws IOException {
        if (conn != null) return conn;

        SocketChannel channel = SocketChannelPool.open(address);
        conn = new MysqlConnection(channel, doAuthenticate(channel));
        return conn;
    }

    /**
     * 登陆认证：数据库中保存的密码是用 SHA1(SHA1(password)) 加密的
     *
     * @param channel
     * @return
     */
    private long doAuthenticate(SocketChannel channel) throws IOException {
        BinaryPacket greeting = new BinaryPacket();
        greeting.read(channel);
        HandshakePacket hp = new HandshakePacket();
        // 初始化报文
        hp.read(greeting);
        BinaryPacket authResp = null;
        try {
            authResp = auth411(hp, channel);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("权限验证发生异常", e);
        }
        log.info("线程ID {}", hp.getThreadId());

        switch (authResp.getBody()[0]) {
            case OkPacket.FIELD_COUNT:
                break;
            case ErrorPacket.FIELD_COUNT:
                ErrorPacket err = new ErrorPacket();
                err.read(authResp);
                throw new ErrorPacketException(new String(err.getMessage()));
            case EofPacket.FIELD_COUNT:
                auth323(authResp.getPacketId(), hp.getSeed(), channel);
                break;
            default:
                throw new UnknownPacketException(authResp.toString());
        }

        return hp.getThreadId();
    }

    private BinaryPacket auth411(HandshakePacket hp, SocketChannel channel) throws IOException, NoSuchAlgorithmException {
        AuthPacket ap = new AuthPacket();
        // 请求序号，前面初始化握手报文为0此处递增一位
        ap.setPacketId((byte) 1);
        // 权能标志 ap.clientFlags = CLIENT_FLAGS;
        ap.setClientFlags(CLIENT_FLAGS);
        // 请求报文支持的最大长度值
        ap.setMaxPacketSize(MAX_PACKET_SIZE);
        // 字符编码
        ap.setCharsetIndex(hp.getServerCharsetNumber() & 0xff);
        ap.setUser(username);

        String pwd = password;
        if (pwd != null && pwd.length() > 0) {
            byte[] password = pwd.getBytes();
            byte[] seed = hp.getSeed();
            byte[] restOfScramble = hp.getRestOfScrambleBuff();
            byte[] authSeed = new byte[seed.length + restOfScramble.length];
            System.arraycopy(seed, 0, authSeed, 0, seed.length);
            System.arraycopy(restOfScramble, 0, authSeed, seed.length, restOfScramble.length);
            ap.setPassword(SecurityUtil.scramble411(password, authSeed));
        }
        ap.write(channel);
        BinaryPacket bin = new BinaryPacket();
        bin.read(channel);
        return bin;
    }

    private void auth323(byte packetId, byte[] seed, SocketChannel channel) throws IOException {
        Auth323Packet a323 = new Auth323Packet();
        a323.setPacketId(packetId++);
        String passwd = password;
        if (passwd != null && passwd.length() > 0) {
            a323.setSeed(SecurityUtil.scramble323(passwd, new String(seed)).getBytes());
        }
        a323.write(channel);
        BinaryPacket bin = new BinaryPacket();
        bin.read(channel);
        switch (bin.getBody()[0]) {
            case OkPacket.FIELD_COUNT:
                break;
            case ErrorPacket.FIELD_COUNT:
                ErrorPacket err = new ErrorPacket();
                err.read(bin);
                throw new ErrorPacketException(new String(err.getMessage()));
            default:
                throw new UnknownPacketException(bin.toString());
        }

    }

    private static long getClientFlags() {
        int flag = 0;
        flag |= Capabilities.CLIENT_LONG_PASSWORD;
        flag |= Capabilities.CLIENT_FOUND_ROWS;
        flag |= Capabilities.CLIENT_LONG_FLAG;
        flag |= Capabilities.CLIENT_CONNECT_WITH_DB;
        // flag |= Capabilities.CLIENT_NO_SCHEMA;
        // flag |= Capabilities.CLIENT_COMPRESS;
        flag |= Capabilities.CLIENT_ODBC;
        // flag |= Capabilities.CLIENT_LOCAL_FILES;
        flag |= Capabilities.CLIENT_IGNORE_SPACE;
        flag |= Capabilities.CLIENT_PROTOCOL_41;
        flag |= Capabilities.CLIENT_INTERACTIVE;
        // flag |= Capabilities.CLIENT_SSL;
        flag |= Capabilities.CLIENT_IGNORE_SIGPIPE;
        flag |= Capabilities.CLIENT_TRANSACTIONS;
        // flag |= Capabilities.CLIENT_RESERVED;
        flag |= Capabilities.CLIENT_SECURE_CONNECTION;
        // client extension
        // flag |= Capabilities.CLIENT_MULTI_STATEMENTS;
        // flag |= Capabilities.CLIENT_MULTI_RESULTS;
        return flag;
    }

    @Override
    protected void doStart() {
        if (conn == null) {
            try {
                this.getConnection();
            } catch (IOException e) {
                throw new RuntimeException(String.format("连接数据库发生异常 %s", e.getMessage()));
            }
        }
        conn.start();
    }

    @Override
    protected void doStop() {
        conn.stop();
    }
}
