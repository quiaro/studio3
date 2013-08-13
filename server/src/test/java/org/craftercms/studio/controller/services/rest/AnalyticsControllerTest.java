package org.craftercms.studio.controller.services.rest;


import java.util.Map;

import org.craftercms.studio.api.analytics.AnalyticsManager;
import org.craftercms.studio.api.dto.AnalyticsReport;
import org.craftercms.studio.api.dto.Context;
import org.craftercms.studio.api.exception.ItemNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test {@link AnalyticsController}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/spring/mockito-context.xml", "/spring/web-context.xml"})
public class AnalyticsControllerTest {


    @Autowired
    private AnalyticsManager analyticsManager;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        reset(this.analyticsManager);
    }

    @Test
    public void testReportIsCall() throws Exception {
        when(this.analyticsManager.report((Context)Mockito.any(), Mockito.anyString(),
                                            Mockito.anyString(), Mockito.anyMapOf(String.class,Object.class))).
                thenReturn(new AnalyticsReport("testReport"));

           this.mockMvc.perform(
                  get("/api/1/analytics/report/testSite?report=testReport") //Url
                  .contentType(MediaType.APPLICATION_JSON)) //
                  .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Check that is JSON
                  .andExpect(jsonPath("$.reportName").value("testReport")); // Check that Response is a Serialize DTO

        verify(this.analyticsManager,times(1)).report((Context)Mockito.any(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyMapOf(String.class,Object.class));
    }

    @Test
    public void testSiteSendParams() throws Exception {
        when(this.analyticsManager.report((Context)Mockito.any(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyMapOf(String.class,Object.class)))
                .then(new Answer<AnalyticsReport>() {
                    @Override
                    public AnalyticsReport answer(final InvocationOnMock invocation) throws Throwable {
                        Map map=Map.class.cast(invocation.getArguments()[3]);
                        return new AnalyticsReport((String)map.get("testParam"));
                    }
                });
        this.mockMvc.perform(
                get("/api/1/analytics/report/testSite?report=testReport&testParam=TestParamReport")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Check that is JSON
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reportName").value("TestParamReport")); //

        verify(this.analyticsManager, times(1)).report((Context)Mockito.any(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyMapOf(String.class,Object.class));
    }


    @Test
    public void testSiteNotFound() throws Exception {
        when(this.analyticsManager.report((Context)Mockito.any(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyMapOf(String.class,Object.class)))
                .thenThrow(new ItemNotFoundException("Site testSite does Not Exist"));

        this.mockMvc.perform(
                get("/api/1/analytics/report/testSite?report=testReport") //Url
                        .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Check that is JSON
                .andExpect(status().isNotFound());

        verify(this.analyticsManager, times(1)).report((Context)Mockito.any(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyMapOf(String.class,Object.class));
    }

    @Test
    public void testReportNameNotFound() throws Exception {
        when(this.analyticsManager.report((Context)Mockito.any(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyMapOf(String.class,Object.class)))
                .thenThrow(new ItemNotFoundException("Report testReport does Not Exist"));

        this.mockMvc.perform(
                get("/api/1/analytics/report/testSite?report=testReport") //Url
                        .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Check that is JSON
                .andExpect(status().isNotFound());

        verify(this.analyticsManager, times(1)).report((Context)Mockito.any(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyMapOf(String.class,Object.class));
    }

}
