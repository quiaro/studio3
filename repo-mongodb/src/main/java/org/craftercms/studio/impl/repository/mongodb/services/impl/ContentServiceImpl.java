/*
 * Copyright (C) 2007-2013 Crafter Software Corporation.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.craftercms.studio.impl.repository.mongodb.services.impl;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.craftercms.studio.api.NotImplementedException;
import org.craftercms.studio.api.content.ContentService;
import org.craftercms.studio.api.content.PathService;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.Tree;
import org.craftercms.studio.commons.exception.InvalidContextException;
import org.craftercms.studio.commons.filter.Filter;
import org.craftercms.studio.impl.repository.mongodb.services.NodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.PropertyResolver;

/**
 * Mongo DB implementation of ContentService.
 */
public class ContentServiceImpl implements ContentService {

    /**
     * Node Service Impl.
     */
    private NodeService nodeService;
    /**
     * System properties.
     */
    private PropertyResolver properties;
    /**
     * Logger.
     */
    private Logger log = LoggerFactory.getLogger(ContentServiceImpl.class);
    /**
     * Path Service.
     */
    private PathService pathService;

    @Override
    public String create(final String ticket, final String site, final String path, final InputStream content) {
        throw new NotImplementedException("Not implemented yet");
    }

    @Override
    public String create(final String ticket, final String site, final String path) throws InvalidContextException {

        if (StringUtils.isEmpty(path) || StringUtils.isBlank(path)) {
            throw new IllegalArgumentException("Path can't be null or empty");
        }

        if (StringUtils.isEmpty(site) || StringUtils.isBlank(site)) {
            throw new IllegalArgumentException("Site can't be null or empty");
        }

        if (StringUtils.isEmpty(ticket) || StringUtils.isBlank(ticket)) {
            throw new IllegalArgumentException("Ticket can't be null or empty");
        }

        if (siteExists(ticket, site)) {
            String exist = pathService.getItemIdByPath(ticket, site, path);
            log.debug("Checking if folder/file in {} for site {} exists", path, site);
            if (exist != null) {
                log.debug("Folder/File {} exist for site {} ", path, site);
                throw new InvalidContextException("Folder named " + path + " already exist in the site {}");
            }
            return "";
        } else {
            log.debug("Site with name {} does not exist", site);
            throw new IllegalArgumentException("Site with name " + site + " does not exist");
        }

    }

    private boolean siteExists(final String ticket, final String site) {
        return nodeService.getSiteNode(site) != null;
    }

    @Override
    public InputStream read(final String ticket, final String contentId) {
        return null;
    }

    @Override
    public void update(final String ticket, final String contentId, final InputStream content) {
        throw new NotImplementedException("Not implemented yet");
    }

    @Override
    public void delete(final String ticket, final String contentId) {
        throw new NotImplementedException("Not implemented yet");
    }

    @Override
    public Tree<Item> getChildren(final String ticket, final String site, final String contentId, final int depth,
                                  final List<Filter> filters) {
        throw new NotImplementedException("Not implemented yet");
    }

    @Override
    public void move(final String ticket, final String site, final String sourceId, final String destinationId,
                     final boolean includeChildren) {
        throw new NotImplementedException("Not implemented yet");
    }

    @Override
    public List<String> getSites(final String ticket) {
        throw new NotImplementedException("Not implemented yet");
    }

    public void setNodeServiceImpl(final NodeService nodeService) {
        this.nodeService = nodeService;
    }

    public void setPropertyResolver(PropertyResolver propertyResolver) {
        this.properties = propertyResolver;
    }

    public void setPathServices(PathService pathServices) {
        this.pathService = pathServices;
    }
}
