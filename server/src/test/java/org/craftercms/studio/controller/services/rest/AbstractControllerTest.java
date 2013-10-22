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

package org.craftercms.studio.controller.services.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.RandomStringUtils;
import org.craftercms.studio.api.lifecycle.Action;
import org.craftercms.studio.commons.dto.Activity;
import org.craftercms.studio.commons.dto.DeploymentChannel;
import org.craftercms.studio.commons.dto.FormDefinition;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.commons.dto.LockHandle;
import org.craftercms.studio.commons.dto.LockStatus;
import org.craftercms.studio.commons.dto.ModuleConfiguration;
import org.craftercms.studio.commons.dto.ResultSet;
import org.craftercms.studio.commons.dto.SecurityPermission;
import org.craftercms.studio.commons.dto.Site;
import org.craftercms.studio.commons.dto.Tree;
import org.craftercms.studio.commons.dto.TreeNode;
import org.craftercms.studio.commons.dto.User;
import org.craftercms.studio.commons.dto.Version;
import org.craftercms.studio.commons.dto.WorkflowPackage;
import org.craftercms.studio.commons.dto.WorkflowTransition;
import org.craftercms.studio.controller.services.rest.dto.WorkflowStartRequest;
import org.craftercms.studio.controller.services.rest.dto.WorkflowTransitionRequest;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Abstract class for unit tests of all controllers
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/spring/mockito-context.xml", "/spring/web-context.xml", "/spring/messageFormatting-studio3-web-context.xml"})
public abstract class AbstractControllerTest {

