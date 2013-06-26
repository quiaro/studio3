package org.craftercms.studio3.test.web.suppport.message;

import org.craftercms.studio3.web.support.message.MessageFormatterManager;
import org.craftercms.studio3.web.support.message.impl.AbstractExceptionMessageFormatter;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring/messageFormatting-studio3-web-context.xml")
public class MessageFormatterManagerTest {

    public static final String ERROR = "Error";
    @Autowired
    MessageFormatterManager manager;

    @Before
    public void testThatManagerIsNotNull() {
        assertNotNull(manager);
    }

    @Test
    public void testThatRegisterFormatterOnRuntime() {
        manager.registerFormatter(IllegalArgumentException.class, new TestFormatter());
        assertNotNull(manager.getFormatter(IllegalArgumentException.class));
        assertEquals(TestFormatter.class, manager.getFormatter(IllegalArgumentException.class).getClass());
    }


    @Test
    public void testThatReturnDefault() {
        manager.registerFormatter(IllegalArgumentException.class, new TestFormatter());
        assertNotNull(manager.getFormatter(DummyException.class));
    }

    @Test
    public void testThatNullReturnSomething() {
        manager.registerFormatter(IllegalArgumentException.class, new TestFormatter());
        assertNotNull(manager.getFormatter(null));
    }

    @Test
    public void testThatResultOfFormattedMessageIsSame() {
        manager.registerFormatter(IllegalArgumentException.class, new TestFormatter());
        final IllegalArgumentException testException = new IllegalArgumentException(ERROR);
        assertEquals(manager.getFormattedMessage(testException)
                ,manager.getFormatter(IllegalArgumentException.class).getFormattedMessage(testException));
    }

//=========================== Support Test classes ====================================

    /**
     * Internal Class to test on Runtime Register of Exceptions.
     */
    class TestFormatter extends AbstractExceptionMessageFormatter {

        protected TestFormatter() {
            super(IllegalArgumentException.class);
            setHttpResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        @Override
        protected JSONObject generateDetailMessage(final Exception ex) throws JSONException {
            return null;
        }
    }

    class DummyException extends Exception{





    }
}
