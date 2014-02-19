/*
 * Copyright (C) 2007-2013 Crafter Software Corporation.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.craftercms.studio.repo;

import org.craftercms.studio.commons.exception.StudioException;

/**
 * Repository Exception thrown when a internal error in the
 * repository happens.
 */
public abstract class RepositoryException  extends StudioException{

    private static final long serialVersionUID = 3582031699646144755L;

    public RepositoryException(final Throwable cause, String ... args) {
        super(ErrorCode.REPOSITORY_ERROR, cause, args);
    }

    public RepositoryException(String ... args) {
        super(ErrorCode.REPOSITORY_ERROR, args);
    }

}
