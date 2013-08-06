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

/**
 * User.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public class User {

    /**
     * User id.
     */
    private String userId;

    /**
     * Password.
     */
    private String password;

    /**
     * User ig getter.
     * @return user id
     */
    public final String getUserId() {
        return userId;
    }

    /**
     * User id setter.
     * @param userId userId
     */
    public final void setUserId(final String userId) {
        this.userId = userId;
    }

    /**
     * Password getter.
     * @return password
     */
    public final String getPassword() {
        return password;
    }

    /**
     * Password setter.
     * @param password password
     */
    public final void setPassword(final String password) {
        this.password = password;
    }
}