    @Autowired
    protected WebApplicationContext wac;
    protected MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    protected String generateRequestBody(Object jsonObject) {
        ObjectMapper mapper = new ObjectMapper();

        String toRet = "";
        try {
            toRet = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return toRet;
    }

    protected User createUserMock() {
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setPassword(RandomStringUtils.randomAlphanumeric(10));
        return user;
    }

    protected SecurityPermission createSecurityPermissionMock() {
        SecurityPermission permission = new SecurityPermission();
        permission.setName(RandomStringUtils.randomAlphabetic(10));
        permission.setName(RandomStringUtils.randomAlphabetic(100));
        return permission;
    }

    protected String createActivityJson(boolean broken) throws Exception {
        Activity activity1 = new Activity();

        activity1.setDate(new Date());
        activity1.setSiteId(UUID.randomUUID().toString());
        if(broken){
            activity1.setSiteName("");
            activity1.setTarget("");
            activity1.setType("");
            activity1.setCreator(" ");
        }else{
            activity1.setSiteName("testSite");
            activity1.setTarget("testTarget");
            activity1.setType("SAVED");
            activity1.setCreator("Carlos Ortiz");
        }
        HashMap<String, String> targetProperties = new HashMap<>();
        targetProperties.put("targetProp1", "Hello");
        targetProperties.put("targetProp2", (new Date()).toString());
        activity1.setTargetProperties(targetProperties);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(activity1);
    }

    protected List<Activity> createActivities() throws Exception {
        Activity activity1 = new Activity();
        activity1.setCreator("Carlos Ortiz");
        activity1.setDate(new Date());
        activity1.setSiteId(UUID.randomUUID().toString());
        activity1.setSiteName("testSite");
        activity1.setTarget("testTarget");
        activity1.setSiteName("");
        HashMap<String, String> targetProperties = new HashMap<>();
        targetProperties.put("targetProp1", "Hello");
        targetProperties.put("targetProp2", (new Date()).toString());
        activity1.setTargetProperties(targetProperties);
        // 2
        Activity activit2 = new Activity();
        activit2.setCreator("Carlos Ortiz");
        activit2.setDate(new Date());
        activit2.setSiteId(UUID.randomUUID().toString());
        activit2.setSiteName("testSite");
        activit2.setTarget("testTarget");
        activit2.setSiteName("");
        HashMap<String, String> targetProperties2 = new HashMap<>();
        targetProperties2.put("targetProp1", "World");
        targetProperties2.put("targetProp2", (new Date()).toString());
        activit2.setTargetProperties(targetProperties2);
        return Arrays.asList(activity1, activit2);
    }

    protected List<Item> generateItemListMock() {
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

    protected DeploymentChannel createDeploymentChannelMock() {
        DeploymentChannel channel = new DeploymentChannel();
        channel.setId(UUID.randomUUID().toString());
        channel.setName(RandomStringUtils.randomAlphabetic(10));
        channel.setDisabled(false);
        channel.setHost("localhost");
        channel.setPort("9191");
        channel.setPublishingUrl("http://localhost:9191/publish");
        channel.setPublishMetadata(false);
        channel.setStatusUrl("http://localhost:9191/api/1/monitoring/status");
        channel.setTarget(RandomStringUtils.randomAlphabetic(10));
        channel.setType(RandomStringUtils.randomAlphanumeric(10));
        channel.setVersionUrl("http://localhost:9191/api/1/version");
        return channel;
    }

    protected List<DeploymentChannel> generateChannelsList() {
        List<DeploymentChannel> items = new ArrayList<DeploymentChannel>();
        for (int i = 0; i < 1 + (int)(Math.random() * (5)); i++) {
            items.add(createDeploymentChannelMock());
        }
        return items;
    }

    protected Action createActionMock() {
        TestAction action = new TestAction();
        action.setId(UUID.randomUUID().toString());
        action.setName(RandomStringUtils.randomAlphabetic(10));
        return action;
    }

    protected List<Item> generateDeploymentHistory() {
        List<Item> items = new ArrayList<Item>();
        for (int i = 0; i < 1 + (int)(Math.random() * (50)); i++) {
            items.add(createItemMock());
        }
        return items;
    }

    protected List<Action> generateListOfActions() {
        List<Action> actions = new ArrayList<Action>();
        for (int i = 0; i < 1 + (int)(Math.random() * (50)); i++) {
            actions.add(createActionMock());
        }
        return actions;
    }

    protected LockHandle createLockHandleMock() {
        LockHandle lockHandle = new LockHandle();
        lockHandle.setId(UUID.randomUUID().toString());
        return lockHandle;
    }

    protected List<LockStatus> createLockStatus() {
        List<LockStatus> lockStatuses = new ArrayList<LockStatus>();
        for (int i = 0; i < 5 + (int)(Math.random() * ((10 - 5) + 1)); i++) {
            LockStatus ls = new LockStatus();
            ls.setValue(RandomStringUtils.randomAlphanumeric(10));
            lockStatuses.add(ls);
        }
        return lockStatuses;
    }

    protected Tree<Item> generateItemTreeMock() {
        Item root = createItemMock();
        Tree<Item> itemTreeMock = new Tree<Item>(root);
        TreeNode<Item> rootNode = itemTreeMock.getRootNode();
        for (int i = 0; i < 1 + (int)(3* Math.random()); i++) {
            Item item = createItemMock();
            rootNode.addChild(item);
        }
        for (TreeNode<Item> nodeItem : rootNode.getChildren()) {
            for (int i = 0; i < 1 + (int)(3 * Math.random()); i++) {
                Item item = createItemMock();
                nodeItem.addChild(item);
            }
        }
        return itemTreeMock;
    }

    protected List<Site> generateSiteListMock() {
        List<Site> toRet = new ArrayList<Site>();
        for (int i = 0; i < 5 + (int)(Math.random() * ((10 - 5) + 1)); i++) {
            Site site = new Site();
            site.setSiteId(RandomStringUtils.randomAlphabetic(10));
            site.setSiteName(RandomStringUtils.randomAlphabetic(10));
            toRet.add(site);
        }
        return toRet;
    }


    protected ResultSet generateResultSetMock() {
        int size = 5 + (int)(Math.random() * (5));
        ResultSet rs = new ResultSet();
        rs.setSize(size);
        return rs;
    }

    protected Tree<Version> generateVersionTree() {
        Version root = createVersionMock();
        Tree<Version> versionTreeMock = new Tree<Version>(root);
        TreeNode<Version> rootNode = versionTreeMock.getRootNode();
        for (int i = 0; i < 1 + (int)(3* Math.random()); i++) {
            Version version = createVersionMock();
            rootNode.addChild(version);
        }
        for (TreeNode<Version> nodeItem : rootNode.getChildren()) {
            for (int i = 0; i < 1 + (int)(3 * Math.random()); i++) {
                Version version = createVersionMock();
                nodeItem.addChild(version);
            }
        }
        return versionTreeMock;
    }

    protected WorkflowStartRequest generateStartWorkflowRequest() {
        WorkflowStartRequest workflowStartRequest = new WorkflowStartRequest();
        workflowStartRequest.setPackageName(RandomStringUtils.randomAlphanumeric(10));
        List<String> comments = new ArrayList<String>();
        for (int i = 0; i < 5 + (int)(Math.random() * (6)); i++) {
            comments.add(RandomStringUtils.randomAlphanumeric((int)(256 * Math.random())));
        }
        workflowStartRequest.setComments(comments);
        List<Item> items = new ArrayList<Item>();
        for (int i = 0; i < 5 + (int)(Math.random() * (6)); i++) {
            items.add(createItemMock());
        }
        workflowStartRequest.setItems(items);
        return workflowStartRequest;
    }

    protected Version createVersionMock() {
        Version version = new Version();
        version.setLabel(RandomStringUtils.randomNumeric(1) + "." + RandomStringUtils.randomNumeric(2));
        version.setComment(RandomStringUtils.randomAlphabetic(100));
        return version;
    }

    protected List<WorkflowPackage> generateListOfPackages() {
        List<WorkflowPackage> packages = new ArrayList<WorkflowPackage>();
        for (int i = 0; i < 1 + (int)(Math.random() * (50)); i++) {
            packages.add(createPackageMock());
        }
        return packages;
    }

    protected WorkflowPackage createPackageMock() {
        WorkflowPackage workflowPackage = new WorkflowPackage();
        workflowPackage.setId(UUID.randomUUID().toString());
        workflowPackage.setState(RandomStringUtils.randomAlphabetic(8));
        workflowPackage.setScheduledDate(new Date());
        workflowPackage.setDescription(RandomStringUtils.randomAlphanumeric(256));
        workflowPackage.setItems(generateItemListMock());
        workflowPackage.setName(RandomStringUtils.randomAlphabetic(15));
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("prop1", RandomStringUtils.randomAlphanumeric(10));
        props.put("prop2", RandomStringUtils.randomAlphanumeric(10));
        workflowPackage.setProperties(props);
        workflowPackage.setSubmittedBy(RandomStringUtils.randomAlphabetic(10));
        workflowPackage.setWorkflowId(RandomStringUtils.randomAlphanumeric(15));
        return workflowPackage;
    }

    protected List<WorkflowTransition> generateListOfTransitions() {
        List<WorkflowTransition> transitions = new ArrayList<WorkflowTransition>();
        for (int i = 0; i < 1 + (int)(Math.random() * (50)); i++) {
            transitions.add(createTransitionMock());
        }
        return transitions;
    }

    protected WorkflowTransition createTransitionMock() {
        WorkflowTransition transition = new WorkflowTransition();
        transition.setId(UUID.randomUUID().toString());
        transition.setName(RandomStringUtils.randomAlphabetic(10));
        return transition;
    }

    protected WorkflowTransitionRequest generateTransitionRequestMock() {
        WorkflowTransitionRequest requestObject = new WorkflowTransitionRequest();
        requestObject.setPackageId(RandomStringUtils.randomAlphanumeric(10));
        //requestObject.setPackageId(" ");
        WorkflowTransition transition = new WorkflowTransition();
        transition.setId(UUID.randomUUID().toString());
        transition.setName(RandomStringUtils.randomAlphabetic(10));
        requestObject.setTransition(transition);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("param1", RandomStringUtils.randomAlphanumeric(20));
        params.put("param2", RandomStringUtils.randomAlphanumeric(20));
        requestObject.setParams(params);
        return requestObject;
    }

    protected List<FormDefinition> generateFormDefinitionList() {
        List<FormDefinition> forms = new ArrayList<FormDefinition>();
        for (int i = 0; i < 1 + (int)(Math.random() * (50)); i++) {
            forms.add(createFormDefinitionMock());
        }
        return forms;
    }

    protected FormDefinition createFormDefinitionMock() {
        FormDefinition form = new FormDefinition();
        form.setName(RandomStringUtils.randomAlphabetic(10));
        form.setId(UUID.randomUUID().toString());
        Map<String, Object> schema = new HashMap<String, Object>();
        schema.put("param1", "param1");
        schema.put("param2", "param2");
        form.setSchema(schema);
        form.setSiteId(RandomStringUtils.randomAlphabetic(10));
        form.setSiteName(RandomStringUtils.randomAlphabetic(10));
        return form;
    }

    protected ModuleConfiguration createModuleConfigurationMock() {
        ModuleConfiguration module = new ModuleConfiguration();
        module.setModuleName(RandomStringUtils.randomAlphabetic(10));
        module.setModuleType(RandomStringUtils.randomAlphabetic(10));
        return module;
    }

    public class TestAction implements Action {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(final String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }
    }
}
