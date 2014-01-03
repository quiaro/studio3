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

import java.io.File;
import java.io.InputStream;

import org.apache.commons.lang.RandomStringUtils;
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

    /**
     * Create asset item in repository.
     *
     * @param context         context
     * @param site            site
     * @param destinationPath path to write to
     * @param fileName        file name of asset
     * @param content         content input stream, can be null if creating a 0 byte file
     * @param mimeType        mimeType of asset, can be null if unknown
     * @return item id
     * @throws StudioException
     */
    @Override
    public ItemId create(final Context context, final String site, final String destinationPath, final String fileName, final InputStream content, final String mimeType) throws StudioException {
        StringBuilder sb = new StringBuilder(destinationPath);
        sb.append(File.separator);
        sb.append(fileName);
        Item item = createAssetItem(fileName);
        return contentManager.create(context, site, sb.toString(), item, content);
    }

    // TODO: review this function ..
    // Content manager requires Item object to add new item to repository
    private Item createAssetItem(String fileName) {
        Item item = new Item();
        item.setCreatedBy(RandomStringUtils.random(10));
        item.setFileName(fileName);
        item.setLabel(fileName);
        return item;
    }

    @Override
    public Item read(final Context context, final String site, final String itemId) throws StudioException {
        return contentManager.read(context, site, itemId);
    }

    // Getters and setters

    public ContentManager getContentManager() {
        return contentManager;
    }

    public void setContentManager(final ContentManager contentManager) {
        this.contentManager = contentManager;
    }
}
