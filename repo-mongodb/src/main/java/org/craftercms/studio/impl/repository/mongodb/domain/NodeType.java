package org.craftercms.studio.impl.repository.mongodb.domain;

/**
 * Posible Node Type.
 */
public enum NodeType {
    /**
     * Node of type FILE.
     * This means that Nodes of this type
     * have a fileId associated to them, also that it can't  be a parent of any other Nodes.
     */
    FILE,
    /**
     * Node of type FOLDER.
     * This means that nodes of this type can be parent of other nodes
     * and they will never have a fileId associated to them.
     */
    FOLDER;


    @Override
    public String toString() {
        switch (this){
            case FOLDER:
                return "FOLDER";
            case FILE :
                return "FILE";
            default:
                return "OTHER";
        }
    }

    public static NodeType fromString(String nodeType) {
        if (nodeType.equalsIgnoreCase("FILE")) {
            return NodeType.FILE;
        } else {
            return NodeType.FOLDER;
        }
    }
}
