package org.craftercms.studio.api.analytics;

import org.craftercms.studio.commons.exception.StudioException;

/**
 * Thrown when a Analytics report could not be generated.
 *
 * @author Carlos Ortiz
 * @author Sumer Jabri
 */
public class ReportException extends StudioException {

    public ReportException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ReportException(final String message) {
        super(message);
    }

    public ReportException(final Throwable cause) {
        super(cause);
    }
}
