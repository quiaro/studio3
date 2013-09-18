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

package org.craftercms.studio.controller.services.rest.dto;

import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.craftercms.studio.commons.dto.WorkflowTransition;
import org.craftercms.studio.commons.validation.StringNotEmpty;

/**
 * Workflow Transition Request object. Represents JSON object sent as request body.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public class WorkflowTransitionRequest {

    @StringNotEmpty
    private String packageId;

    @NotNull
    private WorkflowTransition transition;

    @NotNull
    private Map<String, Object> params;

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(final String packageId) {
        this.packageId = packageId;
    }

    public WorkflowTransition getTransition() {
        return transition;
    }

    public void setTransition(final WorkflowTransition transition) {
        this.transition = transition;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(final Map<String, Object> params) {
        this.params = params;
    }
}
