package cn.forbearance.utils;

import cn.forbearance.mysql.protocol.packets.Message;

import java.util.BitSet;

/**
 * @author cristina
 */
public class DecodeUtil {

    public static int bigEndianInteger(byte[] bytes, int offset, int length) {
        int result = 0;
        for (int i = offset; i < (offset + length); i++) {
            byte b = bytes[i];
            result = (result << 8) | (b >= 0 ? (int) b : (b + 256));
        }
        return result;
    }

    private static byte[] reverse(byte[] bytes) {
        for (int i = 0, length = bytes.length >> 1; i < length; i++) {
            int j = bytes.length - 1 - i;
            byte t = bytes[i];
            bytes[i] = bytes[j];
            bytes[j] = t;
        }
        return bytes;
    }

    public static BitSet readBitSet(Message mm, int length, boolean bigEndian) {
        // according to MySQL internals the amount of storage required for N columns is INT((N+7)/8) bytes
        byte[] bytes = mm.readBytes((length + 7) >> 3);
        if (!bigEndian) {
            reverse(bytes);
        }
        BitSet result = new BitSet();
        for (int i = 0; i < length; i++) {
            if ((bytes[i >> 3] & (1 << (i % 8))) != 0) {
                result.set(i);
            }
        }
        return result;
    }

    public static BitSet readBitSet(Message mm) {
        int length = (int) mm.readLength();
        // according to MySQL internals the amount of storage required for N columns is INT((N+7)/8) bytes
        byte[] bytes = mm.readBytes((length + 7) >> 3);
        BitSet bitSet = new BitSet();
        for (int i = 0; i < length; i++) {
            if ((bytes[i >> 3] & (i << (i % 8))) != 0) {
                bitSet.set(i);
            }
        }
        return bitSet;
    }
}
