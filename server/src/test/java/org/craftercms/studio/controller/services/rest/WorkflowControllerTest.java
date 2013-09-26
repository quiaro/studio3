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

import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.craftercms.studio.api.workflow.WorkflowManager;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.WorkflowTransition;
import org.craftercms.studio.commons.filter.WorkflowPackageFilter;
import org.junit.After;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

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
public class WorkflowControllerTest extends AbstractControllerTest {

    //Mocks
    @Autowired
    private WorkflowManager workflowManagerMock;

    @InjectMocks
    private WorkflowController workflowController;


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
                .content(generateRequestBody(generateStartWorkflowRequest()).getBytes())
                .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.workflowManagerMock, times(1)).start(Mockito.anyString(), Mockito.anyListOf(String.class),
            Mockito.anyListOf(Item.class));
    }

    // TODO: invalid request tests

    @Test
    public void testPackage() throws Exception {
        when(this.workflowManagerMock.getPackage(Mockito.anyString())).thenReturn(generateItemListMock());

        this.mockMvc.perform(
            get("/api/1/workflow/package/sample?packageId=1")
                .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.workflowManagerMock, times(1)).getPackage(Mockito.anyString());
    }

    @Test
    public void testPackageMissingPackageId() throws Exception {
        when(this.workflowManagerMock.getPackage(Mockito.anyString())).thenReturn(generateItemListMock());

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

        verify(this.workflowManagerMock, times(1)).getPackages(Mockito.anyString(),
            Mockito.anyListOf(WorkflowPackageFilter.class));
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
                .content(generateRequestBody(generateTransitionRequestMock()).getBytes())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                )
            .andExpect(status().isOk());

        verify(this.workflowManagerMock, times(1)).transition(Mockito.anyString(),
            Mockito.any(WorkflowTransition.class), Mockito.anyMapOf(String.class, Object.class));
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
