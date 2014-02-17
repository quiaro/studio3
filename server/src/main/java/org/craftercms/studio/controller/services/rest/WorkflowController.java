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

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.craftercms.studio.api.workflow.WorkflowManager;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.WorkflowPackage;
import org.craftercms.studio.commons.dto.WorkflowTransition;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.controller.services.rest.dto.WorkflowStartRequest;
import org.craftercms.studio.controller.services.rest.dto.WorkflowTransitionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Workflow controller.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
@Controller
@RequestMapping("/api/1/workflow")
public class WorkflowController {

    /**
     * Workflow Manager instance
     */
    @Autowired
    private WorkflowManager workflowManager;

    @RequestMapping(value = "/start/{site}", method = RequestMethod.POST)
    @ResponseBody
    public String start(@PathVariable final String site, @Valid @RequestBody final WorkflowStartRequest requestBody) throws StudioException {
        return this.workflowManager.start(requestBody.getPackageName(), requestBody.getComments(), requestBody.getItems());
    }


    @RequestMapping(value = "/package/{site}", method = RequestMethod.GET)
    @ResponseBody
    public List<Item> getPackage(@PathVariable final String site, @RequestParam(required = true) final String
        packageId) throws StudioException  {
        return this.workflowManager.getPackage(packageId);
    }

    @RequestMapping(value = "/packages/{site}", method = RequestMethod.GET)
    @ResponseBody
    public List<WorkflowPackage> getPackages(@PathVariable final String site, @RequestParam(required = true) final
    List<String>
        filters) throws StudioException  {
        return this.workflowManager.getPackages(site, null);
    }

    @RequestMapping(value = "/transitions/{site}", method = RequestMethod.GET)
    @ResponseBody
    public List<WorkflowTransition> transitions(@PathVariable final String site, @RequestParam(required = true) final String
        packageId) throws StudioException  {
        return this.workflowManager.getTransitions(packageId);
    }

    @RequestMapping(value = "/transition/{site}", method = RequestMethod.POST)
    public void transition(@PathVariable final String site, @Valid @RequestBody WorkflowTransitionRequest requestBody,
                           final HttpServletRequest request, final HttpServletResponse response) throws StudioException {
        this.workflowManager.transition(requestBody.getPackageId(), requestBody.getTransition(), requestBody.getParams());
    }

    private Map<String, Object> parseTransitionRequestBody(String requestBody) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = null;
        try {
            map = mapper.readValue(requestBody.getBytes(), new TypeReference<Map<String, Object>>() { });
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return map;
    }

    @RequestMapping(value = "/cancel/{site}", method = RequestMethod.POST)
    public void cancel(@PathVariable final String site, @RequestParam(required = true) final String packageId,
                       final HttpServletRequest request, final HttpServletResponse response) throws StudioException {
        this.workflowManager.cancel(packageId);
    }
}
