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

import org.junit.Assert;
import org.craftercms.studio.impl.repository.mongodb.domain.Node;
import org.craftercms.studio.impl.repository.mongodb.services.impl.PathServicesImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test of PathServicesImpl
 */
public class PathServicesImplTest {


    /**
     * Node Service (the one to be tested)
     */
    private NodeService nodeService;
    /**
     * Path Service Impl
     */
    private PathServicesImpl pathServices;

    @Before
    public void setUp() throws Exception {
        pathServices = new PathServicesImpl();
        nodeService = mock(NodeService.class);
        pathServices.setNodeServiceImpl(nodeService);
    }

    @Test
    public void testGetPathByItem() throws Exception {
        when(nodeService.getNode(Mockito.anyString())).thenReturn(createTree());
        String path = pathServices.getPathByItemId("","","");
        String exceptedPath="/A/B/C/D";
        Assert.assertEquals(path, exceptedPath);

    }

    private Node createTree() {
        Node root = new Node();
        root.getMetadata().setNodeName("/");
        Node a = new Node();
        a.getMetadata().setNodeName("A");
        Node b = new Node();
        b.getMetadata().setNodeName("B");
        Node c = new Node();
        c.getMetadata().setNodeName("C");
        Node d = new Node();
        d.getMetadata().setNodeName("D");
        //Making the Tree
        d.setParent(c);
        c.setParent(b);
        b.setParent(a);
        a.setParent(root);
        return d;
    }
}
