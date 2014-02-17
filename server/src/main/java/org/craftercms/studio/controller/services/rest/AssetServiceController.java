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

package org.craftercms.studio.controller.services.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.mangofactory.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiError;
import com.wordnik.swagger.annotations.ApiErrors;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.craftercms.studio.api.content.AssetService;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.utils.RestControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Asset controller.
 * Defines restful services for assets.
 *
 * @author Dejan Brkic
 */
@Controller
@RequestMapping(value = "/api/1/content/asset")
public class AssetServiceController {

    @Autowired
    private AssetService assetService;

    /**
     * Add asset file to repository.
     *
     * @param site          site identifier
     * @param parentId      parent identifier
     * @param fileName      asset file name
     * @param file          asset content file
     * @param mimeType      mime type
     * @param properties    additional properties
     * @return              item representing given asset in repository
     * @throws StudioException
     */
    @ApiErrors(
        @ApiError(code = 400, reason = "Bad request")
    )
    @ApiModel(type = Item.class)
    @RequestMapping(value = "/create/{site}",
        params = {"parent_id", "file_name", "mime_type"},
        method = RequestMethod.POST
    )
    @ResponseBody
    public Item create(
            @ApiParam (name = "site", required = true, value = "String")
            @PathVariable String site,

            @ApiParam (name = "parent_id", required =  true, value = "String")
            @RequestParam(value = "parent_id") String parentId,

            @ApiParam(name = "file_name", required = true, value = "String")
            @RequestParam(value = "file_name") String fileName,

            @ApiParam(name = "file", required = true, value = "org.springframework.web.multipart.MultipartFile")
            @RequestParam(value = "file") MultipartFile file,

            @ApiParam(name = "mime_type", required = true, value = "String")
            @RequestParam(value = "mime_type") String mimeType,

            @ApiParam(name = "properties", required = false, value = "Map<String, String>")
            @RequestParam(value = "properties", required = false) Map<String, String> properties)

        throws StudioException {

        InputStream contentStream = null;
        try {
            contentStream = file.getInputStream();
        } catch (IOException e) {
            throw new StudioException(StudioException.ErrorCode.SYSTEM_ERROR, e);
        }
        Context context = RestControllerUtils.createMockContext();
        return assetService.create(context, site, parentId, fileName, contentStream, mimeType, properties);
    }



    /**
     * Read asset meta-data for given item id.
     *
     * @param site      site identifier
     * @param itemId    asset identifier
     * @return          asset meta data
     * @throws          StudioException
     */
    @ApiErrors(
        @ApiError(code = 400, reason = "Bad request")
    )
    @ApiModel(type = Item.class)
    @RequestMapping(value = "/read/{site}",
                    params = { "item_id" },
                    method = RequestMethod.GET
    )
    @ResponseBody
    public Item read(
            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable String site,

            @ApiParam(name = "item_id", required = true, value = "String")
            @RequestParam(value = "item_id") String itemId
    ) throws StudioException {

        Context context = RestControllerUtils.createMockContext();
        return assetService.read(context, site, itemId);
    }

    /**
     * Read textual content for given asset id.

     * @param site      site identifier
     * @param itemId    asset item id
     * @return          textual content of asset
     * @throws StudioException
     */
    @ApiErrors(
        @ApiError(code = 400, reason = "Bad request")
    )
    @ApiModel(type = String.class)
    @RequestMapping(value = "/read_text",
                    params = { "item_id" },
                    method = RequestMethod.GET)
    @ResponseBody
    public String getTextContent(
            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable String site,

            @ApiParam(name = "item_id", required = true, value = "String")
            @RequestParam(value = "item_id") String itemId
    ) throws StudioException {

        Context context = RestControllerUtils.createMockContext();
        return assetService.getTextContent(context, site, itemId);
    }

