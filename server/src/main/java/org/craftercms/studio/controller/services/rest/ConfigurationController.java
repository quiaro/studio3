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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.craftercms.studio.api.configuration.ConfigurationManager;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.ModuleConfiguration;
import org.craftercms.studio.controller.services.rest.dto.ConfigurationWriteRequest;
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
 * Configuration controller.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
@Controller
@RequestMapping("/api/1/config")
public class ConfigurationController {

    @Autowired
    private ConfigurationManager configurationManager;

    @RequestMapping(value = "/configuration/{site}", method = RequestMethod.GET)
    @ResponseBody
    public ModuleConfiguration configuration(@PathVariable final String site,
                                             @RequestParam(required = true) final String module) {
        return this.configurationManager.getConfiguration(new Context(), site, module);
    }

    @RequestMapping(value = "/configure/{site}", method = RequestMethod.POST)
    @ResponseBody
    public void configure(@PathVariable final String site, @RequestParam(required = true) final String module,
                          @Valid @RequestBody final ModuleConfiguration moduleConfiguration) {
        this.configurationManager.configure(new Context(), site, module, moduleConfiguration);
    }

    @RequestMapping(value = "/content/{site}", method = RequestMethod.GET)
    public void content(@PathVariable final String site, @RequestParam(required = true) final String object,
                        final HttpServletRequest request, HttpServletResponse response) {
        final InputStream content = this.configurationManager.getContent(new Context(), site, object);
        try {
            final OutputStream out = response.getOutputStream();
            IOUtils.copy(content, out);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @RequestMapping(value = "/write/{site}", method = RequestMethod.POST)
    @ResponseBody
    public void write(@PathVariable final String site, @RequestParam(required = true) final String object,
                      @Valid @RequestBody(required = true) final ConfigurationWriteRequest writeRequest) {
        InputStream contentStream = IOUtils.toInputStream(writeRequest.getContent());
        this.configurationManager.write(new Context(), site, object, contentStream);
    }
}
