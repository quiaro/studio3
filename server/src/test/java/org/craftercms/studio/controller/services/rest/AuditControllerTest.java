package org.craftercms.studio.controller.services.rest;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.craftercms.studio.api.audit.AuditService;
import org.craftercms.studio.commons.dto.Activity;
import org.craftercms.studio.commons.dto.Context;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuditControllerTest extends AbstractControllerTest {

    @Autowired
    private AuditService auditService;

    @InjectMocks
    private AuditController auditController;

    @After
    public void tearDown() throws Exception {
        reset(this.auditService);
    }

    @Test
    public void testGetActivities() throws Exception {
        when(auditService.getActivities(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyListOf(String.class))).thenReturn(createActivities());

        this.mockMvc.perform(get("/api/1/audit/activity/TestSite"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.", Matchers.any(Collection.class)))
                .andExpect(jsonPath("$.[0].creator", Matchers.equalTo("Carlos Ortiz")))
                .andExpect(jsonPath("$.[0].targetProperties", Matchers.any(Map.class)))
                .andExpect(jsonPath("$.[0].targetProperties", Matchers.hasKey("targetProp2"))
                        // prints request/response useful for debug
                        //.andDo(print()
                );

        verify(this.auditService, times(1)).getActivities(Mockito.any(Context.class), Mockito.anyString(),
                Mockito.anyListOf(String.class));
    }

    @Test
    public void testSaveActivity() throws Exception {
        final String saveId=UUID.randomUUID().toString();
        when(this.auditService.logActivity(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(Activity.class)))
                .thenAnswer(new Answer<Object>() {
                    @Override
                    public Object answer(final InvocationOnMock invocation) throws Throwable {
                        Activity activity = (Activity) invocation.getArguments()[2];
                        activity.setId(saveId);
                        return activity;
                    }
                });

        this.mockMvc.perform(post("/api/1/audit/log/testSite")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createActivityJson(false)))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(saveId))
                );

        verify(this.auditService, times(1)).logActivity(Mockito.any(Context.class),
                Mockito.anyString(), Mockito.any(Activity.class));
    }

    @Test
    public void testSaveActivityInvalid() throws Exception {
        final String saveId=UUID.randomUUID().toString();
        when(this.auditService.logActivity(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.any(Activity.class)))
                .thenAnswer(new Answer<Object>() {
                    @Override
                    public Object answer(final InvocationOnMock invocation) throws Throwable {
                        Activity activity = (Activity) invocation.getArguments()[2];
                        activity.setId(saveId);
                        return activity;
                    }
                });

        this.mockMvc.perform(post("/api/1/audit/log/testSite")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createActivityJson(true)))
                //.andDo(print())
                .andExpect(status().isBadRequest()
                );

        verify(this.auditService, times(0)).logActivity(Mockito.any(Context.class),
                Mockito.anyString(), Mockito.any(Activity.class));
    }
}
