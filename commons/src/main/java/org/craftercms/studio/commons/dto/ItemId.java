package org.craftercms.studio.commons.dto;

/**
 * @author Sumer Jabri
 */
public class ItemId {
    String itemId;

    public ItemId(final String itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return itemId;
    }
}
