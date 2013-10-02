package org.craftercms.studio.commons.exception;

/**
 * @author Sumer Jabri
 */
public class ItemAlreadyCheckedOutException extends StudioException {

    public ItemAlreadyCheckedOutException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ItemAlreadyCheckedOutException(final String message) {
        super(message);
    }

    public ItemAlreadyCheckedOutException(final Throwable cause) {
        super(cause);
    }
}
