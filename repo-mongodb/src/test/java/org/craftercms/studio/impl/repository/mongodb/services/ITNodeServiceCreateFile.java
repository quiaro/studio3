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

import org.junit.Assert;
import org.craftercms.studio.impl.repository.mongodb.domain.Node;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Integration Test of Creation of a File
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/craftercms/studio/craftercms-mongo-repocitory.xml")
public class ITNodeServiceCreateFile implements ApplicationContextAware {

    private static final String FILE_NAME = "Dr. John A. Zoiberg";
    private static final String FILE_CREATOR = "Decapod 10";
    private NodeService nodeService;
    private ApplicationContext applicationContext;


    @Before
    public void setUp() throws Exception {
        nodeService = applicationContext.getBean(NodeService.class);
    }

    @Test
    public void testCreateFile() throws Exception {
        Node parent = nodeService.getRootNode();
        InputStream inputStream = this.getClass().getResourceAsStream("/files/index.xml");
        Assert.assertNotNull("Input Stream is null", inputStream); //make sure we read the file.
        Node createdNode = nodeService.createFileNode(parent, FILE_NAME, FILE_CREATOR, inputStream);
        Node expectedNode = nodeService.getNode(createdNode.getId());
        Assert.assertNotNull(createdNode);
        Assert.assertNotNull(expectedNode);
        Assert.assertEquals(expectedNode, createdNode);
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

}
