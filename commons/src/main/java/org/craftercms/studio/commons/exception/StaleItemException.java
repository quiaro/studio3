package org.craftercms.studio.commons.exception;

/**
 * @author Sumer Jabri
 */
public class StaleItemException extends StudioException {
    public StaleItemException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public StaleItemException(final Throwable cause) {
        super(cause);
    }

    public StaleItemException(final String message) {
        super(message);
    }
}
