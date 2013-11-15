package org.craftercms.studio.api.content;

import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.commons.exception.StudioException;

import java.io.InputStream;

/**
 * Asset Manager interface
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 */
public interface AssetService {
    /**
     * Create an asset file in the repository.
     *
     * @param context         context
     * @param destinationPath path to write to
     * @param fileName        file name of asset
     * @param content         content input stream, can be null if creating a 0 byte file
     * @param mimeType        mimeType of asset, can be null if unknown
     * @return item id
     */
    ItemId create(Context context, String site, String destinationPath, String fileName, InputStream content,
                String mimeType) throws StudioException;
    // TODO Consider exceptions: DuplicateFileException, InvalidDestinationPath, InvalidContext, SiteNotFoundException <= remove parentE

    /**
     * Read content for given item id.
     *
     * @param context context
     * @param itemId  item id
     * @return content item
     */
    Item read(Context context, String itemId) throws StudioException;


    // TODO: Consider adding update and delete methods
}
