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

import javax.validation.Valid;

import org.craftercms.studio.api.content.AssetService;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.commons.exception.NotImplementedException;
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
 * Asset controller.
 * Defines restful services for assets.
 *
 * @author Dejan Brkic
 */
@Controller
@RequestMapping(value = "/api/1/content/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;

    /**
     * Create an asset file in the repository.
     *
     * @param site              site
     * @param destinationPath   path to write asset to
     * @param fileName          asset file name
     * @param file              asset content
     * @param mimeType          asset mime type
     * @return item id of newly created asset item in repository
     * @throws StudioException
     */
    @RequestMapping(value = "/create/{site}",
        params = {"site", "destination_path", "file_name", "file", "mime_type"},
        method = RequestMethod.POST
    )
    @ResponseBody
    public ItemId create(@PathVariable String site,
                       @Valid @RequestParam(value = "destination_path") String destinationPath,
                       @Valid @RequestParam(value = "file_name") String fileName,
                       @RequestParam(value = "file") MultipartFile file,
                       @RequestParam(value = "mime_type") String mimeType)
        throws StudioException {

        /** TODO:
         * Provide security context
         */

        InputStream contentStream = null;
        try {
            contentStream = file.getInputStream();
        } catch (IOException e) {
            throw new StudioException("Error getting content from multipart request") {};
        }
        return assetService.create(null, site, destinationPath, fileName, contentStream, mimeType);
    }

    /**
     * Read asset content for given item id.
     * @param site      site
     * @param itemId    asset item id
     * @return asset content
     * @throws StudioException
     */
    @RequestMapping(value = "/read/{site}",
                    params = { "item_id" },
                    method = RequestMethod.GET
    )
    @ResponseBody
    public InputStream read(@PathVariable String site,
                            @Valid @RequestParam(value = "item_id", required = true) String itemId)
        throws StudioException {

        /** TODO:
         * Provide security context
         */

        return assetService.read(null, itemId);
    }

    /**
     * Update asset content for given item id.
     *
     * @param site      site
     * @param itemId    asset item id
     * @param file      asset content
     * @throws StudioException
     */
    @RequestMapping(value = "/update/{site}",
                    params = { "item_id", "file" },
                    method = RequestMethod.POST
    )
    public void update(@PathVariable String site,
                       @Valid @RequestParam(value = "item_id", required = true) String itemId,
                       @RequestParam(value = "file") MultipartFile file) throws StudioException {
        throw new NotImplementedException("Not implemented yet!");
    }

    /**
     * Delete asset for given item id.
     *
     * @param site      site
     * @param itemId    asset item id
     * @throws StudioException
     */
    @RequestMapping(value = "/delete/{site}",
                    params = { "item_id" },
                    method = RequestMethod.POST
    )
    public void delete(@PathVariable String site,
                       @Valid @RequestParam(value = "item_id", required = true) String itemId)
        throws StudioException {
        throw new NotImplementedException("Not implemented yet!");
    }
}
