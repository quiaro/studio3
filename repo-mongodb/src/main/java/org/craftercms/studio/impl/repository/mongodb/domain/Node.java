package org.craftercms.studio.impl.repository.mongodb.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javolution.util.FastMap;
import org.apache.commons.collections.ListUtils;

/**
 * Content Node.
 *
 * @author Carlos Ortiz.
 * @author Sumer Jabri.
 * @author Dejan Brkic.
 */
//Sets collection name
public class Node implements Cloneable {

    /**
     * Core metadata.
     */
    private CoreMetadata core;
    /**
     * Node Id.
     */
    private String id;
    /**
     * Parent Node can't be null.
     */
    private LinkedList<String> ancestors;
    /**
     * Node Type.
     */
    private NodeType type;

    /**
     * Pretty much everything else.
     */
    private Map<String, Object> additional;

    public Node() {
        ancestors = new LinkedList<>();
        core = new CoreMetadata();
        additional = new FastMap<>();
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
        ancestors.addLast(parent.getId());// new node is child of its parent. added to his ancestry line
        this.type = type;
        core = new CoreMetadata();
        additional = new FastMap<>();
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
        this.core = node.getCore().copy();
        this.additional = new FastMap<>(node.getAdditional());
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

    public LinkedList<String> getAncestors() {
        return ancestors;
    }

    public void setAncestors(final LinkedList<String> ancestors) {
        this.ancestors = ancestors;
    }

    public CoreMetadata getCore() {
        return core;
    }

    public Map<String, Object> getAdditional() {
        return additional;
    }

    public void setAdditional(final Map<String, Object> additional) {
        this.additional = additional;
    }

    /**
     * This will get the full Ancestry for this <b>Node Including it self</b>.
     *
     * @return a Unmodifiable List of the Ancestry for this node <b>Including It self.</b>
     */
    public List<Node> getAncestry() {
        LinkedList<Node> tmp = (LinkedList<Node>)ancestors.clone();
        tmp.addLast(this);
        return ListUtils.unmodifiableList(tmp);
    }

    public void setCore(final CoreMetadata core) {
        this.core = core;
    }

    /**
     * Gets current Node parent.
     *
     * @return current node parent <br/>
     * Null if there is no parent.
     */
    public String getParentId() {
        if (!ancestors.isEmpty()) {
            return ancestors.getLast();
        } else {
            return null;
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Node node = (Node)o;

        if (!additional.equals(node.additional)) {
            return false;
        }
        if (!ancestors.equals(node.ancestors)) {
            return false;
        }
        if (!core.equals(node.core)) {
            return false;
        }
        if (!id.equals(node.id)) {
            return false;
        }
        if (type != node.type) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = core.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + ancestors.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + additional.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Node{");
        sb.append("core=").append(core);
        sb.append(", id='").append(id).append('\'');
        sb.append(", ancestors=").append(ancestors);
        sb.append(", type=").append(type);
        sb.append(", additional=").append(additional);
        sb.append('}');
        return sb.toString();
    }
}




