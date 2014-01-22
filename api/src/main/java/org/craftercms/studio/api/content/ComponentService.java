package org.craftercms.studio.api.content;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.commons.exception.StudioException;

/**
 * Component Service provides an API to managing Components in a CrafterCMS-based site.
 *
 * @author Sumer Jabri
 */
public interface ComponentService {
    /**
     * Create a new descriptor.
     *
     * @param context         the caller's context
     * @param site            the site to use
     * @param destinationPath path to create the descriptor (this is relative off of the base path for this type)
     * @param fileName        file name of the descriptor
     * @param content         the InputStream containing the XML that is compliant with the model defined in Studio
     *                        (typically done using Studio's Form Engine).
     * @param properties      key-value-pair properties, can be null
     * @return the Item descriptor
     * @throws StudioException
     */
    Item create(Context context, String site, String destinationPath, String fileName, InputStream content,
                Map<String, String> properties) throws StudioException;

    /**
     * Create a new descriptor.
     *
     * @param context         the caller's context
     * @param site            the site to use
     * @param destinationPath path to create the descriptor (this is relative off of the base path for this type)
     * @param fileName        file name of the descriptor
     * @param content         the XML that is compliant with the model defined in Studio (typically done using
     *                        Studio's Form Engine).
     * @param properties      key-value-pair properties, can be null
     * @return the Item descriptor
     * @throws StudioException
     */
    Item create(Context context, String site, String destinationPath, String fileName, String content, Map<String,
        String> properties) throws StudioException;

    /**
     * Read a descriptor and return it.
     *
     * @param context the caller's context
     * @param site    the site to use
     * @param itemId  the item to read
     * @return the Item descriptor
     * @throws StudioException
     */
    Item read(Context context, String site, ItemId itemId) throws StudioException;

    /**
     * Update a descriptor given an InputStream.
     *
     * @param context    the caller's context
     * @param site       the site to use
     * @param itemId     the item to read
     * @param content    the InputStream containing the XML that is compliant with the model defined in Studio
     *                   (typically done using Studio's Form Engine).
     * @param properties key-value-pair properties, can be null
     * @return the Item descriptor
     * @throws StudioException
     */
    Item update(Context context, String site, ItemId itemId, InputStream content, Map<String,
        String> properties) throws StudioException;

    /**
     * Update a descriptor given an InputStream.
     *
     * @param context    the caller's context
     * @param site       the site to use
     * @param itemId     the item to read
     * @param content    the XML that is compliant with the model defined in Studio (typically done using
     *                   Studio's Form Engine).
     * @param properties key-value-pair properties, can be null
     * @return the Item descriptor
     * @throws StudioException
     */
    Item update(Context context, String site, ItemId itemId, String content, Map<String,
        String> properties) throws StudioException;

    /**
     * Delete a descriptor.
     *
     * @param context the caller's context
     * @param site    the site to use
     * @param itemId  the item to delete
     * @throws StudioException
     */
    void delete(Context context, String site, ItemId itemId) throws StudioException;

    /**
     * Find descriptors matching a query.
     *
     * @param context the caller's context
     * @param site    the site to use
     * @param query   mdb query string
     * @return list of items matching the query
     * @throws StudioException
     */
    List<Item> findBy(Context context, String site, String query) throws StudioException;
}
