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

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.mangofactory.swagger.annotations.ApiModel;
import com.mangofactory.swagger.annotations.ListType;
import com.wordnik.swagger.annotations.ApiError;
import com.wordnik.swagger.annotations.ApiErrors;
import com.wordnik.swagger.annotations.ApiParam;
import org.craftercms.studio.api.content.DescriptorService;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemId;
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
 * Descriptor Service RESTful Controller.
 * Defines RESTful services for descriptors.
 *
 * @author Dejan Brkic
 */
@Controller
@RequestMapping("/api/1/descriptor")
public class DescriptorServiceController {

    /**
     * Descriptor Service instance.
     */
    @Autowired
    DescriptorService descriptorService;

    /**
     * Create a new descriptor.
     *
     * @param site              site identifier
     * @param contentTypeId     content type identifier
     * @param parentId          parent identifier
     * @param fileName          file name
     * @param file           content stream
     * @param properties        additional properties
     * @return                  item
     * @throws StudioException
     */
    @ApiErrors(
        @ApiError(code = 400, reason = "Bad Request")
    )
    @ApiModel(type = Item.class)
    @RequestMapping(
        value = "/create/{site}",
        method = RequestMethod.POST
    )
    @ResponseBody
    public Item create(

            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable String site,

            @ApiParam(name = "content_type_id", required = true, value = "String")
            @RequestParam(value = "content_type_id", required = true) String contentTypeId,

            @ApiParam(name = "parent_id", required = true, value = "String")
            @RequestParam(value = "parent_id", required = true) String parentId,

            @ApiParam(name = "file_name", required = true, value = "String")
            @RequestParam(value ="file_name", required = true) String fileName,

            @ApiParam(name = "content", required = true, value = "MultipartFile")
            @RequestParam(value = "file") MultipartFile file,

            @ApiParam(name = "properties", required = false, value = "Map<String, String>")
            @RequestParam(value = "properties", required = false) Map<String,
                String> properties
    ) throws StudioException {

        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Create a new descriptor.
     *
     * @param site              site identifier
     * @param contentTypeId     content type identifier
     * @param parentId          parent identifier
     * @param fileName          file name
     * @param content           content
     * @param properties        additional properties
     * @return                  item
     * @throws StudioException
     */
    @ApiErrors(
        @ApiError(code = 400, reason = "Bad request")
    )
    @ApiModel(type = Item.class)
    @RequestMapping(
        value = "/create/{site}",
        method = RequestMethod.POST
    )
    @ResponseBody
    public Item create(

            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable String site,

            @ApiParam(name = "content_type_id", required = true, value = "String")
            @RequestParam(value = "content_type_id", required = true) String contentTypeId,

            @ApiParam(name = "parent_id", required = true, value = "String")
            @RequestParam(value = "parent_id", required = true) String parentId,

            @ApiParam(name = "file_name", required = true, value = "String")
            @RequestParam(value = "file_name", required = true) String fileName,

            @ApiParam(name = "content", required = true, value = "String")
            @RequestParam(value = "content", required = true) String content,

            @ApiParam(name = "properties", required = false, value ="Map<String, String>")
            @RequestParam(value = "properties") Map<String, String> properties
    ) throws StudioException {

        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Create a new descriptor as copy of existing.
     *
     * @param site          site identifier
     * @param itemId        item identifier of descriptor to be duplicated
     * @param parentId      parent identifier
     * @param fileName      file name
     * @return              item
     * @throws StudioException
     */
    @ApiErrors(
        @ApiError(code = 400, reason = "Bad request")
    )
    @ApiModel(type = Item.class)
    @RequestMapping(
        value = "/duplicate/{site}",
        method = RequestMethod.POST
    )
    @ResponseBody
    public Item duplicate(

            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable String site,

            @ApiParam(name = "item_id", required = true, value = "String")
            @RequestParam(value = "item_id", required = true) String itemId,

            @ApiParam(name = "parent_id", required = true, value = "String")
            @RequestParam(value = "parent_id", required = true) String parentId,

            @ApiParam(name = "file_name", required = true, value = "String")
            @RequestParam(value = "file_name") String fileName
    ) throws StudioException {

        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Move/rename descriptor.
     *
     * @param site          site identifier
     * @param itemId        item identifier
     * @param parentId      destination parent identifier
     * @param fileName      file name
     * @return              item
     * @throws StudioException
     */
    @ApiErrors(
        @ApiError(code = 400, reason = "Bad request")
    )
    @ApiModel(type = Item.class)
    @RequestMapping(
        value = "/move/{site}",
        method = RequestMethod.POST
    )
    @ResponseBody
    public Item move(

            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable String site,

            @ApiParam(name = "item_id", required = true, value = "String")
            @RequestParam(value = "item_id", required = true) String itemId,

            @ApiParam(name = "parent_id", required = true, value = "String")
            @RequestParam(value = "parent_id", required = true) String parentId,

            @ApiParam(name = "file_name", required = true, value = "String")
            @RequestParam(value = "file_name", required = true) String fileName
    ) throws StudioException {

        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Get descriptor item.
     *
     * @param site      site identifier
     * @param itemId    item identifier
     * @return          item
     * @throws StudioException
     */
    @ApiErrors(
        @ApiError(code = 400, reason = "Bad request")
    )
    @ApiModel(type = Item.class)
    @RequestMapping(
        value = "/read/{site}",
        method = RequestMethod.GET
    )
    @ResponseBody
    public Item read(

            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable String site,

            @ApiParam(name = "item_id", required = true, value = "String")
            @RequestParam(value = "item_id", required = true) String itemId
    ) throws StudioException {

        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Update given descriptor.
     *
     * @param site          site identifier
     * @param itemId        descriptor item identifier
     * @param file          content stream
     * @param properties    additional properties
     * @return              item
     * @throws StudioException
     */
    @ApiErrors(
        @ApiError(code = 400, reason = "Bad request")
    )
    @ApiModel(type = Item.class)
    @RequestMapping(
        value = "/update/{site}",
        method = RequestMethod.POST
    )
    @ResponseBody
    public Item update(

            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable String site,

            @ApiParam(name = "item_id", required = true, value = "String")
            @RequestParam(value = "item_id", required = true) String itemId,

            @ApiParam(name = "file", required = true, value = "MultipartFile")
            @RequestParam(value = "file", required = true) MultipartFile file,

            @ApiParam(name = "properties", required = false, value = "Map<String, String>")
            @RequestParam(value = "properties", required = false) Map<String, String> properties
    ) throws StudioException {

        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Update given descriptor.
     *
     * @param site          site identifier
     * @param itemId        descriptor item identifier
     * @param content       descriptor content
     * @param properties    additional properties
     * @return              item
     * @throws StudioException
     */
    public Item update(

            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable String site,

            @ApiParam(name = "item_id", required = true, value = "String")
            @RequestParam(value = "item_id", required = true) String itemId,

            @ApiParam(name = "content", required = true, value = "String")
            @RequestParam(value = "content", required = true) String content,

            @ApiParam(name = "content", required = false, value = "Map<String, String>")
            @RequestParam(value = "properties") Map<String, String> properties
    ) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Delete descriptor.
     *
     * @param site      site identifier
     * @param itemId    descriptor item identifier
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
            @PathVariable String site,

            @ApiParam(name = "item_id", required = true, value = "String")
            @RequestParam(value = "item_id", required = true) String itemId
    ) throws StudioException {

        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Find descriptor by given query.
     *
     * @param site      site identifier
     * @param query     search query
     * @return          list of descriptor items
     * @throws StudioException
     */
    @ApiErrors(
        @ApiError(code = 400, reason = "Bad request")
    )
    @ApiModel(type = Item.class, collection = true, listType = ListType.List)
    @RequestMapping(
        value = "/find/{site}",
        method = RequestMethod.POST
    )
    @ResponseBody
    public List<Item> findBy(

            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable String site,

            @ApiParam(name = "site", required = true, value = "String")
            @RequestParam(value = "query", required = true) String query
    ) throws StudioException {

        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    public void setDescriptorService(final DescriptorService descriptorService) {
        this.descriptorService = descriptorService;
    }
}
