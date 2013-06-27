package org.craftercms.studio3.test.web.suppport.message;

import java.util.Arrays;
import java.util.List;

import javolution.util.FastList;
import org.craftercms.studio3.web.exceptions.ValidationException;
import org.craftercms.studio3.web.support.message.ExceptionMessageFormatter;
import org.craftercms.studio3.web.support.message.impl.AbstractExceptionMessageFormatter;
import org.craftercms.studio3.web.support.message.impl.ValidationExceptionFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.validation.ObjectError;

import static org.junit.Assert.*;

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
            ExceptionMessageFormatter messageFormatter = new ValidationExceptionFormatter();

            ValidationException ex = new ValidationException("Validation Error", OBJECT_ERROR_LIST);
            new JSONObject(messageFormatter.getFormattedMessage(ex));
        } catch (JSONException ex) {
            fail("Unable to parse response for ValidationExceptionFormatter#getFormatMessage due a exception \n"
                    + ex.toString());

        }
    }

    @Test
    public void testGenerateDetailMessageIsAnArray() throws Exception {
        ExceptionMessageFormatter messageFormatter = new ValidationExceptionFormatter();
        ValidationException ex = new ValidationException("Validation Error", OBJECT_ERROR_LIST);
        new JSONObject(messageFormatter.getFormattedMessage(ex)).
                getJSONArray(AbstractExceptionMessageFormatter.JSON_DETAIL_MESSAGE_KEY);

    }

    @Test
    public void testGenerateDetailMessageArrayLength() throws Exception {
        ExceptionMessageFormatter messageFormatter = new ValidationExceptionFormatter();
        ValidationException ex = new ValidationException("Validation Error", OBJECT_ERROR_LIST);
        final JSONArray jsonArray = new JSONObject(messageFormatter.getFormattedMessage(ex)).
                getJSONArray(AbstractExceptionMessageFormatter.JSON_DETAIL_MESSAGE_KEY);
        assertEquals(jsonArray.length(), TOTAL_FIELDS);
    }

    @Test
    public void testGenerateDetailMessageContents() throws Exception {
        ExceptionMessageFormatter messageFormatter = new ValidationExceptionFormatter();
        ValidationException ex = new ValidationException("Validation Error", OBJECT_ERROR_LIST);
        final JSONArray jsonArray = new JSONObject(messageFormatter.getFormattedMessage(ex)).
                getJSONArray(AbstractExceptionMessageFormatter.JSON_DETAIL_MESSAGE_KEY);
        for (int i = 0; i < jsonArray.length(); i++) {
            String detail=jsonArray.getJSONObject(i).getString(ValidationExceptionFormatter.JSON_DETAIL_FIELD_KEY);
            String message=jsonArray.getJSONObject(i).getString(ValidationExceptionFormatter.JSON_DETAIL_MESSAGE_KEY);
            assertEquals(detail,OBJECT_ERROR_LIST.get(i).getObjectName());
            assertEquals(message,OBJECT_ERROR_LIST.get(i).getDefaultMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCanSendAnything() throws Exception {
        ExceptionMessageFormatter messageFormatter = new ValidationExceptionFormatter();
        Exception ex = new Exception("Not a Validation");
        messageFormatter.getFormattedMessage(ex);
    }

    @Test()
    public void testListOfErrorsEmpty() throws Exception {
        ExceptionMessageFormatter messageFormatter = new ValidationExceptionFormatter();
        ValidationException ex = new ValidationException("Validation Error", new FastList<ObjectError>());
        messageFormatter.getFormattedMessage(ex);
    }


}
