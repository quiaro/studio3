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

import org.craftercms.studio.api.content.PathService;
import org.craftercms.studio.impl.repository.mongodb.domain.Node;
import org.craftercms.studio.impl.repository.mongodb.exceptions.MongoRepositoryException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test of PathServicesImpl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/craftercms/studio/craftercms-mongo-repository.xml")
public class ITPathServicesImpl implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private PathService pathService;

    @Before
    public void setUp() throws Exception {
        pathService = applicationContext.getBean(PathService.class);

    }

    @Test
    public void testGetPathByItem() throws Exception {
        String path = pathService.getPathByItemId("TicketID", "SITE", createSampleNodeTree());
        System.out.println("PATh" + path);
        Assert.assertEquals(path, "/Philip J. Fry/Yancy Fry, Sr./Yancy Fry/Hubert J. Farnsworth");
    }

    private String createSampleNodeTree() throws MongoRepositoryException {
        NodeService nodeService = applicationContext.getBean(NodeService.class);
        Node a = nodeService.createFolderNode(nodeService.getRootNode(), "Philip J. Fry", "TestUser");
        Node b = nodeService.createFolderNode(a, "Yancy Fry, Sr.", "TestUser");
        Node c = nodeService.createFolderNode(b, "Yancy Fry", "TestUser");
        Node d = nodeService.createFolderNode(c, "Hubert J. Farnsworth", "TestUser");
        return d.getId();

    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

