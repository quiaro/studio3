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

package org.craftercms.studio3.test.web.suppport.message;


import org.craftercms.studio3.web.support.message.AbstractExceptionMessageFormatter;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class AbstractExceptionMessageFormatterTest {
    private static final String TEST_MESSAGE = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                                      "Morbi iaculis mattis nulla, eget pretium turpis.";
    private static final int NON_EXISTAND_HTTP_CODE=666;


    /**
     * Test validation of
     * <ul>
     *     <li>Message can't be null</li>
     *     <li>Message can't be empty</li>
     *     <li>Given http status code int is a valid status code</li>
     * </ul>
     */
    @Test(expected = IllegalStateException.class)
    public void testThatRequireParamsAreValid(){
        new Test1AbstractExceptionMessageFormatter(null, HttpStatus.BAD_REQUEST.value()).getFormatMessage(new Exception());
        new Test1AbstractExceptionMessageFormatter("",HttpStatus.OK.value());
        new Test1AbstractExceptionMessageFormatter(TEST_MESSAGE, NON_EXISTAND_HTTP_CODE).getFormatMessage(new Exception());
    }

    /**
     * Test that the generated response is a valid JSON
     * Object
     */
    @Test
    public void testThatReturnStringISJSON(){
        final AbstractExceptionMessageFormatter formater =
                               new Test1AbstractExceptionMessageFormatter(TEST_MESSAGE, HttpStatus.BAD_REQUEST.value());
       final String jsonString=formater.getFormatMessage(new Exception());
        try {
            JSONObject jsonObject=new JSONObject(jsonString);
        }catch (JSONException ex){
            Assert.fail(String.format("The return String %s is not a valid JSON due %s",jsonString,ex.toString()));
        }
    }

    /**
     * Null Response
     */
    class Test1AbstractExceptionMessageFormatter extends AbstractExceptionMessageFormatter {
        public Test1AbstractExceptionMessageFormatter(String message, int code) {
            setDefaultMessage(message);
            setHttpResponseCode(code);
        }

        @Override
        protected JSONObject generateDetailMessage(Exception exception) throws JSONException {
            return null;
        }
    }
}
