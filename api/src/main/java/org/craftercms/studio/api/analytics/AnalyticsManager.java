package org.craftercms.studio.api.analytics;

import java.util.Map;

import org.craftercms.studio.api.dto.AnalyticsReport;
import org.craftercms.studio.api.security.Context;

/**
 * Analytics management API
 *
 * @author Sumer Jabri
 */
public interface AnalyticsManager {
    /**
     * Generate an analytics report
     *
     * @param context context for the report
     * @param site target website
     * @param params report paramters
     * @return the analytics report
     */
    AnalyticsReport report(Context context, String site, Map<String, Object> params);
}
