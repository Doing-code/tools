package cn.forbearance.utils;

import cn.forbearance.mysql.protocol.socket.SocketChannel;

import java.io.EOFException;
import java.io.IOException;

/**
 * @author cristina
 */
public class StreamUtil {

    private static final long NULL_LENGTH = -1;
    private static final byte[] EMPTY_BYTES = new byte[0];

    /**
     * 从给定的 {@link  SocketChannel} 中读取3个字节，并将其解析为一个无符号整数（UD3：Unsigned 3 Bytes）
     * <p>
     * << 8 和 << 16 的目的是将字节数组中的各个字节的值按照其在整数中的位置进行合并。
     *
     * @param in
     * @return
     */
    public static int readUb3(SocketChannel in) throws IOException {
        byte[] bytes = new byte[3];
        read(in, bytes, 0, bytes.length);
        // 通过使用位运算与0xff，确保将字节的值转换为无符号整数.
        int i = bytes[0] & 0xff;
        i |= (bytes[1] & 0xff) << 8;
        i |= (bytes[2] & 0xff) << 16;
        return i;
    }

    public static byte read(SocketChannel in) throws IOException {
        byte got = in.read();
        if (got < 0) {
            throw new EOFException();
        }
        return (byte) (got & 0xff);
    }

    public static void read(SocketChannel in, byte[] bte, int offset, int length) throws IOException {
        in.read(bte, offset, length);
    }

    public static int readUb2(SocketChannel in) throws IOException {
        byte[] b = new byte[2];
        read(in, b, 0, b.length);
        int i = b[0] & 0xff;
        i |= (b[1] & 0xff) << 8;
        return i;
    }

    public static long readLong(SocketChannel in) throws IOException {
        byte[] b = new byte[8];
        read(in, b, 0, b.length);
        long l = (long) (b[0] & 0xff);
        l |= (long) (b[1] & 0xff) << 8;
        l |= (long) (b[2] & 0xff) << 16;
        l |= (long) (b[3] & 0xff) << 24;
        l |= (long) (b[4] & 0xff) << 32;
        l |= (long) (b[5] & 0xff) << 40;
        l |= (long) (b[6] & 0xff) << 48;
        l |= (long) (b[7] & 0xff) << 56;
        return l;
    }

    public static byte[] readWithLength(SocketChannel in) throws IOException {
        int length = (int) readLength(in);
        if (length <= 0) {
            return EMPTY_BYTES;
        }
        byte[] b = new byte[length];
        read(in, b, 0, b.length);
        return b;
    }

    public static long readLength(SocketChannel in) throws IOException {
        int length = in.read();
        if (length < 0) throw new EOFException();
        switch (length) {
            case 251:
                return NULL_LENGTH;
            case 252:
                return readUb2(in);
            case 253:
                return readUb3(in);
            case 254:
                return readLong(in);
            default:
                return length;
        }
    }

    public static void write(SocketChannel out, byte b) throws IOException {
        out.write(b & 0xff);
    }

    public static void writeUb2(SocketChannel out, int i) throws IOException {
        byte[] b = new byte[2];
        b[0] = (byte) (i & 0xff);
        b[1] = (byte) (i >>> 8);
        out.write(b);
    }

    public static void writeUb3(SocketChannel out, int i) throws IOException {
        byte[] b = new byte[3];
        b[0] = (byte) (i & 0xff);
        b[1] = (byte) (i >>> 8);
        b[2] = (byte) (i >>> 16);
        out.write(b);
    }

    public static void writeUb4(SocketChannel out, long l) throws IOException {
        byte[] b = new byte[4];
        b[0] = (byte) (l & 0xff);
        b[1] = (byte) (l >>> 8);
        b[2] = (byte) (l >>> 16);
        b[3] = (byte) (l >>> 24);
        out.write(b);
    }

    public static void writeLong(SocketChannel out, long l) throws IOException {
        byte[] b = new byte[8];
        b[0] = (byte) (l & 0xff);
        b[1] = (byte) (l >>> 8);
        b[2] = (byte) (l >>> 16);
        b[3] = (byte) (l >>> 24);
        b[4] = (byte) (l >>> 32);
        b[5] = (byte) (l >>> 40);
        b[6] = (byte) (l >>> 48);
        b[7] = (byte) (l >>> 56);
        out.write(b);
    }

    public static void writeWithNull(SocketChannel out, byte[] src) throws IOException {
        out.write(src);
        out.write((byte) 0);
    }

    public static void writeWithLength(SocketChannel out, byte[] src) throws IOException {
        int length = src.length;
        if (length < 251) {
            out.write((byte) length);
        } else if (length < 0x10000L) {
            out.write((byte) 252);
            writeUb2(out, length);
        } else if (length < 0x1000000L) {
            out.write((byte) 253);
            writeUb3(out, length);
        } else {
            out.write((byte) 254);
            writeLong(out, length);
        }
        out.write(src);
    }

    public static void writeBytes(SocketChannel channel, byte[] src) throws IOException {
        channel.write(src);
    }
}
