package org.craftercms.studio.api.content;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.commons.exception.StudioException;

/**
 * Asset Service provides an API to managing static assets in a CrafterCMS-based site. Examples of files managed by
 * this service include: Images, video, audio, Javascript, CSS, etc.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 */
public interface AssetService {
    // TODO Consider exceptions: DuplicateFileException, InvalidDestinationPath, InvalidContext,

    /**
     * Create an asset file.
     *
     * @param context         the caller's context
     * @param site            the site to use
     * @param destinationPath path to write to (this is relative off of the base path for this type)
     * @param fileName        file name of asset
     * @param content         content InputStream, can be null if creating a 0 byte file
     * @param mimeType        mimeType of asset, can be null if unknown
     * @param properties      key-value-pair properties, can be null
     * @return the Item descriptor
     * @throws StudioException
     */
    Item create(Context context, String site, String destinationPath, String fileName, InputStream content,
                String mimeType, Map<String, String> properties) throws StudioException;

    /**
     * Create an asset file.
     *
     * @param context         the caller's context
     * @param site            the site to use
     * @param destinationPath path to write to (this is relative off of the base path for this type)
     * @param fileName        file name of asset
     * @param content         content as a string (textual content)
     * @param mimeType        mimeType of asset, can be null if unknown
     * @param properties      key-value-pair properties, can be null
     * @return the Item descriptor
     * @throws StudioException
     */
    Item create(Context context, String site, String destinationPath, String fileName, String content,
                String mimeType, Map<String, String> properties) throws StudioException;

    /**
     * Create an asset file.
     *
     * @param context         the caller's context
     * @param site            the site to use
     * @param destinationPath path to write to (this is relative off of the base path for this type)
     * @param fileName        file name of asset
     * @param content         content as a byte array
     * @param mimeType        mimeType of asset, can be null if unknown
     * @param properties      key-value-pair properties, can be null
     * @return the Item descriptor
     * @throws StudioException
     */
    Item create(Context context, String site, String destinationPath, String fileName, byte[] content,
                String mimeType, Map<String, String> properties) throws StudioException;

    /**
     * Read item meta-data descriptor and return it.
     *
     * @param context the caller's context
     * @param site    the site to use
     * @param itemId  the item to read
     * @return the Item descriptor
     * @throws StudioException
     */
    Item read(Context context, String site, String itemId) throws StudioException;

    /**
     * Read item textual content and return it.
     *
     * @param context the caller's context
     * @param site    the site to use
     * @param itemId  the item to read
     * @return the Item file content as text
     * @throws StudioException
     */
    String getTextContent(Context context, String site, String itemId) throws StudioException;

    /**
     * Get an asset's InputStream.
     *
     * @param context the caller's context
     * @param site    the site to use
     * @param itemId  the item to read
     * @return InputStream to the content
     * @throws StudioException
     */
    InputStream getInputStream(Context context, String site, ItemId itemId);

    /**
     * Update an asset given an InputStream.
     *
     * @param context    the caller's context
     * @param site       the site to use
     * @param itemId     the item to read
     * @param content    the InputStream containing the content
     * @param properties key-value-pair properties, can be null
     * @return the Item descriptor
     * @throws StudioException
     */
    Item update(Context context, String site, ItemId itemId, InputStream content, Map<String,
        String> properties) throws StudioException;

    /**
     * Update an asset given an InputStream.
     *
     * @param context    the caller's context
     * @param site       the site to use
     * @param itemId     the item to read
     * @param content    the textual content as a String
     * @param properties key-value-pair properties, can be null
     * @return the Item descriptor
     * @throws StudioException
     */
    Item update(Context context, String site, ItemId itemId, String content, Map<String,
        String> properties) throws StudioException;

    /**
     * Update an asset given an InputStream.
     *
     * @param context    the caller's context
     * @param site       the site to use
     * @param itemId     the item to read
     * @param content    the binary content as a byte array
     * @param properties key-value-pair properties, can be null
     * @return the Item descriptor
     * @throws StudioException
     */
    Item update(Context context, String site, ItemId itemId, byte[] content, Map<String,
        String> properties) throws StudioException;

    /**
     * Delete an asset.
     *
     * @param context the caller's context
     * @param site    the site to use
     * @param itemId  the asset to delete
     * @throws StudioException
     */
    void delete(Context context, String site, ItemId itemId) throws StudioException;

    /**
     * Find assets matching a query.
     *
     * @param context the caller's context
     * @param site    the site to use
     * @param query   mdb query string
     * @return list of items matching the query
     * @throws StudioException
     */
    List<Item> findBy(Context context, String site, String query) throws StudioException;
}
