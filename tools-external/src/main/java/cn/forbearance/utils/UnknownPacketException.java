package cn.forbearance.utils;

/**
 * @author cristina
 */
public class UnknownPacketException extends RuntimeException {

    public UnknownPacketException() {
        super();
    }

    public UnknownPacketException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownPacketException(String message) {
        super(message);
    }

    public UnknownPacketException(Throwable cause) {
        super(cause);
    }
}
