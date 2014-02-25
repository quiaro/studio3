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

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.craftercms.studio.api.content.TemplateService;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.commons.exception.StudioException;

/**
 * Template service implementation.
 *
 * @author Dejan Brkic
 */
public class TemplateServiceImpl implements TemplateService {

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
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
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
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
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
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
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
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
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
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
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
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
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
}
