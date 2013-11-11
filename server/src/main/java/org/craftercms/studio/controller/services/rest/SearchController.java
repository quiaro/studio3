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

import org.craftercms.studio.api.search.SearchManager;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO: javadoc.
 */

@Controller
@RequestMapping("/api/1/search")
public class SearchController {

    /**
     * Search Manager instance
     */
    @Autowired
    private SearchManager searchManager;

    /**
     * TODO: javadoc.
     * @param site site
     * @param query query
     * @param request request
     * @param response response
     */
    @RequestMapping(value = "/find/{site}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultSet search(@PathVariable final String site, @RequestParam(required = true) final String query,
                       final HttpServletRequest request, final HttpServletResponse response) {
        return this.searchManager.find(null, query);
    }
}
