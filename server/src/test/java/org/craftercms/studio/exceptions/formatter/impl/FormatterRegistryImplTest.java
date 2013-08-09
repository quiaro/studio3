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

import org.craftercms.studio.exceptions.formatter.FormatterRegistry;
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

/**
 * Unit tests for FormatterRegistryImpl.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring/messageFormatting-studio3-web-context.xml")
public class FormatterRegistryImplTest {

    public static final String ERROR = "Error";
    @Autowired
    FormatterRegistry manager;

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
    class TestFormatter extends AbstractExceptionFormatter {

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
