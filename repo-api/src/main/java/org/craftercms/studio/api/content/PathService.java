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

package org.craftercms.studio.api.content;

import org.craftercms.studio.api.RepositoryException;

/**
 * @author Sumer Jabri
 */
public interface PathService {

    /**
     * Get item id by path.
     * @param ticket security ticket
     * @param site site
     * @param path item pah
     * @return item id
     */
    String getItemIdByPath(String ticket, String site,  String path);

    /**
     * Get item path by item id.
     *
     * @param ticket security ticket
     * @param site site
     * @param itemId item id
     * @return item path
     */
    String getPathByItemId(final String ticket, final String site, final String itemId) throws RepositoryException;
}
