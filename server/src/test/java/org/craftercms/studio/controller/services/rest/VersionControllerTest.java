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

import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.craftercms.studio.api.version.VersionManager;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.LockHandle;
import org.craftercms.studio.commons.dto.Tree;
import org.craftercms.studio.commons.dto.TreeNode;
import org.craftercms.studio.commons.dto.Version;
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
 * Unit test for Version Controller
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/spring/mockito-context.xml", "/spring/web-context.xml", "/spring/messageFormatting-studio3-web-context.xml"})
public class VersionControllerTest {

    // Mocks
    @Autowired
    private VersionManager versionManagerMock;

    @InjectMocks
    private VersionController versionController;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @After
    public void tearDown() {
        reset(this.versionManagerMock);
    }

    @Test
    public void testGetContent() throws Exception {
        when(this.versionManagerMock.history((Context)Mockito.any(), Mockito.anyString())).thenReturn
            (generateVersionTree());

        this.mockMvc.perform(
            get("/api/1/version/history/site?itemId=1")
                .accept(MediaType.ALL))
            .andExpect(status().isOk())
        ;

        verify(this.versionManagerMock, times(1)).history((Context)Mockito.any(), Mockito.anyString());
    }

    private Tree<Version> generateVersionTree() {
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

    private Version createVersionMock() {
        Version version = new Version();
        version.setLabel(RandomStringUtils.randomNumeric(1) + "." + RandomStringUtils.randomNumeric(2));
        version.setComment(RandomStringUtils.randomAlphabetic(100));
        return version;
    }

    @Test
    public void testGetContentMissingItemId() throws Exception {
        when(this.versionManagerMock.history((Context)Mockito.any(), Mockito.anyString())).thenReturn
            (generateVersionTree());

        this.mockMvc.perform(
            get("/api/1/version/history/site")
                .accept(MediaType.ALL))
            .andExpect(status().isBadRequest())
        ;

        verify(this.versionManagerMock, times(0)).history((Context)Mockito.any(), Mockito.anyString());
    }

    @Test
    public void testRevert() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.versionManagerMock).revert((Context)Mockito.any(), Mockito.anyString(), Mockito.anyString());

        this.mockMvc.perform(
            post("/api/1/version/revert/site").accept(MediaType.ALL)
                .param("itemId", "1")
                .param("versionToRevertTo", "1.0"))
            .andExpect(status().isOk());

        verify(this.versionManagerMock, times(1)).revert((Context)Mockito.any(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void testRevertMissingItemId() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.versionManagerMock).revert((Context)Mockito.any(), Mockito.anyString(), Mockito.anyString());

        this.mockMvc.perform(
            post("/api/1/version/revert/site").accept(MediaType.ALL)
                .param("versionToRevertTo", "1.0"))
            .andExpect(status().isBadRequest());

        verify(this.versionManagerMock, times(0)).revert((Context)Mockito.any(), Mockito.anyString(),
            Mockito.anyString());
    }

    @Test
    public void testRevertMissingRevertVersion() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.versionManagerMock).revert((Context)Mockito.any(), Mockito.anyString(), Mockito.anyString());

        this.mockMvc.perform(
            post("/api/1/version/revert/site").accept(MediaType.ALL)
                .param("itemId", "1"))
            .andExpect(status().isBadRequest());

        verify(this.versionManagerMock, times(0)).revert((Context)Mockito.any(), Mockito.anyString(),
            Mockito.anyString());
    }
}
