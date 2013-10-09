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

package org.craftercms.studio.impl.version;

import org.craftercms.studio.api.version.VersionManager;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.DiffResult;
import org.craftercms.studio.commons.dto.Tree;
import org.craftercms.studio.commons.dto.Version;
import org.craftercms.studio.commons.exception.NotImplementedException;

/**
 * Version manager implementation.
 *
 * @author Dejan Brkic
 */
public class VersionManagerImpl implements VersionManager {


    @Override
    public Tree<Version> history(final Context context, final String itemId) {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public void revert(final Context context, final String itemId, final String revertVersion) {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public DiffResult diff(final Context context, final String itemId, final String version1, final String version2) {
        throw new NotImplementedException("Not implemented yet!");
    }
}
