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

import org.craftercms.studio.impl.repository.mongodb.datarepos.NodeDataRepository;
import org.craftercms.studio.impl.repository.mongodb.domain.Node;
import org.craftercms.studio.impl.repository.mongodb.domain.NodeType;
import org.craftercms.studio.impl.repository.mongodb.exceptions.MongoRepositoryException;
import org.craftercms.studio.impl.repository.mongodb.services.impl.NodeServiceImpl;
import org.craftercms.studio.impl.repository.mongodb.utils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.dao.DataIntegrityViolationException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test for Folder Create in  Node Service.
 */
public class NodeServiceCreateFolderTest {

    /**
     * Node Service (the one to be tested)
     */
    private NodeServiceImpl nodeService;
    /**
     * Node Repo Mock
     */
    private NodeDataRepository nodeDataRepository;

    @Before
    public void setUp() throws Exception {
        nodeService = new NodeServiceImpl();
        nodeDataRepository = mock(NodeDataRepository.class);
        nodeService.setNodeDataRepository(nodeDataRepository);
        // Return the same save object.
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParentNodeIsNotFolder() throws Exception {
        Node badParent = new Node();
        badParent.setType(NodeType.FILE);
        nodeService.createFolderNode(badParent, "TestBadParent", "Philip J. Fry,");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParentIsNull() throws Exception {
        nodeService.createFolderNode(null, "TestBadParent", "Philip J. Fry,");
    }

    @Test()
    public void testCreate() throws Exception {
        when(nodeDataRepository.save(Mockito.any(Node.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                return invocation.getArguments()[0];
            }
        });
        Node parent = new Node();
        parent.setType(NodeType.FOLDER);
        Node node = nodeService.createFolderNode(parent, "TestFolder", "Philip J. Fry");
        Assert.assertNotNull(node);
        Assert.assertNotNull(node.getMetadata());
        Assert.assertEquals(node.getMetadata().getCore().getCreator(), "Philip J. Fry");
        Assert.assertEquals(node.getMetadata().getCore().getNodeName(), "TestFolder");
        Assert.assertEquals(node.getParent(), parent);
        TestUtils.isUUIDValid(node.getId());
        Assert.assertTrue(nodeService.isNodeFolder(node));
    }

    @Test(expected = MongoRepositoryException.class)
    public void testCreateDataException() throws Exception {
        when(nodeDataRepository.save(Mockito.any(Node.class))).thenThrow(DataIntegrityViolationException.class);
        Node parent = new Node();
        parent.setType(NodeType.FOLDER);
        Node node = nodeService.createFolderNode(parent, "TestFolder", "Philip J. Fry");
        Assert.assertTrue(nodeService.isNodeFolder(node));
    }

    @Test
    public void testIsFolder() throws Exception {
        Node folder = new Node();
        Node file = new Node();
        folder.setType(NodeType.FOLDER);
        file.setType(NodeType.FILE);
        Assert.assertFalse(nodeService.isNodeFolder(file));
        Assert.assertTrue(nodeService.isNodeFolder(folder));
    }



}
