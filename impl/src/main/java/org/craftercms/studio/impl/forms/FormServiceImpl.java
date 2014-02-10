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

package org.craftercms.studio.impl.forms;

import java.util.List;
import java.util.Map;

import org.craftercms.studio.api.content.FormService;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Form;
import org.craftercms.studio.commons.exception.NotImplementedException;
import org.craftercms.studio.commons.exception.StudioException;

/**
 * Forms Manager implementation.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public class FormServiceImpl implements FormService {

    @Override
    public Form create(final Context context, final String site, final String formName, final String formXml, final Map<String, String> properties) throws StudioException {
        return null;
    }

    @Override
    public Form duplicate(final Context context, final String site, final String formId, final String formName) throws StudioException {
        return null;
    }

    @Override
    public Form duplicate(final Context context, final String site, final String formId, final String destinationSite, final String formName) throws StudioException {
        return null;
    }

    @Override
    public Form read(final Context context, final String site, final String formId) throws StudioException {
        return null;
    }

    @Override
    public Form update() throws StudioException {
        return null;
    }

    @Override
    public void delete(final Context context, final String site, final String formId) throws StudioException {

    }

    @Override
    public List<Form> findBy(final Context context, final String site, final String query) throws StudioException {
        return null;
    }

    @Override
    public List<Form> list(final Context context, final String site, final List<String> filters) {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public void update(final Context context, final String site, final Form form) {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public void remove(final Context context, final String site, final String formId) {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public void copy(final Context context, final String site, final String source, final String destination) {
        throw new NotImplementedException("Not implemented yet!");
    }
}
