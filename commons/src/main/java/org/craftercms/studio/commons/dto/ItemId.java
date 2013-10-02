package org.craftercms.studio.commons.dto;

/**
 * @author Sumer Jabri
 */
public class ItemId {
    String itemId;

    ItemId(final String itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return itemId;
    }
}
