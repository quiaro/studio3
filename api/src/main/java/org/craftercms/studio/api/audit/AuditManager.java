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
package org.craftercms.studio.api.audit;

import org.craftercms.studio.api.dto.Activity;
import org.craftercms.studio.api.dto.Context;

import java.util.List;
import java.util.Map;

/**
 * Audit Manager.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public interface AuditManager {

    /**
     * Get activities.
     * @param context context
     * @param site site
     * @param filters filters
     * @return list of activities
     */
    List<Activity> getActivities(Context context, String site, Map<String, Object> filters);

    /**
     * Log activity.
     * @param context context
     * @param site site
     * @param activity activity
     */
    void logActivity(Context context, String site, Activity activity);
}
