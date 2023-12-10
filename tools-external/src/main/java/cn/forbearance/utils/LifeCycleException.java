package cn.forbearance.utils;

/**
 * @author cristina
 */
public class LifeCycleException extends RuntimeException {

    public LifeCycleException() {
        super();
    }

    public LifeCycleException(String message, Throwable cause) {
        super(message, cause);
    }

    public LifeCycleException(String message) {
        super(message);
    }

    public LifeCycleException(Throwable cause) {
        super(cause);
    }
}
