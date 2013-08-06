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
 * Security Permission.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public class SecurityPermission {

    /**
     * Name.
     */
    private String name;

    /**
     * Description.
     */
    private String description;

    /**
     * Name getter.
     * @return name
     */
    public final String getName() {
        return name;
    }

    /**
     * Name setter.
     * @param name name
     */
    public final void setName(final String name) {
        this.name = name;
    }

    /**
     * Description getter.
     * @return description
     */
    public final String getDescription() {
        return description;
    }

    /**
     * Description setter.
     * @param description description
     */
    public final void setDescription(final String description) {
        this.description = description;
    }
}
