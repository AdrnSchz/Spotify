package persistence.exceptions;

/**
 * Exception thrown by the persistence layer.
 *
 * @author Group 6
 * @version 1.0
 */
public class PersistenceException extends Exception {
    public PersistenceException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public PersistenceException(String msg) {
        super(msg);
    }
}