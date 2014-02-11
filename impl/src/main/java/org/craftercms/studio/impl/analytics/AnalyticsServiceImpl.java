package org.craftercms.studio.impl.analytics;

import java.util.Map;

import org.craftercms.studio.api.analytics.AnalyticsService;
import org.craftercms.studio.api.analytics.ReportException;
import org.craftercms.studio.commons.dto.AnalyticsReport;
import org.craftercms.studio.commons.dto.Context;

/**
 * {@link AnalyticsService} default implementation.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public class AnalyticsServiceImpl implements AnalyticsService {
    public AnalyticsServiceImpl() {
    }


    //@Override
    public AnalyticsReport report(final Context context, final String site,
                                  final String report, final Map<String, Object> params)
    {
        return null;
    }

    @Override
    public AnalyticsReport generateReport(final Context context, final String site, final String reportId, final
    Map<String, Object> params) throws ItemNotFoundException, ReportException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

