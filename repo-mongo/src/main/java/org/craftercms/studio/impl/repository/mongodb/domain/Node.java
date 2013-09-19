package org.craftercms.studio.impl.repository.mongodb.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Content Node.
 *
 * @author Carlos Ortiz.
 * @author Sumer Jabri.
 * @author Dejan Brkic.
 */
@Document(collection = "nodes")//Sets collection name
public class Node implements Cloneable {

    /**
     * Node Id.
     */
    @Id //Forces MongoMapper to use this as a ID instead ObjectId of mongo.
    @Indexed(unique = true)//make sure is Unique
    private String id;
    /**
     * Parent Node can't be null.
     */
    private Node parent;
    /**
     * Node Type.
     */
    private NodeType type;
    /**
     * Metadata of this node.
     */
    private CoreMetadata metadata;

    public Node() {
    }

    public Node(final Node parent, final NodeType type) {

        this.parent = parent;
        this.type = type;
    }

    protected Node(final Node node) {
        this.id = node.getId();
        this.parent = node.getParent();
        this.type = node.getType();
        this.metadata = node.getMetadata().copy();//also Copy the Metadata Object.
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Node)) {
            return false;
        }

        final Node node = (Node)o;

        if (!id.equals(node.id)) {
            return false;
        }
        if (!metadata.equals(node.metadata)) {
            return false;
        }
        if (parent != null? !parent.equals(node.parent): node.parent != null) {
            return false;
        }
        if (type != node.type) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int magicNumberForHash = 31;
        int result = id != null? id.hashCode(): 0;
        result = magicNumberForHash * result + (parent != null? parent.hashCode(): 0);
        result = magicNumberForHash * result + (type != null? type.hashCode(): 0);
        result = magicNumberForHash * result + (metadata != null? metadata.hashCode(): 0);
        return result;
    }

    /**
     * Copies (including a copy of CoreMetadata)  current object properties to a new instance.
     *
     * @return a exact copy of the object (different mem ref)
     */
    public Node copy() {
        return new Node(this);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return copy();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Node{");
        sb.append("id='").append(id).append('\'');
        sb.append(", parent=").append(parent);
        sb.append(", type=").append(type);
        sb.append(", metadata=").append(metadata);
        sb.append('}');
        return sb.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(final Node parent) {
        this.parent = parent;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(final NodeType type) {
        this.type = type;
    }

    public CoreMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(final CoreMetadata metadata) {
        this.metadata = metadata;
    }
}




