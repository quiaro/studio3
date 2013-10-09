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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Content item transport object.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
@JsonAutoDetect
public class Item implements Comparable<Item> {
    // Fundamental
    /**
     * Crafter Studio item id
     */
    private ItemId id;
    /**
     * Object Version Number: Monotonically increasing number on persist
     */
    private int objectVersionNumber;
    /**
     * List of ancestors in TODO order
     */
    private List<ItemId> ancestors;

    // Core Metadata
    /**
     * Underlying repository id.
     */
    private String repoId;      // TODO think about this
    private String label;
    private String fileName;
    private String path;
    private String createdBy;
    private Date creationDate;
    private String modifiedBy;
    private Date lastModifiedDate;
    private String type;        // Blueprint, Component, Page, Static Asset, Rendering Template, ...
    private boolean isFolder;   // TODO think about this
    private String state;       // TODO ENUM
    private String workflow;    // TODO ASK ABOUT THIS ONE

    // Something something properties
    private String mimeType;
    private boolean placeInNav;
    private boolean disabled;

    ////TODO LOCK TYPE
    /**
     * User id of the lock owner, null if item is not locked
     */
    private String lockOwner;

    /**
     * The URL to preview this item, null if item is not previewable
     */
    private String previewUrl;


    // Security Properties
    private boolean securityInherited;
    //private List<ACL> acls;

    private String contentType;
    private List<String> renderingTemplates;
    private Date scheduledDate;
    private List<String> packages;

    // Additional Metadata
    private Map<String, Object> properties;

    public Item() {
    }

    /**
     * Compare items.
     *
     * @param item item to compare to
     * @return comparison result
     */
    @Override
    public final int compareTo(final Item item) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    // Getters and setters
    @JsonProperty
    public ItemId getId() {
        return this.id;
    }

    public void setId(ItemId id) {
        this.id = id;
    }

    @JsonProperty
    public String getRepoId() {
        return this.repoId;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
    }

    @JsonProperty
    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @JsonProperty
    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @JsonProperty
    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @JsonProperty
    public String getPreviewUrl() {
        return this.previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    @JsonProperty
    public String getMimeType() {
        return this.mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @JsonProperty
    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @JsonProperty
    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @JsonProperty
    public boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    @JsonProperty
    public Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @JsonProperty
    public boolean isPlaceInNav() {
        return this.placeInNav;
    }

    public void setPlaceInNav(boolean placeInNav) {
        this.placeInNav = placeInNav;
    }

    @JsonProperty
    public String getLockOwner() {
        return this.lockOwner;
    }

    public void setLockOwner(String lockOwner) {
        this.lockOwner = lockOwner;
    }

    @JsonProperty
    public List<String> getRenderingTemplates() {
        return this.renderingTemplates;
    }

    public void setRenderingTemplates(List<String> renderingTemplates) {
        this.renderingTemplates = renderingTemplates;
    }

    @JsonProperty
    public Date getScheduledDate() {
        return this.scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    @JsonProperty
    public List<String> getPackages() {
        return this.packages;
    }

    public void setPackages(List<String> packages) {
        this.packages = packages;
    }

    @JsonProperty
    public Map<String, Object> getProperties() {
        return this.properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
