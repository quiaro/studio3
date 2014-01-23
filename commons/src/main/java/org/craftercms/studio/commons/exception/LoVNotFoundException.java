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

package org.craftercms.studio.commons.exception;

/**
 * Root exception for all exceptions defined in Studio.
 *
 * @author Sumer Jabri
 */
public abstract class LoVNotFoundException extends Exception {
    private static final long serialVersionUID = 8822603836288820982L;

    /**
     * Construct with a message and cause exception.
     *
     * @param message description
     * @param cause   original cause exception
     */
    public LoVNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Construct with a message.
     *
     * @param message description
     */
    public LoVNotFoundException(final String message) {
        super(message);
    }

    /**
     * Construct with a message using {@link String#format(String, Object...)}.
     *
     * @param message message format (as {@link String#format(String, Object...)} )
     * @param args   arguments to format the message
     */
    public LoVNotFoundException(final String message, final Object... args) {
        super(String.format(message, args));
    }

    /**
     * Construct with a cause exception.
     *
     * @param cause original cause exception
     */
    public LoVNotFoundException(final Throwable cause) {
        super(cause);
    }
}
