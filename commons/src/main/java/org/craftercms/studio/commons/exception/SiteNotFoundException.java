package org.craftercms.studio.commons.exception;

/**
 * @author Sumer Jabri
 */
public class SiteNotFoundException extends StudioException {
    public SiteNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public SiteNotFoundException(final Throwable cause) {
        super(cause);
    }

    public SiteNotFoundException(final String message) {
        super(message);
    }
}
