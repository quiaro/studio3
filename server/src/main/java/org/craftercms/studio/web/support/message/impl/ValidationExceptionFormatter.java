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

package org.craftercms.studio.web.support.message.impl;

import org.craftercms.studio.web.exceptions.ValidationException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;

/**
 * Valitation Exception  Implementation for AbstractExceptionMessageFormatter.
 */
public class ValidationExceptionFormatter extends AbstractExceptionMessageFormatter {
    /**
     * Json key for field name.
     */
    public static final String JSON_DETAIL_FIELD_KEY = "field";
    /**
     * Json key for detail.
     */
    public static final String JSON_DETAIL_MESSAGE_KEY = "detail";

    public ValidationExceptionFormatter() {
        super(ValidationException.class);
        setHttpResponseCode(HttpStatus.BAD_REQUEST.value());
    }

    @Override
    public JSONObject generateDetailMessage(Exception ex) throws JSONException {
        final JSONObject returnJson = new JSONObject();
        if (ex instanceof ValidationException) {
            final ValidationException validationException = (ValidationException) ex;
            if (!validationException.getErrors().isEmpty()) {
                final JSONArray jsonArray = new JSONArray();
                for (ObjectError o : validationException.getErrors()) {
                    final JSONObject errorJson = new JSONObject();
                    errorJson.put(JSON_DETAIL_FIELD_KEY, o.getObjectName());
                    errorJson.put(JSON_DETAIL_MESSAGE_KEY, o.getDefaultMessage());
                    jsonArray.put(errorJson);
                }
                returnJson.put(AbstractExceptionMessageFormatter.JSON_DETAIL_MESSAGE_KEY, jsonArray);
            }
            return returnJson;
        }else {
            throw new IllegalArgumentException(String.format("The Given Exception (%s) is not a valid %s instance",
                                                         ex.getClass().getCanonicalName(),
                                                        this.getClass().getCanonicalName()));
        }
    }
}

