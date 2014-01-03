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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.craftercms.studio.api.configuration.ConfigurationManager;
import org.craftercms.studio.commons.dto.Configuration;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.controller.services.rest.dto.ConfigurationWriteRequest;
import org.junit.After;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for Configuration Controller
 * 
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public class ConfigurationControllerTest extends AbstractControllerTest {
    
    @Autowired
    private ConfigurationManager configurationManagerMock;
    
    @InjectMocks
    private ConfigurationController configurationController;

    @After
    public void tearDown() throws Exception {
        reset(this.configurationManagerMock);
    }

    @Test
    public void testConfiguration() throws Exception {
        when(this.configurationManagerMock.getConfiguration(Mockito.any(Context.class),
            Mockito.anyString(), Mockito.anyString())).thenReturn(createModuleConfigurationMock());

        this.mockMvc.perform(
            get("/api/1/config/configuration/sample")
                .param("module", RandomStringUtils.randomAlphabetic(10))
                .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.configurationManagerMock, times(1)).getConfiguration(Mockito.any(Context.class),
            Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void testConfigurationMissingModuleName() throws Exception {
        when(this.configurationManagerMock.getConfiguration(Mockito.any(Context.class),
            Mockito.anyString(), Mockito.anyString())).thenReturn(createModuleConfigurationMock());

        this.mockMvc.perform(
            get("/api/1/config/configuration/sample")
                .accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.configurationManagerMock, times(0)).getConfiguration(Mockito.any(Context.class),
            Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void testConfigure() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.configurationManagerMock).configure(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.any(Configuration.class));

        this.mockMvc.perform(
            post("/api/1/config/configure/sample")
                .param("module", RandomStringUtils.randomAlphabetic(10))
                .content(generateRequestBody(createModuleConfigurationMock()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.configurationManagerMock, times(1)).configure(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.any(Configuration.class));
    }

    @Test
    public void testConfigureMissingModuleParam() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.configurationManagerMock).configure(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.any(Configuration.class));

        this.mockMvc.perform(
            post("/api/1/config/configure/sample")
                .content(generateRequestBody(createModuleConfigurationMock()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.configurationManagerMock, times(0)).configure(Mockito.any(Context.class),
            Mockito.anyString(), Mockito.anyString(), Mockito.any(Configuration.class));
    }

    @Test
    public void testConfigureMissingConfiguration() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.configurationManagerMock).configure(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString(), Mockito.any(Configuration.class));

        this.mockMvc.perform(
            post("/api/1/config/configure/sample")
                .param("module", RandomStringUtils.randomAlphabetic(10))
                .content(StringUtils.EMPTY)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.configurationManagerMock, times(0)).configure(Mockito.any(Context.class),
            Mockito.anyString(), Mockito.anyString(), Mockito.any(Configuration.class));
    }

    @Test
    public void testGetContent() throws Exception {
        when(this.configurationManagerMock.getContent(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(ItemId.class))).thenReturn(getSampleConfiguration());

        this.mockMvc.perform(
            get("/api/1/config/content/sample")
                .param("object", RandomStringUtils.randomAlphabetic(10))
                .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.configurationManagerMock, times(1)).getContent(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(ItemId.class));
    }

    private InputStream getSampleConfiguration() {
        InputStream sampleContent = this.getClass().getResourceAsStream("/configuration/sample.xml");
        assertNotNull(sampleContent);
        return sampleContent;
    }

    @Test
    public void testGetContentMissingObject() throws Exception {
        when(this.configurationManagerMock.getContent(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(ItemId.class))).thenReturn(getSampleConfiguration());

        this.mockMvc.perform(
            get("/api/1/config/content/sample")
                .accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.configurationManagerMock, times(0)).getContent(Mockito.any(Context.class),
            Mockito.anyString(), Mockito.any(ItemId.class));
    }

    @Test
    public void testWrite() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.configurationManagerMock).write(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(ItemId.class), Mockito.any(InputStream.class));

        this.mockMvc.perform(post("/api/1/config/write/sample")
            .param("object", RandomStringUtils.randomAlphabetic(10))
            .content(generateRequestBody(createWriteRequest()).getBytes())
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.configurationManagerMock, times(1)).write(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(ItemId.class), Mockito.any(InputStream.class));
    }

    private ConfigurationWriteRequest createWriteRequest() {
        ConfigurationWriteRequest request = new ConfigurationWriteRequest();
        try {
            request.setContent(IOUtils.toString(getSampleConfiguration()));
        } catch (IOException e) {
            request.setContent(RandomStringUtils.randomAlphanumeric(256));
        }
        return request;
    }

    @Test
    public void testWriteMissingObject() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.configurationManagerMock).write(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(ItemId.class), Mockito.any(InputStream.class));

        this.mockMvc.perform(
            post("/api/1/config/write/sample")
                .content(generateRequestBody(getSampleConfiguration()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.configurationManagerMock, times(0)).write(Mockito.any(Context.class),
            Mockito.anyString(), Mockito.any(ItemId.class), Mockito.any(InputStream.class));
    }

    @Test
    public void testWriteMissingRequestBody() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.configurationManagerMock).write(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(ItemId.class), Mockito.any(InputStream.class));

        this.mockMvc.perform(
            post("/api/1/config/write/sample")
                .param("object", RandomStringUtils.randomAlphabetic(10))
                .content(StringUtils.EMPTY)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.configurationManagerMock, times(0)).write(Mockito.any(Context.class),
            Mockito.anyString(), Mockito.any(ItemId.class), Mockito.any(InputStream.class));
    }
}
