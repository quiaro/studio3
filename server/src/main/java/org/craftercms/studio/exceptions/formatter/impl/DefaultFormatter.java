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

package org.craftercms.studio.exceptions.formatter.impl;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

/**
 * Fallback implementation for Message formatting.
 * <b>{@link AbstractExceptionFormatter#generateDetailMessage(Exception)}
 * will allways return null, and HTTP code will be 500</b>
 */
public class DefaultFormatter extends AbstractExceptionFormatter {

    public DefaultFormatter() {
        super(Exception.class);
        setHttpResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Override
    protected JSONObject generateDetailMessage(final Exception ex) throws JSONException {
        return null;
    }
}
