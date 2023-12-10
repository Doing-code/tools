package cn.forbearance.mysql.protocol.socket;

import java.io.IOException;

/**
 * @author cristina
 */
public interface SocketChannel {

    /**
     * 写入字节数组
     *
     * @param bytes
     * @throws IOException
     */
    void write(byte[] bytes) throws IOException;

    /**
     * 写入一个字节
     *
     * @param bte
     * @throws IOException
     */
    void write(int bte) throws IOException;

    /**
     * 读取指定长度字节
     *
     * @param bytes data
     * @param off   起始位置
     * @param len   读取长度
     * @throws IOException
     */
    void read(byte[] bytes, int off, int len) throws IOException;

    /**
     * 读取一个字节
     *
     * @return
     * @throws IOException
     */
    byte read() throws IOException;

    /**
     * 是否正在连接
     *
     * @return
     */
    boolean isConnected();

    /**
     * 关闭连接
     */
    void close();
}
