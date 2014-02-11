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

import org.craftercms.studio.commons.dto.DiffResult;
import org.craftercms.studio.commons.dto.Item;

/**
 * Version Service.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public interface VersionService {

    /**
     * Checkout item for edit.
     *
     * @param ticket security ticket
     * @param itemId item id
     * @return Id of working copy
     */
    String checkOut(String ticket, String itemId) throws ItemAlreadyCheckedOutException;

    /**
     * Cancel checkout.
     *
     * @param ticket        security ticket
     * @param itemId        item id
     * @param workingCopyId id of working copy obtained during
     *                      {@link VersionService#checkOut(String, String)}
     */
    void cancelCheckOut(String ticket, String itemId, String workingCopyId);

    /**
     * Cancel checkout.
     *
     * @param ticket         security ticket
     * @param itemId         item id
     * @param workingCopyId  id of working copy obtained during
     * @param isMajorVersion is this a major or minor version
     * @param comment        comment to attach to the check in
     *                       {@link VersionService#checkOut(String, String)}
     */
    void checkIn(String ticket, String itemId, String workingCopyId, boolean isMajorVersion, String comment);

    /**
     * Get all item versions.
     *
     * @param ticket security ticket
     * @param itemId item id
     * @return return list of items (versions)
     */
    List<Item> getAllVersions(String ticket, String itemId);

    /**
     * Revert item to given version.
     *
     * @param ticket  security ticket
     * @param itemId  item id
     * @param version version to revert to
     */
    void revert(String ticket, String itemId, String version);
    // TODO think some more about the version param

    /**
     * Diff on two item versions.
     *
     * @param ticket   security ticket
     * @param itemId   item id
     * @param version1 version 1
     * @param version2 version 2
     * @return diff result
     */
    DiffResult diff(String ticket, String itemId, String version1, String version2);
    // TODO think some more about the version param
}
