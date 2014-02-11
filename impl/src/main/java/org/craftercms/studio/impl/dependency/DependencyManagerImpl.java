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

package org.craftercms.studio.impl.dependency;

import java.util.List;

import org.craftercms.studio.api.dependency.DependencyManager;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;

/**
 * Dependency manager implementation.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public class DependencyManagerImpl implements DependencyManager {

    @Override
    public List<Item> dependsOn(final Context context, final String itemId, final String operation) {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public List<Item> dependentOn(final Context context, final String itemId, final String operation) {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public List<Item> refresh(final Context context, final String itemId) {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public void add(final Context context, final String itemId, final String operation, final List<Item> items) {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public void remove(final Context context, final String itemId, final String operation, final List<Item> items) {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public void update(final Context context, final String itemId, final String operation, final List<Item> items) {
        throw new NotImplementedException("Not implemented yet!");
    }
}
