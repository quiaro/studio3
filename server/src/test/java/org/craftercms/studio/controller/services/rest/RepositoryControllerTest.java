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
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.craftercms.studio.api.content.ContentManager;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.LockHandle;
import org.craftercms.studio.commons.dto.Site;
import org.craftercms.studio.commons.dto.Tree;
import org.craftercms.studio.commons.exception.ItemNotFoundException;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.commons.extractor.ItemExtractor;
import org.craftercms.studio.commons.filter.ItemFilter;
import org.craftercms.studio.mock.content.TreeMock;
import org.junit.After;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

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
public class RepositoryControllerTest extends AbstractControllerTest {

    // Mocks
    @Autowired
    private ContentManager contentManagerMock;

    @InjectMocks
    private RepositoryController repositoryController;

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
        when(this.contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString())).thenReturn(sampleContent);

        this.mockMvc.perform(
                        get("/api/1/content/read/sample?itemId=1&version=1")
                                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().bytes(IOUtils.toByteArray(reader)))
        ;

        verify(this.contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString());
    }

    @Test
    public void testGetContentNoParameters() throws Exception {
        when(this.contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString())).thenReturn(IOUtils
            .toInputStream("TEST"));

        this.mockMvc.perform(
                get("/api/1/content/read/sample")
                        .accept(MediaType.ALL))
                .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).read(Mockito.any(Context.class), Mockito.anyString());
    }

    @Test
    public void testGetContentNonExistingContent() throws Exception {
        doThrow(new ItemNotFoundException("Unit test.")).when(this.contentManagerMock).read(Mockito.any(Context.class),
            Mockito.anyString());

        this.mockMvc.perform(
                get("/api/1/content/read/sample?itemId=1&version=1")
                        .accept(MediaType.ALL))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        ;

        verify(this.contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString());

    }

    @Test
    public void testGetContentInternalException() throws Exception {
        doThrow(new StudioException("Unit test.") {

            private static final long serialVersionUID = 949955896967217476L;

        }).when(this.contentManagerMock).read(Mockito.any(Context.class), Mockito.anyString());

        this.mockMvc.perform(
                get("/api/1/content/read/sample?itemId=1&version=1")
                        .accept(MediaType.ALL))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        ;

        verify(this.contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString());
    }

    @Test
    public void testGetContentMissingContentId() throws Exception {
        when(this.contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString())).thenReturn(IOUtils
            .toInputStream("TEST"));

        this.mockMvc.perform(
                get("/api/1/content/read/sample?version=1")
                        .accept(MediaType.ALL))
                .andExpect(status().isBadRequest())
        ;

        verify(this.contentManagerMock, times(0)).read(Mockito.any(Context.class), Mockito.anyString());
    }



    @Test
    public void testGetContentMissingVersion() throws Exception {
        InputStream sampleContent = this.getClass().getResourceAsStream("/content/sample.xml");
        URL url = this.getClass().getResource("/content/sample.xml");
        FileReader reader = new FileReader(url.getFile());
        assertNotNull(sampleContent);
        when(this.contentManagerMock.read(Mockito.any(Context.class), Mockito.anyString())).thenReturn(sampleContent);

        this.mockMvc.perform(
                get("/api/1/content/read/sample?itemId=1")
                        .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().bytes(IOUtils.toByteArray(reader)))
        ;

        verify(this.contentManagerMock, times(1)).read(Mockito.any(Context.class), Mockito.anyString());
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
        }).when(this.contentManagerMock).update(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(InputStream.class));

        this.mockMvc.perform(
                post("/api/1/content/update/site")
                        .accept(MediaType.ALL)
                        .param("itemId", "1")
                        .content(reqBody)
        )
                .andExpect(status().isOk())
        ;

        verify(this.contentManagerMock, times(1)).update(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(InputStream.class));
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
        }).when(this.contentManagerMock).update(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(InputStream.class));

        this.mockMvc.perform(
            post("/api/1/content/update/site")
                .accept(MediaType.ALL)
                .content(reqBody)
        )
            .andExpect(status().isBadRequest())
        ;

        verify(this.contentManagerMock, times(0)).update(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(InputStream.class));
    }

    @Test
    public void testUpdateNullContent() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Void answer(final InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).update(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(InputStream.class));

        this.mockMvc.perform(post("/api/1/content/update/site").accept(MediaType.ALL).param("itemId",
            "1").content(StringUtils.EMPTY.getBytes()))
            .andExpect(status().isBadRequest())
        ;

        verify(this.contentManagerMock, times(0)).update(Mockito.any(Context.class), Mockito.anyString(), Mockito.any
            (InputStream.class));
    }

    @Test
    public void testOpenForEdit() throws Exception {
        when(this.contentManagerMock.open(Mockito.any(Context.class), Mockito.anyString())).thenReturn(createLockHandleMock());

        this.mockMvc.perform(
                get("/api/1/content/open/site?itemId=1").accept(MediaType.ALL))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(this.contentManagerMock, times(1)).open((Context)Mockito.any(), Mockito.anyString());
    }

    @Test
    public void testOpenForEditMissingItemId() throws Exception {
        when(this.contentManagerMock.open((Context)Mockito.any(), Mockito.anyString()))
            .thenReturn(createLockHandleMock());

        this.mockMvc.perform(
            get("/api/1/content/open/site").accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).open(Mockito.any(Context.class), Mockito.anyString());
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
        }).when(this.contentManagerMock).save(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(LockHandle.class), Mockito.any(InputStream.class));

        this.mockMvc.perform(
            post("/api/1/content/save/site")
                .accept(MediaType.ALL)
                .param("itemId", "1")
                .param("lockHandleId", UUID.randomUUID().toString())
                .content(reqBody)
        )
            .andExpect(status().isOk())
        ;

        verify(this.contentManagerMock, times(1)).save(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
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
        }).when(this.contentManagerMock).save(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(LockHandle.class), Mockito.any(InputStream.class));

        this.mockMvc.perform(
            post("/api/1/content/save/site")
                .accept(MediaType.ALL)
                .param("lockHandleId", UUID.randomUUID().toString())
                .content(reqBody)
        )
            .andExpect(status().isBadRequest())
        ;

        verify(this.contentManagerMock, times(0)).save(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
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
        }).when(this.contentManagerMock).save(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(LockHandle.class), Mockito.any(InputStream.class));

        this.mockMvc.perform(
            post("/api/1/content/save/site")
                .accept(MediaType.ALL)
                .param("itemId", "1")
                .content(reqBody)
        )
            .andExpect(status().isBadRequest())
        ;

        verify(this.contentManagerMock, times(0)).save(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
    }

    @Test
    public void testSaveContentMissingContent() throws Exception {

        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).save(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(LockHandle
            .class), Mockito.any(InputStream.class));

        this.mockMvc.perform(
            post("/api/1/content/save/site")
                .accept(MediaType.ALL)
                .param("itemId", "1")
                .param("lockHandleId", UUID.randomUUID().toString())
                .content(StringUtils.EMPTY.getBytes())
        )
            .andExpect(status().isBadRequest())
        ;

        verify(this.contentManagerMock, times(0)).save(Mockito.any(Context.class), Mockito.anyString(), Mockito.any(LockHandle.class), Mockito.any(InputStream.class));
    }

    @Test
    public void testClose() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).close(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(LockHandle.class));

        this.mockMvc.perform(
            post("/api/1/content/close/site").accept(MediaType.ALL)
                .param("itemId", "1")
                .param("lockHandleId", UUID.randomUUID().toString()))
            .andExpect(status().isOk());

        verify(this.contentManagerMock, times(1)).close(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(LockHandle.class));
    }

    @Test
    public void testCloseMissingItemId() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).close(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(LockHandle.class));

        this.mockMvc.perform(
            post("/api/1/content/close/site").accept(MediaType.ALL)
                .param("lockHandleId", UUID.randomUUID().toString()))
            .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).close(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(LockHandle.class));
    }

    @Test
    public void testCloseMissingLockHandle() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).close(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(LockHandle.class));

        this.mockMvc.perform(
            post("/api/1/content/close/site").accept(MediaType.ALL)
                .param("itemId", "1"))
            .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).close(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(LockHandle.class));
    }

    @Test
    public void testDeleteContent() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).delete(Mockito.any(Context.class),Mockito.anyListOf(Item.class));

        this.mockMvc.perform(post("/api/1/content/delete/site").accept(MediaType.ALL)
                .content(generateRequestBody(generateItemListMock()).getBytes()))
        .andExpect(status().isOk());

        verify(this.contentManagerMock, times(1)).delete(Mockito.any(Context.class), Mockito.anyListOf(Item.class));
    }

    @Test
    public void testDeleteContentMissingItems() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).delete(Mockito.any(Context.class), Mockito.anyListOf(Item.class));

        this.mockMvc.perform(post("/api/1/content/delete/site").accept(MediaType.ALL)
            .content((new String()).getBytes()))
            .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).delete(Mockito.any(Context.class), Mockito.anyListOf(Item.class));
    }

    @Test
    public void testCopy() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).copy(Mockito.any(Context.class), Mockito.anyListOf(Item.class),
            Mockito.anyString(), Mockito.anyBoolean());

        String itemsJSONList = generateRequestBody(generateItemListMock());
        this.mockMvc.perform(post("/api/1/content/copy/site").accept(MediaType.ALL)
            .param("destinationPath", RandomStringUtils.randomAlphabetic(10))
            .param("includeChildren", (new Random()).nextBoolean() ? "true" : "false")
            .content(itemsJSONList.getBytes()))
            .andExpect(status().isOk());

        verify(this.contentManagerMock, times(1)).copy(Mockito.any(Context.class), Mockito.anyListOf(Item.class),
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
        }).when(this.contentManagerMock).copy(Mockito.any(Context.class), Mockito.anyListOf(Item.class),
            Mockito.anyString(), Mockito.anyBoolean());

        String itemsJSONList = generateRequestBody(generateItemListMock());
        this.mockMvc.perform(post("/api/1/content/copy/site").accept(MediaType.ALL)
            .param("destinationPath", RandomStringUtils.randomAlphabetic(10))
            .content(itemsJSONList.getBytes()))
            .andExpect(status().isOk());

        verify(this.contentManagerMock, times(1)).copy(Mockito.any(Context.class), Mockito.anyListOf(Item.class),
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
        }).when(this.contentManagerMock).copy(Mockito.any(Context.class), Mockito.anyListOf(Item.class),
            Mockito.anyString(), Mockito.anyBoolean());

        String itemsJSONList = generateRequestBody(generateItemListMock());
        this.mockMvc.perform(post("/api/1/content/copy/site").accept(MediaType.ALL)
            .param("includeChildren", (new Random()).nextBoolean() ? "true" : "false")
            .content(itemsJSONList.getBytes()))
            .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).copy(Mockito.any(Context.class), Mockito.anyListOf(Item.class),
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
        }).when(this.contentManagerMock).copy(Mockito.any(Context.class), Mockito.anyListOf(Item.class),
            Mockito.anyString(), Mockito.anyBoolean());

        this.mockMvc.perform(post("/api/1/content/copy/site").accept(MediaType.ALL)
            .param("destinationPath", RandomStringUtils.randomAlphabetic(10))
            .param("includeChildren", (new Random()).nextBoolean() ? "true" : "false")
            .content(StringUtils.EMPTY.getBytes()))
            .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).copy(Mockito.any(Context.class), Mockito.anyListOf(Item.class),
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
        }).when(this.contentManagerMock).move(Mockito.any(Context.class), Mockito.anyListOf(Item.class),
            Mockito.anyString());

        String itemsJSONList = generateRequestBody(generateItemListMock());
        this.mockMvc.perform(post("/api/1/content/move/site").accept(MediaType.ALL)
            .param("destinationPath", RandomStringUtils.randomAlphabetic(10))
            .content(itemsJSONList.getBytes()))
            .andExpect(status().isOk());

        verify(this.contentManagerMock, times(1)).move(Mockito.any(Context.class), Mockito.anyListOf(Item.class),
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
        }).when(this.contentManagerMock).move(Mockito.any(Context.class), Mockito.anyListOf(Item.class),
            Mockito.anyString());

        this.mockMvc.perform(post("/api/1/content/move/site").accept(MediaType.ALL)
            .param("destinationPath", RandomStringUtils.randomAlphabetic(10))
            .content(StringUtils.EMPTY.getBytes()))
            .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).move(Mockito.any(Context.class), Mockito.anyListOf(Item.class),
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
        }).when(this.contentManagerMock).move(Mockito.any(Context.class), Mockito.anyListOf(Item.class),
            Mockito.anyString());

        String itemsJSONList = generateRequestBody(generateItemListMock());
        this.mockMvc.perform(post("/api/1/content/move/site").accept(MediaType.ALL)
            .content(itemsJSONList.getBytes()))
            .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).move(Mockito.any(Context.class), Mockito.anyListOf(Item.class),
            Mockito.anyString());
    }

    @Test
    public void testLock() throws Exception {
        when(this.contentManagerMock.lock(Mockito.any(Context.class), Mockito.anyListOf(Item.class)))
            .thenReturn(createLockHandleMock());

        String itemsJSONList = generateRequestBody(generateItemListMock());
        this.mockMvc.perform(
            post("/api/1/content/lock/site").accept(MediaType.ALL)
                .content(itemsJSONList.getBytes()))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(this.contentManagerMock, times(1)).lock(Mockito.any(Context.class), Mockito.anyListOf(Item.class));
    }

    @Test
    public void testLockMissingItems() throws Exception {
        when(this.contentManagerMock.lock(Mockito.any(Context.class), Mockito.anyListOf(Item.class)))
            .thenReturn(createLockHandleMock());

        this.mockMvc.perform(post("/api/1/content/lock/site").accept(MediaType.ALL).content(StringUtils.EMPTY.getBytes()))
            .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).lock(Mockito.any(Context.class), Mockito.anyListOf(Item.class));
    }

    @Test
    public void testUnlock() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).unlock(Mockito.any(Context.class), Mockito.anyListOf(Item.class),
            Mockito.any(LockHandle.class));

        String itemsJSONList = generateRequestBody(generateItemListMock());
        this.mockMvc.perform(post("/api/1/content/unlock/site").accept(MediaType.ALL).param("lockHandle",
            createLockHandleMock().getId()).content(itemsJSONList.getBytes()))
            .andExpect(status().isOk());

        verify(this.contentManagerMock, times(1)).unlock(Mockito.any(Context.class), Mockito.anyListOf(Item.class),
            Mockito.any(LockHandle.class));
    }

    @Test
    public void testUnlockMissingLockHandle() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).unlock(Mockito.any(Context.class), Mockito.anyListOf(Item.class),
            Mockito.any(LockHandle.class));

        String itemsJSONList = generateRequestBody(generateItemListMock());
        this.mockMvc.perform(
            post("/api/1/content/unlock/site").accept(MediaType.ALL)
                .content(itemsJSONList.getBytes()))
            .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).unlock(Mockito.any(Context.class), Mockito.anyListOf(Item.class),
            Mockito.any(LockHandle.class));
    }

    @Test
    public void testUnlockMissingItems() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.contentManagerMock).unlock(Mockito.any(Context.class), Mockito.anyListOf(Item.class),
            Mockito.any(LockHandle.class));

        String itemsJSONList = generateRequestBody(generateItemListMock());
        this.mockMvc.perform(
            post("/api/1/content/unlock/site").accept(MediaType.ALL)
                .param("lockHandle", createLockHandleMock().getId())
                .content(StringUtils.EMPTY.getBytes()))
            .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).unlock(Mockito.any(Context.class), Mockito.anyListOf(Item.class),
            Mockito.any(LockHandle.class));
    }

    @Test
    public void testGetLockStatus() throws Exception {
        when(this.contentManagerMock.getLockStatus(Mockito.any(Context.class), Mockito.anyListOf(Item.class)))
            .thenReturn(createLockStatus());

        String itemsJSONList = generateRequestBody(generateItemListMock());
        this.mockMvc.perform(
            post("/api/1/content/get_lock_status/site").accept(MediaType.ALL)
                .content(itemsJSONList.getBytes()))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(this.contentManagerMock, times(1)).getLockStatus(Mockito.any(Context.class), Mockito.anyListOf(Item.class));
    }

    @Test
    public void testGetLockStatusMissingItems() throws Exception {
        when(this.contentManagerMock.getLockStatus((Context)Mockito.any(), (List<Item>)Mockito.any()))
            .thenReturn(createLockStatus());

        String itemsJSONList = generateRequestBody(generateItemListMock());
        this.mockMvc.perform(post("/api/1/content/get_lock_status/site")
            .accept(MediaType.ALL)
            .content(StringUtils.EMPTY.getBytes()))
            .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).getLockStatus(Mockito.any(Context.class), Mockito.anyListOf(Item.class));
    }

    @Test
    public void testGetChildren() throws Exception {
        when(this.contentManagerMock.list(Mockito.any(Context.class), Mockito.anyString()))
            .thenReturn(generateItemListMock());

        this.mockMvc.perform(
            get("/api/1/content/list/site?itemId=1").accept(MediaType.ALL))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(this.contentManagerMock, times(1)).list(Mockito.any(Context.class), Mockito.anyString());
    }

    @Test
    public void testGetChildrenMissingItemId() throws Exception {
        when(this.contentManagerMock.list(Mockito.any(Context.class), Mockito.anyString()))
            .thenReturn(generateItemListMock());

        this.mockMvc.perform(get("/api/1/content/list/site").accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).list(Mockito.any(Context.class), Mockito.anyString());
    }

    @Test
    public void testGetTree() throws Exception {
        when(this.contentManagerMock.tree(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyInt(),
            Mockito.anyListOf(ItemFilter.class), Mockito.anyListOf(ItemExtractor.class))).thenReturn(generateItemTreeMock
            ());

        Tree<Item> tree = generateItemTreeMock();
        System.out.println(generateRequestBody(tree));

        try {
            JAXBContext jc = JAXBContext.newInstance(Tree.class);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.marshal(tree, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        this.mockMvc.perform(
            get("/api/1/content/tree/site?itemId=1&depth=1").accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.contentManagerMock, times(1)).tree((Context)Mockito.any(), Mockito.anyString(), Mockito.anyInt(),
            Mockito.anyList(), Mockito.anyList());
    }



    @Test
    public void testGetTreeMissingItemId() throws Exception {
        when(this.contentManagerMock.tree(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyInt(),
            Mockito.anyListOf(ItemFilter.class), Mockito.anyListOf(ItemExtractor.class))).thenReturn(generateItemTreeMock
            ());

        this.mockMvc.perform(
            get("/api/1/content/tree/site?depth=1").accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.contentManagerMock, times(0)).tree(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyInt(),
            Mockito.anyListOf(ItemFilter.class), Mockito.anyListOf(ItemExtractor.class));
    }

    @Test
    public void testGetSites() throws Exception {
        List<Site> siteListMock = generateSiteListMock();
        String siteListJSON = generateRequestBody(siteListMock);
        when(this.contentManagerMock.getSiteList(Mockito.any(Context.class))).thenReturn(siteListMock);

        this.mockMvc.perform(
                get("/api/1/content/site_list")
                        .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(siteListJSON))
        ;

        verify(this.contentManagerMock, times(1)).getSiteList(Mockito.any(Context.class));
    }
}
