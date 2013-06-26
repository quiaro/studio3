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


import org.craftercms.studio3.web.support.message.impl.AbstractExceptionMessageFormatter;
import org.craftercms.studio3.web.support.message.ExceptionMessageFormatter;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.*;

public class AbstractExceptionMessageFormatterTest {
    private static final String TEST_MESSAGE = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                                      "Morbi iaculis mattis nulla, eget pretium turpis.";

    private static final int NON_EXIST_HTTP_CODE=666;
    private static final int DEFAULT_LENGTH_OF_JSON_MSG = 2;

    /**
     * Test validation of
     * <ul>
     *     <li>Message can't be null</li>
     * </ul>
     */
    @Test(expected = IllegalStateException.class)
    public void testThatRequireParamsAreValid(){
        new Test1AbstractExceptionMessageFormatter(HttpStatus.BAD_REQUEST.value()).
                                                  getFormatMessage(new Exception(null,null));

    }

    /**
     * Test validation of
     * <ul>
     *     <li>Message can't be empty</li>
     * </ul>
     */
    @Test(expected = IllegalStateException.class)
    public void testThatDefaultMessageValid(){
        new Test1AbstractExceptionMessageFormatter(HttpStatus.OK.value()).getFormatMessage(new Exception(""));
    }


    /**
     * Test validation of
     * <ul>
     *     <li>Given http status code int is a valid status code</li>
     * </ul>
     */
    @Test(expected = IllegalStateException.class)
    public void testThatErrorCodeIsValid(){
        new Test1AbstractExceptionMessageFormatter(NON_EXIST_HTTP_CODE)
                .getFormatMessage(new Exception(TEST_MESSAGE));
    }


    /**
     * Test that the generated response is a valid JSON
     * Object
     */
    @Test
    public void testThatReturnStringISJSON(){
        final ExceptionMessageFormatter formater =
                               new Test1AbstractExceptionMessageFormatter(HttpStatus.BAD_REQUEST.value());
       final String jsonString=formater.getFormatMessage(new Exception(TEST_MESSAGE));
        try {
            new JSONObject(jsonString);
        }catch (JSONException ex){
            fail(String.format("The return String %s is not a valid JSON due %s", jsonString, ex.toString()));
        }
    }

    /**
     * Test Detail Message
     */
    @Test
    public void testThatMessageDetailIsAppendAndGenerated(){
        final ExceptionMessageFormatter formatter=
              new Test1AbstractExceptionMessageFormatter(HttpStatus.BAD_GATEWAY.value());

        try {
            JSONObject json=new JSONObject(formatter.getFormatMessage(new Exception(TEST_MESSAGE)));
            assertTrue(json.get(AbstractExceptionMessageFormatter.JSON_DETAIL_MESSAGE_KEY).equals(TEST_MESSAGE));
        } catch (JSONException e) {
            fail(String.format("Unable to finish testing detail message Formatting due %s",e.toString()));
        }

    }


    /**
     * Test That Http Code and Default Message are
     *  serialize properly.
     */
    @Test
    public void testThatSerializeIsOk(){
        final ExceptionMessageFormatter formatter=
                new Test2AbstractExceptionMessageFormatter(HttpStatus.BAD_GATEWAY.value());

        try {
            JSONObject json=new JSONObject(formatter.getFormatMessage(new Exception(TEST_MESSAGE)));
            assertEquals(json.get(AbstractExceptionMessageFormatter.JSON_CODE_KEY),HttpStatus.BAD_GATEWAY.value());
            assertEquals(json.get(AbstractExceptionMessageFormatter.JSON_MESSAGE_KEY),TEST_MESSAGE);
        } catch (JSONException e) {
            fail(String.format("Unable to finish testing detail message Formatting due %s",e.toString()));
        }

    }



    /**
     * Test if Detail Message is null don't append to final result
     */
    @Test
    public void testThatMessageDetailIsNullDontAppend(){
        final ExceptionMessageFormatter formatter=
                new Test2AbstractExceptionMessageFormatter(HttpStatus.BAD_GATEWAY.value());

        try {
            JSONObject json=new JSONObject(formatter.getFormatMessage(new Exception(TEST_MESSAGE)));
            assertEquals(json.length(),DEFAULT_LENGTH_OF_JSON_MSG);
        } catch (JSONException e) {
            fail(String.format("Unable to finish testing detail message Formatting due %s",e.toString()));
        }

    }


