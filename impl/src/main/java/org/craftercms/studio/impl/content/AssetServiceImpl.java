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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.craftercms.studio.api.content.AssetService;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.commons.exception.NotImplementedException;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.internal.content.ContentManager;

/**
 * Implementation of {@link org.craftercms.studio.api.content.AssetService}.
 *
 * @author Dejan Brkic
 */
public class AssetServiceImpl implements AssetService {

    private ContentManager contentManager;

    // TODO: review this function ..
    // Content manager requires Item object to add new item to repository
    private Item createAssetItem(String fileName) {
        Item item = new Item();
        item.setCreatedBy(RandomStringUtils.random(10));
        item.setFileName(fileName);
        item.setLabel(fileName);
        return item;
    }

    /**
     * {@link org.craftercms.studio.api.content.AssetService}
     *
     * @param context         the caller's context
     * @param site            the site to use
     * @param destinationPath path to write to (this is relative off of the base path for this type)
     * @param fileName        file name of asset
     * @param content         content InputStream, can be null if creating a 0 byte file
     * @param mimeType        mimeType of asset, can be null if unknown
     * @param properties      key-value-pair properties, can be null
     * @return
     * @throws StudioException
     */
    @Override
    public Item create(final Context context, final String site, final String destinationPath, final String fileName, final InputStream content, final String mimeType, final Map<String, String> properties) throws StudioException {
        StringBuilder sb = new StringBuilder(destinationPath);
        sb.append(File.separator);
        sb.append(fileName);
        Item item = createAssetItem(fileName);
        ItemId itemId = contentManager.create(context, site, sb.toString(), item, content);
        item = contentManager.read(context, site, itemId.getItemId());
        return item;
    }

    /**
     *
     * @param context           the caller's context
     * @param site              the site to use
     * @param destinationPath   path to write to (this is relative off of the base path for this type)
     * @param fileName          file name of asset
     * @param content           content as a string (textual content)
     * @param mimeType          mimeType of asset, can be null if unknown
     * @param properties        key-value-pair properties, can be null
     * @return                  item representing asset
     * @throws StudioException
     */
    @Override
    public Item create(final Context context, final String site, final String destinationPath, final String fileName,
                       final String content, final String mimeType, final Map<String, String> properties
    ) throws StudioException {
        StringBuilder sb = new StringBuilder(destinationPath);
        sb.append(File.separator);
        sb.append(fileName);
        Item item = createAssetItem(fileName);
        InputStream contentStream = IOUtils.toInputStream(content);
        ItemId itemId = contentManager.create(context, site, sb.toString(), item, contentStream);
        item = contentManager.read(context, site, itemId.getItemId());
        return item;
    }

    @Override
    public Item create(final Context context, final String site, final String destinationPath, final String fileName, final byte[] content, final String mimeType, final Map<String, String> properties) throws StudioException {
        StringBuilder sb = new StringBuilder(destinationPath);
        sb.append(File.separator);
        sb.append(fileName);
        Item item = createAssetItem(fileName);
        InputStream contentStream = new ByteArrayInputStream(content);
        ItemId itemId = contentManager.create(context, site, sb.toString(), item, contentStream);
        item = contentManager.read(context, site, itemId.getItemId());
        return item;
    }

    @Override
    public Item read(final Context context, final String site, final String itemId) throws StudioException {
        return contentManager.read(context, site, itemId);
    }

    @Override
    public String getTextContent(final Context context, final String site, final String itemId) throws StudioException {
        Item item = read(context, site, itemId);
        InputStream content = item.getInputStream();
        try {
            return IOUtils.toString(content);
        } catch (IOException e) {
            throw new StudioException("Error while getting text content for item id " + itemId + " (site: " + site +
                ")", e) {

                private static final long serialVersionUID = -257641507057960720L;
            };
        }
    }

    @Override
    public InputStream getInputStream(final Context context, final String site,
                                      final ItemId itemId) throws StudioException {
        Item item = read(context, site, itemId.getItemId());
        return item.getInputStream();
    }

    @Override
    public Item update(final Context context, final String site, final ItemId itemId, final InputStream content,
                       final Map<String, String> properties) throws StudioException {

        throw new NotImplementedException("Not implemented yet");
    }

    @Override
    public Item update(final Context context, final String site, final ItemId itemId, final String content, final Map<String, String> properties) throws StudioException {
        throw new NotImplementedException("Not implemented yet");
    }

    @Override
    public Item update(final Context context, final String site, final ItemId itemId, final byte[] content,
                       final Map<String, String> properties) throws StudioException {
        throw new NotImplementedException("Not implemented yet");
    }

    @Override
    public void delete(final Context context, final String site, final ItemId itemId) throws StudioException {
        throw new NotImplementedException("Not implemented yet");
    }

    @Override
    public List<Item> findBy(final Context context, final String site, final String query) throws StudioException {
        throw new NotImplementedException("Not implemented yet");
    }

    // Getters and setters

    public ContentManager getContentManager() {
        return contentManager;
    }

    public void setContentManager(final ContentManager contentManager) {
        this.contentManager = contentManager;
    }
}
