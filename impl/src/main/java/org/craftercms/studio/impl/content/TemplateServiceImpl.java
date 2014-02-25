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


    @Override
    public Item create(final Context context, final String site, final String parentId, final String fileName, final InputStream content, final Map<String, String> properties) throws StudioException {
        return null;
    }

    @Override
    public Item create(final Context context, final String site, final String parentId, final String fileName, final String content, final Map<String, String> properties) throws StudioException {
        return null;
    }

    @Override
    public Item read(final Context context, final String site, final ItemId itemId) throws StudioException {
        return null;
    }

    @Override
    public Item update(final Context context, final String site, final ItemId itemId, final InputStream content,
                       final Map<String, String> properties) throws StudioException {
        return null;
    }

    @Override
    public Item update(final Context context, final String site, final ItemId itemId, final String content, final
    Map<String, String> properties) throws StudioException {
        return null;
    }

    @Override
    public void delete(final Context context, final String site, final ItemId itemId) throws StudioException {

    }

    @Override
    public List<Item> findBy(final Context context, final String site, final String query) throws StudioException {
        return null;
    }
}
