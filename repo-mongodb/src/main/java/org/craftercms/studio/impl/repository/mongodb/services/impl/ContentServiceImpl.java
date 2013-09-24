/*
 * Copyright (C) 2007-2013 Crafter Software Corporation.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.craftercms.studio.impl.repository.mongodb.services.impl;

import java.io.InputStream;
import java.util.List;

import org.craftercms.studio.api.NotImplementedException;
import org.craftercms.studio.api.content.ContentService;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.Tree;
import org.craftercms.studio.commons.filter.Filter;
import org.craftercms.studio.impl.repository.mongodb.services.NodeService;

/**
 * Mongo DB implementation of ContentService.
 */
public class ContentServiceImpl implements ContentService {
    /**
     * Node Service Impl.
      */
    private NodeService nodeService;

    @Override
    public String create(final String ticket, final String site, final String path, final InputStream content) {

        throw new NotImplementedException("Not implemented yet");
    }

    @Override
    public String create(final String ticket, final String site, final String path) {
        throw new NotImplementedException("Not implemented yet");
    }

    @Override
    public InputStream read(final String ticket, final String contentId) {
        return null;
    }

    @Override
    public void update(final String ticket, final String contentId, final InputStream content) {
        throw new NotImplementedException("Not implemented yet");
    }

    @Override
    public void delete(final String ticket, final String contentId) {
        throw new NotImplementedException("Not implemented yet");
    }

    @Override
    public Tree<Item> getChildren(final String ticket, final String site, final String contentId, final int depth,
                                  final List<Filter> filters) {
        throw new NotImplementedException("Not implemented yet");
    }

    @Override
    public void move(final String ticket, final String site, final String sourceId, final String destinationId,
                     final boolean includeChildren) {
        throw new NotImplementedException("Not implemented yet");
    }

    @Override
    public List<String> getSites(final String ticket) {
        throw new NotImplementedException("Not implemented yet");
    }


    public void setNodeServiceImpl(NodeService nodeService) {
        this.nodeService = nodeService;
    }
}
