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
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.craftercms.studio.api.dependency.DependencyManager;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.exception.StudioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Dependency Controller.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
@Controller
@RequestMapping("/api/1/dependency")
public class DependencyController {

    @Autowired
    private DependencyManager dependencyManager;

    @RequestMapping(value = "/dependent-on/{site}", method = RequestMethod.GET)
    @ResponseBody
    public List<Item> dependentOn(@PathVariable final String site, @RequestParam(required = true) final String itemId,
                          @RequestParam(required = true) final String operation) throws StudioException {
        return this.dependencyManager.dependentOn(null, itemId, operation);
    }

    @RequestMapping(value = "/list/{site}", method = RequestMethod.GET)
    @ResponseBody
    public List<Item> list(@PathVariable final String site, @RequestParam(required = true) final String itemId,
                     @RequestParam(required = true) final String operation) throws StudioException {
        return this.dependencyManager.dependsOn(null, itemId, operation);
    }

    @RequestMapping(value = "/refresh/{site}", method = RequestMethod.GET)
    @ResponseBody
    public List<Item> refresh(@PathVariable final String site, @RequestParam(required = true) final String itemId)
        throws StudioException {
        return this.dependencyManager.refresh(null, itemId);
    }

    @RequestMapping(value = "/add/{site}", method = RequestMethod.POST)
    public void add(@PathVariable final String site, @RequestParam(required = true) final String itemId,
                    @RequestParam(required = true) final String operation,
                    @Valid @RequestBody final String dependencies, final HttpServletRequest request,
                    final HttpServletResponse response) throws StudioException {
        ObjectMapper mapper = new ObjectMapper();
        List<Item> items = null;
        try {
            items = mapper.readValue(dependencies.getBytes(), new TypeReference<List<Item>>() { });
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        this.dependencyManager.add(null, itemId, operation, items);
    }

    @RequestMapping(value = "/remove/{site}", method = RequestMethod.POST)
    public void remove(@PathVariable final String site, @RequestParam(required = true) final String itemId,
                       @RequestParam(required = true) final String operation,
                       @Valid @RequestBody final String dependencies, final HttpServletRequest request,
                       final HttpServletResponse response) throws StudioException {
        ObjectMapper mapper = new ObjectMapper();
        List<Item> items = null;
        try {
            items = mapper.readValue(dependencies.getBytes(), new TypeReference<List<Item>>() { });
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        this.dependencyManager.remove(null, itemId, operation, items);
    }

    @RequestMapping(value = "/update/{site}", method = RequestMethod.POST)
    public void update(@PathVariable final String site, @RequestParam(required = true) final String itemId,
                       @RequestParam(required = true) final String operation,
                       @Valid @RequestBody final String dependencies, final HttpServletRequest request,
                       final HttpServletResponse response) throws StudioException {

        ObjectMapper mapper = new ObjectMapper();
        List<Item> items = null;
        try {
            items = mapper.readValue(dependencies.getBytes(), new TypeReference<List<Item>>() { });
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        this.dependencyManager.update(null, itemId, operation, items);
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new Validator() {
            @Override
            public boolean supports(final Class<?> clazz) {
                return String.class.equals(clazz);
            }

            @Override
            public void validate(final Object o, final Errors errors) {
                if (o instanceof String) {
                    if (StringUtils.isEmpty((String)o)) {
                        errors.reject((String)o, "Request body can not be empty");
                    }
                }
            }
        });
    }
}
