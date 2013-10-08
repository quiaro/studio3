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

package org.craftercms.studio.impl.repository.mongodb.datarepos;

import java.util.List;

import org.craftercms.studio.impl.repository.mongodb.domain.Node;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * Spring data Repository for Node Objects.
 * Implemented by Spring wizardly.
 */
public interface NodeDataRepository extends MongoRepository<Node, String> {
    /**
     * Finds all the Nodes with a given parent.
     *
     *
     * @param parent Parent to search for.
     * @return A List of nodes parent equals given parent.<br/>
     * Empty list if none where found.
     */
    List<Node> findAllByAncestors(List<Node> parent);

    /**
     * Finds the Root Node.
     * <b> In the case that for some external/bug there is multiple
     * root nodes, by default </b>
     *
     * @return Returns the Root node
     */
    @Query("{ ancestors: [] }")
    //Has no ancestors AKA root
    Node findRootNode();

    /**
     * Finds all Nodes with a given Name.
     *
     * @param name Name of the Node
     * @return A list of nodes where the name is the given, <br/> empty list
     * if nothing is found.
     */
    //List<Node> findAllByMetadataNodeName(String name);

    Node findNodeByAncestorsAndMetadataCoreNodeName(List<Node> ancestors, String nodeName);
}
