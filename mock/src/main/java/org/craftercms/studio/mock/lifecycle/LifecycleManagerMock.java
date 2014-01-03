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

package org.craftercms.studio.mock.lifecycle;

import java.util.List;

import org.craftercms.studio.api.lifecycle.Action;
import org.craftercms.studio.api.lifecycle.LifecycleManager;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.exception.NotImplementedException;

/**
 * Lifecycle Manager mock implementation.
 *
 * @author Dejan Brkic
 */
public class LifecycleManagerMock implements LifecycleManager {

    @Override
    public List<Action> getPossibleActions(final Context context, final String site, final List<String> itemIds) {
        throw new NotImplementedException("Not implemented yet!");
    }
}
