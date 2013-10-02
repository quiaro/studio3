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

package org.craftercms.studio.impl.repository.mongodb.services;

import java.io.InputStream;
import java.util.List;

import org.craftercms.studio.impl.repository.mongodb.domain.Node;
import org.craftercms.studio.impl.repository.mongodb.exceptions.MongoRepositoryException;

/**
 * Node Services in Definition.
 * <br/>
 * <b>This class is intended to be use internally only
 * usage outside this artifact could break or corrupt the repository</b>
 */
public interface NodeService {

    /**
     * Creates a File Node.
     * This also validated that the parent node is not null
     * and Folder Type.
     *
     * @param parent      Parent Node Must be of Folder Type.
     * @param fileName    File folderName
     * @param creatorName Creator name (who create this)
     * @param content     content of this node
     * @return A new File Node.
     * @throws IllegalArgumentException <ul>
     *                                  <li>If parent is null</li>
     *                                  <li>If parent is not a Folder (Node of type Folder)</li>
     *                                  </ul>
     * @throws org.craftercms.studio.impl.repository.mongodb.exceptions.MongoRepositoryException
     *                                  If system was unable to store de node on the repository.
     */
    Node createFileNode(Node parent, String fileName, String creatorName,
                        InputStream content) throws MongoRepositoryException;

    /**
     * Create a new Folder TypeNode.
     *
     * @param parent      Parent Node Must be of Folder Type.
     * @param folderName  File folderName
     * @param creatorName Creator name (who create this)
     * @return a new Folder Node.
     * @throws IllegalArgumentException <ul>
     *                                  <li>If parent is null</li>
     *                                  <li>If parent is not a Folder (Node of type Folder)</li>
     *                                  <li>If metadata is incompatible with a Folder Node</li>
     *                                  </ul>
     * @throws org.craftercms.studio.impl.repository.mongodb.exceptions.MongoRepositoryException
     *                                  If system was unable to store de node on the repository.
     */
    Node createFolderNode(Node parent, String folderName, String creatorName) throws MongoRepositoryException;

    /**
     * Finds all nodes for a given parent.
     * Sending parent as null is equivalent to call getRootNode()
     *
     * @param parent parent to look for
     * @return Empty List if given node has no children.
     *         List of all nodes that are children of the given node.
     */
    List<Node> findNodesByParent(Node parent);

    /**
     * Gets the Root node.
     *
     * @return the Root node , <b>Never Null</b>
     */
    Node getRootNode();

    /**
     * Checks if the node is a Folder.
     *
     * @param nodeToCheck node to check
     * @return True if the node is a Folder, false otherwise.
     */
    boolean isNodeFolder(Node nodeToCheck);

    /**
     * Checks if the node is a File.
     *
     * @param nodeToCheck node to check
     * @return True if the node is a File, false otherwise.
     */
    boolean isNodeFile(Node nodeToCheck);

    /**
     * Finds a node using it's Id.
     * @param nodeId Id of the node
     * @return The node with the given ID, null if not found.
     * @throws IllegalArgumentException If nodeId is null, empty or blank
     * @throws MongoRepositoryException If is unable to find.
     */
    Node getNode(String nodeId) throws MongoRepositoryException;


}
