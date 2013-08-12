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

package org.craftercms.studio.api.exception;

/**
 * Invalid Activity Exception.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public class InvalidActivityException extends StudioException {

    private static final long serialVersionUID = -5395857997343692667L;

    public InvalidActivityException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidActivityException(String message) {
        super(message);
    }

    public InvalidActivityException(Throwable cause) {
        super(cause);
    }
}
