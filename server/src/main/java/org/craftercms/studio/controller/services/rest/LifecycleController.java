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

import org.craftercms.studio.api.lifecycle.Action;
import org.craftercms.studio.api.lifecycle.LifecycleManager;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.exception.StudioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Lifecycle controller.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
@Controller
@RequestMapping("/api/1/lifecycle")
public class LifecycleController {

    @Autowired
    private LifecycleManager lifecycleManager;

    @RequestMapping(value = "/actions/{site}", method = RequestMethod.GET)
    @ResponseBody
    public List<Action> actions(@PathVariable final String site, @RequestParam(required = true) final List<String>
        itemIds) throws StudioException {
        return this.lifecycleManager.getPossibleActions(null, site, itemIds);
    }
}
