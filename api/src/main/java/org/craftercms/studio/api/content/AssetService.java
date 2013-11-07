package org.craftercms.studio.api.content;

import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.exception.StudioException;

import java.io.InputStream;

/**
 * Asset Manager interface
 *
 * @author Sumer Jabri
 */
public interface AssetService {
    /**
     * create content.
     *
     * @param context context
     * @param path    path to write to
     * @param item    item meta-data
     * @param content content
     */
    void create(Context context, String site, String path, Item item, InputStream content) throws StudioException;

    /**
     * Read content for given item id.
     *
     * @param context context
     * @param itemId  item id
     * @return content
     */
    InputStream read(Context context, String itemId) throws StudioException;
}
