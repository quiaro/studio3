
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.craftercms.studio.api.lifecycle.Action;
import org.craftercms.studio.api.lifecycle.LifecycleManager;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.junit.After;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit test for Lifecycle Controller.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/spring/mockito-context.xml", "/spring/web-context.xml", "/spring/messageFormatting-studio3-web-context.xml"})
public class LifecycleControllerTest {

    // Mocks
    @Autowired
    private LifecycleManager lifecycleManagerMock;
    
    @InjectMocks
    private LifecycleController lifecycleController;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

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

    private List<Action> generateListOfActions() {
        List<Action> actions = new ArrayList<Action>();
        for (int i = 0; i < 1 + (int)(Math.random() * (50)); i++) {
            actions.add(createActionMock());
        }
        return actions;
    }

    private Action createActionMock() {
        TestAction action = new TestAction();
        action.setId(UUID.randomUUID().toString());
        action.setName(RandomStringUtils.randomAlphabetic(10));
        return action;
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

    public class TestAction implements Action {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(final String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }
    }
}
