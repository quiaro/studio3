/*
 * Copyright (C) 2007-2013 Crafter Software Corporation.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.craftercms.studio.impl.blueprints;

import java.util.List;
import java.util.Map;

import org.craftercms.studio.api.blueprints.BlueprintsManager;
import org.craftercms.studio.commons.dto.Context;

/**
 * Blueprints Manager implementation.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public class BlueprintsManagerImpl implements BlueprintsManager {

    @Override
    public List<String> list(final Context context, final Map<String, Object> filters) {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public void remove(final Context context, final String blueprint) {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public void install(final Context context, final String pluginName) {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public void createBlueprintFromSite(final Context context, final String site, final String blueprintName,
                                        final String destination) {
        throw new NotImplementedException("Not implemented yet!");
    }
}
