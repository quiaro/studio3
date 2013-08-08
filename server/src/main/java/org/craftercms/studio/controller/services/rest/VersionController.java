/*
 * Copyright (C) 2007-2013 Crafter Software Corporation.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.craftercms.studio.controller.services.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO: javadoc.
 */
@Controller
@RequestMapping("/api/1/version")
public class VersionController {

    /**
     * TODO: javadoc.
     * @param site site.
     * @param itemId itemId.
     * @param request request.
     * @param response response.
     */
    @RequestMapping(value = "/history/{site}/{itemId}", method = RequestMethod.GET)
    public void getVersionHistory(@PathVariable final String site, @PathVariable final String itemId, final HttpServletRequest request, final HttpServletResponse response) {}

    /**
     * TODO: javadoc.
     * @param site site.
     * @param itemId itemId.
     * @param versionToRevertTo version to revert to.
     * @param request request.
     * @param response response.
     */
    @RequestMapping(value = "/revert/{site}/{itemId}", method = RequestMethod.POST)
    public void revert(@PathVariable final String site, @PathVariable final String itemId, final String versionToRevertTo, final HttpServletRequest request, final HttpServletResponse response) {}
}
