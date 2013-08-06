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

import java.util.List;

/**
 * Security context.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public class Context {

    private String username;
    private String ticket;
    private List<SecurityPermission> permissions;
    private List<SecurityGroup> groups;
    private List<SecurityRole> roles;
    private long ttl;

    public Context() {
    }

    // Getters and setters

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTicket() {
        return this.ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public List<SecurityPermission> getPermissions() {
        return this.permissions;
    }

    public void setPermissions(List<SecurityPermission> permissions) {
        this.permissions = permissions;
    }

    public List<SecurityGroup> getGroups() {
        return this.groups;
    }

    public void setGroups(List<SecurityGroup> groups) {
        this.groups = groups;
    }

    public List<SecurityRole> getRoles() {
        return this.roles;
    }

    public void setRoles(List<SecurityRole> roles) {
        this.roles = roles;
    }

    public long getTtl() {
        return this.ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }
}
