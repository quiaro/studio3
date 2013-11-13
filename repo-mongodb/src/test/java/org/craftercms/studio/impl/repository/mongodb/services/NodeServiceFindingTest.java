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

import java.util.UUID;

import org.craftercms.studio.impl.repository.mongodb.domain.Node;
import org.craftercms.studio.impl.repository.mongodb.exceptions.MongoRepositoryException;
import org.craftercms.studio.impl.repository.mongodb.services.impl.NodeServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataAccessResourceFailureException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Node Service Test for finding nodes
 */
public class NodeServiceFindingTest {

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
    public void testFindNodeIdIsEmpty() throws Exception {
        nodeService.getNode("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindNodeIdIsWhitespaceOnlyy() throws Exception {
        nodeService.getNode("     ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindNodeIdIsNull() throws Exception {
        nodeService.getNode(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindNodeNotFound() throws Exception {
        when(nodeDataRepository.findOne(Mockito.anyString())).thenReturn(null);
        Node n = nodeService.getNode(null);
        Assert.assertNull(n);
    }

    @Test(expected = MongoRepositoryException.class)
    public void testFindNodeDataAccessException() throws Exception {
        when(nodeDataRepository.findOne(Mockito.anyString())).thenThrow(DataAccessResourceFailureException.class);
        nodeService.getNode(UUID.randomUUID().toString());
    }


    @Test
    public void testFindNode() throws Exception {
        when(nodeDataRepository.findOne(Mockito.anyString())).thenReturn(new Node());
        Node foundNode = nodeService.getNode(UUID.randomUUID().toString());
        Assert.assertNotNull(foundNode);
    }


}
