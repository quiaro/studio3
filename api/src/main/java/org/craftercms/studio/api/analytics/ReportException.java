package org.craftercms.studio.api.analytics;

import org.craftercms.studio.commons.exception.StudioException;

/**
 * Thrown when a Analytics report could not be generated.
 *
 * @author Carlos Ortiz
 * @author Sumer Jabri
 */
public class ReportException extends StudioException {


    private static final long serialVersionUID = 4675261806931477828L;

    public ReportException(final Throwable cause) {
        super(ErrorCode.REPORT_ERROR, cause);
    }

    public ReportException() {
        super(ErrorCode.REPORT_ERROR);
    }
}
