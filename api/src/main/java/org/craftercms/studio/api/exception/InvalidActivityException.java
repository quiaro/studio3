package org.craftercms.studio.api.exception;

/**
 * @author Sumer Jabri
 */
public class InvalidActivityException extends StudioException {
    public InvalidActivityException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidActivityException(final String message) {
        super(message);
    }

    public InvalidActivityException(final Throwable cause) {
        super(cause);
    }
}
