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
package org.craftercms.studio.api.dependency;

import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.Context;

import java.util.List;

/**
 * Dependency Manager.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public interface DependencyManager {

    /**
     * Return list of items that given item depends on.
     * @param context context
     * @param itemId item id
     * @param operation operation
     * @return list of items
     */
    List<Item> dependsOn(Context context, String itemId, String operation);

    /**
     * Return list of items that are dependant on given item.
     * @param context context
     * @param itemId item id
     * @param operation operation
     * @return list of items
     */
    List<Item> dependentOn(Context context, String itemId, String operation);

    /**
     * Refresh dependencies for given item.
     * @param context context
     * @param itemId item id
     * @return list of dependency items
     */
    List<Item> refresh(Context context, String itemId);

    /**
     * Add dependencies for item.
     * @param context context
     * @param itemId item id
     * @param operation operation
     * @param items items
     */
    void add(Context context, String itemId, String operation,
             List<Item> items);

    /**
     * Remove dependencies for item.
     * @param context context
     * @param itemId item id
     * @param operation operation
     * @param items items
     */
    void remove(Context context, String itemId, String operation,
                List<Item> items);

    /**
     * Update dependencies for item.
     * @param context context
     * @param itemId item id
     * @param operation operation
     * @param items items
     */
    void update(Context context, String itemId, String operation,
                List<Item> items);
}
