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
package org.craftercms.studio.api.version;

import org.craftercms.studio.api.dto.Context;
import org.craftercms.studio.api.dto.DiffResult;
import org.craftercms.studio.api.dto.Tree;
import org.craftercms.studio.api.dto.Version;


/**
 * Version Manager.
 */
public interface VersionManager {

    /**
     * Get version history for item
     * @param context context
     * @param itemId itemId
     * @return tree of version history
     */
    Tree<Version> history(Context context, String itemId);

    /**
     * Revert version for item
     * @param context context
     * @param itemId itemId
     * @param revertVersion revertVersion
     */
    void revert(Context context, String itemId, String revertVersion);

    /**
     * Difference between two versions
     * @param context context
     * @param itemId item id
     * @param version1 version1
     * @param version2 version2
     * @return differences
     */
    DiffResult diff(Context context, String itemId, String version1,
                    String version2);
}
