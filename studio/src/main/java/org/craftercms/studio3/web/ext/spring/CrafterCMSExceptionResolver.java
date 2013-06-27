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
package org.craftercms.studio3.web.ext.spring;


import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.craftercms.studio3.web.support.message.ExceptionMessageFormatter;
import org.craftercms.studio3.web.support.message.MessageFormatterManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;


/**
 * Makes sure that All Exceptions are return in a JSON.
 * by using {@link MessageFormatterManager}
 *
 * @author cortiz
 */
public class CrafterCMSExceptionResolver extends AbstractHandlerExceptionResolver {

    protected MessageFormatterManager messageFormatterManager;
    private Logger log = LoggerFactory.getLogger(CrafterCMSExceptionResolver.class);

    public CrafterCMSExceptionResolver() {
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,
                                              Object handler, Exception ex)
    {
        final ExceptionMessageFormatter exceptionFormatter = this.messageFormatterManager.getFormatter(ex.getClass());
        try {
            response.setHeader("Content-Type", MediaType.APPLICATION_JSON.toString());
            response.setStatus(exceptionFormatter.getHttpResponseCode());
            response.getWriter().write(exceptionFormatter.getFormattedMessage(ex));
        } catch (IOException e) {
            this.log.error("Unable to generate send error due a IOException ", e);
        }
        return new ModelAndView();
    }

    public void setMessageFormatterManager(MessageFormatterManager messageFormatterManager) {
        this.messageFormatterManager = messageFormatterManager;
    }
}
