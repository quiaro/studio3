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
 * Root exception for all exceptions defined in Studio. All exceptions in Studio throw an Error Id which is
 * translated to an actual message that is localized and can be more easily supported.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 */
public class StudioException extends Exception {
    private static final long serialVersionUID = 8822403836288820982L;

    /**
     * Construct with an error code and cause exception.
     *
     * @param errorCode {@link org.craftercms.studio.commons.exception.StudioException.ErrorCode}
     * @param cause     original cause exception
     */
    public StudioException(final ErrorCode errorCode, final Throwable cause) {
        super(LookUpErrorMessage(errorCode), cause);
    }

    /**
     * Construct with an error code.
     *
     * @param errorCode {@link org.craftercms.studio.commons.exception.StudioException.ErrorCode}
     */
    public StudioException(final ErrorCode errorCode) {
        super(LookUpErrorMessage(errorCode));
    }

    protected static String LookUpErrorMessage(final ErrorCode errorCode) {
        return errorCode.toString(); // TODO Implement this to look up in a resource bundle
    }

    public enum ErrorCode {
        INVALID_CONTEXT,
        INVALID_SITE,
        NOT_IMPLEMENTED,
        ITEM_NOT_FOUND,
        ACCESS_DENIED,
        INVALID_ACTIVITY,
        STALE_ITEM,
        SYSTEM_ERROR
    }
}
