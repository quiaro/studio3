/*
 * Copyright (C) 2007-2013 Crafter Software Corporation.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.craftercms.studio.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.craftercms.studio.commons.dto.Activity;
import org.craftercms.studio.commons.dto.DeploymentChannel;
import org.craftercms.studio.commons.dto.FormDefinition;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.commons.dto.WorkflowTransition;
import org.craftercms.studio.commons.extractor.ItemExtractor;
import org.craftercms.studio.commons.filter.ItemFilter;
import org.craftercms.studio.commons.filter.WorkflowPackageFilter;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"/spring/mockito-context.xml", "/spring/unit-testing-context.xml"})
public abstract class AbstractManagerTest {

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    protected List<Item> createItemListMock() {
        List<Item> itemListMock = new ArrayList<>();
        for (int i = 0; i < 5 + (int)(Math.random() * ((10 - 5) + 1)); i++) {
            itemListMock.add(createItemMock());
        }
        return itemListMock;
    }

    protected Item createItemMock() {
        Item item = new Item();
        item.setContentType(RandomStringUtils.randomAlphabetic(10));
        item.setDisabled(false);
        item.setFileName(RandomStringUtils.randomAlphanumeric(10));
        item.setId(new ItemId(UUID.randomUUID().toString()));
        item.setLastModifiedDate(new Date());
        item.setLockOwner(RandomStringUtils.randomAlphabetic(10));
        item.setMimeType(RandomStringUtils.randomAlphabetic(10));
        item.setPackages(new ArrayList<String>());
        item.setPath(RandomStringUtils.randomAlphabetic(100));
        item.setPlaceInNav(true);
        item.setPreviewUrl(RandomStringUtils.randomAlphabetic(100));
        item.setProperties(new HashMap<String, Object>());
        item.setRenderingTemplates(new ArrayList<String>());
        item.setRepoId(RandomStringUtils.randomAlphabetic(10));
        item.setScheduledDate(new Date());
        item.setState(RandomStringUtils.randomAlphabetic(10));
        item.setLabel(RandomStringUtils.randomAlphabetic(10));
        return item;
    }

    protected List<ItemFilter> createItemFilterListMock() {
        List<ItemFilter> itemFilterListMock = new ArrayList<>();
        for (int i = 0; i < 5 + (int)(Math.random() * ((10 - 5) + 1)); i++) {
            itemFilterListMock.add(createItemFilterMock());
        }
        return itemFilterListMock;
    }

    protected ItemFilter createItemFilterMock() {
        ItemFilter filter = new ItemFilter();
        return filter;
    }

    protected List<ItemExtractor> createItemExtractorListMock() {
        List<ItemExtractor> itemExtractorListMock = new ArrayList<>();
        for (int i = 0; i < 5 + (int)(Math.random() * ((10 - 5) + 1)); i++) {
            itemExtractorListMock.add(createItemExtractorMock());
        }
        return itemExtractorListMock;
    }

    protected ItemExtractor createItemExtractorMock() {
        ItemExtractor extractor = new ItemExtractor();
        return extractor;
    }

    protected List<WorkflowPackageFilter> createWorkflowPackageListMock() {
        List<WorkflowPackageFilter> workflowPackageFilterListMock = new ArrayList<>();
        for (int i = 0; i < 5 + (int)(Math.random() * ((10 - 5) + 1)); i++) {
            workflowPackageFilterListMock.add(createWorkflowPackageFilterMock());
        }
        return workflowPackageFilterListMock;
    }

    protected WorkflowPackageFilter createWorkflowPackageFilterMock() {
        WorkflowPackageFilter filter = new WorkflowPackageFilter();
        return filter;
    }

    protected WorkflowTransition createWorkflowTransitionMock() {
        WorkflowTransition transition = new WorkflowTransition();
        transition.setId(UUID.randomUUID().toString());
        transition.setName(RandomStringUtils.randomAlphabetic(10));
        return transition;
    }

    protected List<String> createStringListMock() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 5 + (int)(Math.random() * ((10 - 5) + 1)); i++) {
            list.add(RandomStringUtils.randomAlphanumeric(25));
        }
        return list;
    }

    protected DeploymentChannel createDeploymentChannelMock() {
        DeploymentChannel channel = new DeploymentChannel();
        channel.setVersionUrl(RandomStringUtils.randomAlphabetic(150));
        channel.setType(RandomStringUtils.randomAlphabetic(10));
        channel.setTarget(RandomStringUtils.randomAlphanumeric(20));
        channel.setStatusUrl(RandomStringUtils.randomAlphabetic(150));
        channel.setDisabled(false);
        channel.setId(UUID.randomUUID().toString());
        channel.setName(RandomStringUtils.randomAlphabetic(15));
        channel.setExcludePatterns(createStringListMock());
        channel.setIncludePatterns(createStringListMock());
        channel.setHost(RandomStringUtils.randomAlphabetic(10));
        channel.setPort(RandomStringUtils.randomNumeric(4));
        channel.setPublishingUrl(RandomStringUtils.randomAlphabetic(150));
        channel.setPublishMetadata(false);
        return channel;
    }

    protected List<String> createItemIdListMock() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 5 + (int)(Math.random() * ((10 - 5) + 1)); i++) {
            list.add(UUID.randomUUID().toString());
        }
        return list;
    }

    protected Activity createActivityMock() {
        Activity activity = new Activity();
        activity.setId(UUID.randomUUID().toString());
        activity.setCreator(RandomStringUtils.randomAlphabetic(10));
        activity.setDate(new Date());
        activity.setSiteId(RandomStringUtils.randomAlphabetic(10));
        activity.setSiteName(RandomStringUtils.randomAlphabetic(20));
        activity.setTarget(RandomStringUtils.randomAlphanumeric(20));
        Map<String, String> props = new HashMap<String, String>();
        props.put("param1", "param1");
        props.put("param2", "param2");
        activity.setTargetProperties(props);
        activity.setType(RandomStringUtils.randomAlphabetic(10));
        return activity;
    }

    protected FormDefinition createFormDefinitionMock() {
        FormDefinition form = new FormDefinition();
        form.setId(UUID.randomUUID().toString());
        form.setName(RandomStringUtils.randomAlphabetic(20));
        form.setSiteName(RandomStringUtils.randomAlphabetic(10));
        form.setSiteId(RandomStringUtils.randomAlphabetic(10));
        Map<String, Object> schema = new HashMap<String, Object>();
        schema.put("param1", "param1");
        schema.put("param2", "param2");
        form.setSchema(schema);
        return form;
    }

    protected Map<String, Object> createMapMock() {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < 5 + (int)(Math.random() * ((10 - 5) + 1)); i++) {
            map.put(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphanumeric(20));
        }
        return map;
    }
}
