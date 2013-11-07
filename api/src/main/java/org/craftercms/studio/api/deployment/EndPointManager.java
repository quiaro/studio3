/*
 * Copyright (C) 2007-2013 Crafter Software Corporation.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.craftercms.studio.api.deployment;

import java.util.List;
import java.util.Map;

import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Endpoint;

/**
 * Endpoint Manager.
 * Install, remove, list and create Endpoints.
 *
 * @author Sumer Jabri
 */
public interface EndpointManager {
    /**
     * Create an Endpoint.
     *
     * @param context  context
     * @param endpoint Endpoint to install
     */
    void create(Context context, Endpoint endpoint);

    /**
     * Get an Endpoint.
     *
     * @param context  context
     * @param endpointId Endpoint to retrieve
     */
    Endpoint get(Context context, String endpointId);

    /**
     * List Endpoints.
     *
     * @param context context
     * @param filters filters
     * @return list of Endpoints
     */
    List<Endpoint> list(Context context, Map<String, Object> filters);

    /**
     * Update an Endpoint.
     *
     * @param context  context
     * @param endpoint Endpoint to update
     */
    void update(Context context, Endpoint endpoint);

    /**
     * Remove Endpoint
     *
     * @param context    context
     * @param endpointId id of the endpoint to remove
     */
    void delete(Context context, String endpointId);
}
