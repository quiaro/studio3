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

import org.apache.commons.io.IOUtils;
import org.craftercms.studio.api.search.SearchManager;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.ResultSet;
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

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for SearchController.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/spring/mockito-context.xml", "/spring/web-context.xml", "/spring/messageFormatting-studio3-web-context.xml"})
public class SearchControllerTest {

    // Mocks
    @Autowired
    private SearchManager searchManagerMock;

    @InjectMocks
    private SearchController searchController;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        reset(this.searchManagerMock);
    }

    @Test
    public void testFind() throws Exception {
        when(this.searchManagerMock.find((Context)Mockito.any(), Mockito.anyString())).thenReturn
            (generateResultSetMock());

        this.mockMvc.perform(
            get("/api/1/search/find/site?query=q")
                .accept(MediaType.ALL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        ;

        verify(this.searchManagerMock, times(1)).find((Context)Mockito.any(), Mockito.anyString());
    }

    @Test
    public void testFindMissingQuery() throws Exception {
        when(this.searchManagerMock.find((Context)Mockito.any(), Mockito.anyString())).thenReturn
            (generateResultSetMock());

        this.mockMvc.perform(
            get("/api/1/search/find/site")
                .accept(MediaType.ALL))
            .andExpect(status().isBadRequest())
        ;

        verify(this.searchManagerMock, times(0)).find((Context)Mockito.any(), Mockito.anyString());
    }

    private ResultSet generateResultSetMock() {
        int size = 5 + (int)(Math.random() * (5));
        ResultSet rs = new ResultSet();
        rs.setSize(size);
        return rs;
    }
}
