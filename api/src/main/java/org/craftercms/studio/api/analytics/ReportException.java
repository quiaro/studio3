package org.craftercms.studio.api.analytics;

import org.craftercms.studio.commons.exception.StudioException;

/**
 * Thrown when a Analytics report could not be generated.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public class ReportException extends StudioException {

    private static final long serialVersionUID = -5539419989924182794L;

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
