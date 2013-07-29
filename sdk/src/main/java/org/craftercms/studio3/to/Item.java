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
package org.craftercms.studio3.to;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TODO: javadoc.
 */
public class Item {

    /**
     * Id
     */
    private String id;

    /**
     * Repository Id
     */
    private String repoId;

    /**
     * Name
     */
    private String name;

    /**
     * File name
     */
    private String fileName;

    /**
     * Path
     */
    private String path;

    /**
     * Preview URL
     */
    private String previewUrl;

    /**
     * Studio Type
     */
    private String studioType;

    /**
     * Mime Type
     */
    private String mimeType;

    /**
     * Content Type
     */
    private String contentType;

    /**
     * State
     */
    private String state;

    /**
     * Disabled
     */
    private boolean disabled;

    /**
     * Last Modified Date
     */
    private Date lastModifiedDate;

    /**
     * Modifier
     */
    private String modifier;

    /**
     * Create Date
     */
    private Date createDate;

    /**
     * Creator
     */
    private String creator;

    /**
     * Place in Navigation
     */
    private boolean placeInNav;

    /**
     * Lock Owner
     */
    private String lockOwner;

    /**
     * Previewable
     */
    private boolean previewable;

    /**
     * Associated templates
     */
    private List<String> renderingTemplates;

    /**
     * Scheduled Date
     */
    private Date scheduledDate;

    /**
     * Workflow Packages
     */
    private List<WorkflowPackage> packages;

    /**
     * Properties
     */
    private Map<String, String> properties;

    /**
     * Getter.
     * @return Id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter.
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    public String getRepoId() {
        return repoId;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getStudioType() {
        return studioType;
    }

    public void setStudioType(String studioType) {
        this.studioType = studioType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public boolean isPlaceInNav() {
        return placeInNav;
    }

    public void setPlaceInNav(boolean placeInNav) {
        this.placeInNav = placeInNav;
    }

    public String getLockOwner() {
        return lockOwner;
    }

    public void setLockOwner(String lockOwner) {
        this.lockOwner = lockOwner;
    }

    public boolean isPreviewable() {
        return previewable;
    }

    public void setPreviewable(boolean previewable) {
        this.previewable = previewable;
    }

    public List<String> getRenderingTemplates() {
        return renderingTemplates;
    }

    public void setRenderingTemplates(List<String> renderingTemplates) {
        this.renderingTemplates = renderingTemplates;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public List<WorkflowPackage> getPackages() {
        return packages;
    }

    public void setPackages(List<WorkflowPackage> packages) {
        this.packages = packages;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
