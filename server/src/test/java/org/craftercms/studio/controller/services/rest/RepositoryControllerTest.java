/*
 * Copyright (C) 2007-2013 Crafter Software Corporation.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.craftercms.studio.controller.services.rest;

import org.apache.commons.io.IOUtils;
import org.craftercms.studio.api.content.ContentManager;
import org.craftercms.studio.api.dto.Context;
import org.craftercms.studio.api.exception.ItemNotFoundException;
import org.craftercms.studio.api.exception.StudioException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.*;
import java.net.URL;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Unit test for RepositoryController.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/spring/mockito-context.xml", "/spring/web-context.xml", "/spring/messageFormatting-studio3-web-context.xml"})
public class RepositoryControllerTest {

    // Mocks
    @Autowired
    private ContentManager contentManagerMock;

    @InjectMocks
    private RepositoryController repositoryController;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        reset(this.contentManagerMock);
    }

    @Test
    public void testGetContent() throws Exception {
        InputStream sampleContent = this.getClass().getResourceAsStream("/content/sample.xml");
        URL url = this.getClass().getResource("/content/sample.xml");
        FileReader reader = new FileReader(url.getFile());
        assertNotNull(sampleContent);
        when(this.contentManagerMock.read((Context) Mockito.any(), (String)Mockito.any())).thenReturn(sampleContent);

        this.mockMvc.perform(
                        get("/api/1/content/read?itemId=1&version=1")
                                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().bytes(IOUtils.toByteArray(reader)))
        ;

        verify(this.contentManagerMock, times(1)).read((Context) Mockito.any(), Mockito.anyString());
    }

    @Test
    public void testGetContentNoParameters() throws Exception {
        when(this.contentManagerMock.read((Context) Mockito.any(), (String)Mockito.any())).thenReturn(IOUtils.toInputStream("TEST"));

        this.mockMvc.perform(
                get("/api/1/content/read")
                        .accept(MediaType.ALL))
                .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).read((Context)Mockito.any(), Mockito.anyString());
    }

    @Test
    public void testGetContentNonExistingContent() throws Exception {
        doThrow(new ItemNotFoundException("Unit test.")).when(this.contentManagerMock).read((Context)Mockito.any(), Mockito.anyString());

        this.mockMvc.perform(
                get("/api/1/content/read?itemId=1&version=1")
                        .accept(MediaType.ALL))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        ;

        verify(this.contentManagerMock, times(1)).read((Context) Mockito.any(), Mockito.anyString());

    }

    @Test
    public void testGetContentInternalException() throws Exception {
        doThrow(new StudioException("Unit test.") {

            private static final long serialVersionUID = 949955896967217476L;

        }).when(this.contentManagerMock).read((Context) Mockito.any(), Mockito.anyString());

        this.mockMvc.perform(
                get("/api/1/content/read?itemId=1&version=1")
                        .accept(MediaType.ALL))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        ;

        verify(this.contentManagerMock, times(1)).read((Context) Mockito.any(), Mockito.anyString());
    }

    @Test
    public void testGetContentMissingContentId() throws Exception {
        when(this.contentManagerMock.read((Context) Mockito.any(), (String)Mockito.any())).thenReturn(IOUtils.toInputStream("TEST"));

        this.mockMvc.perform(
                get("/api/1/content/read?version=1")
                        .accept(MediaType.ALL))
                .andExpect(status().isBadRequest())
        ;

        verify(this.contentManagerMock, times(0)).read((Context) Mockito.any(), Mockito.anyString());
    }



    @Test
    public void testGetContentMissingVersion() throws Exception {
        InputStream sampleContent = this.getClass().getResourceAsStream("/content/sample.xml");
        URL url = this.getClass().getResource("/content/sample.xml");
        FileReader reader = new FileReader(url.getFile());
        assertNotNull(sampleContent);
        when(this.contentManagerMock.read((Context) Mockito.any(), (String)Mockito.any())).thenReturn(sampleContent);

        this.mockMvc.perform(
                get("/api/1/content/read?itemId=1")
                        .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().bytes(IOUtils.toByteArray(reader)))
        ;

        verify(this.contentManagerMock, times(1)).read((Context) Mockito.any(), Mockito.anyString());
    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testOpenForEdit() throws Exception {

    }

    @Test
    public void testSaveContent() throws Exception {

    }

    @Test
    public void testClose() throws Exception {

    }

    @Test
    public void testDeleteContent() throws Exception {

    }

    @Test
    public void testCopy() throws Exception {

    }

    @Test
    public void testMove() throws Exception {

    }

    @Test
    public void testLock() throws Exception {

    }

    @Test
    public void testUnlock() throws Exception {

    }

    @Test
    public void testGetLockStatus() throws Exception {

    }

    @Test
    public void testGetChildren() throws Exception {

    }

    @Test
    public void testGetTree() throws Exception {

    }

    @Test
    public void testGetSites() throws Exception {

    }
}
