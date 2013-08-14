package org.craftercms.studio.commons.exception;

/**
 * @author Sumer Jabri
 */
public class ObjectNotFoundException extends StudioException {
    public ObjectNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ObjectNotFoundException(final String message) {
        super(message);
    }

    public ObjectNotFoundException(final Throwable cause) {
        super(cause);
    }
}
