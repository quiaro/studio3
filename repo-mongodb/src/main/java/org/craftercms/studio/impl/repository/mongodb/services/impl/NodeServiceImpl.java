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
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.mongodb.gridfs.GridFSFile;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.craftercms.studio.api.content.PathService;
import org.craftercms.studio.impl.repository.mongodb.MongoRepositoryDefaults;
import org.craftercms.studio.impl.repository.mongodb.MongoRepositoryQueries;
import org.craftercms.studio.impl.repository.mongodb.data.MongodbDataService;
import org.craftercms.studio.impl.repository.mongodb.data.MongodbDataServiceUtils;
import org.craftercms.studio.impl.repository.mongodb.domain.CoreMetadata;
import org.craftercms.studio.impl.repository.mongodb.domain.Node;
import org.craftercms.studio.impl.repository.mongodb.domain.NodeType;
import org.craftercms.studio.impl.repository.mongodb.exceptions.MongoRepositoryException;
import org.craftercms.studio.impl.repository.mongodb.services.GridFSService;
import org.craftercms.studio.impl.repository.mongodb.services.NodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Default Implementation of {@link org.craftercms.studio.impl.repository.mongodb.services.NodeService}.
 */
public class NodeServiceImpl implements NodeService {



    /**
     * Logger.
     */
    private Logger log = LoggerFactory.getLogger(NodeServiceImpl.class);

    /**
     * Grid FS Helper Services.
     */
    private GridFSService gridFSService;

    /**
     * Path Services
     */
    private PathService pathServices;

    /**
     * String Buffer Size.
     */
    private static final int DEFAULT_BUILDER_SIZE = 512;
    /**
     * Data Service.
     */
    private MongodbDataService dataService;


    /**
     * Default CTOR.
     */
    public NodeServiceImpl() {
    }

