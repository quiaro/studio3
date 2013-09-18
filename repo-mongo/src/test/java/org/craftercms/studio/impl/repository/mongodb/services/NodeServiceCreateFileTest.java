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

import com.mongodb.gridfs.GridFSDBFile;
import org.bson.types.ObjectId;
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
import org.springframework.dao.DataAccessResourceFailureException;

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
    private NodeDataRepository nodeDataRepository;
    private GridFSService gridFSService;

    @Before
    public void setUp() throws Exception {
        nodeService = new NodeServiceImpl();
        nodeDataRepository = mock(NodeDataRepository.class);
        gridFSService = mock(GridFSService.class);
        nodeService.setNodeDataRepository(nodeDataRepository);
        nodeService.setGridFSService(gridFSService);
        // Return the same save object.

    }

    @Test
    public void testCreateFile() throws Exception {

        when(nodeDataRepository.save(Mockito.any(Node.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                return invocation.getArguments()[0];
            }
        });
        when(nodeDataRepository.findByParentIsNull()).thenReturn(new Node(null, NodeType.FOLDER));
        when(gridFSService.saveFile(Mockito.anyString(), (InputStream)Mockito.any())).thenReturn(new TestGridFsFile());


        InputStream testInput = NodeServiceCreateFileTest.class.getResourceAsStream("classpath:/files/index.xml");
        Node fileNode = nodeService.createFileNode(nodeService.getRootNode(), "TestFile", "Doctor John A. Zoidberg",
            testInput);
        Assert.assertNotNull(fileNode);
        Assert.assertNotNull(fileNode.getMetadata());
        Assert.assertEquals(fileNode.getMetadata().getCreator(), "Doctor John A. Zoidberg");
        Assert.assertEquals(fileNode.getMetadata().getName(), "TestFile");
        Assert.assertEquals(fileNode.getParent(), nodeService.getRootNode());
        TestUtils.isUUIDValid(fileNode.getId());
        Assert.assertTrue(nodeService.isNodeFile(fileNode));
    }


    @Test(expected = MongoRepositoryException.class)
    public void testCreateFileGridFSError() throws Exception {
        when(nodeDataRepository.findByParentIsNull()).thenReturn(new Node(null, NodeType.FOLDER));
        when(nodeDataRepository.save(Mockito.any(Node.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                return invocation.getArguments()[0];
            }
        });

        when(gridFSService.saveFile(Mockito.anyString(), (InputStream)Mockito.any())).thenThrow
            (DataAccessResourceFailureException.class);


        InputStream testInput = NodeServiceCreateFileTest.class.getResourceAsStream("classpath:/files/index.xml");
        nodeService.createFileNode(nodeService.getRootNode(), "TestFile", "Doctor John A. Zoidberg",
            testInput);

    }


    @Test(expected = MongoRepositoryException.class)
    public void testCreateFileModeServiceError() throws Exception {
        when(nodeDataRepository.findByParentIsNull()).thenReturn(new Node(null, NodeType.FOLDER));
        when(nodeDataRepository.save(Mockito.any(Node.class))).thenThrow(DataAccessResourceFailureException.class);
        when(gridFSService.saveFile(Mockito.anyString(), (InputStream)Mockito.any())).thenReturn(new TestGridFsFile());
        InputStream testInput = NodeServiceCreateFileTest.class.getResourceAsStream("classpath:/files/index.xml");
       nodeService.createFileNode(nodeService.getRootNode(), "TestFile", "Doctor John A. Zoidberg",
            testInput);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testParentIsNull() throws MongoRepositoryException {
        nodeService.createFileNode(null, "TestFile", "Doctor John A. Zoidberg", null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testParentIsNotFolder() throws MongoRepositoryException {
        Node file = new Node();
        file.setType(NodeType.FILE);
        nodeService.createFileNode(file, "TestFile", "Doctor John A. Zoidberg", null);
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

    class TestGridFsFile extends GridFSDBFile {

        @Override
        public Object getId() {
            return new ObjectId();
        }
    }
}
