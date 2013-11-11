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

import org.craftercms.studio.api.content.VersionManager;
import org.craftercms.studio.commons.dto.Context;
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
 * Unit test for Version Controller
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public class VersionControllerTest extends AbstractControllerTest {

    // Mocks
    @Autowired
    private VersionManager versionManagerMock;

    @InjectMocks
    private VersionController versionController;

    @After
    public void tearDown() {
        reset(this.versionManagerMock);
    }

    @Test
    public void testGetContent() throws Exception {
        when(this.versionManagerMock.history(Mockito.any(Context.class), Mockito.anyString()))
            .thenReturn(generateVersionTree());

        this.mockMvc.perform(
            get("/api/1/version/history/site?itemId=1")
                .accept(MediaType.ALL))
            .andExpect(status().isOk())
        ;

        verify(this.versionManagerMock, times(1)).history(Mockito.any(Context.class), Mockito.anyString());
    }

    @Test
    public void testGetContentMissingItemId() throws Exception {
        when(this.versionManagerMock.history(Mockito.any(Context.class), Mockito.anyString()))
            .thenReturn(generateVersionTree());

        this.mockMvc.perform(
            get("/api/1/version/history/site")
                .accept(MediaType.ALL))
            .andExpect(status().isBadRequest())
        ;

        verify(this.versionManagerMock, times(0)).history(Mockito.any(Context.class), Mockito.anyString());
    }

    @Test
    public void testRevert() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.versionManagerMock).revert(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString());

        this.mockMvc.perform(
            post("/api/1/version/revert/site").accept(MediaType.ALL)
                .param("itemId", "1")
                .param("versionToRevertTo", "1.0"))
            .andExpect(status().isOk());

        verify(this.versionManagerMock, times(1)).revert(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString());
    }

    @Test
    public void testRevertMissingItemId() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.versionManagerMock).revert(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString());

        this.mockMvc.perform(
            post("/api/1/version/revert/site").accept(MediaType.ALL)
                .param("versionToRevertTo", "1.0"))
            .andExpect(status().isBadRequest());

        verify(this.versionManagerMock, times(0)).revert(Mockito.any(Context.class), Mockito.anyString(),
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
        }).when(this.versionManagerMock).revert(Mockito.any(Context.class), Mockito.anyString(), Mockito.anyString());

        this.mockMvc.perform(
            post("/api/1/version/revert/site").accept(MediaType.ALL)
                .param("itemId", "1"))
            .andExpect(status().isBadRequest());

        verify(this.versionManagerMock, times(0)).revert(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString());
    }
}
