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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.media.sound.EmergencySoundbank;
import org.apache.commons.lang.RandomStringUtils;
import org.craftercms.studio.api.workflow.WorkflowManager;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.WorkflowPackage;
import org.craftercms.studio.commons.dto.WorkflowTransition;
import org.craftercms.studio.commons.filter.WorkflowPackageFilter;
import org.craftercms.studio.controller.services.rest.dto.WorkflowStartRequest;
import org.craftercms.studio.controller.services.rest.dto.WorkflowTransitionRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit test for Workflow Controller
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/spring/mockito-context.xml", "/spring/web-context.xml", "/spring/messageFormatting-studio3-web-context.xml"})
public class WorkflowControllerTest {

    //Mocks
    @Autowired
    private WorkflowManager workflowManagerMock;

    @InjectMocks
    private WorkflowController workflowController;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @After
    public void tearDown() throws Exception {
        reset(this.workflowManagerMock);
    }

    @Test
    public void testStart() throws Exception {
        when(this.workflowManagerMock.start(Mockito.anyString(), Mockito.anyListOf(String.class),
            Mockito.anyListOf(Item.class))).thenReturn(UUID.randomUUID().toString());

        this.mockMvc.perform(
            post("/api/1/workflow/start/sample")
                .contentType(MediaType.APPLICATION_JSON)
                .content(generateStartWorkflowJSON().getBytes())
                .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.workflowManagerMock, times(1)).start(Mockito.anyString(), Mockito.anyListOf(String.class),
            Mockito.anyListOf(Item.class));
    }

