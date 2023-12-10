package cn.forbearance.mysql.protocol.socket;

import cn.forbearance.utils.ThrowableUtil;
import cn.hutool.core.lang.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

/**
 * @author cristina
 */
public class BioSocketChannel implements SocketChannel {

    private final Logger log = LoggerFactory.getLogger(BioSocketChannel.class);

    /**
     * 输入/输出 缓冲区 1024 字节
     */
    private static final int INPUT_STREAM_BUFFER = 1024;
    private static final int OUTPUT_STREAM_BUFFER = 1024;

    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private volatile boolean isConnected;

    public BioSocketChannel(Socket socket) {
        Assert.notNull(socket, "socket cannot be null");
        this.socket = socket;
        try {
            this.in = new BufferedInputStream(socket.getInputStream(), INPUT_STREAM_BUFFER);
            this.out = socket.getOutputStream();
            isConnected = socket.isConnected();
        } catch (IOException e) {
            log.error(ThrowableUtil.getStackTrace(e));
        }
    }

    @Override
    public void write(byte[] bytes) throws IOException {
        out.write(bytes);
    }

    @Override
    public void write(int bte) throws IOException {
        out.write(bte);
    }

    @Override
    public void read(byte[] bytes, int off, int len) throws IOException {
        // got 读取到的字节数.
        for (int got; len > 0; ) {
            got = in.read(bytes, off, len);
            if (got < 0) {
                throw new EOFException();
            }
            off += got;
            len -= got;
        }
    }

    @Override
    public byte read() throws IOException {
        int got = in.read();
        if (got < 0) {
            throw new EOFException();
        }
        return (byte) (got & 0xff);
    }

    @Override
    public boolean isConnected() {
        return isConnected;
    }

    @Override
    public void close() {
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            log.error(ThrowableUtil.getStackTrace(e));
        }
        this.isConnected = false;
    }
}
