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

package org.craftercms.studio.impl.deployment;

import java.util.List;

import org.craftercms.studio.api.deployment.DeploymentManager;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.DeploymentChannel;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.exception.StudioException;

/**
 * Deployment Manager Implementation.
 *
 * @author Dejan Brkic
 */
public class DeploymentManagerImpl implements DeploymentManager {

    @Override
    public List<Item> history(final Context context, final String site, final List<String> filters) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public List<DeploymentChannel> channels(final Context context, final String site, final String environment) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public void updateChannel(final Context context, final String site, final DeploymentChannel channel) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public void removeChannel(final Context context, final String site, final DeploymentChannel channel) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public void deploy(final Context context, final String site, final List<String> itemIds) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public String status(final Context context, final String site, final DeploymentChannel channel) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public long version(final Context context, final String site, final DeploymentChannel channel) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public void abort(final Context context, final String site, final DeploymentChannel channel) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }
}
