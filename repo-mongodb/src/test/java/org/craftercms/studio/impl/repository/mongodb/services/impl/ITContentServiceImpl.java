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

import org.craftercms.studio.api.content.ContentService;
import org.craftercms.studio.commons.dto.Item;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/craftercms/studio/craftercms-mongo-repository.xml")
public class ITContentServiceImpl implements ApplicationContextAware {


    private ApplicationContext applicationContext;
    private ContentService contentService;

    @Before
    public void setUp() throws Exception {
        this.contentService = applicationContext.getBean(ContentService.class);
    }

    @Test
    public void testCreateFolder() throws Exception {
        Item item = new Item();
        item.setLabel("Robot Hell");
        item.setFileName("robot-hell");
        item.setCreatedBy("Robo Devil");
        Item newItem = this.contentService.create("none", "ITTestSite", "/testSite", item);
        Assert.assertNotNull(newItem);
        Assert.assertEquals(newItem.getModifiedBy(),"Robo Devil");
        Assert.assertEquals(newItem.getPath(), "/ITTestSite/testSite/robot-hell");
    }

    @Test
    public void testCreateFile() throws Exception {

    }

    @Test
    public void testRead() throws Exception {

    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
