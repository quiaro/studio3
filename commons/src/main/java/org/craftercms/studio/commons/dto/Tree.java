package org.craftercms.studio.commons.dto;

public class Tree<T extends Comparable<T>> {

    private TreeNode<T> rootNode;

    public Tree() {
    }

    public Tree(final TreeNode<T> rootNode) {
        this.rootNode = rootNode;
    }

    public Tree(final T root) {
        TreeNode<T> rootNode = new TreeNode<T>(root, null, null);
        this.rootNode = rootNode;
    }

    public TreeNode<T> getRootNode() {
        return rootNode;
    }

    public void setRootNode(final TreeNode<T> rootNode) {
        this.rootNode = rootNode;
    }

}
