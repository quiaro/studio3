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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.craftercms.studio.api.security.SecurityService;

import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.SecurityPermission;
import org.craftercms.studio.commons.dto.User;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.controller.services.rest.dto.UpdateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Security controller.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
@Controller
@RequestMapping("/api/1/security")
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Context login(@RequestParam(required = true) final String username,
                         @RequestParam(required = true) final String password, final HttpServletRequest request,
                         final HttpServletResponse response) throws StudioException {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        return this.securityService.login(null, username, password);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public void logout() throws StudioException {
        this.securityService.logout(null);
    }

    @RequestMapping(value = "/validate", method = RequestMethod.GET)
    @ResponseBody
    public void validate(final HttpServletRequest request, final HttpServletResponse response) throws StudioException {
        if (!this.securityService.validate(null)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public List<User> users(@RequestParam(required = true) final String site) throws StudioException {
        return this.securityService.getUsers(null, site);
    }

    @RequestMapping(value = "/update_user", method = RequestMethod.POST)
    @ResponseBody
    public String updateUser(@Valid @RequestBody UpdateUserRequest requestBody) throws StudioException {
        return this.securityService.updateUser(null, requestBody.getUser(), requestBody.getPassword(),
            requestBody.getRole());
    }

    @RequestMapping(value = "/remove_user", method = RequestMethod.POST)
    @ResponseBody
    public void removeUser(@RequestParam(required = true) final String user) throws StudioException {
        this.securityService.removeUser(null, user);
    }

    @RequestMapping(value = "/permissions", method = RequestMethod.GET)
    @ResponseBody
    public List<SecurityPermission> permissions(@RequestParam(required = true) final String site,
                                                @RequestParam(required = true) final String itemId) throws StudioException {
        return this.securityService.getPermissions(null, site, itemId);
    }
}
