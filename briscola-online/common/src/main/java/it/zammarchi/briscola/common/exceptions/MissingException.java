package it.zammarchi.briscola.common.exceptions;

/**
 * Exception to be thrown when something requested doesn't exist.
 */
public class MissingException extends Exception {
    public MissingException() {

    }

    public MissingException(String message) {
        super(message);
    }

    public MissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingException(Throwable cause) {
        super(cause);
    }
}
