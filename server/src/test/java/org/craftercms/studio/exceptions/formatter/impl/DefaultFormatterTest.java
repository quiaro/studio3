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

import org.craftercms.studio.exceptions.formatter.ExceptionFormatter;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for DefaultFormatter.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public class DefaultFormatterTest {


    private static final String TEST_MESSAGE = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
            "Morbi iaculis mattis nulla, eget pretium turpis.";

    /**
     * Default HTTP Should Not return null
     *
     * @throws Exception
     */
    @Test
    public void testFormatMessage() throws Exception {
        ExceptionFormatter defaultMessageFormatter = new DefaultFormatter();
        assertNotNull(defaultMessageFormatter.getFormattedMessage(new Exception(TEST_MESSAGE)));
    }

    /**
     * Default HTTP Error should be 500
     */
    @Test
    public void testHttpStatus() {
        ExceptionFormatter defaultMessageFormatter = new DefaultFormatter();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), defaultMessageFormatter.getHttpResponseCode());
    }

    /**
     * Details should be null , @{link JSONObject} will throw a exception is a key is not
     * found.
     *
     * @throws Exception
     */
    @Test(expected = JSONException.class)
    public void testGenerateDetailMessage() throws Exception {
        ExceptionFormatter defaultMessageFormatter = new DefaultFormatter();
        JSONObject jsonResponse = new JSONObject(defaultMessageFormatter.getFormattedMessage(new Exception(TEST_MESSAGE)));
        jsonResponse.get(AbstractExceptionFormatter.JSON_DETAIL_MESSAGE_KEY);
    }
}
