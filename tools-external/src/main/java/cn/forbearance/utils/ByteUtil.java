package cn.forbearance.utils;

import java.nio.charset.StandardCharsets;

/**
 * 字节转其它数据类型
 * <p>
 * 0xff = 1111 1111 = 2^8 - 1 = 255
 *
 * @author cristina
 */
public class ByteUtil {

    public static int byte2Int(byte[] data) {
        int l = 0;
        for (int i = 0; i < data.length; i++) {
            l |= ((data[i] & 0xff) << (i << 3));
        }
        return l;
    }

    public static long byte2Long(byte[] data) {
        long l = 0;
        for (int i = 0; i < data.length; i++) {
            l |= (data[i] & 0xff) << (i << 3);
        }
        return l;
    }

    /**
     * | 按位或  只要有一个为1则为1 否则为0
     * & 按位与 都为1则为1 否则为0
     * ^ 按位异或 相同为0 不同则为1
     * << 表示左移，不分正负数，低位补0
     * >> 表示右移，如果该数为正，则高位补0，若为负数，则高位补1
     * >>> 表示无符号右移，也叫逻辑右移，即若该数为正，则高位补0，而若该数为负数，则右移后高位同样补0
     *
     * @param length
     * @return
     */
    public static byte[] getBytesWithLength(int length) {
        byte[] bb = null;
        if (length < 251) {
            bb = new byte[1];
            bb[0] = (byte) length;
        } else if (length < 0x10000L) {
            bb = new byte[3];
            bb[0] = (byte) 252;
            bb[1] = (byte) (length & 0xff);
            bb[2] = (byte) (length >>> 8);
        } else if (length < 0x1000000L) {
            bb = new byte[4];
            bb[0] = (byte) 253;
            bb[1] = (byte) (length & 0xff);
            bb[2] = (byte) (length >>> 8);
            bb[3] = (byte) (length >>> 16);
        } else {
            bb = new byte[9];
            bb[0] = (byte) 254;
            bb[1] = (byte) (length & 0xff);
            bb[2] = (byte) (length >>> 8);
            bb[3] = (byte) (length >>> 16);
            bb[4] = (byte) (length >>> 24);
            bb[5] = (byte) (length >>> 32);
            bb[6] = (byte) (length >>> 40);
            bb[7] = (byte) (length >>> 48);
            bb[8] = (byte) (length >>> 56);
        }
        return bb;
    }

    public static int getLengthWithBytes(byte[] src) {
        int length = src.length;
        if (length < 251) {
            return 1 + length;
        } else if (length < 0x10000L) {
            return 3 + length;
        } else if (length < 0x1000000L) {
            return 4 + length;
        } else {
            return 9 + length;
        }
    }

    public static String byte2String(byte[] data) {
        if (data == null || data.length <= 0) {
            return "";
        }
        return new String(data, StandardCharsets.UTF_8);
    }
}
