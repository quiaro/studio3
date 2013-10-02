
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

import java.util.UUID;

import org.craftercms.studio.api.lifecycle.LifecycleManager;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit test for Lifecycle Controller.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public class LifecycleControllerTest extends AbstractControllerTest {

    // Mocks
    @Autowired
    private LifecycleManager lifecycleManagerMock;
    
    @InjectMocks
    private LifecycleController lifecycleController;

    @After
    public void tearDown() throws Exception {
        reset(this.lifecycleManagerMock);
    }

    @Test
    public void testActions() throws Exception {
        when(this.lifecycleManagerMock.getPossibleActions(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyListOf(String.class))).thenReturn(generateListOfActions());

        this.mockMvc.perform(
            get("/api/1/lifecycle/actions/sample")
                .param("itemIds", UUID.randomUUID().toString(), UUID.randomUUID().toString())
                .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.lifecycleManagerMock, times(1)).getPossibleActions(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyListOf(String.class));
    }

    @Test
    public void testActionsMissingItems() throws Exception {
        when(this.lifecycleManagerMock.getPossibleActions(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyListOf(String.class))).thenReturn(generateListOfActions());

        this.mockMvc.perform(
            get("/api/1/lifecycle/actions/sample")
                .accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.lifecycleManagerMock, times(0)).getPossibleActions(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyListOf(String.class));
    }
}
