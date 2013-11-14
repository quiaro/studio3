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

package org.craftercms.studio.impl.repository.mongodb.services.impl;

import java.util.UUID;

import org.craftercms.studio.api.content.PathService;
import org.craftercms.studio.impl.repository.mongodb.domain.Node;
import org.craftercms.studio.impl.repository.mongodb.domain.NodeType;
import org.craftercms.studio.impl.repository.mongodb.services.NodeService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.env.PropertyResolver;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

/**
 * ContentServiceImpl testing
 */
public class ContentServiceImplTest {


    private ContentServiceImpl contentService;
    private PathService pathService;
    private NodeService nodeService;
    private PropertyResolver propertyResolver;

    @Before
    public void setUp() throws Exception {
        contentService = new ContentServiceImpl();


        pathService = mock(PathService.class);
        contentService.setPathServices(pathService);
        nodeService = mock(NodeService.class);
        contentService.setNodeServiceImpl(nodeService);
        propertyResolver = mock(PropertyResolver.class);
        reset(pathService, nodeService, propertyResolver);
    }

    @Test
    public void testCreate() throws Exception {
//        Item mock = new Item();
//        mock.setFileName("robot-hell");
//        mock.setLabel("Robot Hell");
//        when(pathService.isPathValid(Mockito.anyString())).thenReturn(true);
//        when(nodeService.getNode(Mockito.anyString())).thenReturn(newMockNode());
//        when(nodeService.createFolderNode(Mockito.any(Node.class), Mockito.anyString(), Mockito.anyString(),
//            Mockito.anyString())).thenAnswer(new Answer<Object>() {
//            @Override
//            public Object answer(final InvocationOnMock invocation) throws Throwable {
//                return invocation.getArguments()[0];
//            }
//        });
//        when(nodeService.isNodeFolder(Mockito.any(Node.class))).thenReturn(true);
//        when(pathService.getPathByItemId(Mockito.anyString(), Mockito.anyString(),
//            Mockito.anyString())).thenReturn("/test/test");
//        Item item = contentService.create("ATicket", "test", "/test", mock);
//        Assert.assertNotNull(item);
//        Assert.assertEquals(item.getPath(), "/test/test");
    }




    private Node newMockNode() {
        Node root = new Node();
        root.getCore().setLabel("root");
        root.getCore().setNodeName("/");
        root.setType(NodeType.FOLDER);
        Node testRoot = new Node(root, NodeType.FOLDER);
        testRoot.getCore().setLabel("Test Root");
        testRoot.getCore().setNodeName("test");
        testRoot.setId(UUID.randomUUID().toString());
        return testRoot;
    }


}
