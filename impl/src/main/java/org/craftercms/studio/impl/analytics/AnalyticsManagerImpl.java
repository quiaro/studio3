package org.craftercms.studio.impl.analytics;

import java.util.Map;

import org.craftercms.studio.api.analytics.AnalyticsManager;
import org.craftercms.studio.api.dto.AnalyticsReport;
import org.craftercms.studio.api.dto.Context;

/**
 * {@link AnalyticsManager} default implementation.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public class AnalyticsManagerImpl implements AnalyticsManager {
    public AnalyticsManagerImpl() {
    }


    @Override
    public AnalyticsReport report(final Context context, final String site,
                                  final String report, final Map<String, Object> params)
    {
        return null;
    }
}

