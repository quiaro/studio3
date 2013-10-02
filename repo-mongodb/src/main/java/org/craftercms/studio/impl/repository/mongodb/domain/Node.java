package org.craftercms.studio.impl.repository.mongodb.domain;

import java.util.LinkedList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
    @DBRef
    @Indexed
    private LinkedList<Node> ancestors;
    /**
     * Node Type.
     */
    private NodeType type;
    /**
     * Metadata of this node.
     */
    private Metadata metadata;

    public Node() {
        ancestors = new LinkedList<>();
        metadata = new Metadata();
    }

    /**
     * Creates a new node, add parent to new node's ancestry line.
     *
     * @param parent Parent of the new Node
     * @param type   Type of the new Node
     */
    public Node(final Node parent, final NodeType type) {
        ancestors = new LinkedList<>(parent.getAncestors());
        //Collections.copy(ancestors, parent.getAncestors());
        ancestors.addLast(parent);// new node is child of its parent. added to his ancestry line
        this.type = type;
        metadata = new Metadata();
    }

    /**
     * Internal CTOR to be use on copy/clone methods.
     *
     * @param node Node to by copy of this
     */
    protected Node(final Node node) {
        this.id = node.getId();
        this.ancestors = node.getAncestors();
        this.type = node.getType();
        this.metadata = node.getMetadata().copy();//also Copy the Metadata Object.
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
        //Ignoring the Ancestors to make ToString fast.
        final StringBuilder sb = new StringBuilder("Node{");
        sb.append("id='").append(id).append('\'');
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

    public NodeType getType() {
        return type;
    }

    public void setType(final NodeType type) {
        this.type = type;
    }

    public LinkedList<Node> getAncestors() {
        return ancestors;
    }

    public void setAncestors(final LinkedList<Node> ancestors) {
        this.ancestors = ancestors;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(final Metadata metadata) {
        this.metadata = metadata;
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

        if (!ancestors.equals(node.ancestors)) {
            return false;
        }
        if (!id.equals(node.id)) {
            return false;
        }
        if (!metadata.equals(node.metadata)) {
            return false;
        }
        if (type != node.type) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + ancestors.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + metadata.hashCode();
        return result;
    }

    /**
     * Gets current Node parent.
     *
     * @return current node parent <br/>
     * Null if there is no parent.
     */
    public Node getParent() {
        if (!ancestors.isEmpty()) {
            return ancestors.getLast();
        } else {
            return null;
        }
    }

}




