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
package org.craftercms.studio.api.blueprints;

import org.craftercms.studio.api.dto.Context;

import java.util.List;
import java.util.Map;

/**
 * Blueprints Manager.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public interface BlueprintsManager {

    /**
     * List blueprints.
     * @param context context
     * @param filters filters
     * @return list of blueprints
     */
    List<String> list(Context context, Map<String, Object> filters);

    /**
     * Remove blueprint.
     * @param context context
     * @param blueprint blueprint
     */
    void remove(Context context, String blueprint);

    /**
     * Import blueprint.
     * @param context context
     * @param params parameters
     */
    void importBlueprint(Context context, Map<String, Object> params);

    /**
     * Create blueprint from site.
     * @param context context
     * @param site site
     * @param blueprintName blueprint name
     * @param destination destination
     */
    void createBlueprintFromSite(Context context, String site, String blueprintName, String destination);
}
