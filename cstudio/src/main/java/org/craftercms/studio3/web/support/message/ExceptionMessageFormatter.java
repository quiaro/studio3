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

package org.craftercms.studio3.web.support.message;

/**
 * Manages how a exception should be formatted
 * so it can be send to the clients in a standard method.
 */
public interface ExceptionMessageFormatter {
    /**
     * Create a String representation of a JSON
     * that will be send to the client.
     *
     * @param exception Exception to be formatter
     * @return a JSON as string of the formatted exception <b>Never Null or Empty</b>
     */
    String getFormatMessage(Exception exception);

    /**
     * HTTP Status Code that will be send to the client
     * <b>Must be a valid HTTP Status Code</b>
     */
    int getHttpResponseCode();
}