    @Override
    public Node createFileNode(final Node parent, final String fileName, String label, final String creatorName,
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
                newNode.setCore(createNodeMetadata(fileName, creatorName, content, label));
                if (isNodeUniqueNodeInTree(newNode)) {
                    dataService.save(NODES_COLLECTION, newNode);
                    log.debug("File node  {} saved ", newNode);
                    return newNode;
                } else {
                    log.debug("Node with name {} is already exist on given tree path {}",
                        newNode.getCore().getNodeName(), newNode.getAncestors());
                    throw new IllegalArgumentException("Node named " + fileName + " Already exist in given path " +
                        parent.getCore().getNodeName());
                }
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
    public Node createFolderNode(final Node parent, final String folderName, final String folderLabel,
                                 final String creatorName) throws MongoRepositoryException {
        log.debug("Validating params for creating a new Folder Node");
        if (parent == null) {
            log.error("Trying to create a node with parent null");
            throw new IllegalArgumentException("Parent Node can't be null");
        }


        if (isNodeFolder(parent)) {
            //TODO Validate that folder with same name and ancestor does not exist.
            log.debug("Params for new Folder node are ok");
            log.debug("Generating ID and CoreMetadata");
            Node newNode = new Node(parent, NodeType.FOLDER);
            newNode.setId(UUID.randomUUID().toString());
            newNode.setCore(createBasicMetadata(folderName, creatorName, folderLabel));
            log.debug("Generated Id {} , and coreMetadata {}", newNode.getId(), newNode);
            log.debug("Saving Folder");

            if (isNodeUniqueNodeInTree(newNode)) {
                dataService.save(NODES_COLLECTION, newNode);
            } else {
                log.debug("Node with name {} is already exist on given tree path {}",
                    newNode.getCore().getNodeName(), newNode.getAncestors());
                throw new IllegalArgumentException("Node named " + folderName + " Already exist in given path " +
                    parent.getCore().getNodeName());
            }

            log.debug("Node saved, returning node {}", newNode);
            return newNode;
        } else {
            log.error("Trying to create a node with a non folder parent");
            throw new IllegalArgumentException("Parent Node has to be a folder Type");
        }
    }

    @Override
    public Iterable<Node> findNodesByParents(final List<Node> parents) throws MongoRepositoryException {
        log.debug("Finding all children of {}", parents);
        Iterable<Node> foundNodes = dataService.find(NODES_COLLECTION, Node.class,
            MongoRepositoryQueries.GET_BY_ANCESTORS, MongodbDataServiceUtils.collectionToString(nodeListToIdList
            (parents)));
        log.debug("Found {} children nodes ", foundNodes);
        return foundNodes;
    }


    @Override
    public Iterable<Node> findNodeByParent(final Node node) throws MongoRepositoryException {
        log.debug("Finding all children of {}", node);
        Iterable<Node> foundNodes = dataService.find(NODES_COLLECTION, Node.class, MongoRepositoryQueries
            .GET_BY_ANCESTORS, node.getId());
        log.debug("Found {} children nodes ", foundNodes);
        return foundNodes;
    }

    @Override
    public Node getRootNode() throws MongoRepositoryException {

        return dataService.findOne(NODES_COLLECTION, MongoRepositoryQueries.GET_ROOT_NODE, Node.class);

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
        if (StringUtils.isBlank(nodeId)) {
            log.error("Given Node Id is either null,empty or blank");
            throw new IllegalArgumentException("Node Id can't be null, empty or blank");
        }
        log.debug("Getting node by id = {}", nodeId);
        Node foundNode = dataService.findById(NODES_COLLECTION, nodeId, Node.class);
        log.debug("Found {} ", foundNode);
        return foundNode;
    }

    @Override
    public Node findNodeByAncestorsAndName(final List<Node> ancestors, final String nodeName) throws
        MongoRepositoryException {
        if (StringUtils.isBlank(nodeName)) {
            log.debug("Node name can't be empty or blank");
            throw new IllegalArgumentException("Can't search node with name either null ,empty or blank");
        }
        if (ancestors == null || ancestors.isEmpty()) {                      //Quick List of 1
            return dataService.findOne(NODES_COLLECTION, Node.class,
                MongoRepositoryQueries.GET_BY_ANCESTORS_AND_NAME, getRootNode().getId(), nodeName);
        } else {
            return dataService.findOne(NODES_COLLECTION, Node.class,
                MongoRepositoryQueries.GET_BY_ANCESTORS_AND_NAME, MongodbDataServiceUtils.collectionToString
                (nodeListToIdList(ancestors)), nodeName);
        }
    }

    @Override
    public Node getSiteNode(String siteName) throws MongoRepositoryException {
        return findNodeByAncestorsAndName(Arrays.asList(getRootNode()), siteName);
    }

    @Override
    public Node createFolderStructure(final String path, final String creator) throws MongoRepositoryException {
        String[] pathParts = path.substring(1).split(MongoRepositoryDefaults.REPO_DEFAULT_PATH_SEPARATOR_CHAR);
        Node parentNode = getRootNode();
        for (int i = 0; i < pathParts.length; i++) {
            Node pivot = findNodeByAncestorsAndName(parentNode.getAncestry(), pathParts[i]);
            if (pivot == null) {
                parentNode = createFolderNode(parentNode, pathParts[i], pathParts[i], creator);

            } else {
                parentNode = pivot;
            }
        }
        return parentNode;
    }

    @Override
    public InputStream getFile(final String fileId) throws MongoRepositoryException {
        if (StringUtils.isBlank(fileId)) {
            log.debug("File Id can't be empty or blank");
            throw new IllegalArgumentException("Can't search file if the id either null ,empty or blank");
        }
        return gridFSService.getFile(fileId);
    }

    @Override
    public Iterable<Node> getChildren(final String nodeId) throws MongoRepositoryException {
        if (!StringUtils.isBlank(nodeId)) {
            Node parent = getNode(nodeId);
            return findNodesByParents(Arrays.asList(parent));
        }
        return null;
    }

    @Override
    public String getNodePath(Node node) throws MongoRepositoryException {
        //make it bigger so it will not have to resize it for a bit.
        StringBuilder builder = new StringBuilder(DEFAULT_BUILDER_SIZE);
        //First Add the Node with the given ID

        Iterator<Node> nodeListIterator = (Iterator<Node>)dataService.find(NODES_COLLECTION, Node.class,
            MongoRepositoryQueries.GET_ANCESTORS, MongodbDataServiceUtils.collectionToString(node.getAncestors()));
        while (nodeListIterator.hasNext()) {
            Node tmpNode = nodeListIterator.next();
            builder.append(tmpNode.getCore().getNodeName());
        }
        if (!node.getCore().getNodeName().equals(MongoRepositoryDefaults.REPO_DEFAULT_PATH_SEPARATOR_CHAR)) {
            builder.append(MongoRepositoryDefaults.REPO_DEFAULT_PATH_SEPARATOR_CHAR);
        }
        builder.append(node.getCore().getNodeName());
        String path = builder.toString();
        log.debug("Calculated Path is {}", path);
        return path;
    }

    @Override
    public void countRootNodes() {
        long count = dataService.getCollection(NODES_COLLECTION).count(MongoRepositoryQueries.GET_ROOT_NODE);
        if (count > 1) {
            log.error("Found {} root nodes, stopping repository to prevent it's corruption or data loses", count);
            throw new IllegalStateException("Multiple Root Nodes found");
        }
    }

    //ToDo JDK8 Do this in a nice Lambda Exp.
    private List<String> nodeListToIdList(final List<Node> nodes) {
        List ids = ListUtils.transformedList(nodes, new NodesToIdTransformer());
        return ids;
    }


    private boolean isNodeUniqueNodeInTree(Node nodeToValidate) throws MongoRepositoryException {
        return dataService.findOne(NODES_COLLECTION, Node.class, MongoRepositoryQueries.GET_BY_ANCESTORS_AND_NAME,
            nodeToValidate.getId(), nodeToValidate.getCore().getNodeName()) == null;
    }

    private CoreMetadata createNodeMetadata(final String fileName, final String creatorName,
                                            final InputStream content, final String folderLabel) throws
        MongoRepositoryException {
        CoreMetadata coreMetadata = createBasicMetadata(fileName, creatorName, folderLabel);
        GridFSFile savedFile = gridFSService.saveFile(fileName, content);
        coreMetadata.setSize(savedFile.getLength());
        coreMetadata.setFileId(savedFile.getId().toString());

        return coreMetadata;
    }

    private CoreMetadata createBasicMetadata(final String fileName, final String creatorName,
                                             final String folderLabel) {
        CoreMetadata coreMetadata = new CoreMetadata();
        coreMetadata.setNodeName(fileName);
        coreMetadata.setCreator(creatorName);
        coreMetadata.setCreateDate(new Date());
        coreMetadata.setLastModifiedDate(new Date());
        coreMetadata.setModifier(creatorName);
        coreMetadata.setLabel(folderLabel);
        return coreMetadata;
    }


    public void setGridFSService(final GridFSService gridFSService) {
        this.gridFSService = gridFSService;
    }

    public void setPathServices(PathService pathServices) {
        this.pathServices = pathServices;
    }

    public void setDataService(MongodbDataService dataService) {
        this.dataService = dataService;
    }


    class NodesToIdTransformer implements Transformer {

        @Override
        public Object transform(final Object input) {
            return ((Node)input).getId();
        }
    }
}
