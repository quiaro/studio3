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

import org.craftercms.studio.api.search.SearchService;
import org.craftercms.studio.commons.dto.Context;
import org.junit.After;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

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
public class SearchControllerTest extends AbstractControllerTest {

    // Mocks
    @Autowired
    private SearchService searchServiceMock;

    @InjectMocks
    private SearchController searchController;

    @After
    public void tearDown() {
        reset(this.searchServiceMock);
    }

    @Test
    public void testFind() throws Exception {
        when(this.searchServiceMock.find(Mockito.any(Context.class), Mockito.anyString()))
            .thenReturn(generateResultSetMock());

        this.mockMvc.perform(
            get("/api/1/search/find/site?query=q")
                .accept(MediaType.ALL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        ;

        verify(this.searchServiceMock, times(1)).find(Mockito.any(Context.class), Mockito.anyString());
    }

    @Test
    public void testFindMissingQuery() throws Exception {
        when(this.searchServiceMock.find(Mockito.any(Context.class), Mockito.anyString()))
            .thenReturn(generateResultSetMock());

        this.mockMvc.perform(get("/api/1/search/find/site").accept(MediaType.ALL))
            .andExpect(status().isBadRequest())
        ;

        verify(this.searchServiceMock, times(0)).find(Mockito.any(Context.class), Mockito.anyString());
    }

}
