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
package org.craftercms.studio.api.configuration;

import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.exception.StudioException;

/**
 * Configuration Service.
 *
 * @author Sumer Jabri
 */
public interface ConfigurationService {
    /**
     * Retrieve a configuration associated with a URN.
     *
     * @param context context
     * @param site    site
     * @param urn     URN of the resource
     * @return configuration associated with the provided URN
     */
    String read(Context context, String site, String urn) throws StudioException;

    /**
     * Create or update a configuration for the provided URN.
     *
     * @param context       context
     * @param site          site
     * @param urn           URN of the resource
     * @param configuration configuration to store
     */
    void write(Context context, String site, String urn, String configuration) throws StudioException;
}
