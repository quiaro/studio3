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

package org.craftercms.studio.api.exception;

/**
 * Root exception for all exceptions defined in Studio.
 *
 * @author Sumer Jabri
 *
 */
public class StudioException extends Exception{
    private static final long serialVersionUID = 8822403836288820982L;

    /**
     * Default constructor
     *
     */
    public StudioException() {
        super();
    }

    /**
     *
     * Construct with a message and cause exception
     *
     * @param message description
     * @param cause original cause exception
     */
    public StudioException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * Construct with a message
     *
     * @param message description
     */
    public StudioException(String message) {
        super(message);
    }

    /**
     *
     * Construct with a cause exception
     *
     * @param cause original cause exception
     */
    public StudioException(Throwable cause) {
        super(cause);
    }
}
