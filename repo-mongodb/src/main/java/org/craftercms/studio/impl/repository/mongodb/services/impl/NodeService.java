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
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.mongodb.gridfs.GridFSFile;
import org.apache.commons.lang.StringUtils;
import org.craftercms.studio.impl.repository.mongodb.datarepos.NodeDataRepository;
import org.craftercms.studio.impl.repository.mongodb.domain.CoreMetadata;
import org.craftercms.studio.impl.repository.mongodb.domain.Node;
import org.craftercms.studio.impl.repository.mongodb.domain.NodeType;
import org.craftercms.studio.impl.repository.mongodb.exceptions.MongoRepositoryException;
import org.craftercms.studio.impl.repository.mongodb.services.GridFSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;


/**
 * Default Implementation of {@link org.craftercms.studio.impl.repository.mongodb.services.NodeService}.
 */
public class NodeService implements org.craftercms.studio.impl.repository.mongodb.services.NodeService {
    /**
     * Logger.
     */
    private Logger log = LoggerFactory.getLogger(NodeService.class);
    /**
     * Data Repository.
     */
    private NodeDataRepository nodeDataRepository;
    /**
     * Grid FS Helper Services.
     */
    private GridFSService gridFSService;

    /**
     * Default CTOR.
     */
    public NodeService() {
    }

    @Override
    public Node createFileNode(final Node parent, final String fileName, final String creatorName,
                               final InputStream content) throws MongoRepositoryException {
        log.debug("Validating params for creating a new Folder Node");
        if (parent == null) {
            log.error("Trying to create a node with parent null");
            throw new IllegalArgumentException("Parent  of a File Node can't be null");
        }
        if (isNodeFolder(parent)) {
            log.debug("Creating File Node with params {}, {} in {}", fileName, creatorName, parent);
            Node newNode = new Node(parent, NodeType.FILE);
            newNode.setId(UUID.randomUUID().toString());
            try {
                newNode.setMetadata(createNodeMetadata(fileName, creatorName, content));
                newNode = nodeDataRepository.save(newNode);
                log.debug("File node  {} saved ", newNode);
                return newNode;
            } catch (DataAccessException ex) {
                log.error("Unable to save node {} due a DataAccessException", newNode);
                log.error("DataAccessException ", ex);
                throw new MongoRepositoryException("Unable to save Node due a DataAccessException", ex);
            } catch (MongoRepositoryException e) {
                log.error("Unable to save node {} because file was unable to be saved {}", newNode, e.toString());
                throw new MongoRepositoryException("Unable to save node because file was unable to be saved", e);
            }
        } else {
            log.error("Parent node {} is no a folder. can't create a FileNode with out a folder", parent);
            throw new IllegalArgumentException("Node " + parent.getId() + " is not a folder");
        }
    }

    @Override
    public Node createFolderNode(final Node parent, final String folderName, final String creatorName) throws
        MongoRepositoryException {
        log.debug("Validating params for creating a new Folder Node");
        if (parent == null) {
            log.error("Trying to create a node with parent null");
            throw new IllegalArgumentException("Parent Node can't be null");
        }

        if (isNodeFolder(parent)) {
            log.debug("Params for new Folder node are ok");
            log.debug("Generating ID and CoreMetadata");
            Node newNode = new Node(parent, NodeType.FOLDER);
            newNode.setId(UUID.randomUUID().toString());
            newNode.setMetadata(createBasicMetadata(folderName, creatorName));
            log.debug("Generated Id {} , and coreMetadata {}", newNode.getId(), newNode.getMetadata());
            log.debug("Saving Folder");
            try {
                newNode = nodeDataRepository.save(newNode);
            } catch (DataAccessException ex) {
                log.error("Unable to save Folder Node ", ex);
                throw new MongoRepositoryException("Unable to save Folder Node", ex);
            }
            log.debug("Node saved, returning node {}", newNode);
            return newNode;
        } else {
            log.error("Trying to create a node with a non folder parent");
            throw new IllegalArgumentException("Parent Node has to be a folder Type");
        }


    }

    @Override
    public List<Node> findNodesByParent(final Node parent) {
        log.debug("Finding all children of {}", parent);
        List<Node> foundNodes = nodeDataRepository.findAllByParent(parent);
        log.debug("Found {} children nodes ", foundNodes);
        return foundNodes;
    }

    @Override
    public Node getRootNode() {
        return nodeDataRepository.findByParentIsNull();
    }

    @Override
    public boolean isNodeFolder(final Node nodeToCheck) {
        return nodeToCheck.getType() == NodeType.FOLDER;
    }

    @Override
    public boolean isNodeFile(final Node nodeToCheck) {
        return nodeToCheck.getType() == NodeType.FILE;
    }

    @Override
    public Node getNode(final String nodeId) throws MongoRepositoryException {

        if (StringUtils.isEmpty(nodeId) || StringUtils.isBlank(nodeId)) {
            log.error("Given Node Id is either null,empty or blank");
            throw new IllegalArgumentException("Node Id can't be null, empty or blank");
        }

        log.debug("Getting node by id = {}", nodeId);
        try {
            Node foundNode = nodeDataRepository.findOne(nodeId);
            log.debug("Found {} ", foundNode);
            return foundNode;
        } catch (DataAccessException ex) {
            log.error("Unable to find Node with id {} due a DataAccessException", nodeId);
            log.error("DataAccessException is ", ex);
            throw new MongoRepositoryException("Unable to find Node ", ex);
        }
    }


    private CoreMetadata createNodeMetadata(final String fileName, final String creatorName,
                                            final InputStream content) throws MongoRepositoryException {
        CoreMetadata coreMetadata = createBasicMetadata(fileName, creatorName);
        try {
            GridFSFile savedFile = gridFSService.saveFile(fileName, content);
            coreMetadata.setSize(savedFile.getLength());
            coreMetadata.setFileId(savedFile.getId().toString());
        } catch (DataAccessException ex) {
            log.error("Unable to save {} file due a DataAccessException", fileName);
            log.error("DataAccessException thrown ", ex);
            throw new MongoRepositoryException("Unable to save file due a DataAccessException", ex);
        }
        return coreMetadata;
    }

    private CoreMetadata createBasicMetadata(final String fileName, final String creatorName) {
        CoreMetadata coreMetadata = new CoreMetadata();
        coreMetadata.setName(fileName);
        coreMetadata.setCreator(creatorName);
        coreMetadata.setCreateDate(new Date());
        coreMetadata.setLastModifiedDate(new Date());
        coreMetadata.setModifier(creatorName);
        return coreMetadata;
    }

    public void setNodeDataRepository(final NodeDataRepository nodeDataRepository) {
        this.nodeDataRepository = nodeDataRepository;
    }

    public void setGridFSService(final GridFSService gridFSService) {
        this.gridFSService = gridFSService;
    }
}
