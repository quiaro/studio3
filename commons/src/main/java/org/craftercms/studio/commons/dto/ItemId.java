package org.craftercms.studio.commons.dto;

import java.io.Serializable;

/**
 * @author Sumer Jabri
 */
public class ItemId implements Serializable{
    private static final long serialVersionUID = 19678608701816182L;

    String itemId;

    public ItemId(final String itemId) {
        this.itemId = itemId;
    }

    public ItemId() {

    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(final String itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return itemId;
    }
}
