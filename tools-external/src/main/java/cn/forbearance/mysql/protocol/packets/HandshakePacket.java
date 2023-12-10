package cn.forbearance.mysql.protocol.packets;

import cn.forbearance.utils.Msc;

/**
 * 处理初始化握手报文.
 * <p>
 * 1字节：协议版本号
 * NullTerminatedString：数据库版本信息
 * 4字节：连接MySQL Server启动的线程ID
 * 8字节：挑战随机数，用于数据库认证
 * 1字节：填充值(0x00)
 * 2字节：用于与客户端协商通讯方式
 * 1字节：数据库的编码
 * 2字节：服务器状态
 * 13字节：预留字节
 * 12字节：挑战随机数，用于数据库认证
 * 1字节：填充值(0x00)
 *
 * @author cristina
 */
public class HandshakePacket extends Packet {

    /**
     * 协议版本 1个字节
     */
    private byte protocolVersion = Msc.DEFAULT_PROTOCOL_VERSION;

    /**
     * 服务器版本，字符串，NULL终止字符
     */
    private byte[] serverVersion;

    /**
     * 连接MySQL Server启动的线程ID 4个字节
     */
    private long threadId;

    /**
     * 挑战随机数 8字节，用于数据库认证，尾部有一个结束符填充值0x00.
     */
    private byte[] seed;

    /**
     * 服务器功能选项 server_capabilities, 2个字节，用于与客户协商通讯方式
     */
    private int serverCapabilities;

    /**
     * 字节编码 1个字节
     */
    private byte serverCharsetNumber;

    /**
     * 服务器状态 2个字节
     */
    private int serverStatus;
    /**
     * 12字节：挑战随机数，用于数据库认证，尾部有一个结束符填充值0x00.
     */
    private byte[] restOfScrambleBuff;

    private byte[] authPluginName;

    public void read(BinaryPacket data) {
        packetLength = data.packetLength;
        packetId = data.packetId;
        Message msg = new Message(data.getBody());
        protocolVersion = msg.read();
        serverVersion = msg.readBytesWithNull();
        threadId = msg.readUb4();
        seed = msg.readBytesWithNull();
        serverCapabilities = msg.readUb2();
        serverCharsetNumber = msg.read();
        serverStatus = msg.readUb2();
        msg.move(13);
        restOfScrambleBuff = msg.readBytesWithNull();
    }

    public byte getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(byte protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public byte[] getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(byte[] serverVersion) {
        this.serverVersion = serverVersion;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public byte[] getSeed() {
        return seed;
    }

    public void setSeed(byte[] seed) {
        this.seed = seed;
    }

    public int getServerCapabilities() {
        return serverCapabilities;
    }

    public void setServerCapabilities(int serverCapabilities) {
        this.serverCapabilities = serverCapabilities;
    }

    public byte getServerCharsetNumber() {
        return serverCharsetNumber;
    }

    public void setServerCharsetNumber(byte serverCharsetNumber) {
        this.serverCharsetNumber = serverCharsetNumber;
    }

    public int getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(int serverStatus) {
        this.serverStatus = serverStatus;
    }

    public byte[] getRestOfScrambleBuff() {
        return restOfScrambleBuff;
    }

    public void setRestOfScrambleBuff(byte[] restOfScrambleBuff) {
        this.restOfScrambleBuff = restOfScrambleBuff;
    }

    public byte[] getAuthPluginName() {
        return authPluginName;
    }

    public void setAuthPluginName(byte[] authPluginName) {
        this.authPluginName = authPluginName;
    }
}
