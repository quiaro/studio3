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

import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.craftercms.studio.api.content.ContentManager;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.LockHandle;
import org.craftercms.studio.commons.dto.LockStatus;
import org.craftercms.studio.commons.dto.Site;
import org.craftercms.studio.commons.dto.Tree;
import org.craftercms.studio.commons.dto.TreeNode;
import org.craftercms.studio.commons.exception.ItemNotFoundException;
import org.craftercms.studio.commons.exception.StudioException;
import org.json.JSONException;
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

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    }

    @After
    public void tearDown() {
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
        }).when(this.contentManagerMock).save((Context)Mockito.any(), Mockito.anyString(), (LockHandle)Mockito.any(),
            (InputStream)Mockito.any());

        this.mockMvc.perform(
            post("/api/1/content/save/site")
                .accept(MediaType.ALL)
                .param("itemId", "1")
                .param("lockHandleId", UUID.randomUUID().toString())
                .content(reqBody)
        )
            .andExpect(status().isOk())
        ;

        verify(this.contentManagerMock, times(1)).save((Context)Mockito.any(), Mockito.anyString(),
            (LockHandle)Mockito.any(), (InputStream)Mockito.any());
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
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).close((Context)Mockito.any(), Mockito.anyString(), (LockHandle)Mockito.any());

        this.mockMvc.perform(
            post("/api/1/content/close/site").accept(MediaType.ALL)
                .param("itemId", "1")
                .param("lockHandleId", UUID.randomUUID().toString()))
            .andExpect(status().isOk());

        verify(this.contentManagerMock, times(1)).close((Context)Mockito.any(), Mockito.anyString(),
            (LockHandle)Mockito.any());
    }

    protected String generateLockHandleJson() {
        LockHandle lh = createLockHandle();
        ObjectMapper mapper = new ObjectMapper();
        String toRet = "";
        try {
            toRet = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(lh);
        } catch (JsonProcessingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return toRet;
    }

    @Test
    public void testCloseMissingItemId() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).close((Context)Mockito.any(), Mockito.anyString(), (LockHandle)Mockito.any());

        this.mockMvc.perform(
            post("/api/1/content/close/site").accept(MediaType.ALL)
                .param("lockHandleId", UUID.randomUUID().toString()))
            .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).close((Context)Mockito.any(), Mockito.anyString(),
            (LockHandle)Mockito.any());
    }

    @Test
    public void testCloseMissingLockHandle() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).close((Context)Mockito.any(), Mockito.anyString(), (LockHandle)Mockito.any());

        this.mockMvc.perform(
            post("/api/1/content/close/site").accept(MediaType.ALL)
                .param("itemId", "1"))
            .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).close((Context)Mockito.any(), Mockito.anyString(),
            (LockHandle)Mockito.any());
    }

    @Test
    public void testDeleteContent() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).delete((Context)Mockito.any(), (List<Item>)Mockito.any());

        String itemsJSONList = generateItemsJSONListMock(generateItemListMock());
        this.mockMvc.perform(post("/api/1/content/delete/site").accept(MediaType.ALL)
                .content(itemsJSONList.getBytes()))
        .andExpect(status().isOk());

        verify(this.contentManagerMock, times(1)).delete((Context)Mockito.any(), (List<Item>)Mockito.any());
    }

    @Test
    public void testDeleteContentMissingItems() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).delete((Context)Mockito.any(), (List<Item>)Mockito.any());

        this.mockMvc.perform(post("/api/1/content/delete/site").accept(MediaType.ALL)
            .content((new String()).getBytes()))
            .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).delete((Context)Mockito.any(), (List<Item>)Mockito.any());
    }

    private String generateItemsJSONListMock(List<Item> itemListMock) {
        ObjectMapper mapper = new ObjectMapper();

        String toRet = "";
        try {
            toRet = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(itemListMock);
        } catch (JsonProcessingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return toRet;

    }

    private List<Item> generateItemListMock() {
        List<Item> itemListMock = new ArrayList<>();
        for (int i = 0; i < 5 + (int)(Math.random() * ((10 - 5) + 1)); i++) {
            itemListMock.add(createItemMock());
        }
        return itemListMock;
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
    public void testCopy() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).copy((Context)Mockito.any(), (List<Item>)Mockito.any(), Mockito.anyString(),
            Mockito.anyBoolean());

        String itemsJSONList = generateItemsJSONListMock(generateItemListMock());
        this.mockMvc.perform(post("/api/1/content/copy/site").accept(MediaType.ALL)
            .param("destinationPath", RandomStringUtils.randomAlphabetic(10))
            .param("includeChildren", (new Random()).nextBoolean() ? "true" : "false")
            .content(itemsJSONList.getBytes()))
            .andExpect(status().isOk());

        verify(this.contentManagerMock, times(1)).copy((Context)Mockito.any(), (List<Item>)Mockito.any(),
            Mockito.anyString(), Mockito.anyBoolean());
    }

    @Test
    public void testCopyMissingIncludeChildren() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).copy((Context)Mockito.any(), (List<Item>)Mockito.any(), Mockito.anyString(),
            Mockito.anyBoolean());

        String itemsJSONList = generateItemsJSONListMock(generateItemListMock());
        this.mockMvc.perform(post("/api/1/content/copy/site").accept(MediaType.ALL)
            .param("destinationPath", RandomStringUtils.randomAlphabetic(10))
            .content(itemsJSONList.getBytes()))
            .andExpect(status().isOk());

        verify(this.contentManagerMock, times(1)).copy((Context)Mockito.any(), (List<Item>)Mockito.any(),
            Mockito.anyString(), Mockito.eq(true));
    }

    @Test
    public void testCopyMissingDestinationPath() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).copy((Context)Mockito.any(), (List<Item>)Mockito.any(), Mockito.anyString(),
            Mockito.anyBoolean());

        String itemsJSONList = generateItemsJSONListMock(generateItemListMock());
        this.mockMvc.perform(post("/api/1/content/copy/site").accept(MediaType.ALL)
            .param("includeChildren", (new Random()).nextBoolean() ? "true" : "false")
            .content(itemsJSONList.getBytes()))
            .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).copy((Context)Mockito.any(), (List<Item>)Mockito.any(),
            Mockito.anyString(), Mockito.anyBoolean());
    }

    @Test
    public void testCopyMissingItems() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).copy((Context)Mockito.any(), (List<Item>)Mockito.any(), Mockito.anyString(),
            Mockito.anyBoolean());

        this.mockMvc.perform(post("/api/1/content/copy/site").accept(MediaType.ALL)
            .param("destinationPath", RandomStringUtils.randomAlphabetic(10))
            .param("includeChildren", (new Random()).nextBoolean() ? "true" : "false")
            .content((new String()).getBytes()))
            .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).copy((Context)Mockito.any(), (List<Item>)Mockito.any(),
            Mockito.anyString(), Mockito.anyBoolean());
    }

    @Test
    public void testMove() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).move((Context)Mockito.any(), (List<Item>)Mockito.any(), Mockito.anyString());

        String itemsJSONList = generateItemsJSONListMock(generateItemListMock());
        this.mockMvc.perform(post("/api/1/content/move/site").accept(MediaType.ALL)
            .param("destinationPath", RandomStringUtils.randomAlphabetic(10))
            .content(itemsJSONList.getBytes()))
            .andExpect(status().isOk());

        verify(this.contentManagerMock, times(1)).move((Context)Mockito.any(), (List<Item>)Mockito.any(),
            Mockito.anyString());
    }

    @Test
    public void testMoveMissingItems() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).move((Context)Mockito.any(), (List<Item>)Mockito.any(), Mockito.anyString());

        this.mockMvc.perform(post("/api/1/content/move/site").accept(MediaType.ALL)
            .param("destinationPath", RandomStringUtils.randomAlphabetic(10))
            .content((new String()).getBytes()))
            .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).move((Context)Mockito.any(), (List<Item>)Mockito.any(),
            Mockito.anyString());
    }

    @Test
    public void testMoveMissingDestinationPath() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).move((Context)Mockito.any(), (List<Item>)Mockito.any(), Mockito.anyString());

        String itemsJSONList = generateItemsJSONListMock(generateItemListMock());
        this.mockMvc.perform(post("/api/1/content/move/site").accept(MediaType.ALL)
            .content(itemsJSONList.getBytes()))
            .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).move((Context)Mockito.any(), (List<Item>)Mockito.any(),
            Mockito.anyString());
    }

    @Test
    public void testLock() throws Exception {
        when(this.contentManagerMock.lock((Context)Mockito.any(), (List<Item>)Mockito.any()))
            .thenReturn(createLockHandle());

        String itemsJSONList = generateItemsJSONListMock(generateItemListMock());
        this.mockMvc.perform(
            post("/api/1/content/lock/site").accept(MediaType.ALL)
                .content(itemsJSONList.getBytes()))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(this.contentManagerMock, times(1)).lock((Context)Mockito.any(), (List<Item>)Mockito.any());
    }

    @Test
    public void testLockMissingItems() throws Exception {
        when(this.contentManagerMock.lock((Context)Mockito.any(), (List<Item>) Mockito.any()))
            .thenReturn(createLockHandle());

        this.mockMvc.perform(post("/api/1/content/lock/site").accept(MediaType.ALL).content((new String()).getBytes()))
            .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).lock((Context)Mockito.any(), (List<Item>)Mockito.any());
    }

    @Test
    public void testUnlock() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).unlock((Context)Mockito.any(), (List<Item>)Mockito.any(), (LockHandle)
            Mockito.any());

        String itemsJSONList = generateItemsJSONListMock(generateItemListMock());
        this.mockMvc.perform(post("/api/1/content/unlock/site").accept(MediaType.ALL).param("lockHandle",
            createLockHandle().getId()).content(itemsJSONList.getBytes()))
            .andExpect(status().isOk());

        verify(this.contentManagerMock, times(1)).unlock((Context)Mockito.any(), (List<Item>)Mockito.any(), (LockHandle)Mockito.any());
    }

    @Test
    public void testUnlockMissingLockHandle() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).unlock((Context)Mockito.any(), (List<Item>) Mockito.any(),
            (LockHandle)Mockito.any());

        String itemsJSONList = generateItemsJSONListMock(generateItemListMock());
        this.mockMvc.perform(
            post("/api/1/content/unlock/site").accept(MediaType.ALL)
                .content(itemsJSONList.getBytes()))
            .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).unlock((Context)Mockito.any(), (List<Item>)Mockito.any(),
            (LockHandle) Mockito.any());
    }

    @Test
    public void testUnlockMissingItems() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).unlock((Context)Mockito.any(), (List<Item>) Mockito.any(),
            (LockHandle)Mockito.any());

        String itemsJSONList = generateItemsJSONListMock(generateItemListMock());
        this.mockMvc.perform(
            post("/api/1/content/unlock/site").accept(MediaType.ALL)
                .param("lockHandle", createLockHandle().getId())
                .content((new String()).getBytes()))
            .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).unlock((Context)Mockito.any(), (List<Item>)Mockito.any(),
            (LockHandle) Mockito.any());
    }

    @Test
    public void testGetLockStatus() throws Exception {
        when(this.contentManagerMock.getLockStatus((Context)Mockito.any(), (List<Item>)Mockito.any()))
            .thenReturn(createLockStatus());

        String itemsJSONList = generateItemsJSONListMock(generateItemListMock());
        this.mockMvc.perform(
            post("/api/1/content/get_lock_status/site").accept(MediaType.ALL)
                .content(itemsJSONList.getBytes()))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(this.contentManagerMock, times(1)).getLockStatus((Context)Mockito.any(), (List<Item>)Mockito.any());
    }

    private List<LockStatus> createLockStatus() {
        List<LockStatus> lockStatuses = new ArrayList<LockStatus>();
        for (int i = 0; i < 5 + (int)(Math.random() * ((10 - 5) + 1)); i++) {
            LockStatus ls = new LockStatus();
            ls.setValue(RandomStringUtils.randomAlphanumeric(10));
            lockStatuses.add(ls);
        }
        return lockStatuses;
    }

    @Test
    public void testGetLockStatusMissingItems() throws Exception {
        when(this.contentManagerMock.getLockStatus((Context)Mockito.any(), (List<Item>)Mockito.any()))
            .thenReturn(createLockStatus());

        String itemsJSONList = generateItemsJSONListMock(generateItemListMock());
        this.mockMvc.perform(post("/api/1/content/get_lock_status/site").accept(MediaType.ALL).content((new String())
            .getBytes()))
            .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).getLockStatus((Context)Mockito.any(), (List<Item>)Mockito.any());
    }

    @Test
    public void testGetChildren() throws Exception {
        when(this.contentManagerMock.list((Context)Mockito.any(), Mockito.anyString())).thenReturn(generateItemListMock());

        this.mockMvc.perform(
            get("/api/1/content/list/site?itemId=1").accept(MediaType.ALL))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(this.contentManagerMock, times(1)).list((Context)Mockito.any(), Mockito.anyString());
    }

    @Test
    public void testGetChildrenMissingItemId() throws Exception {
        when(this.contentManagerMock.list((Context)Mockito.any(), Mockito.anyString())).thenReturn(generateItemListMock());

        this.mockMvc.perform(get("/api/1/content/list/site").accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).list((Context)Mockito.any(), Mockito.anyString());
    }

    @Test
    public void testGetTree() throws Exception {
        when(this.contentManagerMock.tree((Context)Mockito.any(), Mockito.anyString(), Mockito.anyInt(),
            Mockito.anyList(), Mockito.anyList())).thenReturn(generateItemTreeMock());

        this.mockMvc.perform(
            get("/api/1/content/tree/site?itemId=1&depth=1").accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.contentManagerMock, times(1)).tree((Context)Mockito.any(), Mockito.anyString(), Mockito.anyInt(),
            Mockito.anyList(), Mockito.anyList());
    }

    private Tree<Item> generateItemTreeMock() {
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

    @Test
    public void testGetTreeMissingItemId() throws Exception {
        when(this.contentManagerMock.tree((Context)Mockito.any(), Mockito.anyString(), Mockito.anyInt(),
            Mockito.anyList(), Mockito.anyList())).thenReturn(generateItemTreeMock());

        this.mockMvc.perform(
            get("/api/1/content/tree/site?depth=1").accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).tree((Context)Mockito.any(), Mockito.anyString(), Mockito.anyInt(),
            Mockito.anyList(), Mockito.anyList());
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
}