    /**
     * Test if Detail Message is empty don't append to final result
     */
    @Test
    public void testThatMessageDetailIsEmptyDontAppend(){
        final ExceptionMessageFormatter formatter=
                new Test3AbstractExceptionMessageFormatter(HttpStatus.BAD_GATEWAY.value());

        try {
            JSONObject json=new JSONObject(formatter.getFormatMessage(new Exception(TEST_MESSAGE)));
            assertEquals(json.length(),DEFAULT_LENGTH_OF_JSON_MSG);
        } catch (JSONException e) {
            fail(String.format("Unable to finish testing detail message Formatting due %s",e.toString()));
        }
    }


    /**
     * Test if Detail Message is empty don't append to final result
     */
    @Test
    public void testThatMessageDetailDontOverride(){
        final ExceptionMessageFormatter formatter=
                new Test4AbstractExceptionMessageFormatter(HttpStatus.BAD_GATEWAY.value());
        try {
            JSONObject json=new JSONObject(formatter.getFormatMessage(new Exception(TEST_MESSAGE)));
            assertEquals(json.get(AbstractExceptionMessageFormatter.JSON_CODE_KEY),HttpStatus.BAD_GATEWAY.value());
            assertEquals(json.get(AbstractExceptionMessageFormatter.JSON_MESSAGE_KEY),TEST_MESSAGE);
        } catch (JSONException e) {
            fail(String.format("Unable to finish testing detail message Formatting due %s",e.toString()));
        }
    }

//======================================== Test Implementations ===============================================
    /**
     *  Tests
     *  <ul>
     *      <li>
     *          Return of {@link AbstractExceptionMessageFormatter#getFormatMessage(Exception)} is JSON
     *      </li>
     *      <li>
     *           Message and Http Code are valid
     *      </li>
     *      <li>
     *          Detail Message Implementation is been append to the final JSON
     *      </li>
     *  </ul>
     */
    class Test1AbstractExceptionMessageFormatter extends AbstractExceptionMessageFormatter {
        public Test1AbstractExceptionMessageFormatter(int code) {
            super(Exception.class);
            setHttpResponseCode(code);
        }


        @Override
        protected JSONObject generateDetailMessage(Exception exception) throws JSONException {
           JSONObject obj=new JSONObject();
           obj.put(JSON_DETAIL_MESSAGE_KEY,exception.getMessage());
           return obj;
        }
    }

    /**
     *  Tests
     *  <ul>
     *      <li>
     *          If Detail message is null don't append it to final result
     *      </li>
     *  </ul>
     */
    class Test2AbstractExceptionMessageFormatter extends AbstractExceptionMessageFormatter {
        public Test2AbstractExceptionMessageFormatter(int code) {
            super(Exception.class);
            setHttpResponseCode(code);
        }

        @Override
        protected JSONObject generateDetailMessage(Exception exception) throws JSONException {
           return null;
        }
    }

    /**
     *  Tests
     *  <ul>
     *      <li>
     *          If Detail message is null don't append it to final result
     *      </li>
     *  </ul>
     */
    class Test3AbstractExceptionMessageFormatter extends AbstractExceptionMessageFormatter {
        public Test3AbstractExceptionMessageFormatter(int code) {
            super(Exception.class);
            setHttpResponseCode(code);
        }

        @Override
        protected JSONObject generateDetailMessage(Exception exception) throws JSONException {
            return new JSONObject();
        }
    }

    /**
     *  Tests
     *  <ul>
     *      <li>
     *          Detail Message should not override httpcode or defaultMessage
     *      </li>
     *  </ul>
     */
    class Test4AbstractExceptionMessageFormatter extends AbstractExceptionMessageFormatter {
        public Test4AbstractExceptionMessageFormatter(int code) {
            super(Exception.class);
            setHttpResponseCode(code);
        }

        @Override
        protected JSONObject generateDetailMessage(Exception exception) throws JSONException {
            final JSONObject toReturn= new JSONObject();
            toReturn.put(AbstractExceptionMessageFormatter.JSON_CODE_KEY,HttpStatus.OK.value());
            toReturn.put(AbstractExceptionMessageFormatter.JSON_MESSAGE_KEY,exception.getMessage());
            return toReturn;
        }
    }
}
