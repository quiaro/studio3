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

package org.craftercms.studio.repo.content;

import java.util.List;

/**
 * List of Values Service.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public interface LoVService {

    /**
     * Get list of values.
     * @param ticket security ticket
     * @param lovName list of values name
     * @param type list of values type
     * @return list of values
     */
    <T> List<T> getLoV(String ticket, String lovName, Class<T> type);

    // fixme todo
    <T> void updateLoV(String ticket, String lovName, Class<T> type, List<T> LoV);
}
