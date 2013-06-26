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

package org.craftercms.studio3.web.support.message.impl;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.craftercms.studio3.web.support.message.ExceptionMessageFormatter;
import org.craftercms.studio3.web.support.message.MessageFormatterManager;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;


/**
 * Base class for all Exception message formatter.
 * This class will ensure that all subclasses return
 * the base JSON response that is:
 * <code>
 * {
 * "code":XXX,
 * "message":"Meaningful message"
 * }
 * </code>
 * <b>The meaningful message is call using {@link Exception#getMessage()}</b>
 */
public abstract class AbstractExceptionMessageFormatter implements ExceptionMessageFormatter {
    /**
     * JSON key for code.
     */
    public static final String JSON_CODE_KEY = "code";
    /**
     * JSON key for message.
     */
    public static final String JSON_MESSAGE_KEY = "message";
    /**
     * JSO key for any details.
     */
    public static final String JSON_DETAIL_MESSAGE_KEY = "details";
    /**
     * Response Code that will be send to the client
     * must compliance to http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html .
     */
    private int httpResponseCode;
    private Class<? extends Exception> exceptionClass;
    /**
     * Message Formatter.
     */
    private MessageFormatterManager messageFormatterManager;
    private Logger log = LoggerFactory.getLogger(AbstractExceptionMessageFormatter.class);

    protected AbstractExceptionMessageFormatter(Class<? extends Exception> exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    /**
     * Generates the message using base info httpResponseCode and defaultMessage.
     * calls {@link AbstractExceptionMessageFormatter#generateDetailMessage} for get any detail if needed.
     *
     * @return A String representation of a JSON Object to be return.<br/>
     *         or <B>null</B> if the JSON could no be generate due a error.
     */
    @Override
    public String getFormattedMessage(Exception exception) {
        // Checks that all is ok
        validateDefaultMessageParams(exception);
        String returnMsg = null;
        try {
            final JSONObject jsonToReturn = new JSONObject();
            jsonToReturn.put(JSON_CODE_KEY, this.httpResponseCode);
            jsonToReturn.put(JSON_MESSAGE_KEY, exception.getMessage());
            final JSONObject detailsObject = generateDetailMessage(exception);
            if (detailsObject == null) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Detail Message is null, ignoring");
                }
            } else if (detailsObject.length() == 0) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Detail Message is empty, ignoring");
                }
            } else {
                final Iterator keys = detailsObject.keys();
                while (keys.hasNext()) {
                    final String key = keys.next().toString();
                    if (key.equals(JSON_CODE_KEY) || key.equals(JSON_MESSAGE_KEY)) {
                        if (this.log.isDebugEnabled()) {
                            this.log.debug("Detail message has either {} or {} they are not allow, ignoring them",
                                    JSON_CODE_KEY, JSON_MESSAGE_KEY);
                        }
                    } else {
                        jsonToReturn.put(key, detailsObject.get(key));
                    }
                }
            }
            returnMsg = jsonToReturn.toString();
        } catch (JSONException ex) {
            this.log.error("Unable to format Exception", ex);
        }
        return returnMsg;
    }

    /**
     * Validates that message and http are
     * not null (and >200) and that message is not empty.
     *
     * @throws IllegalStateException if either defaultMessage or httpResponseCode are not valid.
     */
    private void validateDefaultMessageParams(Exception ex) {
        if (StringUtils.isEmpty(ex.getMessage())) {
            throw new IllegalStateException("Default message can't be null or empty");
        }
        try {
            HttpStatus.valueOf(this.httpResponseCode);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException(String.format("%s is not a valid Http Response Code",
                    this.httpResponseCode));
        }

    }

    /**
     * Generates the any extra information that will be append to the
     * end message.
     * Key of that JSON MUST be call
     *
     * @param ex Exception used to generate detail message.
     * @return A JSONObject that will be append to standard out put.
     *         if contains either "code" or "message" as keys they will be
     *         removed.If return empty or null will be ignore.
     * @throws org.json.JSONException if a error generating the exceptionClass happened.
     */
    protected abstract JSONObject generateDetailMessage(Exception ex) throws JSONException;

    public void register() {
        this.messageFormatterManager.registerFormatter(this.exceptionClass, this);
    }

    @Override
    public int getHttpResponseCode() {
        return this.httpResponseCode;
    }

    protected void setHttpResponseCode(int httpResponseCode) {
        this.httpResponseCode = httpResponseCode;
    }

    public void setMessageFormatterManager(MessageFormatterManagerImpl messageFormatterManager) {
        this.messageFormatterManager = messageFormatterManager;
    }
}
