package org.craftercms.studio.internal.content.impl;

import java.io.InputStream;

import org.craftercms.studio.api.content.ContentService;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.exception.NotImplementedException;
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
    public void create(final Context context, final String site, final String path, final Item item, final InputStream content) throws StudioException {
        contentService.create(context.getTicket(), site, path, item, content);
    }

    @Override
    public InputStream read(final Context context, final String itemId) throws StudioException {
        throw new NotImplementedException("Not implemented yet!");
    }

    // Getters and setters

    public ContentService getContentService() {
        return contentService;
    }

    public void setContentService(final ContentService contentService) {
        this.contentService = contentService;
    }
}
