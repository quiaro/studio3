package org.craftercms.studio3.web.support.message;

import org.json.JSONException;
import org.json.JSONObject;

public interface ExceptionMessageFormatter {
    String getFormatMessage(Exception exception);

    /**
     * Generates the any extra information that will be append to the
     * end message.
     *
     * @param ex Exception used to generate detail message.
     * @return A JSONObject that will be append to standard out put.
     *         if contains either "code" or "message" as keys they will be
     *         removed.If return empty or null will be ignore.
     * @throws org.json.JSONException if a error generating the exception happened.
     */
    JSONObject generateDetailMessage(Exception ex) throws JSONException;
}
