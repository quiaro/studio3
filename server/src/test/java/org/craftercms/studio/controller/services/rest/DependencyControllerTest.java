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

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.craftercms.studio.api.dependency.DependencyManager;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
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
 * Unit tests for Dependency Controller.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public class DependencyControllerTest extends AbstractControllerTest {

    // Mocks
    @Autowired
    private DependencyManager dependencyManagerMock;

    @InjectMocks
    private DependencyController dependencyController;

    @After
    public void tearDown() throws Exception {
        reset(this.dependencyManagerMock);
    }

    @Test
    public void testDependentOn() throws Exception {
        when(this.dependencyManagerMock.dependentOn(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(generateItemListMock());

        this.mockMvc.perform(
            get("/api/1/dependency/dependent-on/sample?itemId=1&operation=op").accept(MediaType.ALL))
                .andExpect(status().isOk())
        ;

        verify(this.dependencyManagerMock, times(1)).dependentOn(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString());
    }

    @Test
    public void testDependsOnMissingItemId() throws Exception {
        when(this.dependencyManagerMock.dependentOn(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(generateItemListMock());

        this.mockMvc.perform(
            get("/api/1/dependency/dependent-on/sample?operation=op").accept(MediaType.ALL))
            .andExpect(status().isBadRequest())
        ;

        verify(this.dependencyManagerMock, times(0)).dependentOn(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString());
    }

    @Test
    public void testDependsOnMissingOperation() throws Exception {
        when(this.dependencyManagerMock.dependentOn(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(generateItemListMock());

        this.mockMvc.perform(
            get("/api/1/dependency/dependent-on/sample?itemId=1").accept(MediaType.ALL))
            .andExpect(status().isBadRequest())
        ;

        verify(this.dependencyManagerMock, times(0)).dependentOn((Context)Mockito.any(), Mockito.anyString(),
            Mockito.anyString());
    }

    @Test
    public void testList() throws Exception {
        when(this.dependencyManagerMock.dependsOn((Context)Mockito.any(), Mockito.anyString(), Mockito.anyString()))
            .thenReturn(generateItemListMock());

        this.mockMvc.perform(
            get("/api/1/dependency/list/sample?itemId=1&operation=op").accept(MediaType.ALL))
            .andExpect(status().isOk())
        ;

        verify(this.dependencyManagerMock, times(1)).dependsOn(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString());
    }

    @Test
    public void testListMissingItemId() throws Exception {
        when(this.dependencyManagerMock.dependsOn(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(generateItemListMock());

        this.mockMvc.perform(
            get("/api/1/dependency/list/sample?operation=op").accept(MediaType.ALL))
            .andExpect(status().isBadRequest())
        ;

        verify(this.dependencyManagerMock, times(0)).dependsOn(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString());
    }

    @Test
    public void testListMissingOperation() throws Exception {
        when(this.dependencyManagerMock.dependsOn(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(generateItemListMock());

        this.mockMvc.perform(
            get("/api/1/dependency/list/sample?itemId=1").accept(MediaType.ALL))
            .andExpect(status().isBadRequest())
        ;

        verify(this.dependencyManagerMock, times(0)).dependsOn(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString());
    }

    @Test
    public void testRefresh() throws Exception {
        when(this.dependencyManagerMock.refresh(Mockito.any(Context.class), Mockito.anyString()))
            .thenReturn(generateItemListMock());

        this.mockMvc.perform(
            get("/api/1/dependency/refresh/sample?itemId=1").accept(MediaType.ALL))
            .andExpect(status().isOk())
        ;

        verify(this.dependencyManagerMock, times(1)).refresh(Mockito.any(Context.class), Mockito.anyString());
    }

    @Test
    public void testRefreshMissingItemId() throws Exception {
        when(this.dependencyManagerMock.refresh(Mockito.any(Context.class), Mockito.anyString()))
            .thenReturn(generateItemListMock());

        this.mockMvc.perform(
            get("/api/1/dependency/refresh/sample").accept(MediaType.ALL))
            .andExpect(status().isBadRequest())
        ;

        verify(this.dependencyManagerMock, times(0)).refresh(Mockito.any(Context.class), Mockito.anyString());
    }

    @Test
    public void testAdd() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.dependencyManagerMock).add(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.anyListOf(Item.class));

        String itemsJSONList = generateRequestBody(generateItemListMock());
        this.mockMvc.perform(
            post("/api/1/dependency/add/sample")
                .accept(MediaType.ALL)
                .param("itemId", "1")
                .param("operation", RandomStringUtils.randomAlphabetic(10))
                .content(itemsJSONList.getBytes()))
            .andExpect(status().isOk());

        verify(this.dependencyManagerMock, times(1)).add(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.anyListOf(Item.class));
    }

    @Test
    public void testAddMissingItemId() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.dependencyManagerMock).add(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.anyListOf(Item.class));

        String itemsJSONList = generateRequestBody(generateItemListMock());
        this.mockMvc.perform(
            post("/api/1/dependency/add/sample")
                .accept(MediaType.ALL)
                .param("operation", RandomStringUtils.randomAlphabetic(10))
                .content(itemsJSONList.getBytes()))
            .andExpect(status().isBadRequest());

        verify(this.dependencyManagerMock, times(0)).add(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.anyListOf(Item.class));
    }

    @Test
    public void testAddMissingOperation() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.dependencyManagerMock).add(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.anyListOf(Item.class));

        String itemsJSONList = generateRequestBody(generateItemListMock());
        this.mockMvc.perform(
            post("/api/1/dependency/add/sample")
                .accept(MediaType.ALL)
                .param("itemId", "1")
                .content(itemsJSONList.getBytes()))
            .andExpect(status().isBadRequest());

        verify(this.dependencyManagerMock, times(0)).add(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.anyListOf(Item.class));
    }

    @Test
    public void testAddMissingDependencies() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.dependencyManagerMock).add(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.anyListOf(Item.class));

        this.mockMvc.perform(
            post("/api/1/dependency/add/sample")
                .accept(MediaType.ALL)
                .param("itemId", "1")
                .param("operation", RandomStringUtils.randomAlphabetic(10))
                .content(StringUtils.EMPTY.getBytes()))
            .andExpect(status().isBadRequest());

        verify(this.dependencyManagerMock, times(0)).add(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.anyListOf(Item.class));
    }

    @Test
    public void testRemove() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.dependencyManagerMock).remove(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.anyListOf(Item.class));

        String itemsJSONList = generateRequestBody(generateItemListMock());
        this.mockMvc.perform(
            post("/api/1/dependency/remove/sample")
                .accept(MediaType.ALL)
                .param("itemId", "1")
                .param("operation", RandomStringUtils.randomAlphabetic(10))
                .content(itemsJSONList.getBytes()))
            .andExpect(status().isOk());

        verify(this.dependencyManagerMock, times(1)).remove(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.anyListOf(Item.class));
    }

    @Test
    public void testRemoveMissingItemId() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.dependencyManagerMock).remove(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.anyListOf(Item.class));

        String itemsJSONList = generateRequestBody(generateItemListMock());
        this.mockMvc.perform(
            post("/api/1/dependency/remove/sample")
                .accept(MediaType.ALL)
                .param("operation", RandomStringUtils.randomAlphabetic(10))
                .content(itemsJSONList.getBytes()))
            .andExpect(status().isBadRequest());

        verify(this.dependencyManagerMock, times(0)).remove(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.anyListOf(Item.class));
    }

    @Test
    public void testRemoveMissingOperation() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.dependencyManagerMock).remove(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.anyListOf(Item.class));

        String itemsJSONList = generateRequestBody(generateItemListMock());
        this.mockMvc.perform(
            post("/api/1/dependency/remove/sample")
                .accept(MediaType.ALL)
                .param("itemId", "1")
                .content(itemsJSONList.getBytes()))
            .andExpect(status().isBadRequest());

        verify(this.dependencyManagerMock, times(0)).remove(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.anyListOf(Item.class));
    }

    @Test
    public void testRemoveMissingDependencies() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.dependencyManagerMock).remove(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.anyListOf(Item.class));

        this.mockMvc.perform(
            post("/api/1/dependency/remove/sample")
                .accept(MediaType.ALL)
                .param("itemId", "1")
                .param("operation", RandomStringUtils.randomAlphabetic(10))
                .content(StringUtils.EMPTY.getBytes()))
            .andExpect(status().isBadRequest());

        verify(this.dependencyManagerMock, times(0)).remove(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.anyListOf(Item.class));
    }

    @Test
    public void testUpdate() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.dependencyManagerMock).update(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.anyListOf(Item.class));

        String itemsJSONList = generateRequestBody(generateItemListMock());
        this.mockMvc.perform(
            post("/api/1/dependency/update/sample")
                .accept(MediaType.ALL)
                .param("itemId", "1")
                .param("operation", RandomStringUtils.randomAlphabetic(10))
                .content(itemsJSONList.getBytes()))
            .andExpect(status().isOk());

        verify(this.dependencyManagerMock, times(1)).update(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.anyListOf(Item.class));
    }

    @Test
    public void testUpdateMissingItemId() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.dependencyManagerMock).update(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.anyListOf(Item.class));

        String itemsJSONList = generateRequestBody(generateItemListMock());
        this.mockMvc.perform(
            post("/api/1/dependency/update/sample")
                .accept(MediaType.ALL)
                .param("operation", RandomStringUtils.randomAlphabetic(10))
                .content(itemsJSONList.getBytes()))
            .andExpect(status().isBadRequest());

        verify(this.dependencyManagerMock, times(0)).update(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.anyListOf(Item.class));
    }

    @Test
    public void testUpdateMissingOperation() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.dependencyManagerMock).update(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.anyListOf(Item.class));

        String itemsJSONList = generateRequestBody(generateItemListMock());
        this.mockMvc.perform(
            post("/api/1/dependency/update/sample")
                .accept(MediaType.ALL)
                .param("itemId", "1")
                .content(itemsJSONList.getBytes()))
            .andExpect(status().isBadRequest());

        verify(this.dependencyManagerMock, times(0)).update(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.anyListOf(Item.class));
    }

    @Test
    public void testUpdateMissingDependencies() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.dependencyManagerMock).update(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.anyListOf(Item.class));

        this.mockMvc.perform(
            post("/api/1/dependency/update/sample")
                .accept(MediaType.ALL)
                .param("itemId", "1")
                .param("operation", RandomStringUtils.randomAlphabetic(10))
                .content(StringUtils.EMPTY.getBytes()))
            .andExpect(status().isBadRequest());

        verify(this.dependencyManagerMock, times(0)).update(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.anyListOf(Item.class));
    }
}
