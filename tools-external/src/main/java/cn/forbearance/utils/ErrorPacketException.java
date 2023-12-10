package cn.forbearance.utils;

import cn.forbearance.mysql.protocol.packets.BinaryPacket;
import cn.forbearance.mysql.protocol.packets.ErrorPacket;

/**
 * @author cristina
 */
public class ErrorPacketException extends RuntimeException {

    public ErrorPacketException() {
        super();
    }

    public ErrorPacketException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorPacketException(String message) {
        super(message);
    }

    public ErrorPacketException(Throwable cause) {
        super(cause);
    }

    public static void handleFailure(BinaryPacket bin) {
        ErrorPacket errorPacket = new ErrorPacket();
        errorPacket.read(bin);
        throw new ErrorPacketException(new String(errorPacket.getMessage()));
    }
}
