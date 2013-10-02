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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.SecurityPermission;
import org.craftercms.studio.commons.dto.User;
import org.craftercms.studio.controller.services.rest.dto.UpdateUserRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import org.craftercms.studio.api.security.SecurityManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for Security controller
 */
public class SecurityControllerTest extends AbstractControllerTest {

    @Autowired
    private SecurityManager securityManagerMock;

    @InjectMocks
    private SecurityController securityController;

    @After
    public void tearDown() throws Exception {
        reset(this.securityManagerMock);
    }

    @Test
    public void testLogin() throws Exception {
        when(this.securityManagerMock.login(Mockito.any(URL.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(new Context());

        mockMvc.perform(
            post("/api/1/security/login")
                .param("username", RandomStringUtils.randomAlphabetic(10))
                .param("password", RandomStringUtils.randomAlphanumeric(10))
                .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.securityManagerMock, times(1)).login(Mockito.any(URL.class), Mockito.anyString(),
            Mockito.anyString());
    }

    @Test
    public void testLoginMissingUsername() throws Exception {
        when(this.securityManagerMock.login(Mockito.any(URL.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(new Context());

        mockMvc.perform(
            post("/api/1/security/login")
                .param("password", RandomStringUtils.randomAlphanumeric(10))
                .accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.securityManagerMock, times(0)).login(Mockito.any(URL.class), Mockito.anyString(),
            Mockito.anyString());
    }

    @Test
    public void testLoginMissingPassword() throws Exception {
        when(this.securityManagerMock.login(Mockito.any(URL.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(new Context());

        mockMvc.perform(
            post("/api/1/security/login")
                .param("username", RandomStringUtils.randomAlphabetic(10))
                .accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.securityManagerMock, times(0)).login(Mockito.any(URL.class), Mockito.anyString(),
            Mockito.anyString());
    }

    @Test
    public void testLoginEmptyUsername() throws Exception {
        when(this.securityManagerMock.login(Mockito.any(URL.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(new Context());

        mockMvc.perform(
            post("/api/1/security/login")
                .param("username", StringUtils.EMPTY)
                .param("password", RandomStringUtils.randomAlphanumeric(10))
                .accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.securityManagerMock, times(0)).login(Mockito.any(URL.class), Mockito.anyString(),
            Mockito.anyString());
    }

    @Test
    public void testLoginEmptyPassword() throws Exception {
        when(this.securityManagerMock.login(Mockito.any(URL.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(new Context());

        mockMvc.perform(
            post("/api/1/security/login")
                .param("username", RandomStringUtils.randomAlphanumeric(10))
                .param("password", StringUtils.EMPTY)
                .accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.securityManagerMock, times(0)).login(Mockito.any(URL.class), Mockito.anyString(),
            Mockito.anyString());
    }

    @Test
    public void testLoginInvalidUsername() throws Exception {
        // TODO: implement this test
        /*
        when(this.securityManagerMock.login(Mockito.any(URL.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(new Context());

        mockMvc.perform(
            post("/api/1/security/login")
                .param("username", RandomStringUtils.randomAlphabetic(10))
                .param("password", RandomStringUtils.randomAlphanumeric(10))
                .accept(MediaType.ALL))
            .andExpect(status().isUnauthorized());

        verify(this.securityManagerMock, times(1)).login(Mockito.any(URL.class), Mockito.anyString(),
            Mockito.anyString());
            */
    }

    @Test
    public void testLoginInvalidPassword() throws Exception {
        // TODO: implement this test
        /*
        when(this.securityManagerMock.login(Mockito.any(URL.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(new Context());

        mockMvc.perform(
            post("/api/1/security/login")
                .param("username", RandomStringUtils.randomAlphabetic(10))
                .param("password", RandomStringUtils.randomAlphanumeric(10))
                .accept(MediaType.ALL))
            .andExpect(status().isUnauthorized());

        verify(this.securityManagerMock, times(1)).login(Mockito.any(URL.class), Mockito.anyString(),
            Mockito.anyString());
            */
    }

    @Test
    public void testLogout() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.securityManagerMock).logout(Mockito.any(Context.class));

        mockMvc.perform(
            get("/api/1/security/logout")
                .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.securityManagerMock, times(1)).logout(Mockito.any(Context.class));
    }

    @Test
    public void testValidate() throws Exception {
        when(this.securityManagerMock.validate(Mockito.any(Context.class))).thenReturn(true);

        mockMvc.perform(
            get("/api/1/security/validate")
                .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.securityManagerMock, times(1)).validate(Mockito.any(Context.class));
    }

    @Test
    public void testValidateValidationFailed() throws Exception {
        when(this.securityManagerMock.validate(Mockito.any(Context.class))).thenReturn(false);

        mockMvc.perform(
            get("/api/1/security/validate")
                .accept(MediaType.ALL))
            .andExpect(status().isUnauthorized());

        verify(this.securityManagerMock, times(1)).validate(Mockito.any(Context.class));
    }

    @Test
    public void testUsers() throws Exception {
        when(this.securityManagerMock.getUsers(Mockito.any(Context.class), Mockito.anyString())).thenReturn
            (generateListOfUsersMock());

        mockMvc.perform(
            get("/api/1/security/users")
                .param("site", RandomStringUtils.randomAlphabetic(10))
                .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.securityManagerMock, times(1)).getUsers(Mockito.any(Context.class), Mockito.anyString());
    }

    private List<User> generateListOfUsersMock() {
        List<User> list = new ArrayList<User>();
        for (int i = 0; i < 5 + ((int)(Math.random() * 6)); i++) {
            list.add(createUserMock());
        }
        return list;
    }

    @Test
    public void testUpdateUser() throws Exception {
        when(this.securityManagerMock.updateUser(Mockito.any(Context.class), Mockito.any(User.class),
            Mockito.anyString(), Mockito.anyString())).thenReturn(UUID.randomUUID().toString());

        mockMvc.perform(
            post("/api/1/security/update_user")
                .content(generateRequestBody(generateUpdateUserRequest()).getBytes())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.securityManagerMock, times(1)).updateUser(Mockito.any(Context.class), Mockito.any(User.class), 
            Mockito.anyString(), Mockito.anyString());
    }

    private UpdateUserRequest generateUpdateUserRequest() {
        UpdateUserRequest req = new UpdateUserRequest();
        req.setUser(createUserMock());
        req.setPassword(RandomStringUtils.randomAlphanumeric(10));
        req.setRole(RandomStringUtils.randomAlphanumeric(20));
        return req;
    }
    
    // TODO: unit test for update user invalid requests


    @Test
    public void testRemoveUser() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.securityManagerMock).removeUser(Mockito.any(Context.class), Mockito.anyString());

        mockMvc.perform(
            post("/api/1/security/remove_user")
                .param("user", RandomStringUtils.randomAlphabetic(10))
                .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.securityManagerMock, times(1)).removeUser(Mockito.any(Context.class), Mockito.anyString());
    }

    @Test
    public void testRemoveUserMissingUser() throws Exception {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return null;
            }
        }).when(this.securityManagerMock).removeUser(Mockito.any(Context.class), Mockito.anyString());

        mockMvc.perform(
            post("/api/1/security/remove_user")
                .accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.securityManagerMock, times(0)).removeUser(Mockito.any(Context.class), Mockito.anyString());
    }

    @Test
    public void testPermissions() throws Exception {
        when(this.securityManagerMock.getPermissions(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(generateListOfPermissionsMock());

        mockMvc.perform(
            get("/api/1/security/permissions")
                .param("site", RandomStringUtils.randomAlphabetic(10))
                .param("itemId", UUID.randomUUID().toString())
                .accept(MediaType.ALL))
            .andExpect(status().isOk());

        verify(this.securityManagerMock, times(1)).getPermissions(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString());
    }

    private List<SecurityPermission> generateListOfPermissionsMock() {
        List<SecurityPermission> list = new ArrayList<SecurityPermission>();
        for (int i = 0; i < 5 + ((int)(Math.random() * 6)); i++) {
            list.add(createSecurityPermissionMock());
        }
        return list;
    }

    @Test
    public void testPermissionsMissingSite() throws Exception {
        when(this.securityManagerMock.getPermissions(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(generateListOfPermissionsMock());

        mockMvc.perform(
            get("/api/1/security/permissions")
                .param("itemId", UUID.randomUUID().toString())
                .accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.securityManagerMock, times(0)).getPermissions(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString());
    }

    @Test
    public void testPermissionsMissingItemId() throws Exception {
        when(this.securityManagerMock.getPermissions(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString())).thenReturn(generateListOfPermissionsMock());

        mockMvc.perform(
            get("/api/1/security/permissions")
                .param("site", RandomStringUtils.randomAlphabetic(10))
                .accept(MediaType.ALL))
            .andExpect(status().isBadRequest());

        verify(this.securityManagerMock, times(0)).getPermissions(Mockito.any(Context.class), Mockito.anyString(),
            Mockito.anyString());
    }

    @Test
    public void testUpdatePermission() throws Exception {
        // TODO: define test and controller method
    }
}
