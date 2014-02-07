package org.craftercms.studio.api.content;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.ItemId;
import org.craftercms.studio.commons.exception.StudioException;

/**
 * @author Sumer Jabri
 */
public interface TemplateService {
    // TODO Consider adding duplicate and move
    /**
     * Create a new template.
     *
     * @param context         the caller's context
     * @param site            the site to use
     * @param parentId   the id of the parent item (can be a folder or a descriptor)
     * @param fileName        file name of the template
     * @param content         the InputStream containing the XML that is compliant with the model defined in Studio
     *                        (typically done using Studio's Form Engine).
     * @param properties      key-value-pair properties, can be null
     * @return the Item template
     * @throws StudioException
     */
    Item create(Context context, String site, String parentId, String fileName, InputStream content,
                Map<String, String> properties) throws StudioException;

    /**
     * Create a new template.
     *
     * @param context         the caller's context
     * @param site            the site to use
     * @param parentId   the id of the parent item (can be a folder or a descriptor)
     * @param fileName        file name of the template
     * @param content         the XML that is compliant with the model defined in Studio (typically done using
     *                        Studio's Form Engine).
     * @param properties      key-value-pair properties, can be null
     * @return the Item template
     * @throws StudioException
     */
    Item create(Context context, String site, String parentId, String fileName, String content, Map<String,
        String> properties) throws StudioException;

    /**
     * Read a template and return it.
     *
     * @param context the caller's context
     * @param site    the site to use
     * @param itemId  the item to read
     * @return the Item template
     * @throws StudioException
     */
    Item read(Context context, String site, ItemId itemId) throws StudioException;

    /**
     * Update a template given an InputStream.
     *
     * @param context    the caller's context
     * @param site       the site to use
     * @param itemId     the item to read
     * @param content    the InputStream containing the XML that is compliant with the model defined in Studio
     *                   (typically done using Studio's Form Engine).
     * @param properties key-value-pair properties, can be null
     * @return the Item template
     * @throws StudioException
     */
    Item update(Context context, String site, ItemId itemId, InputStream content, Map<String,
        String> properties) throws StudioException;

    /**
     * Update a template given an InputStream.
     *
     * @param context    the caller's context
     * @param site       the site to use
     * @param itemId     the item to read
     * @param content    the XML that is compliant with the model defined in Studio (typically done using
     *                   Studio's Form Engine).
     * @param properties key-value-pair properties, can be null
     * @return the Item template
     * @throws StudioException
     */
    Item update(Context context, String site, ItemId itemId, String content, Map<String,
        String> properties) throws StudioException;

    /**
     * Delete a template.
     *
     * @param context the caller's context
     * @param site    the site to use
     * @param itemId  the item to delete
     * @throws StudioException
     */
    void delete(Context context, String site, ItemId itemId) throws StudioException;

    /**
     * Find templates matching a query.
     *
     * @param context the caller's context
     * @param site    the site to use
     * @param query   mdb query string
     * @return list of items matching the query
     * @throws StudioException
     */
    List<Item> findBy(Context context, String site, String query) throws StudioException;
}
