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
import java.util.Map;
import javolution.util.FastMap;
import org.craftercms.studio3.web.support.message.AbstractExceptionMessageFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages all message formatter classes.
 */
public class MessageFormatterManagerImpl implements org.craftercms.studio3.web.support.message.MessageFormatterManager {
    /**
     * Collection of all Message Formatter.
     */
    private Map<Class<? extends Exception>, AbstractExceptionMessageFormatter> formatterMap;
    private Logger log = LoggerFactory.getLogger(MessageFormatterManagerImpl.class);

    @Override
    public <T extends AbstractExceptionMessageFormatter>  void registerFormatter(Class<? extends Exception> clazz, T formatter) {
        if (this.formatterMap == null) {
            this.formatterMap = new FastMap<Class<? extends Exception>, AbstractExceptionMessageFormatter>();
        }
        if (this.log.isDebugEnabled()) {
            this.log.debug("Registering Message formatter {}", formatter.getClass().getName());
        }
        this.formatterMap.put(clazz, formatter);
    }

    @Override
    public <T extends AbstractExceptionMessageFormatter> T getFormatter(Class<? extends Exception> clazz) {
        return (T) this.formatterMap.get(clazz);
    }


}
