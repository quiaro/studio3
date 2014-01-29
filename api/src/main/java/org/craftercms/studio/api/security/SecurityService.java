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
package org.craftercms.studio.api.security;

import java.net.URL;
import java.util.List;

import org.craftercms.studio.commons.dto.*;


/**
 * Security Service allows for the creation and management of security constructs such as Permissions. TODO redo this
 * description
 *
 * @author Sumer Jabri
 */
public interface SecurityService {

    /**
     * Login.
     *
     * @param repositoryUrl content url
     * @param username      username
     * @param password      password
     * @return security context
     */
    Context login(URL repositoryUrl, String username, String password);

    /**
     * Logout.
     *
     * @param context context
     */
    void logout(Context context);

    /**
     * Validate security context.
     *
     * @param context context
     * @return true if context is valid, otherwise false
     */
    boolean validate(Context context);

    /**
     * Get users for site.
     *
     * @param context context
     * @param site    site
     * @return list of users
     */
    List<User> getUsers(Context context, String site);

    /**
     * Create or update user.
     *
     * @param context  context
     * @param user     user
     * @param password password
     * @param role     role
     * @return user id
     */
    String updateUser(Context context, User user, String password, String role);

    /**
     * Remove user.
     *
     * @param context context
     * @param user    user
     */
    void removeUser(Context context, String user);

    /**
     * Get permissions.
     *
     * @param context context
     * @param site    site
     * @param itemId  itemId
     * @return list of permission
     */
    List<SecurityPermission> getPermissions(Context context, String site, String itemId);

    /**
     * Update permissions.
     *
     * @param context     context
     * @param site        site
     * @param itemId      item id
     * @param user        user
     * @param permissions permissions
     * @param inherit     inherit true/false
     */
    void updatePermissions(Context context, String site, String itemId, User user, List<SecurityPermission> permissions, boolean inherit);

    /**
     * Update permissions.
     *
     * @param context     context
     * @param site        site
     * @param itemId      item id
     * @param group       group
     * @param permissions permissions
     * @param inherit     inherit true/false
     */
    void updatePermissions(Context context, String site, String itemId, SecurityGroup group, List<SecurityPermission> permissions, boolean inherit);

    /**
     * Get groups.
     *
     * @param context context
     * @param site    site
     * @return list of groups
     */
    List<SecurityGroup> getGroups(Context context, String site);

    /**
     * Create or update group.
     *
     * @param context   context
     * @param site      site
     * @param groupName groupName
     */
    void updateGroup(Context context, String site, String groupName);

    /**
     * Remove group.
     *
     * @param context   context
     * @param site      site
     * @param groupName groupName
     */
    void removeGroup(Context context, String site, String groupName);

    /**
     * Get roles.
     *
     * @param context ßcontext
     * @param site    site
     * @return list of roles
     */
    List<SecurityRole> getRoles(Context context, String site);

    /**
     * Create or update role.
     *
     * @param context  context
     * @param site     site
     * @param roleName roleName
     */
    void updateRole(Context context, String site, String roleName);

    /**
     * Remove role.
     *
     * @param context  context
     * @param site     site
     * @param roleName roleName
     */
    void removeRole(Context context, String site, String roleName);
}
