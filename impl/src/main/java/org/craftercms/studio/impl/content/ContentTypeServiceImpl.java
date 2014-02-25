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

    /**
     * Create a new content type
     *
     * @param context           the caller's context
     * @param site              the site to use
     * @param typeName          name of content type
     * @param formId            the form associated with this content type, see {@link org.craftercms.studio.api
     *                          .content.FormService}
     * @param defaultTemplateId default view template to use with this content type, can be null,
     *                          see {@link org.craftercms.studio.api.content.TemplateService}
     * @param templateIds       map of templates to use based on various criteria (like user-agent/browser,
     *                          or anything else), key to template mapping. Can be null.
     * @param thumbnail         thumbnail view of this type, can be null
     * @param permissionIds     list of permission ids, see {@link org.craftercms.studio.api.security.SecurityService}
     * @param previewable       content type is previewable (like a page)
     * @param lifecycleScripts  lifecycle method definitions to be invoked during the various
     *                          lifecycle events, see {@link ??????????????????}
     * @param properties        key-value-pair properties, can be null
     * @return                  content type descriptor
     * @throws StudioException
     */
    @Override
    public ContentType create(final Context context, final String site, final String typeName, final String formId,
                              final String defaultTemplateId, final Map<String, String> templateIds, final byte[] thumbnail, final List<String> permissionIds, final boolean previewable, final String lifecycleScripts, final Map<String, String> properties) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Update form for given content type.
     *
     * @param context       the caller's context
     * @param site          the site to use
     * @param contentTypeId content type id
     * @param formId        the form associated with this content type, see {@link org.craftercms.studio.api
     *                      .content.FormService}
     * @return              content type descriptor
     * @throws StudioException
     */
    @Override
    public ContentType updateForm(final Context context, final String site, final String contentTypeId, final String
        formId) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Update default template for given content type.
     *
     * @param context           the caller's context
     * @param site              the site to use
     * @param contentTypeId     content type id
     * @param defaultTemplateId default view template to use with this content type, can be null,
     *                          see {@link org.craftercms.studio.api.content.TemplateService}
     * @return                  content type descriptor
     * @throws StudioException
     */
    @Override
    public ContentType updateDefaultTemplate(final Context context, final String site, final String contentTypeId,
                                             final String defaultTemplateId) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Update templates list for given content type.
     *
     * @param context       the caller's context
     * @param site          the site to use
     * @param contentTypeId content type id
     * @param templateIds   map of templates to use based on various criteria (like user-agent/browser,
     *                      or anything else), key to template mapping. Can be null.
     * @return              content type descriptor
     * @throws StudioException
     */
    @Override
    public ContentType updateTemplates(final Context context, final String site, final String contentTypeId, final
    List<String> templateIds) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Update thumbnail image for given content type.
     *
     * @param context       the caller's context
     * @param site          the site to use
     * @param contentTypeId content type id
     * @param thumbnail     thumbnail view of this type, can be null
     * @return              content type descriptor
     * @throws StudioException
     */
    @Override
    public ContentType updateThumbnail(final Context context, final String site, final String contentTypeId, final byte[] thumbnail) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Update permissions for given content type.
     *
     * @param context       the caller's context
     * @param site          the site to use
     * @param contentTypeId content type id
     * @param permissionIds list of permission ids, see {@link org.craftercms.studio.api.security.SecurityService}
     * @return              content type descriptor
     * @throws StudioException
     */
    @Override
    public ContentType updatePermissions(final Context context, final String site, final String contentTypeId, final List<String> permissionIds) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Update previewable flag for given content type.
     *
     * @param context       the caller's context
     * @param site          the site to use
     * @param contentTypeId content type id
     * @param previewable       content type is previewable (like a page)
     * @return              content type descriptor
     * @throws StudioException
     */
    @Override
    public ContentType updatePreviewable(final Context context, final String site, final String contentTypeId, final
    boolean previewable) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Update lifecycle scripts for given content type.
     *
     * @param context       the caller's context
     * @param site          the site to use
     * @param contentTypeId content type id
     * @param lifecycleScripts  lifecycle method definitions to be invoked during the various
     *                          lifecycle events, see {@link ??????????????????}
     * @return                  content type descriptor
     * @throws StudioException
     */
    @Override
    public ContentType updateLifecycleScripts(final Context context, final String site, final String contentTypeId,
                                              final String lifecycleScripts) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Update properties for given content type.
     *
     * @param context       the caller's context
     * @param site          the site to use
     * @param contentTypeId content type id
     * @param properties    key-value-pair properties, can be null
     * @return              content type descriptor
     * @throws StudioException
     */
    @Override
    public ContentType updateProperties(final Context context, final String site, final String contentTypeId, final Map<String, String> properties) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Create duplicate of a content type with new name.
     *
     * @param context       the caller's context
     * @param site          the site to use
     * @param contentTypeId content type id
     * @param typeName      name of content type
     * @return              content type descriptor
     * @throws StudioException
     */
    @Override
    public ContentType duplicate(final Context context, final String site, final String contentTypeId, final String typeName) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Create a duplicate of a content type from different site.
     *
     * @param context         the caller's context
     * @param sourceSite      the source site to copy from
     * @param destinationSite the destination site to copy to
     * @param contentTypeId   the content type to copy
     * @param typeName        name of the target content type
     * @return                content type descriptor
     * @throws StudioException
     */
    @Override
    public ContentType duplicate(final Context context, final String sourceSite, final String destinationSite, final String contentTypeId, final String typeName) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Get content type descriptor.
     *
     * @param context       the caller's context
     * @param site          the site to use
     * @param contentTypeId content type id
     * @return              content type descriptor
     * @throws StudioException
     */
    @Override
    public ContentType read(final Context context, final String site, final String contentTypeId) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Delete content type.
     *
     * @param context       the caller's context
     * @param site          the site to use
     * @param contentTypeId the content type to delete
     * @throws StudioException
     */
    @Override
    public void delete(final Context context, final String site, final String contentTypeId) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Find content types by give criteria.
     *
     * @param context the caller's context
     * @param site    the site to use
     * @param query   mdb query string
     * @return        list of content type descriptors
     * @throws StudioException
     */
    @Override
    public List<Item> findBy(final Context context, final String site, final String query) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }
}
