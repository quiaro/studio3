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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.craftercms.studio.api.content.ContentManager;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.LockHandle;
import org.craftercms.studio.commons.dto.Site;
import org.craftercms.studio.commons.exception.ItemNotFoundException;
import org.craftercms.studio.commons.exception.StudioException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
                        get("/api/1/content/read/sample?itemId=1&version=1")
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
                get("/api/1/content/read/sample")
                        .accept(MediaType.ALL))
                .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).read((Context)Mockito.any(), Mockito.anyString());
    }

    @Test
    public void testGetContentNonExistingContent() throws Exception {
        doThrow(new ItemNotFoundException("Unit test.")).when(this.contentManagerMock).read((Context)Mockito.any(), Mockito.anyString());

        this.mockMvc.perform(
                get("/api/1/content/read/sample?itemId=1&version=1")
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
                get("/api/1/content/read/sample?itemId=1&version=1")
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
                get("/api/1/content/read/sample?version=1")
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
                get("/api/1/content/read/sample?itemId=1")
                        .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().bytes(IOUtils.toByteArray(reader)))
        ;

        verify(this.contentManagerMock, times(1)).read((Context) Mockito.any(), Mockito.anyString());
    }

    @Test
    public void testUpdate() throws Exception {
        InputStream updateContent = this.getClass().getResourceAsStream("/content/update.xml");
        byte[] reqBody = IOUtils.toByteArray(updateContent);
        assertNotNull(updateContent);

        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).update((Context) Mockito.any(), Mockito.anyString(), (InputStream) Mockito.any());

        this.mockMvc.perform(
                post("/api/1/content/update/site")
                        .accept(MediaType.ALL)
                        .param("itemId", "1")
                        .content(reqBody)
        )
                .andExpect(status().isOk())
        ;

        verify(this.contentManagerMock, times(1)).update((Context) Mockito.any(), Mockito.anyString(), (InputStream) Mockito.any());
    }

    @Test
    public void testUpdateMissingItemId() throws Exception {
        InputStream updateContent = this.getClass().getResourceAsStream("/content/update.xml");
        byte[] reqBody = IOUtils.toByteArray(updateContent);
        assertNotNull(updateContent);

        doAnswer(new Answer() {
            @Override
            public Void answer(final InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).update((Context)Mockito.any(), Mockito.anyString(), (InputStream)Mockito.any());

        this.mockMvc.perform(
            post("/api/1/content/update/site")
                .accept(MediaType.ALL)
                .content(reqBody)
        )
            .andExpect(status().isBadRequest())
        ;

        verify(this.contentManagerMock, times(0)).update((Context) Mockito.any(), Mockito.anyString(),
            (InputStream)Mockito.any());
    }

    @Test
    public void testUpdateNullContent() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Void answer(final InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).update((Context) Mockito.any(), Mockito.anyString(), (InputStream)Mockito.any());

        this.mockMvc.perform(
            post("/api/1/content/update/site")
                .accept(MediaType.ALL)
                .param("itemId", "1")
                .content((new String()).getBytes())
        )
            .andExpect(status().isBadRequest())
        ;

        verify(this.contentManagerMock, times(0)).update((Context) Mockito.any(), Mockito.anyString(),
            (InputStream)Mockito.any());
    }

    @Test
    public void testOpenForEdit() throws Exception {
        when(this.contentManagerMock.open((Context)Mockito.any(), Mockito.anyString())).thenReturn(createLockHandle());

        this.mockMvc.perform(
                get("/api/1/content/open/site?itemId=1").accept(MediaType.ALL))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(this.contentManagerMock, times(1)).open((Context)Mockito.any(), Mockito.anyString());
    }

    private LockHandle createLockHandle() {
        LockHandle lockHandle = new LockHandle();
        lockHandle.setId(UUID.randomUUID().toString());
        return lockHandle;
    }

    @Test
    public void testOpenForEditMissingItemId() throws Exception {
        when(this.contentManagerMock.open((Context)Mockito.any(), Mockito.anyString())).thenReturn(createLockHandle());

        this.mockMvc.perform(
            get("/api/1/content/open/site").accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).open((Context)Mockito.any(), Mockito.anyString());
    }

    @Test
    public void testSaveContent() throws Exception {
        InputStream updateContent = this.getClass().getResourceAsStream("/content/save.xml");
        byte[] reqBody = IOUtils.toByteArray(updateContent);
        assertNotNull(updateContent);

        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).save((Context) Mockito.any(), Mockito.anyString(),
                (LockHandle)Mockito.any(), (InputStream) Mockito.any());

        this.mockMvc.perform(
            post("/api/1/content/save/site")
                .accept(MediaType.ALL)
                .param("itemId", "1")
                .param("lockHandleId", UUID.randomUUID().toString())
                .content(reqBody)
        )
            .andExpect(status().isOk())
        ;

        verify(this.contentManagerMock, times(1)).save((Context) Mockito.any(), Mockito.anyString(),
            (LockHandle)Mockito.any(), (InputStream) Mockito.any());
    }

    @Test
    public void testSaveContentMissingItemId() throws Exception {
        InputStream updateContent = this.getClass().getResourceAsStream("/content/save.xml");
        byte[] reqBody = IOUtils.toByteArray(updateContent);
        assertNotNull(updateContent);

        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).save((Context) Mockito.any(), Mockito.anyString(),
            (LockHandle)Mockito.any(), (InputStream) Mockito.any());

        this.mockMvc.perform(
            post("/api/1/content/save/site")
                .accept(MediaType.ALL)
                .param("lockHandleId", UUID.randomUUID().toString())
                .content(reqBody)
        )
            .andExpect(status().isBadRequest())
        ;

        verify(this.contentManagerMock, times(0)).save((Context) Mockito.any(), Mockito.anyString(),
            (LockHandle)Mockito.any(), (InputStream) Mockito.any());
    }

    @Test
    public void testSaveContentMissingLockHandle() throws Exception {
        InputStream updateContent = this.getClass().getResourceAsStream("/content/save.xml");
        byte[] reqBody = IOUtils.toByteArray(updateContent);
        assertNotNull(updateContent);

        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).save((Context) Mockito.any(), Mockito.anyString(),
            (LockHandle)Mockito.any(), (InputStream) Mockito.any());

        this.mockMvc.perform(
            post("/api/1/content/save/site")
                .accept(MediaType.ALL)
                .param("itemId", "1")
                .content(reqBody)
        )
            .andExpect(status().isBadRequest())
        ;

        verify(this.contentManagerMock, times(0)).save((Context) Mockito.any(), Mockito.anyString(),
            (LockHandle)Mockito.any(), (InputStream) Mockito.any());
    }

    @Test
    public void testSaveContentMissingContent() throws Exception {

        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).save((Context) Mockito.any(), Mockito.anyString(),
            (LockHandle)Mockito.any(), (InputStream) Mockito.any());

        this.mockMvc.perform(
            post("/api/1/content/save/site")
                .accept(MediaType.ALL)
                .param("itemId", "1")
                .param("lockHandleId", UUID.randomUUID().toString())
                .content((new String()).getBytes())
        )
            .andExpect(status().isBadRequest())
        ;

        verify(this.contentManagerMock, times(0)).save((Context) Mockito.any(), Mockito.anyString(),
            (LockHandle)Mockito.any(), (InputStream) Mockito.any());
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
        List<Site> siteListMock = generateSiteListMock();
        String siteListJSON = generateJSONSiteList(siteListMock);
        when(this.contentManagerMock.getSiteList((Context) Mockito.any())).thenReturn(siteListMock);

        this.mockMvc.perform(
                get("/api/1/content/site_list")
                        .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(siteListJSON))
        ;

        verify(this.contentManagerMock, times(1)).getSiteList((Context) Mockito.any());
    }

    private String generateJSONSiteList(List<Site> siteList) throws JSONException {
        ObjectMapper mapper = new ObjectMapper();

        String toRet = "";
        try {
            toRet = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(siteList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return toRet;
    }

    private List<Site> generateSiteListMock() {
        List<Site> toRet = new ArrayList<Site>();
        for (int i = 0; i < 5 + (int)(Math.random() * ((10 - 5) + 1)); i++) {
            Site site = new Site();
            site.setSiteId(RandomStringUtils.randomAlphabetic(10));
            site.setSiteName(RandomStringUtils.randomAlphabetic(10));
            toRet.add(site);
        }
        return toRet;
    }

    @Test
    public void testGetSiteListNoSites() {

    }
}
