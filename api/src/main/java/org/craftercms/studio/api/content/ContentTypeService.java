package org.craftercms.studio.api.content;

import java.util.List;
import java.util.Map;

import org.craftercms.studio.commons.dto.ContentType;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.exception.StudioException;

/**
 * Content Type Service provides an API for managing Crafter content types in a CrafterCMS-based site. A Crafter
 * content type models things like pages, content snippets, and so on. The content type comprises a Form definition
 * (which ultimately produces an XML descriptor), an optional view template (Freemarker) and a thumbnail so the user
 * of Crafter Studio can pick the type they'd like to use.
 *
 * @author Sumer Jabri
 */
public interface ContentTypeService {
    /**
     * Create a new content type.
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
     * @return the content type descriptor
     * @throws org.craftercms.studio.commons.exception.StudioException
     */
    ContentType create(Context context, String site, String typeName, String formId, String defaultTemplateId,
                       Map<String, String> templateIds, byte[] thumbnail, List<String> permissionIds,
                       boolean previewable, String lifecycleScripts, Map<String,
        String> properties) throws StudioException;

    // TODO consider simpler creation method as most of the params cannot be determined when a type is first created
    // TODO add lifecycle script placeholders

    /**
     * Update the form associated with a content type.
     *
     * @param context       the caller's context
     * @param site          the site to use
     * @param contentTypeId content type id
     * @param formId        the form associated with this content type, see {@link org.craftercms.studio.api
     *                      .content.FormService}
     * @return the content type descriptor
     * @throws StudioException
     */
    ContentType updateForm(Context context, String site, String contentTypeId, String formId) throws StudioException;

    /**
     * Update the default template associated with a content type.
     *
     * @param context           the caller's context
     * @param site              the site to use
     * @param defaultTemplateId default view template to use with this content type, can be null,
     *                          see {@link org.craftercms.studio.api.content.TemplateService}
     * @param contentTypeId     content type id
     * @return the content type descriptor
     * @throws StudioException
     */
    ContentType updateDefaultTemplate(Context context, String site, String contentTypeId,
                                      String defaultTemplateId) throws StudioException;

    /**
     * Update the template map associated with a content type.
     *
     * @param context       the caller's context
     * @param site          the site to use
     * @param contentTypeId content type id
     * @param templateIds   map of templates to use based on various criteria (like user-agent/browser,
     *                      or anything else), key to template mapping. Can be null.
     * @return the content type descriptor
     * @throws StudioException
     */
    ContentType updateTemplates(Context context, String site, String contentTypeId,
                                List<String> templateIds) throws StudioException;

    /**
     * Update the thumbnail associated with a content type.
     *
     * @param context       the caller's context
     * @param site          the site to use
     * @param contentTypeId content type id
     * @param thumbnail     thumbnail view of this type, can be null
     * @return the content type descriptor
     * @throws StudioException
     */
    ContentType updateThumbnail(Context context, String site, String contentTypeId,
                                byte[] thumbnail) throws StudioException;

    /**
     * Update the permissions associated with a content type.
     *
     * @param context       the caller's context
     * @param site          the site to use
     * @param contentTypeId content type id
     * @param permissionIds list of permission ids, see {@link org.craftercms.studio.api.security.SecurityService}
     * @return the content type descriptor
     * @throws StudioException
     */
    ContentType updatePermissions(Context context, String site, String contentTypeId,
                                  List<String> permissionIds) throws StudioException;

    // TODO document
    ContentType updatePreviewable(Context context, String site, String contentTypeId,
                                  boolean previewable) throws StudioException;
    // TODO document
    ContentType updateLifecycleScripts(Context context, String site, String contentTypeId,
                                  String lifecycleScripts) throws StudioException;
    /**
     * Update the properties of a content type.
     *
     * @param context       the caller's context
     * @param site          the site to use
     * @param contentTypeId content type id
     * @param properties    key-value-pair properties, can be null
     * @return the content type descriptor
     * @throws StudioException
     */
    ContentType updateProperties(Context context, String site, String contentTypeId, Map<String,
        String> properties) throws StudioException;

    /**
     * Create a duplicate of a content type in the current site with a new name.
     *
     * @param context       the caller's context
     * @param site          the site to use
     * @param contentTypeId content type id
     * @param typeName      name of content type
     * @return the content type descriptor
     * @throws StudioException
     */
    ContentType duplicate(Context context, String site, String contentTypeId, String typeName) throws StudioException;

    /**
     * Create a duplicate of a content type in a different site.
     *
     * @param context         the caller's context
     * @param sourceSite      the source site to copy from
     * @param destinationSite the destination site to copy to
     * @param contentTypeId   the content type to copy
     * @param typeName        name of the target content type
     * @return the content type descriptor
     * @throws StudioException
     */
    ContentType duplicate(Context context, String sourceSite, String destinationSite, String contentTypeId,
                          String typeName) throws StudioException;

    /**
     * Read a content type and return its descriptor.
     *
     * @param context       the caller's context
     * @param site          the site to use
     * @param contentTypeId content type id
     * @return the content type descriptor
     * @throws StudioException
     */
    ContentType read(Context context, String site, String contentTypeId) throws StudioException;

    /**
     * Delete a content type.
     *
     * @param context       the caller's context
     * @param site          the site to use
     * @param contentTypeId the content type to delete
     * @throws org.craftercms.studio.commons.exception.StudioException
     */
    void delete(Context context, String site, String contentTypeId) throws StudioException;

    /**
     * Find content types matching a query.
     *
     * @param context the caller's context
     * @param site    the site to use
     * @param query   mdb query string
     * @return list of items matching the query
     * @throws org.craftercms.studio.commons.exception.StudioException
     */
    List<Item> findBy(Context context, String site, String query) throws StudioException;
}
