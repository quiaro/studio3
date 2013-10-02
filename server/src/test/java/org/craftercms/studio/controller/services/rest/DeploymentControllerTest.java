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
import org.apache.commons.lang.StringUtils;
import org.craftercms.studio.api.deployment.DeploymentManager;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.DeploymentChannel;
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
 * Unit tests for Deployment Controller.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public class DeploymentControllerTest extends AbstractControllerTest {

    // Mocks
    @Autowired
    private DeploymentManager deploymentManagerMock;

    @InjectMocks
    private DeploymentController deploymentController;

    @After
    public void tearDown() throws Exception {
        reset(this.deploymentManagerMock);
    }

    @Test
    public void testHistory() throws Exception {
        when(this.deploymentManagerMock.history(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyListOf(String.class)
            )).thenReturn(generateDeploymentHistory());

        this.mockMvc.perform(
            get("/api/1/deployment/history/sample")
                .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.deploymentManagerMock, times(1)).history(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyListOf(String.class));
    }

    @Test
    public void testHistoryWithFilters() throws Exception {
        when(this.deploymentManagerMock.history(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyListOf(String.class)
        )).thenReturn(generateDeploymentHistory());

        this.mockMvc.perform(
            get("/api/1/deployment/history/sample")
                .param("filters", "filter1", "filter2")
                .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.deploymentManagerMock, times(1)).history(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyListOf(String.class));
    }

    @Test
    public void testChannels() throws Exception {
        when(this.deploymentManagerMock.channels(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString()
        )).thenReturn(generateChannelsList());

        this.mockMvc.perform(
            get("/api/1/deployment/channels/sample")
                .param("environment", RandomStringUtils.randomAlphabetic(10))
                .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.deploymentManagerMock, times(1)).channels(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString());
    }

    @Test
    public void testChannelsMissingEnvironment() throws Exception {
        when(this.deploymentManagerMock.channels(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString()
        )).thenReturn(generateChannelsList());

        this.mockMvc.perform(
            get("/api/1/deployment/channels/sample")
                .accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.deploymentManagerMock, times(0)).channels(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString());
    }

    @Test
    public void testUpdateChannel() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.deploymentManagerMock).updateChannel(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(DeploymentChannel.class));

        this.mockMvc.perform(
            post("/api/1/deployment/update_channel/sample")
                .contentType(MediaType.APPLICATION_JSON)
                .content(generateRequestBody(createDeploymentChannelMock()).getBytes())
                .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.deploymentManagerMock, times(1)).updateChannel(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(DeploymentChannel.class));
    }

    @Test
    public void testUpdateChannelMissingRequestBody() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.deploymentManagerMock).updateChannel(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(DeploymentChannel.class));

        this.mockMvc.perform(
            post("/api/1/deployment/update_channel/sample")
                .contentType(MediaType.APPLICATION_JSON)
                .content(StringUtils.EMPTY.getBytes())
                .accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.deploymentManagerMock, times(0)).updateChannel(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(DeploymentChannel.class));
    }

    @Test
    public void testRemoveChannel() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.deploymentManagerMock).removeChannel(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(DeploymentChannel.class));

        this.mockMvc.perform(
            post("/api/1/deployment/remove_channel/sample")
                .contentType(MediaType.APPLICATION_JSON)
                .content(generateRequestBody(createDeploymentChannelMock()).getBytes())
                .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.deploymentManagerMock, times(1)).removeChannel(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(DeploymentChannel.class));
    }

    @Test
    public void testRemoveChannelMissingRequestBody() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.deploymentManagerMock).removeChannel(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(DeploymentChannel.class));

        this.mockMvc.perform(
            post("/api/1/deployment/remove_channel/sample")
                .contentType(MediaType.APPLICATION_JSON)
                .content(StringUtils.EMPTY.getBytes())
                .accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.deploymentManagerMock, times(0)).removeChannel(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(DeploymentChannel.class));
    }

    @Test
    public void testDeploy() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.deploymentManagerMock).deploy(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyListOf(String.class));

        this.mockMvc.perform(
            post("/api/1/deployment/deploy/sample")
                .param("itemIds", UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
                .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.deploymentManagerMock, times(1)).deploy(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyListOf(String.class));
    }

    @Test
    public void testDeployMissingItems() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.deploymentManagerMock).deploy(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyListOf(String.class));

        this.mockMvc.perform(
            post("/api/1/deployment/deploy/sample")
                .accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.deploymentManagerMock, times(0)).deploy(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyListOf(String.class));
    }

    @Test
    public void testStatus() throws Exception {
        when(this.deploymentManagerMock.status(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(DeploymentChannel.class))).thenReturn(RandomStringUtils.randomAlphanumeric(200));

        this.mockMvc.perform(
            get("/api/1/deployment/status/sample")
                .contentType(MediaType.APPLICATION_JSON)
                .content(generateRequestBody(createDeploymentChannelMock()).getBytes())
                .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.deploymentManagerMock, times(1)).status(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(DeploymentChannel.class));
    }

    @Test
    public void testStatusMissingChannel() throws Exception {
        when(this.deploymentManagerMock.status(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(DeploymentChannel.class))).thenReturn(RandomStringUtils.randomAlphanumeric(200));

        this.mockMvc.perform(
            get("/api/1/deployment/status/sample")
                .contentType(MediaType.APPLICATION_JSON)
                .content(StringUtils.EMPTY.getBytes())
                .accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.deploymentManagerMock, times(0)).status(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(DeploymentChannel.class));
    }

    @Test
    public void testVersion() throws Exception {
        when(this.deploymentManagerMock.version(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(DeploymentChannel.class))).thenReturn((long)(Math.random() * Long.MAX_VALUE));

        this.mockMvc.perform(
            get("/api/1/deployment/version/sample")
                .contentType(MediaType.APPLICATION_JSON)
                .content(generateRequestBody(createDeploymentChannelMock()).getBytes())
                .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.deploymentManagerMock, times(1)).version(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(DeploymentChannel.class));
    }

    @Test
    public void testVersionMissingRequestBody() throws Exception {
        when(this.deploymentManagerMock.version(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(DeploymentChannel.class))).thenReturn((long)(Math.random() * Long.MAX_VALUE));

        this.mockMvc.perform(
            get("/api/1/deployment/version/sample")
                .contentType(MediaType.APPLICATION_JSON)
                .content(StringUtils.EMPTY.getBytes())
                .accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.deploymentManagerMock, times(0)).version(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(DeploymentChannel.class));
    }

    @Test
    public void testAbort() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.deploymentManagerMock).abort(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(DeploymentChannel.class));

        this.mockMvc.perform(
            post("/api/1/deployment/abort/sample")
                .contentType(MediaType.APPLICATION_JSON)
                .content(generateRequestBody(createDeploymentChannelMock()).getBytes())
                .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.deploymentManagerMock, times(1)).abort(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(DeploymentChannel.class));
    }

    @Test
    public void testAbortMissingRequestBody() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.deploymentManagerMock).abort(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(DeploymentChannel.class));

        this.mockMvc.perform(
            post("/api/1/deployment/abort/sample")
                .contentType(MediaType.APPLICATION_JSON)
                .content(StringUtils.EMPTY.getBytes())
                .accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.deploymentManagerMock, times(0)).abort(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(DeploymentChannel.class));
    }
}
