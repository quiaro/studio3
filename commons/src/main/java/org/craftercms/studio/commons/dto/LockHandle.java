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
package org.craftercms.studio.commons.dto;

/**
 * Represents a lock around a content item.
 *
 * @author Sumer Jabri
 */
public class LockHandle {

    /**
     *
     */
    private String itemId;

    /**
     *
     */
    private String uname;

    /**
     *
     */
    private long lockDate;

    public String getItemId() {
        return itemId;
    }

    public String getUname() {
        return uname;
    }

    public long getLockDate() {
        return lockDate;
    }

    public void setItemId(final String itemId) {
        this.itemId = itemId;
    }

    public void setUname(final String uname) {
        this.uname = uname;
    }

    public void setLockDate(final long lockDate) {
        this.lockDate = lockDate;
    }
}
