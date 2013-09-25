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

package org.craftercms.studio.impl.repository.mongodb.domain;

import java.util.Date;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

public class NodeTest {


    @Test
    public void testClone() throws Exception {
        Node testNode = new Node();
        testNode.setParent(new Node());
        testNode.setId(UUID.randomUUID().toString());
        testNode.setType(NodeType.FOLDER);
        testNode.getMetadata().setCreateDate(new Date());
        testNode.getMetadata().setCreator("Amy Wong");
        testNode.getMetadata().setLastModifiedDate(new Date());
        testNode.getMetadata().setModifier("Amy Wong");
        testNode.getMetadata().setNodeName("Mars U");
        Node clone = (Node)testNode.clone();
        Assert.assertEquals(testNode, clone);
        Assert.assertFalse(testNode == clone);//Should be Different mem ref
    }


    @Test
    public void testCopy() throws Exception {
        Node testNode = new Node();
        testNode.setParent(new Node());
        testNode.setId(UUID.randomUUID().toString());
        testNode.setType(NodeType.FOLDER);
        testNode.getMetadata().setCreateDate(new Date());
        testNode.getMetadata().setCreator("Amy Wong");
        testNode.getMetadata().setLastModifiedDate(new Date());
        testNode.getMetadata().setModifier("Amy Wong");
        testNode.getMetadata().setNodeName("Mars U");
        Node copy = testNode.copy();
        Assert.assertEquals(testNode, copy);
        Assert.assertFalse(testNode == copy);//Should be Different mem ref
    }
}

