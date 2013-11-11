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

import org.craftercms.studio.api.content.AssetService;
import org.craftercms.studio.commons.exception.StudioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Asset controller.
 * Defines restful services for assets.
 *
 * @author Dejan Brkic
 */
@Controller
@RequestMapping(value = "/api/1/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @RequestMapping(value = "/create/{site}")
    public void create(@PathVariable String site, @RequestParam String path, @RequestParam MultipartFile file) throws
        StudioException{

        /** TODO:
         * Provide security context
         * Extract metadata
         */

        InputStream contentStream = null;
        try {
            contentStream = file.getInputStream();
        } catch (IOException e) {
            throw new StudioException("Error getting content from multipart request") {};
        }
        assetService.create(null, site, path, null, contentStream);
    }
}
