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
import java.util.UUID;

import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.impl.repository.mongodb.data.MongodbDataService;
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


import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
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
    private MongodbDataService dataService;

    @Before
    public void setUp() throws Exception {
        nodeService = new NodeServiceImpl();
        dataService = mock(MongodbDataService.class);
        nodeService.setDataService(dataService);
        // Return the same save object.
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParentNodeIsNotFolder() throws Exception {
        Node badParent = new Node();
        badParent.setType(NodeType.FILE);
        nodeService.createFolderNode(badParent, "TestBadParent","Test Bad Parent", "Philip J. Fry,");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParentIsNull() throws Exception {
        nodeService.createFolderNode(null, "TestBadParent","Test bad Parent", "Philip J. Fry,");
    }

    @Test()
    public void testCreate() throws Exception {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(dataService).save(Mockito.anyString(), Mockito.any(Node.class));

        Node parent = new Node();
        String nodeId = UUID.randomUUID().toString();
        parent.setType(NodeType.FOLDER);
        parent.setId(nodeId);
        Node node = nodeService.createFolderNode(parent, "TestFolder","Test Folder" ,"Philip J. Fry");
        Assert.assertNotNull(node);
        Assert.assertNotNull(node);
        Assert.assertEquals(node.getCore().getCreator(), "Philip J. Fry");
        Assert.assertEquals(node.getCore().getNodeName(), "TestFolder");
        Assert.assertEquals(node.getParentId(), nodeId);
        TestUtils.isUUIDValid(node.getId());
        Assert.assertTrue(nodeService.isNodeFolder(node));
    }

    @Test(expected = MongoRepositoryException.class)
    public void testCreateDataException() throws Exception {
        doThrow(MongoRepositoryException.class).when(dataService).save(Mockito.anyString(), Mockito.any(Node.class));
        Node parent = new Node();
        parent.setType(NodeType.FOLDER);
        Node node = nodeService.createFolderNode(parent, "TestFolder","Test Folder", "Philip J. Fry");
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
