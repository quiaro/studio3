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

import java.io.InputStream;

import org.craftercms.studio.commons.dto.Configuration;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.ItemId;

/**
 * Configuration Manager.
 *
 * @author Carlos Ortiz
 */
public interface ConfigurationService {

    /**
     * Get configuration.
     *
     * @param context context
     * @param site    site
     * @param module  module
     * @return module configuration
     */
    Configuration getConfiguration(Context context, String site, String module);
    // todo Consider passing a wildcard

    /**
     * Create or update module configuration.
     *
     * @param context             context
     * @param site                site
     * @param module              module
     * @param configuration module configuration
     */
    void configure(Context context, String site, String module, Configuration configuration);

    /**
     * Get configuration object.
     *
     * @param context  context
     * @param site     site
     * @param itemId configuration item Id
     * @return content
     */
    InputStream getContent(Context context, String site, ItemId itemId);

    /**
     * Create or update configuration.
     *
     * @param context  context
     * @param site     site
     * @param itemId configuration item id
     * @param content  content
     */
    void write(Context context, String site, ItemId itemId, InputStream content);
}
