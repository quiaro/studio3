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

package org.craftercms.studio.controller.services.rest;

import java.util.List;
import java.util.Map;


import com.mangofactory.swagger.annotations.ApiModel;
import com.mangofactory.swagger.annotations.ListType;
import com.wordnik.swagger.annotations.ApiError;
import com.wordnik.swagger.annotations.ApiErrors;
import com.wordnik.swagger.annotations.ApiParam;
import org.craftercms.studio.api.content.ContentTypeService;
import org.craftercms.studio.commons.dto.ContentType;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.exception.StudioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * ContentTypeService RESTful controller.
 * Defines RESTful services for content types.
 *
 * @author Dejan Brkic
 */
@Controller
@RequestMapping("/api/1/content_type")
public class ContentTypeServiceController {

    @Autowired
    ContentTypeService contentTypeService;

    /**
     * Create a new content type.
     *
     * @param site                  site identifier
     * @param typeName              content type name
     * @param formId                form identifier
     * @param defaultTemplateId     default rendering template
     * @param templateIds           template identifiers
     * @param thumbnail             thumbnail image
     * @param permissionIds         permission identifiers
     * @param previewable           previewable flag
     * @param lifecycleScripts      lifecycle scripts
     * @param properties            additional properties
     * @return                      content type container
     * @throws StudioException
     */
    @ApiErrors(
        @ApiError(code = 400, reason = "Bad request")
    )
    @ApiModel(type = ContentType.class)
    @RequestMapping(
        value = "/create/{site}",
        method = RequestMethod.POST
    )
    @ResponseBody
    public ContentType create(

            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable final String site,

            @ApiParam(name = "type_name", required = true, value = "String")
            @RequestParam(value = "type_name", required = true) final String typeName,

            @ApiParam(name = "form_id", required = true, value = "String")
            @RequestParam(value = "form_id", required = true) final String formId,

            @ApiParam(name = "default_template_id", required = true, value = "String")
            @RequestParam(value = "default_template_id", required = true) final String defaultTemplateId,

            @ApiParam(name = "template_ids", required = false, value = "Map<String, String>")
            @RequestParam(value = "template_ids", required = false) final Map<String, String> templateIds,

            @ApiParam(name = "thumbnail", required = false, value = "MultipartFile")
            @RequestParam(value = "thumbnail", required = false) final MultipartFile thumbnail,

            @ApiParam(name = "permissionIds", required = false, value = "List<String>")
            @RequestParam(value = "permissionIds", required = false) final List<String> permissionIds,

            @ApiParam(name = "previewable", required = false, value = "boolean")
            @RequestParam(value = "previewable", required = false, defaultValue = "false") final boolean previewable,

            @ApiParam(name = "lifecycle_scripts", required = false, value = "Map<String, String")
            @RequestParam(value = "lifecycle_scripts", required = false) final String lifecycleScripts,

            @ApiParam(name = "properties", required = false, value = "Map<String, String>")
            @RequestParam(value = "properties", required = false) final Map<String, String> properties
    ) throws StudioException {

        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Update form associated with given content type.
     *
     * @param site              site identifier
     * @param contentTypeId     content type identifier
     * @param formId            form identifier
     * @return                  content type container
     * @throws StudioException
     */
    @ApiErrors(
        @ApiError(code = 400, reason = "Bad request")
    )
    @ApiModel(type = ContentType.class)
    @RequestMapping(
        value = "/update_form/{site}",
        method = RequestMethod.POST
    )
    @ResponseBody
    public ContentType updateForm(

            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable final String site,

            @ApiParam(name = "content_type_id", required = true, value = "String")
            @RequestParam(value = "content_type_id", required = true) final String contentTypeId,

            @ApiParam(name = "form_id", required = true, value = "String")
            @RequestParam(value = "form_id", required = true) final String formId
    ) throws StudioException {

        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Update default template for given content type.
     * @param site                  site identifier
     * @param contentTypeId         content type identifier
     * @param defaultTemplateId     rendering template identifier
     * @return                      content type container
     *
     * @throws StudioException
     */
    @ApiErrors(
        @ApiError(code = 400, reason = "Bad request")
    )
    @ApiModel(type = ContentType.class)
    @RequestMapping(
        value = "update_default_template/{site}"
    )
    @ResponseBody
    public ContentType updateDefaultTemplate(

            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable final String site,

            @ApiParam(name = "content_type_id", required = true, value = "String")
            @RequestParam(value = "content_type_id", required = true) final String contentTypeId,

            @ApiParam(name = "default_template_id", required = true, value = "String")
            @RequestParam(value = "default_template_id", required = true) final String defaultTemplateId
    ) throws StudioException {

        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Update templates list for given content type.
     *
     * @param site              site identifier
     * @param contentTypeId     content type identifier
     * @param templateIds       list of template ids
     * @return                  content type container
     * @throws StudioException
     */
    @ApiErrors(
        @ApiError(code = 400, reason = "Bad request")
    )
    @ApiModel(type = ContentType.class)
    @RequestMapping(
        value = "/update_templates/{site}",
        method = RequestMethod.POST
    )
    @ResponseBody
    public ContentType updateTemplates(

            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable final String site,

            @ApiParam(name = "content_type_id", required = true, value = "String")
            @RequestParam(value = "content_type_id", required = true) final String contentTypeId,

            @ApiParam(name = "template_ids", required = true, value = "String")
            @RequestParam(value = "template_ids", required = true) final List<String> templateIds
    ) throws StudioException {

        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Update thumbnail image for given content type.
     *
     * @param site              site identifier
     * @param contentTypeId     content type identifier
     * @param thumbnail         thumbnail image
     * @return                  content type container
     * @throws StudioException
     */
    @ApiErrors(
        @ApiError(code = 400, reason = "Bad request")
    )
    @ApiModel(type = ContentType.class)
    @RequestMapping(
        value = "/update_thumbnail/{site}",
        method = RequestMethod.POST
    )
    @ResponseBody
    public ContentType updateThumbnail(

            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable final String site,

            @ApiParam(name = "content_type_id", required = true, value = "String")
            @RequestParam(value = "content_type_id", required = true) final String contentTypeId,

            @ApiParam(name = "thumbnail", required = true, value = "MultipartFile")
            @RequestParam(value = "thumbnail", required = true) final MultipartFile thumbnail
    ) throws StudioException {

        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Update security permissions for given content type.
     *
     * @param site              site identifier
     * @param contentTypeId     content type identifier
     * @param permissionIds     list of permissions ids
     * @return                  content type container
     * @throws StudioException
     */
    @ApiErrors(
        @ApiError(code = 400, reason = "Bad request")
    )
    @ApiModel(type = ContentType.class)
    @RequestMapping(
        value = "/update_permissions/{site}",
        method = RequestMethod.POST
    )
    @ResponseBody
    public ContentType updatePermissions(

            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable final String site,

            @ApiParam(name = "content_type_id", required = true, value = "String")
            @RequestParam(value = "content_type_id", required = true) final String contentTypeId,

            @ApiParam(name = "permission_ids", required = true, value = "List<String>")
            @RequestParam(value = "permission_ids", required = true) final List<String> permissionIds
    ) throws StudioException {

        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Update previewable flag for given content type.
     *
     * @param site              site identifier
     * @param contentTypeId     content type identifier
     * @param previewable       previewable flag
     * @return                  content type container
     * @throws StudioException
     */
    @ApiErrors(
        @ApiError(code = 400, reason = "Bad request")
    )
    @ApiModel(type = ContentType.class)
    @RequestMapping(
        value = "/update_previewable/{site}",
        method = RequestMethod.POST
    )
    @ResponseBody
    public ContentType updatePreviewable(

            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable final String site,

            @ApiParam(name = "content_type_id", required = true, value = "String")
            @RequestParam(value = "content_type_id", required = true) final String contentTypeId,

            @ApiParam(name = "previewable", required = true, value = "boolean")
            @RequestParam(value = "previewable", required = true) final boolean previewable
    ) throws StudioException {

        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Update lifecycle scripts for given content type.
     *
     * @param site                  site identifier
     * @param contentTypeId         content type identifier
     * @param lifecycleScripts      lifecycle scripts
     * @return                      content type containers
     * @throws StudioException
     */
    @ApiErrors(
        @ApiError(code = 400, reason = "Bad request")
    )
    @ApiModel(type = ContentType.class)
    @RequestMapping(
        value = "/update_lifecycle_scripts/{site}",
        method = RequestMethod.POST
    )
    @ResponseBody
    public ContentType updateLifecycleScripts(

            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable final String site,

            @ApiParam(name = "content_type_id", required = true, value = "String")
            @RequestParam(value = "content_type_id", required = true) final String contentTypeId,

            @ApiParam(name = "lifecycle_scripts", required = true, value = "String")
            @RequestParam(value = "lifecycle_scripts", required = true) final String lifecycleScripts
    ) throws StudioException {

        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Update properties for given content type.
     *
     * @param site              site identifier
     * @param contentTypeId     content type identifier
     * @param properties        additional properties
     * @return                  content type container
     * @throws StudioException
     */
    @ApiErrors(
        @ApiError(code = 400, reason = "Bad request")
    )
    @ApiModel(type = ContentType.class)
    @RequestMapping(
        value = "/update_properties/{site}",
        method = RequestMethod.POST
    )
    @ResponseBody
    public ContentType updateProperties(

            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable final String site,

            @ApiParam(name = "content_type_id", required = true, value = "String")
            @RequestParam(value = "content_type_id", required = true) final String contentTypeId,

            @ApiParam(name = "properties", required = true, value = "Map<String, String>")
            @RequestParam(value = "properties", required = true) final Map<String, String> properties
    ) throws StudioException {

        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Duplicate existing content type.
     *
     * @param site              site identifier
     * @param contentTypeId     content type to be duplicated
     * @param typeName          name for newly created content type
     * @return                  content type container
     * @throws StudioException
     */
    @ApiErrors(
        @ApiError(code = 400, reason = "Bad request")
    )
    @ApiModel(type = ContentType.class)
    @RequestMapping(
        value = "/duplicate/{site}",
        method = RequestMethod.POST
    )
    @ResponseBody
    public ContentType duplicate(

            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable final String site,

            @ApiParam(name = "content_type_id", required = true, value = "String")
            @RequestParam(value = "content_type_id", required = true) final String contentTypeId,

            @ApiParam(name = "type_name", required = true, value = "String")
            @RequestParam(value = "type_name", required = true) final String typeName
    ) throws StudioException {

        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Duplicate existing content type from one site to another.
     *
     * @param sourceSite        source site identifier
     * @param destinationSite   destination site identifier
     * @param contentTypeId     content type to be duplicated
     * @param typeName          name for newly created content type
     * @return                  content type container
     * @throws StudioException
     */
    @ApiErrors(
        @ApiError(code = 400, reason = "Bad request")
    )
    @ApiModel(type = ContentType.class)
    @RequestMapping(
        value = "/duplicate",
        method = RequestMethod.POST
    )
    @ResponseBody
    public ContentType duplicate(

            @ApiParam(name = "source_site", required = true, value = "String")
            @RequestParam(value = "source_site", required = true) final String sourceSite,

            @ApiParam(name = "destination_site", required = true, value = "String")
            @RequestParam(value = "destination_site", required = true) final String destinationSite,

            @ApiParam(name = "content_type_id", required = true, value = "String")
            @RequestParam(value = "content_type_id", required = true) final String contentTypeId,

            @ApiParam(name = "type_name", required = true, value = "String")
            @RequestParam(value = "type_name", required = true) final String typeName
    ) throws StudioException {

        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Get content type with given id.
     *
     * @param site              site identifier
     * @param contentTypeId     content type identifier
     * @return                  content type container
     * @throws StudioException
     */
    @ApiErrors(
        @ApiError(code = 400, reason = "Bad request")
    )
    @ApiModel(type = ContentType.class)
    @RequestMapping(
        value = "/read/{site}",
        method = RequestMethod.GET
    )
    @ResponseBody
    public ContentType read(

            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable final String site,

            @ApiParam(name = "content_type_id", required = true, value = "String")
            @RequestParam(value = "content_type_id", required = true) final String contentTypeId
    ) throws StudioException {

        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Delete content type with given id
     *
     * @param site              site identifier
     * @param contentTypeId     content type identifier
     * @throws StudioException
     */
    @ApiErrors(
        @ApiError(code = 400, reason = "Bad request")
    )
    @RequestMapping(
        value = "/delete/{site}",
        method = RequestMethod.POST
    )
    public void delete(

            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable final String site,

            @ApiParam(name = "content_type_id", required = true, value = "String")
            @RequestParam(value = "content_type_id", required = true) final String contentTypeId
    ) throws StudioException {

        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Find content types.
     *
     * @param site      site identifier
     * @param query     search query
     * @return          list of content types
     * @throws StudioException
     */
    @ApiErrors(
        @ApiError(code = 400, reason = "Bad request")
    )
    @ApiModel(type = ContentType.class, collection = true, listType = ListType.List)
    @RequestMapping(
        value = "/find/{site}",
        method = RequestMethod.GET
    )
    @ResponseBody
    public List<ContentType> findBy(

            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable final String site,

            @ApiParam(name = "query", required = true, value = "String")
            @RequestParam(value = "query", required = true) final String query
    ) throws StudioException {

        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    public void setContentTypeService(final ContentTypeService contentTypeService) {
        this.contentTypeService = contentTypeService;
    }
}
