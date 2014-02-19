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

import org.craftercms.studio.api.content.DescriptorService;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.commons.exception.StudioException;

/**
 * @author Dejan Brkic
 */
public class DescriptorServiceImpl implements DescriptorService {
    @Override
    public Item create(final Context context, final String site, final String contentTypeId, final String parentId,
                       final String fileName, final InputStream content, final Map<String, String> properties) throws
        StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public Item create(final Context context, final String site, final String contentTypeId, final String parentId, final String fileName, final String content, final Map<String, String> properties) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public Item duplicate(final Context context, final String site, final ItemId itemId, final String parentId, final
    String fileName) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public Item move(final Context context, final String site, final ItemId itemId, final String parentId, final String fileName) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public Item read(final Context context, final String site, final ItemId itemId) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public Item update(final Context context, final String site, final ItemId itemId, final InputStream content,
                       final Map<String, String> properties) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public Item update(final Context context, final String site, final ItemId itemId, final String content,
                       final Map<String, String> properties) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public void delete(final Context context, final String site, final ItemId itemId) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public List<Item> findBy(final Context context, final String site, final String query) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }
}
