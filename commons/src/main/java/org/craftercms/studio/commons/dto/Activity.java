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
import java.util.Map;

/**
 * Activity.
 *
 * @author Carlos Ortiz
 */
public class Activity {
    // todo activities need props

    private String siteId;
    private String siteName;
    private String target;
    private Map<String, String> targetProperties;
    private String type;
    private Date date;
    private String creator;
    private String id;

    public Activity() {
    }

    // Getters and setters

    public String getSiteId() {
        return this.siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return this.siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getTarget() {
        return this.target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Map<String, String> getTargetProperties() {
        return this.targetProperties;
    }

    public void setTargetProperties(Map<String, String> targetProperties) {
        this.targetProperties = targetProperties;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Activity{");
        sb.append("siteId='").append(siteId).append('\'');
        sb.append(", siteName='").append(siteName).append('\'');
        sb.append(", target='").append(target).append('\'');
        sb.append(", targetProperties=").append(targetProperties);
        sb.append(", type='").append(type).append('\'');
        sb.append(", date=").append(date);
        sb.append(", creator='").append(creator).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
