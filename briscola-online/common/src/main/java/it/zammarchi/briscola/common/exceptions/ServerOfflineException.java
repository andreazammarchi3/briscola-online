package it.zammarchi.briscola.common.exceptions;

/**
 * Exception to be thrown when the server does not respond.
 */
public class ServerOfflineException extends Exception {
    public ServerOfflineException() {

    }

    public ServerOfflineException(String message) {
        super(message);
    }

    public ServerOfflineException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerOfflineException(Throwable cause) {
        super(cause);
    }
}
