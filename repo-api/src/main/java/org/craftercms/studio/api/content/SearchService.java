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

import java.util.List;

import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.filter.Filter;

/**
 * Search Service.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public interface SearchService {

    /**
     * Find items.
     * @param ticket security ticket
     * @param query query string
     * @param filters filters
     * @return list of items
     */
    List<Item> find(String ticket, String query, List<Filter> filters);
}
