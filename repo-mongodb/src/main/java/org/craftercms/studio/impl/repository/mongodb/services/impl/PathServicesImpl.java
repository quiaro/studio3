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

import java.util.LinkedList;

import org.apache.commons.lang3.StringUtils;
import org.craftercms.studio.api.RepositoryException;
import org.craftercms.studio.api.content.PathService;
import org.craftercms.studio.impl.repository.mongodb.MongoRepositoryDefaults;
import org.craftercms.studio.impl.repository.mongodb.domain.Node;
import org.craftercms.studio.impl.repository.mongodb.exceptions.MongoRepositoryException;
import org.craftercms.studio.impl.repository.mongodb.services.NodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Default Path implementation for Mongodb repository.
 */
public class PathServicesImpl implements PathService {

    /**
     * Node Service.
     */
    private NodeService nodeService;
    private Logger log = LoggerFactory.getLogger(PathServicesImpl.class);

    @Override
    public String getItemIdByPath(final String ticket, final String site, final String path) throws RepositoryException {

        if (StringUtils.isBlank(ticket)) {
            log.debug("Given Ticket is blank or empty");
            throw new IllegalArgumentException("Given Ticket is Blank or empty");
        }

        if (StringUtils.isBlank(site)) {
            log.debug("Given Site is blank or empty");
            throw new IllegalArgumentException("Given Site is Blank or empty");
        }

        if (!isPathValid(path)) {
            log.debug("Given Path is blank or empty");
            throw new IllegalArgumentException("Given Path is not a valid path");
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
            return nodeService.getNodePath(node);
        }
    }

    @Override
    public boolean isPathValid(final String path) {
        if (StringUtils.isBlank(path)) {
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

    private Node walkDownTheTree(String[] pathToDescent) throws MongoRepositoryException {

        Node tempNode = nodeService.getRootNode();
        //If pathToDescent length is 0 then you are getting root path right?
        for (int i = 0; i < pathToDescent.length; i++) {
            if (StringUtils.isBlank(pathToDescent[i])) {
                return nodeService.getRootNode();
            }
            LinkedList<Node> ancestors = (LinkedList<Node>)tempNode.getAncestors().clone();
            ancestors.addLast(tempNode);
            tempNode = nodeService.findNodeByAncestorsAndName(ancestors, pathToDescent[i]);
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
