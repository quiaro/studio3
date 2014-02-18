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
package org.craftercms.studio.api.lifecycle;

import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.exception.StudioException;

import java.util.List;

/**
 * Lifecycle Manager.
 *
 * @author Carlos Ortiz
 */
public interface LifecycleManager {

    /**
     * List of actions allowed against items.
     * @param context context
     * @param site site
     * @param itemIds list of item ids
     * @return list of actions
     */
    List<Action> getPossibleActions(Context context, String site, List<String> itemIds) throws StudioException;
}
