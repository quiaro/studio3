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

package org.craftercms.studio.web.exceptions;

import java.util.List;

import org.craftercms.studio.utils.exceptions.AbstractCrafterCMSException;
import org.springframework.validation.ObjectError;

/**
 * Throw when the object send is not valid validation to the proper validators
 * define in {@see org.craftercms.studio.web.validation} package.
 */
public class ValidationException extends AbstractCrafterCMSException {
    /**
     * List of All Errors.
     */
    private final List<ObjectError> errors;

    /**
     * Creates a Instance of ValidationException base on a List of error.
     *
     * @param message Exception Summary Message
     * @param errors  List of all validation errors
     */
    public ValidationException(String message, List<ObjectError> errors) {
        super(message);
        this.errors = errors;
    }

    /**
     * Gets a List of all Validation Errors.
     *
     * @return list of validation errors for this exception.
     */
    public final List<ObjectError> getErrors() {
        return this.errors;
    }
}
