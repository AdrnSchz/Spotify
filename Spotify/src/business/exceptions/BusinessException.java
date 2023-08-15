package business.exceptions;

/**
 * Exception thrown by the business layer.
 *
 * @author Group 6
 * @version 1.0
 */
public class BusinessException extends Exception {
    public BusinessException(String msg, Throwable cause) {
        super(msg, cause);
    }
    public BusinessException(String msg) {
        super(msg);
    }
    public BusinessException(Throwable cause) {
        this(cause.getMessage(), cause);
    }
}
