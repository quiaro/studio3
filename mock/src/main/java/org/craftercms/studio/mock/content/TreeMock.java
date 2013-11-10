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

package org.craftercms.studio.mock.content;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.Tree;
import org.craftercms.studio.commons.dto.TreeNode;

/**
 * Tree DTO mock.
 *
 * @author Dejan Brkic
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "tree")
public class TreeMock {

    //private Tree<Item> tree;
    @XmlElement(name = "rootNode")
    private TreeNodeMock rootNode;

    public TreeMock() {
    }

    public TreeMock(final TreeNodeMock rootNode) {
        this.rootNode = rootNode;
        //this.tree = new Tree<Item>(rootNode);
    }

    public TreeMock(final Item root) {
        TreeNodeMock rootNode = new TreeNodeMock(root, null, null);
        this.rootNode = rootNode;
        //this.tree = new Tree<>(root);
    }

    public TreeNodeMock getRootNode() {
        return rootNode;
    }

    public void setRootNode(final TreeNodeMock rootNode) {
        this.rootNode = rootNode;
        //if (tree == null) {
        //    tree = new Tree<>();
        //}
        //tree.setRootNode(rootNode);
    }


}
