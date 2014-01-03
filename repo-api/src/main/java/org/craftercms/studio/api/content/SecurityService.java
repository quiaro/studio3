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

package org.craftercms.studio.api.content;

import java.util.List;

/**
 * Security Service.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public interface SecurityService {
    /**
     * Login.
     *
     * @param repositoryUrl repository login url
     * @param user          user
     * @param password      password
     * @return security ticket
     */
    String login(String repositoryUrl, String user, String password);

    /**
     * Logout.
     *
     * @param ticket security ticket
     */
    void logout(String ticket);

    /**
     * Get user permissions.
     * <p/>
     * Must have READ or MANAGE permission
     *
     * @param ticket security ticket
     * @param itemId item id
     * @return list of permissions
     */
    List<Permission> getPermissions(String ticket, String itemId);

    /**
     * Add authority permissions.
     * Adds a list of permissions (incrementally) to an item for a user or group of users.
     * <p/>
     * Must have MANAGE permission
     *
     * @param ticket      security ticket
     * @param authorities authorities (users or groups) to grand the permissions to
     * @param itemId      item id
     * @param permissions list of permissions to add
     * @param propagation propagation
     */
    void addPermissions(String ticket, List<String> authorities, String itemId, List<Permission> permissions,
                        Propagation propagation);

    /**
     * Remove user permissions.
     * <p/>
     * Must have MANAGE permission
     *
     * @param ticket      security ticket
     * @param authorities authorities (users or groups) to grand the permissions to
     * @param itemId      itemId
     * @param permissions permissions
     */
    void removePermissions(String ticket, List<String> authorities, String itemId, List<Permission> permissions);

    /**
     * Set propagation scheme.
     * TODO
     * <p/>
     * Must have MANAGE permission
     *
     * @param ticket      security ticket
     * @param itemId      item id, must be a folder
     * @param propagation propagation
     */
    void setPropagation(String ticket, String itemId, Propagation propagation);

    /**
     * Propagation scheme.
     * Supports three propagation schemes:
     * Propagate: Propagate the permissions to the children
     * ...
     */
    public enum Propagation {
        PROPAGATE, DO_NOT_PROPAGATE, INHERIT;
    }

    /**
     * TODO: Document me
     */
    public enum Permission {
        READ(0x00000001),
        WRITE(0x00000001),
        DELETE(0x00000004),
        LIST_CHILDREN(0x00000008),
        ADD_CHILDREN(0x00000010),
        UPDATE_PROPERTIES(0x00000020),
        MANAGE(0x80000000),   // TODO think about this some more
        ALL(0xffffffff);
        private final int permissionMask;

        Permission(int permissionMask) {
            this.permissionMask = permissionMask;
        }
    }
}
