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
package org.craftercms.studio.api.forms;

import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.FormDefinition;

import java.util.List;
import java.util.Map;

/**
 * Forms Manager.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public interface FormsManager {

    /**
     * List forms.
     * @param context context
     * @param site site
     * @param filters filters
     * @return list of forms
     */
    List<FormDefinition> list(Context context, String site, List<String> filters);

    /**
     * Create or update form.
     * @param context context
     * @param site site
     * @param formDefinition form definition
     */
    void update(Context context, String site, FormDefinition formDefinition);

    /**
     * Remove form.
     * @param context context
     * @param site site
     * @param formId form id
     */
    void remove(Context context, String site, String formId);

    /**
     * Copy form.
     * @param context context
     * @param site site
     * @param source source
     * @param destination destination
     */
    void copy(Context context, String site, String source, String destination);
}
