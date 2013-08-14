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
package org.craftercms.studio.commons.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Workflow package.
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public class WorkflowPackage {

    /**
     * id.
     */
    private String id;

    /**
     * Name.
     */
    private String name;

    /**
     * Description.
     */
    private String description;

    /**
     * Items.
     */
    private List<Item> items;

    /**
     * Scheduled date.
     */
    private Date scheduledDate;

    /**
     * Submitted by.
     */
    private String submittedBy;

    /**
     * Workflow id.
     */
    private String workflowId;

    /**
     * State.
     */
    private String state;

    /**
     * Properties.
     */
    private Map<String, Object> properties;

    /**
     * Id getter.
     * @return id
     */
    public final String getId() {
        return id;
    }

    /**
     * Id setter.
     * @param id id
     */
    public final void setId(final String id) {
        this.id = id;
    }

    /**
     * Name getter.
     * @return name
     */
    public final String getName() {
        return name;
    }

    /**
     * Name setter.
     * @param name name
     */
    public final void setName(final String name) {
        this.name = name;
    }

    /**
     * Description getter.
     * @return description
     */
    public final String getDescription() {
        return description;
    }

    /**
     * Description setter.
     * @param description description
     */
    public final void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Items getter.
     * @return items
     */
    public final List<Item> getItems() {
        return items;
    }

    /**
     * Items setter.
     * @param items items
     */
    public final void setItems(final List<Item> items) {
        this.items = items;
    }

    /**
     * Scheduled date getter.
     * @return scheduled date
     */
    public final Date getScheduledDate() {
        return scheduledDate;
    }

    /**
     * Scheduled date setter.
     * @param scheduledDate scheduled date
     */
    public final void setScheduledDate(final Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    /**
     * Submitted by getter.
     * @return submitted by
     */
    public final String getSubmittedBy() {
        return submittedBy;
    }

    /**
     * Submitted by setter.
     * @param submittedBy submitted by
     */
    public final void setSubmittedBy(final String submittedBy) {
        this.submittedBy = submittedBy;
    }

    /**
     * Workflow id getter.
     * @return workflow id
     */
    public final String getWorkflowId() {
        return workflowId;
    }

    /**
     * Workflow id setter.
     * @param workflowId workflow id
     */
    public final void setWorkflowId(final String workflowId) {
        this.workflowId = workflowId;
    }

    /**
     * State getter.
     * @return state
     */
    public final String getState() {
        return state;
    }

    /**
     * State setter.
     * @param state state
     */
    public final void setState(final String state) {
        this.state = state;
    }

    /**
     * Properties getter.
     * @return properties
     */
    public final Map<String, Object> getProperties() {
        return properties;
    }

    /**
     * Properties setter.
     * @param properties properties
     */
    public final void setProperties(final Map<String, Object> properties) {
        this.properties = properties;
    }
}
