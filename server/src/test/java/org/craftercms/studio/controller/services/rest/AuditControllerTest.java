package org.craftercms.studio.controller.services.rest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.craftercms.studio.api.audit.AuditManager;
import org.craftercms.studio.commons.dto.Activity;
import org.craftercms.studio.commons.dto.Context;
import org.hamcrest.Matchers;
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

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/spring/mockito-context.xml", "/spring/web-context.xml"})
public class    AuditControllerTest {

    @Autowired
    private AuditManager auditManager;
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        reset(this.auditManager);
    }

    @Test
    public void testGetActivities() throws Exception {
        when(auditManager.getActivities((Context) Mockito.any(), Mockito.anyString(),
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

        verify(this.auditManager, times(1)).getActivities((Context) Mockito.any(), Mockito.anyString(),
                Mockito.anyListOf(String.class));
    }

    @Test
    public void testSaveActivity() throws Exception {
        final String saveId=UUID.randomUUID().toString();
        when(this.auditManager.logActivity((Context) Mockito.any(), Mockito.anyString(), Mockito.any(Activity.class)))
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

        verify(this.auditManager, times(1)).logActivity((Context) Mockito.any(),
                Mockito.anyString(),
                Mockito.any(Activity.class));
    }

    @Test
    public void testSaveActivityInvalid() throws Exception {
        final String saveId=UUID.randomUUID().toString();
        when(this.auditManager.logActivity((Context) Mockito.any(), Mockito.anyString(), Mockito.any(Activity.class)))
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

        verify(this.auditManager, times(0)).logActivity((Context) Mockito.any(),
                Mockito.anyString(),
                Mockito.any(Activity.class));
    }

    private String  createActivityJson(boolean broken) throws Exception {
        Activity activity1 = new Activity();

        activity1.setDate(new Date());
        activity1.setSiteId(UUID.randomUUID().toString());
        if(broken){
            activity1.setSiteName("");
            activity1.setTarget("");
            activity1.setType("");
            activity1.setCreator(" ");
        }else{
            activity1.setSiteName("testSite");
            activity1.setTarget("testTarget");
            activity1.setType("SAVED");
            activity1.setCreator("Carlos Ortiz");
        }
        HashMap<String, Object> targetProperties = new HashMap<>();
        targetProperties.put("targetProp1", "Hello");
        targetProperties.put("targetProp2", new Date());
        activity1.setTargetProperties(targetProperties);
        ObjectMapper mapper=new ObjectMapper();
        return mapper.writeValueAsString(activity1);
    }

    private List<Activity> createActivities() throws Exception {
        Activity activity1 = new Activity();
        activity1.setCreator("Carlos Ortiz");
        activity1.setDate(new Date());
        activity1.setSiteId(UUID.randomUUID().toString());
        activity1.setSiteName("testSite");
        activity1.setTarget("testTarget");
        activity1.setSiteName("");
        HashMap<String, Object> targetProperties = new HashMap<>();
        targetProperties.put("targetProp1", "Hello");
        targetProperties.put("targetProp2", new Date());
        activity1.setTargetProperties(targetProperties);
        // 2
        Activity activit2 = new Activity();
        activit2.setCreator("Carlos Ortiz");
        activit2.setDate(new Date());
        activit2.setSiteId(UUID.randomUUID().toString());
        activit2.setSiteName("testSite");
        activit2.setTarget("testTarget");
        activit2.setSiteName("");
        HashMap<String, Object> targetProperties2 = new HashMap<>();
        targetProperties2.put("targetProp1", "World");
        targetProperties2.put("targetProp2", new Date());
        activit2.setTargetProperties(targetProperties2);
        return Arrays.asList(activity1, activit2);
    }
}
