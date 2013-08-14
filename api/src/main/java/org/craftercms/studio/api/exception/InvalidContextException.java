package org.craftercms.studio.api.exception;

/**
 * @author Sumer Jabri
 */
public class InvalidContextException extends StudioException {
    public InvalidContextException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidContextException(final String message) {
        super(message);
    }

    public InvalidContextException(final Throwable cause) {
        super(cause);
    }
}
