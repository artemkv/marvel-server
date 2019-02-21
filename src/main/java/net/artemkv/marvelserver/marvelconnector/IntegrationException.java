package net.artemkv.marvelserver.marvelconnector;

/**
 * Thrown when the integration is broken, for example, due to misconfiguration.
 * Should be used for errors that cannot be resolved by simply retrying.
 */
public class IntegrationException extends Exception {
    public IntegrationException() {
        super();
    }

    public IntegrationException(String message) {
        super(message);
    }

    public IntegrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public IntegrationException(Throwable cause) {
        super(cause);
    }

    protected IntegrationException(String message, Throwable cause,
                                   boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
