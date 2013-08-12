package org.craftercms.studio.api.analytics;

import org.craftercms.studio.api.exception.StudioException;

/**
 *  Thrown when a Analytics report could no be generate.
 */
public class ReportException extends StudioException {

    private static final long serialVersionUID = -5539419989924182794L;

    public ReportException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ReportException(final String message) {
        super(message);
    }
}
