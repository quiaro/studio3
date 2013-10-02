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


import java.util.Map;

/**
 * Meta Data Service.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public interface MetaDataService {

    /**
     * Get item properties.
     * @param ticket security ticket
     * @param itemId item id
     * @return map of properties
     */
    <T> Map<String, T> getProperties(String ticket, String itemId, Class<T> type);
// TODO Switch to add/remove instead of get/set
    /**
     * Set item properties.
     * @param ticket security ticket
     * @param itemId item id
     * @param properties properties
     */
    <T> void setProperties(String ticket, String itemId, Map<String, T> properties);

    /**
     * Get item property.
     * @param ticket security ticket
     * @param itemId item id
     * @param property property
     * @param type property type (class)
     * @return value
     */
    <T> T getProperty(String ticket, String itemId, String property, Class<T> type);

    /**
     * Set item property.
     * @param ticket security ticket
     * @param itemId item id
     * @param property property
     * @param value value
     */
    <T> void setProperty(String ticket, String itemId, String property, T value);
}
