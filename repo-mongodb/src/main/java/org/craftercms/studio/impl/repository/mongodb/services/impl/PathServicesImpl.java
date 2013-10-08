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

import java.util.ListIterator;

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

        if (StringUtils.isBlank(ticket)) {
            log.debug("Given Ticket is blank or empty");
            throw new IllegalArgumentException("Given Ticket is Blank or empty");
        }

        if (StringUtils.isBlank(site)) {
            log.debug("Given Site is blank or empty");
            throw new IllegalArgumentException("Given Site is Blank or empty");
        }

        if (!isPathValid(path)){
            log.debug("Given Path is blank or empty");
            throw new IllegalArgumentException("Given Path is Blank or empty");
        }


        log.debug("Converting {} to a path object", path);
        log.debug("Walking down the tree ");
        String[] pathToDescent = path.substring(1).split(MongoRepositoryDefaults.REPO_DEFAULT_PATH_SEPARATOR_CHAR);
        Node foundNode = walkDownTheTree(pathToDescent);
        if (foundNode != null) {
            log.debug("Found a Node with path {} ,node is {}", path, foundNode);
            return foundNode.getId();
        } else {
            log.debug("Node with path {} was not found", path);
            return null;
        }
    }

    @Override
    public String getPathByItemId(final String ticket, final String site,
                                  final String itemId) throws RepositoryException {


        if (StringUtils.isBlank(ticket)) {
            log.debug("Given Ticket is blank or empty");
            throw new IllegalArgumentException("Given Ticket is Blank or empty");
        }
        if (StringUtils.isBlank(site)) {
            log.debug("Given Site is blank or empty");
            throw new IllegalArgumentException("Given Site is Blank or empty");
        }
        if (StringUtils.isBlank(itemId)) {
            log.debug("Given Item ID is blank or empty");
            throw new IllegalArgumentException("Given Path is Blank or empty");
        }


        log.debug("Calculating Path for item with id {}", itemId);
        Node node = nodeService.getNode(itemId);
        if (node == null) {
            log.debug("Node with ID {} could not be found", itemId);
            return null;
        } else {
            log.debug("Node Found {}", node);
            //make it bigger so it will not have to resize it for a bit.
            StringBuilder builder = new StringBuilder(DEFAULT_BUILDER_SIZE);
            //First Add the Node with the given ID

            ListIterator<Node> nodeListIterator = node.getAncestors().listIterator();
            while (nodeListIterator.hasNext()) {
                Node tmpNode = nodeListIterator.next();
                if (nodeListIterator.previousIndex()>0) { // if don't have next is the root node, ignore it
                    builder.append(MongoRepositoryDefaults.REPO_DEFAULT_PATH_SEPARATOR_CHAR);
                    builder.append(tmpNode.getMetadata().getCore().getNodeName());

                }

            }
            builder.append(MongoRepositoryDefaults.REPO_DEFAULT_PATH_SEPARATOR_CHAR);
            builder.append(node.getMetadata().getCore().getNodeName());
            String path = builder.toString();
            log.debug("Calculated Path is {}", path);
            return path;
        }
    }

    @Override
    public boolean isPathValid(final String path) {
        if (StringUtils.isBlank(path)){
            return false;
        }
        return path.matches(MongoRepositoryDefaults.PATH_VALIDATION_REGEX);
    }

    @Override
    public String fullPathFor(final String site, final String path) {
        StringBuilder builder = new StringBuilder();
        builder.append(MongoRepositoryDefaults.REPO_DEFAULT_PATH_SEPARATOR_CHAR);
        builder.append(site);
        builder.append(path);
        return builder.toString();
    }

    private Node walkDownTheTree(String[] pathToDescent) {
        Node tempNode = nodeService.getRootNode();
        for (int i = 0; i < pathToDescent.length; i++) {
            tempNode = nodeService.findNodeByAncestorsAndName(tempNode.getAncestors(), pathToDescent[i]);
            if (tempNode == null) {
                break;
            }
        }
        return tempNode;
    }

    public void setNodeServiceImpl(final NodeService nodeService) {
        this.nodeService = nodeService;
    }
}
