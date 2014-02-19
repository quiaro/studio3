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

package org.craftercms.studio.ext.spring;

import org.craftercms.studio.exceptions.formatter.impl.AbstractExceptionFormatter;
import org.craftercms.studio.testing.controllers.CrafterCMSExceptionResolverTestController;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for CrafterCMSExceptionResolver
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/spring/mockito-context.xml", "/spring/web-context.xml", "/spring/test-servlet-context.xml"})
public class CrafterCMSExceptionResolverTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc =  MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * Test default Exception.
     * @throws Exception
     */
    @Ignore
    @Test
    public void testDoResolveKnowException(){
        try {
            this.mockMvc.perform(get("/testing/throwUnregisterCrafterCMSException").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$." + AbstractExceptionFormatter.JSON_MESSAGE_KEY)
                            .value(CrafterCMSExceptionResolverTestController.EXCEPTION_MSG))
                    .andExpect(jsonPath("$" + AbstractExceptionFormatter.JSON_CODE_KEY)
                            .value(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        } catch (Exception e) {
            fail("Unable to Test testDoResolveException due "+e.toString());
        }

    }


    /**
     * Test default Exception.
     * @throws Exception
     */
    @Test
    public void testDoResolveFallback(){
        try {
            this.mockMvc.perform(get("/testing/throwUnregisterException").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$." + AbstractExceptionFormatter.JSON_MESSAGE_KEY)
                            .value(CrafterCMSExceptionResolverTestController.EXCEPTION_MSG))
                    .andExpect(jsonPath("$" + AbstractExceptionFormatter.JSON_CODE_KEY)
                            .value(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        } catch (Exception e) {
            fail("Unable to Test testDoResolveException due "+e.toString());
        }

    }
}
