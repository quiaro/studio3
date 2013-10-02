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

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.craftercms.studio.api.RepositoryException;
import org.craftercms.studio.api.content.PathService;
import org.craftercms.studio.impl.repository.mongodb.MongoRepositoryDefaults;
import org.craftercms.studio.impl.repository.mongodb.domain.Node;
import org.craftercms.studio.impl.repository.mongodb.services.NodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Default Path implementation for Mongodb repository.
 */
public class PathServicesImpl implements PathService {
    /**
     * Initial size of StringBuilder.
     */
    private static final int DEFAULT_BUILDER_SIZE = 512;
    /**
     * Node Service.
     */
    private NodeService nodeService;
    private Logger log = LoggerFactory.getLogger(PathServicesImpl.class);

    @Override
    public String getItemIdByPath(final String ticket, final String site, final String path) {
        if (StringUtils.isEmpty(ticket) || StringUtils.isBlank(ticket)) {
            log.debug("Given Ticket is blank or empty");
            throw new IllegalArgumentException("Given Ticket is Blank or empty");
        }
        if (StringUtils.isEmpty(site) || StringUtils.isBlank(site)) {
            log.debug("Given Site is blank or empty");
            throw new IllegalArgumentException("Given Site is Blank or empty");
        }
        if (StringUtils.isEmpty(path) || StringUtils.isBlank(path)) {
            log.debug("Given Path is blank or empty");
            throw new IllegalArgumentException("Given Path is Blank or empty");
        }
        log.debug("Converting {} to a path object", path);
        Path internalPath = Paths.get(path);
        log.debug("Internal Path is {}", internalPath);
        //Lets get the node by name

        return null;
    }

    @Override
    public String getPathByItemId(final String ticket, final String site,
                                  final String itemId) throws RepositoryException {
        log.debug("Calculating Path for item with id {}", itemId);
        Node node = nodeService.getNode(itemId);
        if (node == null) {
            log.debug("Node with ID {} could not be found", itemId);
            return null;
        } else {
            log.debug("Node Found {}", node);
            //make it bigger so it will not have to resize it for a bit.
            StringBuilder builder = new StringBuilder(DEFAULT_BUILDER_SIZE);
            walkTheTree(builder, node);
            String path = builder.toString();
            log.debug("Calculated Path is {}", path);
            return path;
        }
    }

    /**
     * Walks the Tree and append at start the parent , moving children forward in  the string.
     *
     * @param builder String Builder where the current string is.
     * @param node    Node to walk and append.
     */
    private void walkTheTree(final StringBuilder builder, final Node node) {
        if (node.getParent() != null) {
            //Always insert at 0 (start of the String)
            builder.insert(0, node.getMetadata().getNodeName()); // Add the Name
            builder.insert(0, MongoRepositoryDefaults.REPO_DEFAULT_PATH_SEPARATOR_CHAR); // Add the separator.
            walkTheTree(builder, node.getParent());
        } //We found '/' aka root
    }

    public void setNodeServiceImpl(final NodeService nodeService) {
        this.nodeService = nodeService;
    }
}
