package org.craftercms.studio.exceptions.formatter.impl;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

/**
 * Formats {@link ItemNotFoundException} to a nice JSON
 * that will return HTTP status 404 (not found).
 */
public class ItemNotFoundFormatter extends AbstractExceptionFormatter{

    /**
     * Register ItemNotFound Exception Mapping.
     * sets Http response code as 404 (not found)
     */
    public ItemNotFoundFormatter() {
        super(ItemNotFoundException.class);
        setHttpResponseCode(HttpStatus.NOT_FOUND.value());
    }

    /**
     * Since a lot of things can be not found ,a detail message will be more complex.
     * using default {@link AbstractExceptionFormatter#getFormattedMessage(Exception)}
     * to just use ItemNotFoundException message its much simple.
     * **/
    @Override
    protected JSONObject generateDetailMessage(final Exception ex) throws JSONException {
        return null;
    }
}
