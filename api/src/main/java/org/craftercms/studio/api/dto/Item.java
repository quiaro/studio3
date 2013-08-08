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
package org.craftercms.studio.api.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Content item transport object.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public class Item implements Comparable<Item> {

    private String id;
    private String repoId;
    private String name;
    private String fileName;
    private String path;
    private String previewUrl;
    private String studioType;
    private String mimeType;
    private String contentType;
    private String state;
    private boolean disabled;
    private Date lastModifiedDate;
    private String modifier;
    private Date createDate;
    private String creator;
    private boolean placeInNav;
    private String lockOwner;
    private boolean previewable;
    private List<String> renderingTemplates;
    private Date scheduledDate;
    private List<String> packages;
    private Map<String, Object> properties;

    public Item() {
    }

    /**
     * Compare items.
     * @param item item to compare to
     * @return comparison result
     */
    @Override
    public final int compareTo(final Item item) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    // Getters and setters
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRepoId() {
        return this.repoId;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPreviewUrl() {
        return this.previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getStudioType() {
        return this.studioType;
    }

    public void setStudioType(String studioType) {
        this.studioType = studioType;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getModifier() {
        return this.modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public boolean isPlaceInNav() {
        return this.placeInNav;
    }

    public void setPlaceInNav(boolean placeInNav) {
        this.placeInNav = placeInNav;
    }

    public String getLockOwner() {
        return this.lockOwner;
    }

    public void setLockOwner(String lockOwner) {
        this.lockOwner = lockOwner;
    }

    public boolean isPreviewable() {
        return this.previewable;
    }

    public void setPreviewable(boolean previewable) {
        this.previewable = previewable;
    }

    public List<String> getRenderingTemplates() {
        return this.renderingTemplates;
    }

    public void setRenderingTemplates(List<String> renderingTemplates) {
        this.renderingTemplates = renderingTemplates;
    }

    public Date getScheduledDate() {
        return this.scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public List<String> getPackages() {
        return this.packages;
    }

    public void setPackages(List<String> packages) {
        this.packages = packages;
    }

    public Map<String, Object> getProperties() {
        return this.properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
