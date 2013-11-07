package org.craftercms.studio.commons.dto;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TreeNode<T extends Comparable<T>> {

    @JsonProperty
    private T value;
    @JsonIgnore
    private TreeNode<T> parent;
    @JsonProperty
    private Set<TreeNode<T>> children;

    public TreeNode() {
    }

    public TreeNode(final T value, final TreeNode<T> parent, final Set<TreeNode<T>> children) {
        this.value = value;
        this.parent = parent;
        this.children = children;
    }

    public void addChild(T child) {
        addChild(new TreeNode<>(child, this, null));
    }

    public void addChild(TreeNode<T> treeNode) {
        if (this.children == null) {
            children = new HashSet<TreeNode<T>>();
        }
        this.children.add(treeNode);
    }

    @JsonProperty
    public T getValue() {
        return value;
    }

    @JsonProperty
    public void setValue(final T value) {
        this.value = value;
    }

    @JsonIgnore
    public TreeNode<T> getParent() {
        return parent;
    }

    @JsonIgnore
    public void setParent(final TreeNode<T> parent) {
        this.parent = parent;
    }

    @JsonProperty
    public Set<TreeNode<T>> getChildren() {
        return children;
    }

    @JsonProperty
    public void setChildren(final Set<TreeNode<T>> children) {
        this.children = children;
    }
}
