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

import java.util.List;

import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.validation.StringNotEmpty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Workflow Start Request object. Represents JSON object sent as request body.
 */
public class WorkflowStartRequest {

    @StringNotEmpty
    private String packageName;

    private List<String> comments;

    @NotEmpty
    private List<Item> items;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(final String packageName) {
        this.packageName = packageName;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(final List<String> comments) {
        this.comments = comments;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(final List<Item> items) {
        this.items = items;
    }
}
