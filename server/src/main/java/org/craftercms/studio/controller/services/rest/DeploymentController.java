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

import org.craftercms.studio.api.deployment.DeploymentManager;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.DeploymentChannel;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.exception.StudioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Deployment Controller.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
@Controller
@RequestMapping("/api/1/deployment")
public class DeploymentController {

    /**
     * Deployment Manager
     */
    @Autowired
    private DeploymentManager deploymentManager;

    /**
     * Get deployment history.
     *
     * @param site Site name.
     * @param filters Filters.
     * @return List of items
     */
    @RequestMapping(value = "/history/{site}", method = RequestMethod.GET)
    @ResponseBody
    public List<Item> history(@PathVariable final String site, @RequestParam(required = false) final List<String>
        filters) throws StudioException {
        return this.deploymentManager.history(new Context(), site, filters);
    }

    /**
     * Get deployment channels.
     *
     * @param site Site name.
     * @param environment Publishing environment.
     * @return List of deployment channels
     */
    @RequestMapping(value = "/channels/{site}", method = RequestMethod.GET)
    @ResponseBody
    public List<DeploymentChannel> history(@PathVariable final String site, @RequestParam(required = true) final String
        environment) throws StudioException {
        return this.deploymentManager.channels(new Context(), site, environment);
    }

    /**
     * Update deployment channel.
     * @param site Site name
     * @param channel Deployment channel
     * @param request Http request
     * @param response Http response
     * @throws StudioException
     */
    @RequestMapping(value = "/update_channel/{site}", method = RequestMethod.POST)
    public void updateChannel(@PathVariable final String site, @Valid @RequestBody final DeploymentChannel channel,
                              final HttpServletRequest request, final HttpServletResponse response) throws
        StudioException {
        this.deploymentManager.updateChannel(new Context(), site, channel);
    }

    /**
     * Remove deployment channel.
     * @param site site
     * @param channel deployment channel
     * @param request http request
     * @param response http response
     * @throws StudioException
     */
    @RequestMapping(value = "/remove_channel/{site}", method = RequestMethod.POST)
    public void removeChannel(@PathVariable final String site, @Valid @RequestBody final DeploymentChannel channel,
                              final HttpServletRequest request, final HttpServletResponse response) throws
        StudioException {
        this.deploymentManager.removeChannel(new Context(), site, channel);
    }

    /**
     * Deploy items.
     * @param site site
     * @param itemIds list of item ids
     * @param request http request
     * @param response http response
     * @throws StudioException
     */
    @RequestMapping(value = "/deploy/{site}", method = RequestMethod.POST)
    public void deploy(@PathVariable final String site, @RequestParam(required = true) final List<String> itemIds,
                       final HttpServletRequest request, final HttpServletResponse response) throws StudioException {
        this.deploymentManager.deploy(new Context(), site, itemIds);
    }

    /**
     * Get deployment channel status.
     * @param site site
     * @param channel deployment channel
     * @return status message
     */
    @RequestMapping(value = "/status/{site}", method = RequestMethod.GET)
    @ResponseBody
    public String status(@PathVariable final String site, @Valid @RequestBody final DeploymentChannel channel) {
        return this.deploymentManager.status(new Context(), site, channel);
    }

    /**
     * Get deployment agent version
     * @param site site
     * @param channel deployment channel
     * @return timestamp
     */
    @RequestMapping(value = "/version/{site}", method = RequestMethod.GET)
    @ResponseBody
    public long version(@PathVariable final String site, @Valid @RequestBody final DeploymentChannel channel) {
        return this.deploymentManager.version(new Context(), site, channel);
    }

    /**
     * Abort current deployment on given channel
     * @param site site
     * @param channel deployment channel
     */
    @RequestMapping(value = "/abort/{site}", method = RequestMethod.POST)
    public void abort(@PathVariable final String site, @Valid @RequestBody final DeploymentChannel channel,
                      final HttpServletRequest request, final HttpServletResponse response) {
        this.deploymentManager.abort(new Context(), site, channel);
    }
}
