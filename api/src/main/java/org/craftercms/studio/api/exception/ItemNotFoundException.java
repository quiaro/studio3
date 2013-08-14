package org.craftercms.studio.api.exception;

/**
 * @author Sumer Jabri
 */
public class ItemNotFoundException extends StudioException {
    private static final long serialVersionUID = -2603281531907601153L;

    public ItemNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ItemNotFoundException(final String message) {
        super(message);
    }
}
