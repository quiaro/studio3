package org.craftercms.studio.internal.content.impl;

import java.io.InputStream;
import java.util.List;

import org.craftercms.studio.commons.dto.LockHandle;
import org.craftercms.studio.repo.content.ContentService;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.internal.content.ContentManager;

/**
 * Repository Manager Implementation.
 *
 * @author Dejan Brkic
 */
public class ContentManagerImpl implements ContentManager {

    private ContentService contentService;

    @Override
    public ItemId create(final Context context, final String site, final String path, final Item item,
                    final InputStream content) throws StudioException {
        Item newItem = contentService.create(context.getTicket(), site, path, item, content);
        return newItem.getId();
    }

    @Override
    public Item read(final Context context, final String site, final String itemId) throws StudioException {
        return contentService.read(context.getTicket(), site, itemId);
    }

    @Override
    public void write(final Context context, final String site, final ItemId itemId, final LockHandle lockHandle,
                      final InputStream
        content) throws StudioException {

        Item item = contentService.read(context.getTicket(), site, itemId.getItemId());
        contentService.update(context.getTicket(), item, content);
    }

    @Override
    public void delete(final Context context, final List<Item> itemsToDelete) {
        for (Item item : itemsToDelete) {
            contentService.delete(context.getTicket(), item.getId().getItemId());
        }
    }

    // Getters and setters

    public ContentService getContentService() {
        return contentService;
    }

    public void setContentService(final ContentService contentService) {
        this.contentService = contentService;
    }
}
