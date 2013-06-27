package org.craftercms.studio3.test.web.ext.spring;

import org.craftercms.studio3.testing.controllers.CrafterCMSExceptionResolverTestController;
import org.craftercms.studio3.web.ext.spring.CrafterCMSExceptionResolver;
import org.craftercms.studio3.web.support.message.MessageFormatterManager;
import org.craftercms.studio3.web.support.message.impl.AbstractExceptionMessageFormatter;
import org.junit.Before;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/spring/web-context.xml","/spring/test-servlet-context.xml"})
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
    @Test
    public void testDoResolveKnowException(){
        try {
            this.mockMvc.perform(get("/testing/throwUnregisterCrafterCMSException").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$." + AbstractExceptionMessageFormatter.JSON_MESSAGE_KEY)
                            .value(CrafterCMSExceptionResolverTestController.EXCEPTION_MSG))
                    .andExpect(jsonPath("$" + AbstractExceptionMessageFormatter.JSON_CODE_KEY)
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
                    .andExpect(jsonPath("$." + AbstractExceptionMessageFormatter.JSON_MESSAGE_KEY)
                            .value(CrafterCMSExceptionResolverTestController.EXCEPTION_MSG))
                    .andExpect(jsonPath("$" + AbstractExceptionMessageFormatter.JSON_CODE_KEY)
                            .value(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        } catch (Exception e) {
            fail("Unable to Test testDoResolveException due "+e.toString());
        }

    }
}
