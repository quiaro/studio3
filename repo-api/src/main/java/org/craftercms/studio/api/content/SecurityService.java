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
    public enum Propagation {
        PROPAGATE, DO_NOT_PROPAGATE, INHERIT;
    }

    /**
     * Login.
     * @param repositoryUrl repository login url
     * @param user user
     * @param password password
     * @return security ticket
     */
    String login(String repositoryUrl, String user, String password);

    /**
     * Logout.
     * @param ticket security ticket
     */
    void logout(String ticket);

    /**
     * Get user permissions.
     * @param ticket security ticket
     * @param itemId item id
     * @return list of permissions
     */
    List<String> getPermissions(String ticket, String itemId);

    /**
     * Add user permissions.
     * @param ticket security ticket
     * @param user user
     * @param itemId item id
     * @param permissions list of permissions to add
     * @param propagation propagation
     */
    void addPermissions(String ticket, String user, String itemId, List<String> permissions, Propagation propagation);

    /**
     * Remove user permissions.
     * @param ticket security ticket
     * @param user user
     * @param itemId itemId
     * @param permissions permissions
     */
    void removePermissions(String ticket, String user, String itemId, List<String> permissions);
}
