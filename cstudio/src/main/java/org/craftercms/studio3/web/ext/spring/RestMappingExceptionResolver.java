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

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

/**
 * Overrides the {@link org.springframework.web.servlet.handler.SimpleMappingExceptionResolver} <br/><br/>.
 *  {@link #doResolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, Object, Exception)} removes the
 *  StackTrace of the exception <br/><br/>
 *  resolve the status
 * @author cortiz
 */
public class RestMappingExceptionResolver extends
        DefaultHandlerExceptionResolver {

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mv = new ModelAndView();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("message", ex.getMessage());
        map.put("localizedMessage", ex.getLocalizedMessage());
        mv.addAllObjects(map);
        return mv;
    }
}
