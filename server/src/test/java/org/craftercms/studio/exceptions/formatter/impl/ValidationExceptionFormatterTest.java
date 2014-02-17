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

package org.craftercms.studio.exceptions.formatter.impl;

import javolution.util.FastList;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.exceptions.ValidationException;
import org.craftercms.studio.exceptions.formatter.ExceptionFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.validation.ObjectError;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Unit tests for ValidationExceptionFormatter.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public class ValidationExceptionFormatterTest {

    private static final int TOTAL_FIELDS = 3;
    private static final List<ObjectError> OBJECT_ERROR_LIST = Arrays.asList(new ObjectError("field1", "Is Empty"),
            new ObjectError("field2",
                    "Is not a valid Date"),
            new ObjectError("field3",
                    "Is not a email Address")
    );

    @Test
    public void testGenerateDetailMessageIsValid() throws Exception {
        try {
            ExceptionFormatter messageFormatter = new ValidationExceptionFormatter();

            ValidationException ex = new ValidationException(StudioException.ErrorCode.SYSTEM_ERROR, OBJECT_ERROR_LIST);
            new JSONObject(messageFormatter.getFormattedMessage(ex));
        } catch (JSONException ex) {
            fail("Unable to parse response for ValidationExceptionFormatter#getFormatMessage due a exception \n"
                    + ex.toString());

        }
    }

    @Test
    public void testGenerateDetailMessageIsAnArray() throws Exception {
        ExceptionFormatter messageFormatter = new ValidationExceptionFormatter();
        ValidationException ex = new ValidationException(StudioException.ErrorCode.SYSTEM_ERROR, OBJECT_ERROR_LIST);
        new JSONObject(messageFormatter.getFormattedMessage(ex)).
                getJSONArray(AbstractExceptionFormatter.JSON_DETAIL_MESSAGE_KEY);

    }

    @Test
    public void testGenerateDetailMessageArrayLength() throws Exception {
        ExceptionFormatter messageFormatter = new ValidationExceptionFormatter();
        ValidationException ex = new ValidationException(StudioException.ErrorCode.SYSTEM_ERROR, OBJECT_ERROR_LIST);
        final JSONArray jsonArray = new JSONObject(messageFormatter.getFormattedMessage(ex)).
                getJSONArray(AbstractExceptionFormatter.JSON_DETAIL_MESSAGE_KEY);
        assertEquals(jsonArray.length(), TOTAL_FIELDS);
    }

    @Test
    public void testGenerateDetailMessageContents() throws Exception {
        ExceptionFormatter messageFormatter = new ValidationExceptionFormatter();
        ValidationException ex = new ValidationException(StudioException.ErrorCode.SYSTEM_ERROR, OBJECT_ERROR_LIST);
        final JSONArray jsonArray = new JSONObject(messageFormatter.getFormattedMessage(ex)).
                getJSONArray(AbstractExceptionFormatter.JSON_DETAIL_MESSAGE_KEY);
        for (int i = 0; i < jsonArray.length(); i++) {
            String detail=jsonArray.getJSONObject(i).getString(ValidationExceptionFormatter.JSON_DETAIL_FIELD_KEY);
            String message=jsonArray.getJSONObject(i).getString(ValidationExceptionFormatter.JSON_DETAIL_MESSAGE_KEY);
            assertEquals(detail,OBJECT_ERROR_LIST.get(i).getObjectName());
            assertEquals(message,OBJECT_ERROR_LIST.get(i).getDefaultMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCanSendAnything() throws Exception {
        ExceptionFormatter messageFormatter = new ValidationExceptionFormatter();
        Exception ex = new Exception("Not a Validation");
        messageFormatter.getFormattedMessage(ex);
    }

    @Test()
    public void testListOfErrorsEmpty() throws Exception {
        ExceptionFormatter messageFormatter = new ValidationExceptionFormatter();
        ValidationException ex = new ValidationException(StudioException.ErrorCode.SYSTEM_ERROR, new FastList<ObjectError>());
        messageFormatter.getFormattedMessage(ex);
    }
}
