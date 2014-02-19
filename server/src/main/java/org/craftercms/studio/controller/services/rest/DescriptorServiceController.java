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

import org.craftercms.studio.api.content.DescriptorService;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.commons.exception.StudioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    public Item create(String site, String contentTypeId, String parentId, String fileName,
                       InputStream content, Map<String, String> properties) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    public Item create(String site, String contentTypeId, String parentId, String fileName,
                       String content, Map<String, String> properties) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    public Item duplicate(String site, String itemId, String parentId,
                          String fileName) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    public Item move(String site, String itemId, String parentId,
                     String fileName) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    public Item read(String site, String itemId) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    public Item update(String site, String itemId, InputStream content, Map<String,
        String> properties) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    public Item update(String site, String itemId, String content, Map<String,
        String> properties) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    public void delete(String site, String itemId) throws StudioException {

    }

    public List<Item> findBy(String site, String query) throws StudioException {
        throw new StudioException(StudioException.ErrorCode.NOT_IMPLEMENTED);
    }

    public void setDescriptorService(final DescriptorService descriptorService) {
        this.descriptorService = descriptorService;
    }
}
