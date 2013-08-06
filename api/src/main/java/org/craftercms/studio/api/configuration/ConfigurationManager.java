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

import org.craftercms.studio.api.dto.Context;
import org.craftercms.studio.api.dto.ModuleConfiguration;

import java.io.InputStream;

/**
 * Configuration Manager.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public interface ConfigurationManager {

    /**
     * Get configuration.
     * @param context context
     * @param site site
     * @param module module
     * @return module configuration
     */
    ModuleConfiguration getConfiguration(Context context, String site, String module);

    /**
     * Create or update module configuration.
     * @param context context
     * @param site site
     * @param module module
     * @param moduleConfiguration module configuration
     */
    void configure(Context context, String site, String module, ModuleConfiguration moduleConfiguration);

    /**
     * Get configuration object.
     * @param context context
     * @param site site
     * @param objectId object id
     * @return content
     */
    InputStream getContent(Context context, String site, String objectId);

    /**
     * Create or update configuration.
     * @param context context
     * @param site site
     * @param objectId object id
     * @param content content
     */
    void write(Context context, String site, String objectId, InputStream content);
}
