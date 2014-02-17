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

package org.craftercms.studio.mock.security;

import java.net.URL;
import java.util.List;

import org.craftercms.studio.api.security.*;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.SecurityGroup;
import org.craftercms.studio.commons.dto.SecurityPermission;
import org.craftercms.studio.commons.dto.SecurityRole;
import org.craftercms.studio.commons.dto.User;
import org.craftercms.studio.commons.exception.StudioException;

/**
 * Security Manager mock implementation.
 *
 * @author Dejan Brkic
 *
 */
public class SecurityServiceMock implements SecurityService {

    @Override
    public Context login(final URL repositoryUrl, final String username, final String password) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public void logout(final Context context) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public boolean validate(final Context context) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public List<User> getUsers(final Context context, final String site) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public String updateUser(final Context context, final User user, final String password, final String role) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public void removeUser(final Context context, final String user) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public List<SecurityPermission> getPermissions(final Context context, final String site, final String itemId) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public void updatePermissions(final Context context, final String site, final String itemId, final User user, final List<SecurityPermission> permissions, final boolean inherit) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public void updatePermissions(final Context context, final String site, final String itemId, final SecurityGroup group, final List<SecurityPermission> permissions, final boolean inherit) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public List<SecurityGroup> getGroups(final Context context, final String site) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public void updateGroup(final Context context, final String site, final String groupName) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public void removeGroup(final Context context, final String site, final String groupName) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public List<SecurityRole> getRoles(final Context context, final String site) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public void updateRole(final Context context, final String site, final String roleName) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public void removeRole(final Context context, final String site, final String roleName) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }
}
