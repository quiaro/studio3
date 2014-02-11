/*
 * Copyright (C) 2007-2013 Crafter Software Corporation.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.craftercms.studio.api.analytics;

import java.util.Map;

import org.craftercms.studio.commons.dto.AnalyticsReport;
import org.craftercms.studio.commons.dto.Context;

/**
 * Analytics Manager.
 *
 * @author Sumer Jabri
 * @author Carlos Ortiz
 */
public interface AnalyticsService {
    /**
     * Runs and return a report.
     *
     * @param context  context for the report
     * @param site     target
     * @param reportId report name
     * @param params   report parameters
     * @return the analytics report <b>never null</b>
     * @throws ItemNotFoundException if report with given named is not found or site is not found.
     * @throws ReportException       if the report can't be generated.
     */
    AnalyticsReport generateReport(Context context, String site, String reportId, Map<String,
            Object> params) throws ItemNotFoundException, ReportException;

}
