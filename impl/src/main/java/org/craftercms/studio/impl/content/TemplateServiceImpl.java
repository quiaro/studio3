/*
 * Copyright (C) 2007-2014 Crafter Software Corporation.
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.craftercms.studio.api.content.TemplateService;
import org.craftercms.studio.api.security.SecurityService;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.commons.dto.LockHandle;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.internal.content.ContentManager;

/**
 * Template service implementation.
 *
 * @author Dejan Brkic
 */
public class TemplateServiceImpl implements TemplateService {

    private ContentManager contentManager;
    private SecurityService securityService;

    /**
     * Create new template.
     *
     * @param context           the caller's context
     * @param site              the site to use
     * @param parentId          the id of the parent item (can be a folder or a descriptor)
     * @param fileName          file name of the template
     * @param content           the InputStream containing the XML that is compliant with the model defined in Studio
     *                          (typically done using Studio's Form Engine).
     * @param properties        key-value-pair properties, can be null
     * @return                  template descriptor
     * @throws StudioException
     */
    @Override
    public Item create(final Context context, final String site, final String parentId, final String fileName, final InputStream content, final Map<String, String> properties) throws StudioException {
        if (context != null && securityService.validate(context)) {
            StringBuilder sb = new StringBuilder(parentId);
            sb.append(File.separator);
            sb.append(fileName);
            Item item = createTemplateItem(fileName);
            ItemId itemId = contentManager.create(context, site, sb.toString(), item, content);
            item = contentManager.read(context, site, itemId.getItemId());
            return item;
        } else {
            throw new StudioException(StudioException.ErrorCode.INVALID_CONTEXT);
        }
    }

    private Item createTemplateItem(String fileName) {
        Item item = new Item();
        item.setCreatedBy(RandomStringUtils.random(10));
        item.setFileName(fileName);
        item.setLabel(fileName);
        return item;
    }

    /**
     * Create new template.
     *
     * @param context           the caller's context
     * @param site              the site to use
     * @param parentId          the id of the parent item (can be a folder or a descriptor)
     * @param fileName          file name of the template
     * @param content           the XML that is compliant with the model defined in Studio (typically done using
     *                          Studio's Form Engine).
     * @param properties        key-value-pair properties, can be null
     * @return                  template descriptor
     * @throws StudioException
     */
    @Override
    public Item create(final Context context, final String site, final String parentId, final String fileName, final String content, final Map<String, String> properties) throws StudioException {
        if (context != null && securityService.validate(context)) {
            StringBuilder sb = new StringBuilder(parentId);
            sb.append(File.separator);
            sb.append(fileName);
            Item item = createTemplateItem(fileName);
            InputStream contentStream = IOUtils.toInputStream(content);
            ItemId itemId = contentManager.create(context, site, sb.toString(), item, contentStream);
            item = contentManager.read(context, site, itemId.getItemId());
            return item;
        } else {
            throw new StudioException(StudioException.ErrorCode.INVALID_CONTEXT);
        }
    }

    /**
     * Read template descriptor.
     *
     * @param context   the caller's context
     * @param site      the site to use
     * @param itemId    the item to read
     * @return          template descriptor
     * @throws StudioException
     */
    @Override
    public Item read(final Context context, final String site, final ItemId itemId) throws StudioException {
        if (context != null && securityService.validate(context)) {
            return contentManager.read(context, site, itemId.getItemId());
        } else {
            throw new StudioException(StudioException.ErrorCode.INVALID_CONTEXT);
        }
    }

    /**
     * Update template.
     *
     * @param context    the caller's context
     * @param site       the site to use
     * @param itemId     the item to read
     * @param content    the InputStream containing the XML that is compliant with the model defined in Studio
     *                   (typically done using Studio's Form Engine).
     * @param properties key-value-pair properties, can be null
     * @return           template descriptor
     * @throws StudioException
     */
    @Override
    public Item update(final Context context, final String site, final ItemId itemId, final InputStream content,
                       final Map<String, String> properties) throws StudioException {
        if (context != null && securityService.validate(context)) {
            LockHandle lockHandle = new LockHandle();
            contentManager.write(context, site, itemId, lockHandle, content);
            return contentManager.read(context, site, itemId.getItemId());
        } else {
            throw new StudioException(StudioException.ErrorCode.INVALID_CONTEXT);
        }
    }

    /**
     * Update template.
     *
     * @param context    the caller's context
     * @param site       the site to use
     * @param itemId     the item to read
     * @param content    the XML that is compliant with the model defined in Studio (typically done using
     *                   Studio's Form Engine).
     * @param properties key-value-pair properties, can be null
     * @return           template descriptor
     * @throws StudioException
     */
    @Override
    public Item update(final Context context, final String site, final ItemId itemId, final String content,
                       final Map<String, String> properties) throws StudioException {
        if (context != null && securityService.validate(context)) {
            LockHandle lockHandle = new LockHandle();
            InputStream contentStream = IOUtils.toInputStream(content);
            contentManager.write(context, site, itemId, lockHandle, contentStream);
            return contentManager.read(context, site, itemId.getItemId());
        } else {
            throw new StudioException(StudioException.ErrorCode.INVALID_CONTEXT);
        }
    }

    /**
     * Delete template.
     *
     * @param context the caller's context
     * @param site    the site to use
     * @param itemId  the item to delete
     * @throws StudioException
     */
    @Override
    public void delete(final Context context, final String site, final ItemId itemId) throws StudioException {
        if (context != null && securityService.validate(context)) {
            List<Item> itemList = new ArrayList<>();
            Item item = contentManager.read(context, site, itemId.getItemId());
            itemList.add(item);
            contentManager.delete(context, itemList);
        } else {
            throw new StudioException(StudioException.ErrorCode.INVALID_CONTEXT);
        }
    }

    /**
     * Find templates by search criteria.
     *
     * @param context the caller's context
     * @param site    the site to use
     * @param query   mdb query string
     * @return        template descriptor
     * @throws StudioException
     */
    @Override
    public List<Item> findBy(final Context context, final String site, final String query) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    public void setContentManager(final ContentManager contentManager) {
        this.contentManager = contentManager;
    }

    public void setSecurityService(final SecurityService securityService) {
        this.securityService = securityService;
    }
}
