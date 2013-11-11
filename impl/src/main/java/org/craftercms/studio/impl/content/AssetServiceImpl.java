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

package org.craftercms.studio.impl.content;

import java.io.InputStream;

import org.craftercms.studio.api.content.AssetService;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.commons.exception.NotImplementedException;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.internal.content.ContentManager;

/**
 * Asset service implementation.
 *
 * @author Dejan Brkic
 */
public class AssetServiceImpl implements AssetService {

    private ContentManager contentManager;

    @Override
    public ItemId create(final Context context, final String site, final String destinationPath, final String fileName, final InputStream content, final String mimeType) throws StudioException {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public InputStream read(final Context context, final String itemId) throws StudioException {
        throw new NotImplementedException("Not implemented yet!");
    }

    // Getters and setters

    public ContentManager getContentManager() {
        return contentManager;
    }

    public void setContentManager(final ContentManager contentManager) {
        this.contentManager = contentManager;
    }
}
