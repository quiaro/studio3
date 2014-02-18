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

package org.craftercms.studio.mock.configuration;

import org.craftercms.studio.api.configuration.ConfigurationService;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.exception.StudioException;

/**
 * Configuration Manager Mock implementation.
 *
 * @author Dejan Brkic
 */
public class ConfigurationServiceMock implements ConfigurationService {

    @Override
    public String read(final Context context, final String site, final String urn) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public void write(final Context context, final String site, final String urn, final String configuration) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }
}
