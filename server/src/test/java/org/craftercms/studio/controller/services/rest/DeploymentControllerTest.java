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
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.craftercms.studio.api.deployment.DeploymentManager;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.DeploymentChannel;
import org.craftercms.studio.commons.dto.Item;
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
 * Unit tests for Deployment Controller.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/spring/mockito-context.xml", "/spring/web-context.xml", "/spring/messageFormatting-studio3-web-context.xml"})
public class DeploymentControllerTest {

    // Mocks
    @Autowired
    private DeploymentManager deploymentManagerMock;

    @InjectMocks
    private DeploymentController deploymentController;

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

    private List<Item> generateDeploymentHistory() {
        List<Item> items = new ArrayList<Item>();
        for (int i = 0; i < 1 + (int)(Math.random() * (50)); i++) {
            items.add(createItemMock());
        }
        return items;
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

    private List<DeploymentChannel> generateChannelsList() {
        List<DeploymentChannel> items = new ArrayList<DeploymentChannel>();
        for (int i = 0; i < 1 + (int)(Math.random() * (5)); i++) {
            items.add(createDeploymentChannelMock());
        }
        return items;
    }

    private DeploymentChannel createDeploymentChannelMock() {
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
                .content(createDeploymentChannelJson().getBytes())
                .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.deploymentManagerMock, times(1)).updateChannel(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(DeploymentChannel.class));
    }

    private String createDeploymentChannelJson() {
        ObjectMapper mapper = new ObjectMapper();

        String toRet = "";
        try {
            toRet = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(createDeploymentChannelMock());
        } catch (JsonProcessingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return toRet;
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
                .content(createDeploymentChannelJson().getBytes())
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
                .content(createDeploymentChannelJson().getBytes())
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
                .content(createDeploymentChannelJson().getBytes())
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
                .content(createDeploymentChannelJson().getBytes())
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
