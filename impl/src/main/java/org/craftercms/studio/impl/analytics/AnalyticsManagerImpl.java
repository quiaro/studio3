package org.craftercms.studio.impl.analytics;

import java.util.Map;

import org.craftercms.studio.api.analytics.AnalyticsManager;
import org.craftercms.studio.api.analytics.AnalyticsReport;
import org.craftercms.studio.api.security.Context;

/**
 *
 * @author Sumer Jabri
 */
public class AnalyticsManagerImpl implements AnalyticsManager {
    public AnalyticsManagerImpl() {
    }

    /**
     *  * {@inheritDoc}
     */
    @Override
    public AnalyticsReport report(final Context context, final String site, final Map<String, Object> params) {
        return null;  //Todo change body of implemented methods use File | Settings | File Templates.
    }
}