    private String generateStartWorkflowJSON() {
        ObjectMapper mapper = new ObjectMapper();

        String toRet = "";
        try {
            toRet = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(generateStartWorkflowRequest());
        } catch (JsonProcessingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return toRet;

    }

    private WorkflowStartRequest generateStartWorkflowRequest() {
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

    private Item createItemMock() {
        Item item = new Item();
        item.setContentType(RandomStringUtils.randomAlphabetic(10));
        item.setCreateDate(new Date());
        item.setCreator(RandomStringUtils.randomAlphabetic(10));
        item.setDisabled(false);
        item.setFileName(RandomStringUtils.randomAlphanumeric(10));
        item.setId(UUID.randomUUID().toString());
        item.setLastModifiedDate(new Date());
        item.setLockOwner(RandomStringUtils.randomAlphabetic(10));
        item.setMimeType(RandomStringUtils.randomAlphabetic(10));
        item.setModifier(RandomStringUtils.randomAlphabetic(10));
        item.setName(RandomStringUtils.randomAlphabetic(10));
        item.setPackages(new ArrayList<String>());
        item.setPath(RandomStringUtils.randomAlphabetic(100));
        item.setPlaceInNav(true);
        item.setPreviewable(true);
        item.setPreviewUrl(RandomStringUtils.randomAlphabetic(100));
        item.setProperties(new HashMap<String, Object>());
        item.setRenderingTemplates(new ArrayList<String>());
        item.setRepoId(RandomStringUtils.randomAlphabetic(10));
        item.setScheduledDate(new Date());
        item.setState(RandomStringUtils.randomAlphabetic(10));
        item.setStudioType(RandomStringUtils.randomAlphabetic(10));
        return item;
    }

    // TODO: invalid request tests

    @Test
    public void testPackage() throws Exception {
        when(this.workflowManagerMock.getPackage(Mockito.anyString())).thenReturn(generateListOfItems());

        this.mockMvc.perform(
            get("/api/1/workflow/package/sample?packageId=1")
                .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.workflowManagerMock, times(1)).getPackage(Mockito.anyString());
    }

    private List<Item> generateListOfItems() {
        List<Item> items = new ArrayList<Item>();
        for (int i = 0; i < 1 + (int)(Math.random() * (50)); i++) {
            items.add(createItemMock());
        }
        return items;
    }

    @Test
    public void testPackageMissingPackageId() throws Exception {
        when(this.workflowManagerMock.getPackage(Mockito.anyString())).thenReturn(generateListOfItems());

        this.mockMvc.perform(
            get("/api/1/workflow/package/sample")
                .accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.workflowManagerMock, times(0)).getPackage(Mockito.anyString());
    }

    @Test
    public void testPackages() throws Exception {
        when(this.workflowManagerMock.getPackages(Mockito.anyString(), Mockito.anyListOf(WorkflowPackageFilter.class)))
            .thenReturn(generateListOfPackages());

        this.mockMvc.perform(
            get("/api/1/workflow/packages/sample")
                .param("filters", RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10))
                .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.workflowManagerMock, times(1)).getPackages(Mockito.anyString(), Mockito.anyListOf
            (WorkflowPackageFilter.class));
    }

    private List<WorkflowPackage> generateListOfPackages() {
        List<WorkflowPackage> packages = new ArrayList<WorkflowPackage>();
        for (int i = 0; i < 1 + (int)(Math.random() * (50)); i++) {
            packages.add(createPackageMock());
        }
        return packages;
    }

    private WorkflowPackage createPackageMock() {
        WorkflowPackage workflowPackage = new WorkflowPackage();
        workflowPackage.setId(UUID.randomUUID().toString());
        workflowPackage.setState(RandomStringUtils.randomAlphabetic(8));
        workflowPackage.setScheduledDate(new Date());
        workflowPackage.setDescription(RandomStringUtils.randomAlphanumeric(256));
        workflowPackage.setItems(generateListOfItems());
        workflowPackage.setName(RandomStringUtils.randomAlphabetic(15));
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("prop1", RandomStringUtils.randomAlphanumeric(10));
        props.put("prop2", RandomStringUtils.randomAlphanumeric(10));
        workflowPackage.setProperties(props);
        workflowPackage.setSubmittedBy(RandomStringUtils.randomAlphabetic(10));
        workflowPackage.setWorkflowId(RandomStringUtils.randomAlphanumeric(15));
        return workflowPackage;
    }

    @Test
    public void testTransitions() throws Exception {
        when(this.workflowManagerMock.getTransitions(Mockito.anyString())).thenReturn(generateListOfTransitions());

        this.mockMvc.perform(
            get("/api/1/workflow/transitions/sample?packageId=1")
                .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.workflowManagerMock, times(1)).getTransitions(Mockito.anyString());
    }

    private List<WorkflowTransition> generateListOfTransitions() {
        List<WorkflowTransition> transitions = new ArrayList<WorkflowTransition>();
        for (int i = 0; i < 1 + (int)(Math.random() * (50)); i++) {
            transitions.add(createTransitionMock());
        }
        return transitions;
    }

    private WorkflowTransition createTransitionMock() {
        WorkflowTransition transition = new WorkflowTransition();
        transition.setId(UUID.randomUUID().toString());
        transition.setName(RandomStringUtils.randomAlphabetic(10));
        return transition;
    }

    @Test
    public void testTransitionsMissingPackageId() throws Exception {
        when(this.workflowManagerMock.getTransitions(Mockito.anyString())).thenReturn(generateListOfTransitions());

        this.mockMvc.perform(
            get("/api/1/workflow/transitions/sample")
                .accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.workflowManagerMock, times(0)).getTransitions(Mockito.anyString());
    }

    @Test
    public void testTransition() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.workflowManagerMock).transition(Mockito.anyString(), Mockito.any(WorkflowTransition.class),
            Mockito.anyMapOf(String.class, Object.class));



        this.mockMvc.perform(
            post("/api/1/workflow/transition/sample")
                .content(generateTransitionRequestJson().getBytes())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                )
            .andExpect(status().isOk());

        verify(this.workflowManagerMock, times(1)).transition(Mockito.anyString(), Mockito.any(WorkflowTransition
            .class), Mockito.anyMapOf(String.class, Object.class));
    }

    private WorkflowTransitionRequest generateTransitionRequest() {
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

    private String generateTransitionRequestJson() {
        ObjectMapper mapper = new ObjectMapper();

        String toRet = "";
        try {
            toRet = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(generateTransitionRequest());
        } catch (JsonProcessingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return toRet;
    }

    @Test
    public void testCancel() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.workflowManagerMock).cancel(Mockito.anyString());

        this.mockMvc.perform(
            post("/api/1/workflow/cancel/sample")
                .param("packageId", "1")
                .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.workflowManagerMock, times(1)).cancel(Mockito.anyString());
    }

    @Test
    public void testCancelMissingPackageId() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.workflowManagerMock).cancel(Mockito.anyString());

        this.mockMvc.perform(
            post("/api/1/workflow/cancel/sample")
                .accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.workflowManagerMock, times(0)).cancel(Mockito.anyString());
    }
}
