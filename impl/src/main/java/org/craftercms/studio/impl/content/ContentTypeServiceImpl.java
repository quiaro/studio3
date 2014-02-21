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

import java.util.List;
import java.util.Map;

import org.craftercms.studio.api.content.ContentTypeService;
import org.craftercms.studio.commons.dto.ContentType;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.exception.StudioException;

/**
 * ContentTypeService default implementation.
 *
 * @author Dejan Brkic
 */
public class ContentTypeServiceImpl implements ContentTypeService {

    @Override
    public ContentType create(final Context context, final String site, final String typeName, final String formId,
                              final String defaultTemplateId, final Map<String, String> templateIds, final byte[] thumbnail, final List<String> permissionIds, final boolean previewable, final String lifecycleScripts, final Map<String, String> properties) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public ContentType updateForm(final Context context, final String site, final String contentTypeId, final String
        formId) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public ContentType updateDefaultTemplate(final Context context, final String site, final String contentTypeId,
                                             final String defaultTemplateId) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public ContentType updateTemplates(final Context context, final String site, final String contentTypeId, final
    List<String> templateIds) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public ContentType updateThumbnail(final Context context, final String site, final String contentTypeId, final byte[] thumbnail) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public ContentType updatePermissions(final Context context, final String site, final String contentTypeId, final List<String> permissionIds) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public ContentType updatePreviewable(final Context context, final String site, final String contentTypeId, final
    boolean previewable) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public ContentType updateLifecycleScripts(final Context context, final String site, final String contentTypeId,
                                              final String lifecycleScripts) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public ContentType updateProperties(final Context context, final String site, final String contentTypeId, final Map<String, String> properties) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public ContentType duplicate(final Context context, final String site, final String contentTypeId, final String typeName) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public ContentType duplicate(final Context context, final String sourceSite, final String destinationSite, final String contentTypeId, final String typeName) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public ContentType read(final Context context, final String site, final String contentTypeId) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public void delete(final Context context, final String site, final String contentTypeId) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    @Override
    public List<Item> findBy(final Context context, final String site, final String query) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }
}
