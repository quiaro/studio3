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

import javax.validation.Valid;

import org.craftercms.studio.api.forms.FormsManager;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.FormDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Forms controller.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
@Controller
@RequestMapping("/api/1/forms")
public class FormsController {

    @Autowired
    private FormsManager formsManager;

    @RequestMapping(value = "/list/{site}", method = RequestMethod.GET)
    @ResponseBody
    public List<FormDefinition> list(@PathVariable final String site,
                                     @RequestParam(required = false) final List<String> filters) {
        return formsManager.list(null, site, filters);
    }

    @RequestMapping(value = "/update/{site}", method = RequestMethod.POST)
    @ResponseBody
    public void update(@PathVariable final String site, @Valid @RequestBody final FormDefinition form) {
        this.formsManager.update(null, site, form);
    }

    @RequestMapping(value = "/remove/{site}", method = RequestMethod.POST)
    @ResponseBody
    public void remove(@PathVariable final String site, @RequestParam(required = true) final String type) {
        this.formsManager.remove(null, site, type);
    }

    @RequestMapping(value = "/copy/{site}", method = RequestMethod.POST)
    @ResponseBody
    public void copy(@PathVariable final String site, @RequestParam(required = true) final String src,
                     @RequestParam(required = true) final String dst) {
        this.formsManager.copy(null, site, src, dst);
    }
}
