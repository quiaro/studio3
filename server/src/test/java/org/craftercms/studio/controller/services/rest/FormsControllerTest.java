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
import org.apache.commons.lang.StringUtils;
import org.craftercms.studio.api.content.FormService;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Form;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit test for forms controller
 */
public class FormsControllerTest extends AbstractControllerTest {

    @Autowired
    private FormService formServiceMock;

    @InjectMocks
    private FormsController formsController;

    @After
    public void tearDown() throws Exception {
        reset(this.formServiceMock);
    }

    @Test
    public void testList() throws Exception {
        List<Form> formsList = generateFormDefinitionList();
        when(this.formServiceMock.list(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyListOf(String.class))).thenReturn(formsList);

        this.mockMvc.perform(
            get("/api/1/forms/list/sample")
                .param("filters", "filter1", "filter2")
                .accept(MediaType.ALL))
            .andExpect(status().isOk())
            .andExpect(content().bytes(generateRequestBody(formsList).getBytes()));

        verify(this.formServiceMock, times(1)).list(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyListOf(String.class));
    }

    @Test
    public void testListMissingFilters() throws Exception {
        List<Form> formsList = generateFormDefinitionList();
        when(this.formServiceMock.list(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyListOf(String.class))).thenReturn(formsList);

        this.mockMvc.perform(
            get("/api/1/forms/list/sample")
                .accept(MediaType.ALL))
            .andExpect(status().isOk())
            .andExpect(content().bytes(generateRequestBody(formsList).getBytes()));

        verify(this.formServiceMock, times(1)).list(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyListOf(String.class));
    }

    @Test
    public void testUpdate() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.formServiceMock).update(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(Form.class));

        this.mockMvc.perform(post("/api/1/forms/update/sample").contentType(MediaType.APPLICATION_JSON).content
            (generateRequestBody(createFormDefinitionMock())).accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.formServiceMock, times(1)).update(Mockito.any(Context.class), Mockito.anyString(), Mockito.any
            (Form.class));
    }

    @Test
    public void testUpdateMissingFormDefinition() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.formServiceMock).update(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(Form.class));

        this.mockMvc.perform(post("/api/1/forms/update/sample").contentType(MediaType.APPLICATION_JSON).content
            (StringUtils.EMPTY).accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.formServiceMock, times(0)).update(Mockito.any(Context.class), Mockito.anyString(), Mockito.any
            (Form.class));
    }

    @Test
    public void testRemove() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.formServiceMock).remove(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString());

        this.mockMvc.perform(post("/api/1/forms/remove/sample")
            .param("type", RandomStringUtils.randomAlphabetic(10))
            .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.formServiceMock, times(1)).remove(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void testRemoveMissingType() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.formServiceMock).remove(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString());

        this.mockMvc.perform(post("/api/1/forms/remove/sample")
            .accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.formServiceMock, times(0)).remove(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString());
    }

    @Test
    public void testCopy() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.formServiceMock).copy(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.anyString());

        this.mockMvc.perform(post("/api/1/forms/copy/sample")
            .param("src", RandomStringUtils.randomAlphabetic(200))
            .param("dst", RandomStringUtils.randomAlphabetic(200))
            .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.formServiceMock, times(1)).copy(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void testCopyMissingSrc() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.formServiceMock).copy(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.anyString());

        this.mockMvc.perform(post("/api/1/forms/copy/sample")
            .param("dst", RandomStringUtils.randomAlphabetic(200))
            .accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.formServiceMock, times(0)).copy(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void testCopyMissingDst() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.formServiceMock).copy(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString(),
            Mockito.anyString());

        this.mockMvc.perform(post("/api/1/forms/copy/sample")
            .param("src", RandomStringUtils.randomAlphabetic(200))
            .accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.formServiceMock, times(0)).copy(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.anyString());
    }
}
