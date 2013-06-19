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

package org.craftercms.studio3.web.support.message;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

/**
 * Base class for all Exception message formatter.
 * This class will ensure that all subclasses return
 * the base JSON response that is
 * <code>
 * {
 * "code":XXX,
 * "message":"Meaningfully message.
 * }
 * </code>
 */
public abstract class AbstractExceptionMessageFormatter {
    /**
     * JSON key for code
     */
    private static final String JSON_CODE_KEY = "code";
    /**
     * JSON key for message
     */
    private static final String JSON_MESSAGE_KEY = "message";
    /**
     * Response Code that will be send to the client
     * must compliance to http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html
     */
    private int httpResponseCode;
    /**
     * Default Message to be send.
     */
    private String defaultMessage;
    private Logger log = LoggerFactory.getLogger(AbstractExceptionMessageFormatter.class);

    /**
     * Makes sure that you always have a code and message.
     *
     * @param httpResponseCode Http code must compliance to http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html
     * @param defaultMessage   Meaningfully message to be send to the client.
     */
    public AbstractExceptionMessageFormatter(int httpResponseCode, String defaultMessage) {
        this.httpResponseCode = httpResponseCode;
        this.defaultMessage = defaultMessage;
    }

    /**
     * Generates the message using base info httpResponseCode and defaultMessage.
     * calls {@link AbstractExceptionMessageFormatter#generateDetailMessage} for get any detail if needed.
     *
     * @return A String representation of a JSON Object to be return.<br/>
     *         or <B>null</B> if the JSON could no be generate due a error.
     */
    public final String getFormatMessage() {
        final JSONObject returnMessage = new JSONObject();
        try {
            returnMessage.put(JSON_CODE_KEY, this.httpResponseCode);
            returnMessage.put(JSON_MESSAGE_KEY, this.defaultMessage);
            final JSONObject detailsObject = generateDetailMessage();
            if (detailsObject == null && detailsObject.length() <= 0) {
                this.log.debug("Detail Message is empty or null, ignoring");
            } else {
                final Iterator<String> keys = detailsObject.keys();
                while (keys.hasNext()) {
                    final String key = keys.next();
                    if (key.equals(JSON_CODE_KEY) || key.equals(JSON_MESSAGE_KEY))
                        this.log.debug("Detail message has either {} or {} they are not allow, ignoring them",
                                JSON_CODE_KEY, JSON_MESSAGE_KEY);
                    else {
                        returnMessage.put(key, detailsObject.get(key));
                    }
                }
            }
            return returnMessage.toString();
        } catch (JSONException ex) {
            this.log.error("Unable to format Exception", ex);
            return null;
        }

    }

    /**
     * Generates the any extra information that will be append to the
     * end message.
     *
     * @return A JSONObject that will be append to standard out put.
     *         if contains either "code" or "message" as keys they will be
     *         removed.If return empty or null will be ignore.
     * @throws JSONException if a error generating the exception happened.
     */
    protected abstract JSONObject generateDetailMessage() throws JSONException;

}
