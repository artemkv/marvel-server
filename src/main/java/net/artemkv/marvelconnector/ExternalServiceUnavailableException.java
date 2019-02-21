package net.artemkv.marvelconnector;

/**
 * Thrown when the external service could not be accessed.
 * Should be used for transient errors when it is safe to retry.
 */
public class ExternalServiceUnavailableException extends Exception {
    public ExternalServiceUnavailableException() {
        super();
    }

    public ExternalServiceUnavailableException(String message) {
        super(message);
    }

    public ExternalServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExternalServiceUnavailableException(Throwable cause) {
        super(cause);
    }

    protected ExternalServiceUnavailableException(String message, Throwable cause,
                                                  boolean enableSuppression,
                                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
