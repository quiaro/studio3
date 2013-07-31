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

package org.craftercms.studio.web.support.message;

/**
 * Message Formatter.
 */
public interface MessageFormatterManager {
    /**
     * Registers a Formatter fot the give class.
     * @param clazz Exception class that will be handel by the Formatter.
     * @param formatter Formatter that will handel the Exception.
     */
    void registerFormatter(Class<? extends Exception> clazz, ExceptionMessageFormatter formatter);

    /**
     * Returns the Register Formatter for the given class.
     * @param clazz Exception base class.
     * @return Formatted Error.
     */
    ExceptionMessageFormatter getFormatter(final Class<? extends Exception> clazz);

    String getFormattedMessage(Exception exception);

}
