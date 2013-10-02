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

import java.util.Arrays;
import java.util.List;

import org.craftercms.studio.impl.repository.mongodb.domain.Node;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.fail;

/**
 * Integration Test of Creation of a Folder
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/craftercms/studio/craftercms-mongo-repository.xml")
public class ITNodeServiceCreateFolder implements ApplicationContextAware {

    private static final String FOLDER_CREATOR="Philip J Fry";
    private static final String FOLDER_NAME="Panucci Pizza";
    private static final String FOLDER_NAME_2 = "Robot Arms Apts";
    private NodeService nodeService;
    private ApplicationContext applicationContext;

    @Before
    public void setUp() throws Exception {
        nodeService = applicationContext.getBean(NodeService.class);
    }

    @Test
    public void testRootFolderIsCreated() throws Exception {
        Node folder = nodeService.createFolderNode(nodeService.getRootNode(), FOLDER_NAME, FOLDER_CREATOR);
        Assert.assertNotNull(folder);
        List<Node> nodes = nodeService.findNodesByParents(Arrays.asList(nodeService.getRootNode()));
        for(Node n :nodes){
            if(n.equals(folder)){ //Node.equals call also CoreMetadata Equals therefor no need to recheck
               return ; // we found it and is equals , my job here is done.
            }
        }
        fail("Saved Folder was not found when searching for it ");
    }


    @Test(expected = IllegalArgumentException.class)
    public void testValidationOfFolderNameInPath() throws Exception {
        Node folder = nodeService.createFolderNode(nodeService.getRootNode(), FOLDER_NAME_2, FOLDER_CREATOR);
        Assert.assertNotNull(folder);
        Node folder2 = nodeService.createFolderNode(nodeService.getRootNode(), FOLDER_NAME_2, FOLDER_CREATOR);

    }


    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


}
