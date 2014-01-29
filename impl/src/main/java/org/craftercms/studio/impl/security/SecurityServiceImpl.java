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

package org.craftercms.studio.impl.security;

import java.net.URL;
import java.util.List;

import org.craftercms.studio.api.security.SecurityService;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.SecurityGroup;
import org.craftercms.studio.commons.dto.SecurityPermission;
import org.craftercms.studio.commons.dto.SecurityRole;
import org.craftercms.studio.commons.dto.User;

/**
 * Security manager implementation.
 *
 * @author Dejan Brkic
 */
public class SecurityServiceImpl implements SecurityService {
    @Override
    public Context login(final URL repositoryUrl, final String username, final String password) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void logout(final Context context) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean validate(final Context context) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<User> getUsers(final Context context, final String site) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String updateUser(final Context context, final User user, final String password, final String role) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeUser(final Context context, final String user) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<SecurityPermission> getPermissions(final Context context, final String site, final String itemId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updatePermissions(final Context context, final String site, final String itemId, final User user,
                                  final List<SecurityPermission> permissions, final boolean inherit) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updatePermissions(final Context context, final String site, final String itemId, final SecurityGroup
        group, final List<SecurityPermission> permissions, final boolean inherit) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<SecurityGroup> getGroups(final Context context, final String site) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateGroup(final Context context, final String site, final String groupName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeGroup(final Context context, final String site, final String groupName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<SecurityRole> getRoles(final Context context, final String site) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateRole(final Context context, final String site, final String roleName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeRole(final Context context, final String site, final String roleName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
