package org.craftercms.studio.test.web.suppport.message;


import org.craftercms.studio.web.support.message.ExceptionMessageFormatter;
import org.craftercms.studio.web.support.message.impl.AbstractExceptionMessageFormatter;
import org.craftercms.studio.web.support.message.impl.DefaultMessageFormatter;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.*;



/**
 * Test Default Message Formatter.
 */
public class DefaultMessageFormatterTest {

    private static final String TEST_MESSAGE = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
            "Morbi iaculis mattis nulla, eget pretium turpis.";

    /**
     * Default HTTP Should Not return null
     *
     * @throws Exception
     */
    @Test
    public void testFormatMessage() throws Exception {
        ExceptionMessageFormatter defaultMessageFormatter = new DefaultMessageFormatter();
        assertNotNull(defaultMessageFormatter.getFormattedMessage(new Exception(TEST_MESSAGE)));
    }

    /**
     * Default HTTP Error should be 500
     */
    @Test
    public void testHttpStatus() {
        ExceptionMessageFormatter defaultMessageFormatter = new DefaultMessageFormatter();
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
        ExceptionMessageFormatter defaultMessageFormatter = new DefaultMessageFormatter();
        JSONObject jsonResponse = new JSONObject(defaultMessageFormatter.getFormattedMessage(new Exception(TEST_MESSAGE)));
        jsonResponse.get(AbstractExceptionMessageFormatter.JSON_DETAIL_MESSAGE_KEY);
    }
}
