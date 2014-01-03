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

package org.craftercms.studio.impl.configuration;

import java.io.InputStream;

import org.craftercms.studio.api.configuration.ConfigurationManager;
import org.craftercms.studio.commons.dto.Configuration;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.ItemId;

/**
 * Configuration manager implementation.
 *
 * @author Dejan Brkic
 */
public class ConfigurationManagerImpl implements ConfigurationManager {
    @Override
    public Configuration getConfiguration(final Context context, final String site, final String module) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void configure(final Context context, final String site, final String module, final Configuration
        configuration) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public InputStream getContent(final Context context, final String site, final ItemId itemId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void write(final Context context, final String site, final ItemId itemId, final InputStream content) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
