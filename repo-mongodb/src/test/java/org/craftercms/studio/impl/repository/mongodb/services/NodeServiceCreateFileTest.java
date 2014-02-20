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

import org.bson.types.ObjectId;
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
 * Test for create a File Node/
 */
public class NodeServiceCreateFileTest {


    /**
     * Node Service (the one to be tested)
     */
    private NodeServiceImpl nodeService;

    private GridFSService gridFSService;

    private MongodbDataService mongodbDataService;

    @Before
    public void setUp() throws Exception {
        nodeService = new NodeServiceImpl();
        mongodbDataService = mock(MongodbDataService.class);
        gridFSService = mock(GridFSService.class);
        nodeService.setDataService(mongodbDataService);
        nodeService.setGridFSService(gridFSService);
        // Return the same save object.

    }

    @Test
    public void testCreateFile() throws Exception {

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(mongodbDataService).save(Mockito.anyString(), Mockito.any(Node.class));

        when(mongodbDataService.findOne(Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Class.class))).thenReturn(new Node(new Node(), NodeType.FOLDER));

        when(gridFSService.createFile(Mockito.anyString(), (InputStream)Mockito.any())).thenReturn(new ObjectId()
            .toString());


        InputStream testInput = NodeServiceCreateFileTest.class.getResourceAsStream("/files/index.xml");
        Node fileNode = nodeService.createFileNode(nodeService.getRootNode(), "TestFile", "test file",
            "Doctor John A. Zoidberg", testInput);
        Assert.assertNotNull(fileNode);
        Assert.assertEquals(fileNode.getCore().getCreator(), "Doctor John A. Zoidberg");
        Assert.assertEquals(fileNode.getCore().getNodeName(), "TestFile");
        TestUtils.isUUIDValid(fileNode.getId());
        Assert.assertTrue(nodeService.isNodeFile(fileNode));
    }


    @Test(expected = MongoRepositoryException.class)
    public void testCreateFileGridFSError() throws Exception {
        when(mongodbDataService.findOne(Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Class.class))).thenReturn(new Node(new Node(), NodeType.FOLDER));

        doThrow(MongoRepositoryException.class).when(gridFSService).createFile(Mockito.anyString(),
            (InputStream)Mockito.any());
        InputStream testInput = NodeServiceCreateFileTest.class.getResourceAsStream("/files/index.xml");
        nodeService.createFileNode(nodeService.getRootNode(), "TestFile", "Test File", "Doctor John A. Zoidberg",
            testInput);

    }


    @Test(expected = MongoRepositoryException.class)
    public void testCreateFileModeServiceError() throws Exception {
        when(mongodbDataService.findOne(Mockito.anyString(), Mockito.anyString(),
            Mockito.any(Class.class))).thenReturn(new Node(new Node(), NodeType.FOLDER));
        doThrow(MongoRepositoryException.class).when(mongodbDataService).save(Mockito.anyString(),
            Mockito.any(Node.class));
        when(gridFSService.createFile(Mockito.anyString(), (InputStream)Mockito.any())).thenReturn(new ObjectId()
            .toString());
        InputStream testInput = NodeServiceCreateFileTest.class.getResourceAsStream("/files/index.xml");
        nodeService.createFileNode(nodeService.getRootNode(), "TestFile", "test File", "Doctor John A. Zoidberg",
            testInput);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testParentIsNull() throws MongoRepositoryException {
        nodeService.createFileNode(null, "TestFile", "Test File", "Doctor John A. Zoidberg", null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testParentIsNotFolder() throws MongoRepositoryException {
        Node file = new Node();
        file.setType(NodeType.FILE);
        nodeService.createFileNode(file, "TestFile", "test file", "Doctor John A. Zoidberg", null);
    }

    @Test
    public void testIsFile() throws Exception {
        Node folder = new Node();
        Node file = new Node();
        folder.setType(NodeType.FOLDER);
        file.setType(NodeType.FILE);
        Assert.assertTrue(nodeService.isNodeFile(file));
        Assert.assertFalse(nodeService.isNodeFile(folder));
    }


}