    /**
     * Read asset content for given id.
     *
     * @param site      site identifier
     * @param itemId    asset identifier
     * @param response  content
     * @throws StudioException
     */
    @ApiErrors(
        @ApiError(code = 400, reason = "Bad request")
    )
    @ApiModel(type = InputStream.class)
    @RequestMapping(value = "/get_content/{site}",
                    params = { "item_id" },
                    method = RequestMethod.GET)
    public void getInputStream(
            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable String site,

            @ApiParam(name = "item_id", required = true, value = "String")
            @RequestParam(value = "item_id") String itemId,

            HttpServletResponse response
    ) throws StudioException {

        Context context = RestControllerUtils.createMockContext();
        ItemId id = new ItemId(itemId);
        InputStream content = assetService.getInputStream(context, site, id);
        OutputStream out = null;
        Item item = assetService.read(context, site, itemId);
        response.setContentType(item.getMimeType());
        try {
            out = response.getOutputStream();
            IOUtils.copy(content, out);
        } catch (IOException e) {
            // TODO: Log error
        } finally {
            IOUtils.closeQuietly(content);
            IOUtils.closeQuietly(out);
        }

    }

    /**
     * Update asset with given id and file.
     *
     * @param site          site identifier
     * @param itemId        asset item id
     * @param file          asset content file
     * @param properties    additional properties
     * @return              item representing asset
     * @throws StudioException
     */
    @ApiErrors(
        @ApiError(code = 400, reason = "Bad Request")
    )
    @ApiModel(type = Item.class)
    @RequestMapping(value = "/update/{site}",
                    params = { "item_id"},
                    method = RequestMethod.POST
    )
    @ResponseBody
    public Item update(
            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable String site,

            @ApiParam(name = "item_id", required = true, value = "String")
            @RequestParam(value = "item_id") String itemId,

            @ApiParam(name = "file", required = true, value = "org.springframework.web.multipart.MultipartFile")
            @RequestParam(value = "file") MultipartFile file,

            @ApiParam(name = "properties", required = false, value = "Map<String, String>")
            @RequestParam(value = "properties", required = false) Map<String, String> properties
    ) throws StudioException {

        Context context = RestControllerUtils.createMockContext();
        ItemId id = new ItemId(itemId);
        InputStream content = null;
        try {
            content = file.getInputStream();
        } catch (IOException e) {
            throw new StudioException(StudioException.ErrorCode.SYSTEM_ERROR, e);
        }
        return assetService.update(context, site, id, content, properties);
    }

    /**
     * Delete asset for given item id.
     *
     * @param site      site identifier
     * @param itemId    asset item id
     * @throws StudioException
     */
    @ApiErrors(
        @ApiError(code = 400, reason = "Bad request")
    )
    @RequestMapping(value = "/delete/{site}",
                    params = { "item_id" },
                    method = RequestMethod.POST
    )
    public void delete(
            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable String site,

            @ApiParam(name = "item_id", required = true, value = "String")
            @RequestParam(value = "item_id") String itemId
    ) throws StudioException {
        Context context = RestControllerUtils.createMockContext();
        ItemId id = new ItemId(itemId);
        assetService.delete(context, site, id);
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    /**
     * Find asset by query.
     *
     * @param site      site identifier
     * @param query     query
     * @return          list of asset items
     * @throws StudioException
     */
    @ApiErrors(
        @ApiError(code = 400, reason = "Bad request")
    )
    @ApiModel(type = Item.class, collection = true)
    @RequestMapping(value = "/find/{site}",
                    params = { "query" },
                    method = RequestMethod.GET)
    @ResponseBody
    public List<Item> findBy(
            @ApiParam(name = "site", required = true, value = "String")
            @PathVariable String site,

            @ApiParam(name = "query", required = true, value = "String")
            @RequestParam(value = "query") String query
    ) throws StudioException {

        Context context = RestControllerUtils.createMockContext();
        return assetService.findBy(context, site, query);
    }
}
