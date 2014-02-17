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
package org.craftercms.studio.api.content;

import java.util.List;
import java.util.Map;

import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Form;
import org.craftercms.studio.commons.exception.StudioException;

/**
 * Form Service TODO complete this
 *
 * @author Sumer Jabri
 */
public interface FormService {
    // TODO CRUD

    /**
     * Create a Studio Form definition.
     *
     * @param context    the caller's context
     * @param site       the site to use
     * @param formName   name of the form being created
     * @param formXml    the form definition in XML compliant with Crafter Studio's Form Engine and are typically
     *                   generated by Crafter Studio's Form Builder TODO add link here.
     * @param properties key-value-pair properties, can be null
     * @return the form descriptor
     * @throws StudioException
     */
    // TODO Consider adding scripts here for lifecycle events and JS for frontend rendering
    Form create(Context context, String site, String formName, String formXml, Map<String,
        String> properties) throws StudioException;

    /**
     * Create a duplicate of an existing form.
     *
     * @param context  the caller's context
     * @param site     the site to use
     * @param formId   id of the form being duplicated
     * @param formName name of the duplicate form
     * @return the duplicate form descriptor
     * @throws StudioException
     */
    Form duplicate(Context context, String site, String formId, String formName) throws StudioException;

    /**
     * Create a duplicate of an existing form in a different site.
     *
     * @param context         the caller's context
     * @param site            the site to use
     * @param formId          id of the form being duplicated
     * @param destinationSite the site to duplicate to
     * @param formName        name of the duplicate form
     * @return the duplicate form descriptor
     * @throws StudioException
     */
    Form duplicate(Context context, String site, String formId, String destinationSite,
                   String formName) throws StudioException;

    /**
     * Read a form descriptor.
     *
     * @param context the caller's context
     * @param site    the site to use
     * @param formId  id of the form to read
     * @return the form descriptor
     * @throws StudioException
     */
    Form read(Context context, String site, String formId) throws StudioException;

    
    Form update() throws StudioException;

    void delete(Context context, String site, String formId) throws StudioException;

    List<Form> findBy(Context context, String site, String query) throws StudioException;

    //todo//////////////////////////////////////todo//////////////////////////////////////////////

    /**
     * List forms.
     *
     * @param context context
     * @param site    site
     * @param filters filters
     * @return list of forms
     */
    List<Form> list(Context context, String site, List<String> filters) throws StudioException;

    /**
     * Create or update form.
     *
     * @param context context
     * @param site    site
     * @param form    form definition
     */
    void update(Context context, String site, Form form) throws StudioException;

    /**
     * Remove form.
     *
     * @param context context
     * @param site    site
     * @param formId  form id
     */
    void remove(Context context, String site, String formId) throws StudioException;

    /**
     * Copy form.
     *
     * @param context     context
     * @param site        site
     * @param source      source
     * @param destination destination
     */
    void copy(Context context, String site, String source, String destination) throws StudioException;
}
