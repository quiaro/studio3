package org.craftercms.studio.controller.services.rest;


import java.util.Map;

import org.craftercms.studio.api.analytics.AnalyticsManager;
import org.craftercms.studio.commons.dto.AnalyticsReport;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.exception.ItemNotFoundException;
import org.junit.After;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test {@link AnalyticsController}
 */
public class AnalyticsControllerTest extends AbstractControllerTest {

    @Autowired
    private AnalyticsManager analyticsManager;

    @InjectMocks
    private AuditController auditController;

    @After
    public void tearDown() throws Exception {
        reset(this.analyticsManager);
    }

    @Test
    public void testReportIsCall() throws Exception {
        when(this.analyticsManager.report(Mockito.any(Context.class), Mockito.anyString(),
                                            Mockito.anyString(), Mockito.anyMapOf(String.class,Object.class))).
                thenReturn(new AnalyticsReport("testReport"));

           this.mockMvc.perform(
                  get("/api/1/analytics/report/testSite?report=testReport") //Url
                  .contentType(MediaType.APPLICATION_JSON)) //
                  .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Check that is JSON
                  .andExpect(jsonPath("$.reportName").value("testReport")); // Check that Response is a Serialize DTO

        verify(this.analyticsManager,times(1)).report(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyMapOf(String.class, Object.class));
    }

    @Test
    public void testSiteSendParams() throws Exception {
        when(this.analyticsManager.report(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyMapOf(String.class, Object.class)))
                .then(new Answer<AnalyticsReport>() {
                    @Override
                    public AnalyticsReport answer(final InvocationOnMock invocation) throws Throwable {
                        Map map = Map.class.cast(invocation.getArguments()[3]);
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
                Mockito.anyString(), Mockito.anyMapOf(String.class, Object.class));
    }


    @Test
    public void testSiteNotFound() throws Exception {
        when(this.analyticsManager.report((Context)Mockito.any(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyMapOf(String.class, Object.class)))
                .thenThrow(new ItemNotFoundException("Site testSite does Not Exist"));

        this.mockMvc.perform(
                get("/api/1/analytics/report/testSite?report=testReport") //Url
                        .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Check that is JSON
                .andExpect(status().isNotFound());

        verify(this.analyticsManager, times(1)).report(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyMapOf(String.class, Object.class));
    }

    @Test
    public void testReportNameNotFound() throws Exception {
        when(this.analyticsManager.report(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyMapOf(String.class, Object.class)))
                .thenThrow(new ItemNotFoundException("Report testReport does Not Exist"));

        this.mockMvc.perform(
                get("/api/1/analytics/report/testSite?report=testReport") //Url
                        .contentType(MediaType.APPLICATION_JSON)) //
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Check that is JSON
                .andExpect(status().isNotFound());

        verify(this.analyticsManager, times(1)).report(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyMapOf(String.class, Object.class));
    }

}
