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
package org.craftercms.studio.internal.concurrency;

import java.util.List;

import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.LockHandle;
import org.craftercms.studio.commons.dto.LockStatus;

/**
 * @author Sumer Jabri
 */
public interface ConcurrencyManager {
    /**
     * Lock content items.
     *
     * @param context     context
     * @param itemsToLock items to lock
     * @return lock handle
     */
    LockHandle lock(Context context, List<Item> itemsToLock);

    /**
     * Unlock content items.
     *
     * @param context       context
     * @param itemsToUnlock items to unlock
     * @param lockHandle    lock handle
     */
    void unlock(Context context, List<Item> itemsToUnlock, LockHandle lockHandle);

    /**
     * Get lock status for items.
     *
     * @param context context
     * @param items   items
     * @return lock status
     */
    List<LockStatus> getLockStatus(Context context, List<Item> items);
}
