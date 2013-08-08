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

import org.craftercms.studio.api.dto.Context;
import org.craftercms.studio.api.dto.Item;
import org.craftercms.studio.api.extractor.ItemExtractor;
import org.craftercms.studio.api.filter.ItemFilter;
import org.craftercms.studio.api.dto.LockHandle;
import org.craftercms.studio.api.dto.LockStatus;
import org.craftercms.studio.api.dto.Site;
import org.craftercms.studio.api.dto.Tree;
import java.io.InputStream;
import java.util.List;

/**
 * Repository Manager.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public interface ContentManager {

    /**
     * Read content for given item id.
     * @param context context
     * @param itemId item id
     * @return content
     */
    InputStream read(Context context, String itemId);

    /**
     * Read content for given item id and version.
     * @param context context
     * @param itemId item id
     * @param version content version
     * @return content
     */
    InputStream read(Context context, String itemId, String version);

    /**
     * Update content.
     * @param context context
     * @param itemId item id
     * @param content content
     */
    void update(Context context, String itemId, InputStream content);

    /**
     * Open item for edit.
     * @param context context
     * @param itemId itemId
     * @return lock handle
     */
    LockHandle open(Context context, String itemId);

    /**
     * Write content.
     * @param context context
     * @param itemId item id
     * @param lockHandle lock handle
     * @param content content
     */
    void save(Context context, String itemId, LockHandle lockHandle,
              InputStream content);

    /**
     * Close content for edit.
     * @param context context
     * @param itemId item id
     * @param lockHandle lock handle
     */
    void close(Context context, String itemId, LockHandle lockHandle);

    /**
     * Delete content items.
     * @param context context
     * @param itemsToDelete items to delete
     */
    void delete(Context context, List<Item> itemsToDelete);

    /**
     * Copy items to destination path.
     * @param context context
     * @param itemsToCopy items to copy
     * @param destinationPath destination path
     * @param includeChildren include children
     */
    void copy(Context context, List<Item> itemsToCopy,
              String destinationPath, boolean includeChildren);

    /**
     * Move items to destination path.
     * @param context context
     * @param itemsToMove items to move
     * @param destinationPath destination path
     */
    void move(Context context, List<Item> itemsToMove, String destinationPath);

    /**
     * Lock content items.
     * @param context context
     * @param itemsToLock items to lock
     * @return lock handle
     */
    LockHandle lock(Context context, List<Item> itemsToLock);

    /**
     * Unlock content items.
     * @param context context
     * @param itemsToUnlock items to unlock
     * @param lockHandle lock handle
     */
    void unlock(Context context, List<Item> itemsToUnlock,
                LockHandle lockHandle);

    /**
     * Get lock status for items.
     * @param context context
     * @param items items
     * @return lock status
     */
    List<LockStatus> getLockStatus(Context context, List<Item> items);

    /**
     * Get children.
     * @param context context
     * @param itemId item id
     * @return children
     */
    List<Item> list(Context context, String itemId);

    /**
     * Get sub-tree with given item as root.
     * @param context context
     * @param itemId item id
     * @param depth depth
     * @param filters filters
     * @param extractors extractors
     * @return tree of items
     */
    Tree<Item> tree(Context context, String itemId, int depth,
                    List<ItemFilter> filters, List<ItemExtractor> extractors);

    /**
     * Get sites.
     * @param context context
     * @return list of sites
     */
    List<Site> getSiteList(Context context);
}
