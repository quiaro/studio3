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
import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.lang3.StringUtils;
import org.craftercms.studio.repo.RepositoryException;
import org.craftercms.studio.repo.content.ContentService;
import org.craftercms.studio.repo.content.PathService;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.commons.dto.Tree;
import org.craftercms.studio.commons.dto.TreeNode;
import org.craftercms.studio.commons.exception.InvalidContextException;
import org.craftercms.studio.commons.filter.Filter;
import org.craftercms.studio.impl.repository.mongodb.domain.CoreMetadata;
import org.craftercms.studio.impl.repository.mongodb.domain.Node;
import org.craftercms.studio.impl.repository.mongodb.exceptions.MongoRepositoryException;
import org.craftercms.studio.impl.repository.mongodb.services.NodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    //  private PropertyResolver properties;
    /**
     * Logger.
     */
    private Logger log = LoggerFactory.getLogger(ContentServiceImpl.class);
    /**
     * Path Service.
     */
    private PathService pathService;

    @Override
    public Item create(final String ticket, final String site, final String path, final Item item,
                       final InputStream content) throws RepositoryException {
        //Validates that all inputs are ok
        if (StringUtils.isBlank(ticket)) {
            throw new IllegalArgumentException("Ticket can't be null empty or whitespace");
        }
        if (StringUtils.isBlank(site)) {
            throw new IllegalArgumentException("Site can't be null empty or whitespace");
        }

        if (!pathService.isPathValid(path)) {
            throw new IllegalArgumentException("Given Path " + path + " is not valid");
        }

        if (item == null) {
            throw new IllegalArgumentException("Item can't be null");
        }


        Node parent = checkParentPath(ticket, site, path, true /* TODO: Make this a Property */, item.getCreatedBy());
        log.debug("Saving File");
        Node newFileNode = nodeService.createFileNode(parent, item.getFileName(), item.getLabel(),
            item.getCreatedBy(), content);
        if (newFileNode != null) {
            log.debug("File Was created {} ", newFileNode);
            return nodeToItem(newFileNode, ticket, site, null);
        } else {
            log.error("Folder node was not created ");
            throw new MongoRepositoryException("Unable to create a folder node, due a unknown reason");
        }

    }

    /**
     * Checks if a path exists.<br/>
     * If mkdirs is true will create the path.
     * Node information will be default.
     *
     * @param ticket Security Ticket
     * @param site   Site
     * @param path   Path to check or create
     * @param mkdirs if True creates directories, if false throws a Error.
     * @return the Node of the given path.
     * @throws MongoRepositoryException           If a Error happens while r/w
     * @throws java.lang.IllegalArgumentException if a portion of the path does not exist and mkdirs is set to false.
     */
    private Node checkParentPath(final String ticket, final String site, final String path, final boolean mkdirs,
                                 final String creator) throws RepositoryException {
        String nodeId = pathService.getItemIdByPath(ticket, site, path);
        if (!StringUtils.isBlank(nodeId)) {
            return nodeService.getNode(nodeId);
        } else {
            log.debug("Portions of {} don't exist", path);
            if (mkdirs) {
                log.debug("Mkdirs is on , Creating missing path portions");
                return nodeService.createFolderStructure(path, creator);
            } else {
                log.debug("Mkdirs is off");
                throw new IllegalArgumentException("Path " + path + " does not exist");
            }
        }
    }

    @Override
    public Item create(final String ticket, final String site, final String path,
                       final Item item) throws InvalidContextException, RepositoryException {
        //Validates that all inputs are ok
        if (StringUtils.isBlank(ticket)) {
            throw new IllegalArgumentException("Ticket can't be null empty or whitespace");
        }
        if (StringUtils.isBlank(site)) {
            throw new IllegalArgumentException("Site can't be null empty or whitespace");
        }

        if (!pathService.isPathValid(path)) {
            throw new IllegalArgumentException("Given Path " + path + " is not valid");
        }

        if (item == null) {
            throw new IllegalArgumentException("Item can't be null");
        }

        Node folderNode = checkParentPath(ticket, site, path, true/*TODO: Make this a property*/, item.getCreatedBy());
        if (folderNode != null) {
            Node createdFolder = nodeService.createFolderNode(folderNode, item.getFileName(), item.getLabel(),
                item.getCreatedBy());
            log.debug("Folder Was created {} ", folderNode);
            return nodeToItem(createdFolder, ticket, site, null);
        } else {
            log.error("Folder node was not created ");
            throw new MongoRepositoryException("Unable to create a folder node, due a unknown reason");
        }
    }

    private Item nodeToItem(final Node newNode, String ticket, String site, final InputStream inputStream) throws RepositoryException {
        CoreMetadata core = newNode.getCore();
        Item item = new Item();
        item.setPath(pathService.getPathByItemId(ticket, site, newNode.getId()));
        item.setId(new ItemId(newNode.getId()));
        item.setLastModifiedDate(core.getLastModifiedDate());
        item.setModifiedBy(core.getCreator());
        item.setCreatedBy(core.getCreator());
        item.setCreationDate(core.getCreateDate());
        item.setFolder(nodeService.isNodeFolder(newNode));
        item.setModifiedBy(core.getModifier());
        item.setRepoId(newNode.getId());
        item.setLabel(core.getLabel());
        item.setFileName(core.getNodeName());
        if (nodeService.isNodeFile(newNode)) {
            MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
            item.setMimeType(mimeTypesMap.getContentType(item.getFileName()));
        }
        item.setInputStream(inputStream);
        return item;
    }

    @Override
    public Item read(final String ticket, final String site, final String contentId) throws RepositoryException,
        InvalidContextException {

        //Validates that all inputs are ok
        if (StringUtils.isBlank(ticket)) {
            throw new IllegalArgumentException("Ticket can't be null empty or whitespace");
        }
        if (StringUtils.isBlank(contentId)) {
            throw new IllegalArgumentException("Content Id can't be null empty or whitespace");
        }

        log.debug("Finding inputstream for content with id " + contentId);

        Node item = nodeService.getNode(contentId);

        if (item == null) {
            return null;
        }
        log.debug("Content found {}", item);
        // we can't read folders
        if (nodeService.isNodeFile(item)) {
            log.debug("Content is a file");
            String fileId = item.getCore().getFileId(); //gets the file id
            // File id can't be null,empty or whitespace
            if (StringUtils.isBlank(fileId)) {
                log.error("Node {} is broken, since file id is not a valid ID", item, fileId);
                throw new MongoRepositoryException("Content with Id " + item.toString() + "Is broken since file Id "
                    + fileId + " is not a valid file id");
            }
            InputStream fileInput = nodeService.getFile(fileId);
            // Content should exist with this id, or something is broken.
            if (fileInput == null) {
                log.error("File with Id {} is not found, node is broken", fileId);
                throw new MongoRepositoryException("File with id is not found, node is broken");
            }
            //Now  finally return it .

            return nodeToItem(item, ticket, site, fileInput);
        } else {
            // can't read folders
            log.debug("Content is a folder");
            throw new InvalidContextException("Content with id " + contentId + " is a folder");
        }

    }

    @Override
    public void update(final String ticket, final Item item, final InputStream content) {

    }

    @Override
    public void delete(final String ticket, final String contentId) {

    }

    @Override
    public Tree<Item> getChildren(final String ticket, final String site, final String contentId, final int depth,
                                  final List<Filter> filters) throws RepositoryException {
        if (StringUtils.isBlank(ticket)) {
            throw new IllegalArgumentException("Ticket can't be null");
        }
        if (StringUtils.isBlank(site)) {
            throw new IllegalArgumentException("Site can't be null");
        }
        if (StringUtils.isBlank(contentId)) {
            throw new IllegalArgumentException("Content Id can't be null");
        }

        Node rootNode = nodeService.getNode(contentId);
        if (rootNode == null) {
            log.debug("Node with Id {} does not exist ", contentId);
            return null;
        }
        Tree<Item> resultTree = new Tree<>();
        TreeNode<Item> root = new TreeNode<>();
        root.setValue(nodeToItem(rootNode, ticket, site, null));
        buildChildrenTree(root, depth, rootNode, ticket, site);
        resultTree.setRootNode(root);
        return resultTree;
    }

    private void buildChildrenTree(final TreeNode<Item> root, final int depth, final Node parent,
                                   final String ticket, final String site) throws RepositoryException {
        Node templateNode = new Node();
        templateNode.setId(root.getValue().getRepoId());
        Iterable<Node> children = nodeService.findNodeByParent(templateNode);
        if (children != null) {
            for (Node child : children) {
                TreeNode<Item> leaf = new TreeNode<>();
                leaf.setValue(nodeToItem(child, ticket, site, null));
                if (depth == -1 || depth > 0) {
                    buildChildrenTree(leaf, (depth - 1), child, ticket, site);
                }
                root.addChild(leaf);
            }
        } else {
            log.debug("Could not find nodes with id {}", root.getValue().getId().toString());
        }
    }

    @Override
    public void move(final String ticket, final String site, final String sourceId, final String destinationId,
                     final boolean includeChildren) {

    }

    @Override
    public List<String> getSites(final String ticket) {
        return null;
    }

    private boolean siteExists(final String ticket, final String site) throws MongoRepositoryException {
        return nodeService.getSiteNode(site) != null;
    }

    public void setNodeServiceImpl(final NodeService nodeService) {
        this.nodeService = nodeService;
    }

    //    public void setPropertyResolver(PropertyResolver propertyResolver) {
    //        this.properties = propertyResolver;
    //    }

    public void setPathServices(PathService pathServices) {
        this.pathService = pathServices;
    }


}
