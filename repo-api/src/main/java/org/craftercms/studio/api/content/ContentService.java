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

import java.io.InputStream;
import java.util.List;

import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.Tree;
import org.craftercms.studio.commons.filter.Filter;

/**
 * Content Service.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public interface ContentService {

    /**
     * Create new content into repository.
     * @param ticket security ticket
     * @param site site
     * @param path path
     * @param content content
     * @return content id
     */
    String create(String ticket, String site, String path, InputStream content);

    /**
     * Read content from repository.
     * @param ticket security ticket
     * @param contentId content id
     * @return content stream
     */
    InputStream read(String ticket, String contentId);

    /**
     * Update content in repository.
     * @param ticket security ticket
     * @param contentId content id
     * @param content content
     */
    void update(String ticket, String contentId, InputStream content);

    /**
     * Delete content from repository.
     * @param ticket security ticket
     * @param contentId content id
     */
    void delete(String ticket, String contentId);

    /**
     * Get children tree.
     * @param ticket security ticket
     * @param site site
     * @param contentId content id
     * @param depth tree max depth
     * @param filters result filters
     * @return children tree
     */
    Tree<Item> getChildren(String ticket, String site, String contentId, int depth, List<Filter> filters);

    /**
     * Move content from source to destination.
     * @param ticket security ticket
     * @param site site
     * @param sourceId source id
     * @param destinationId destination id
     * @param includeChildren include children
     */
    void move(String ticket, String site, String sourceId, String destinationId, boolean includeChildren);

    /**
     * Get list of sites in repository.
     * @param ticket security ticket
     * @return list of site names
     */
    List<String> getSites(String ticket);
}
