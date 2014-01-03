package org.craftercms.studio.mock.content;

import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.craftercms.studio.commons.dto.Item;
import org.craftercms.studio.commons.dto.TreeNode;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "node")
public class TreeNodeMock {

    private Item value;
    private TreeNodeMock parent;
    @XmlElement(name = "node")
    private Set<TreeNodeMock> children;

    //private TreeNode<Item> treeNode;

    public TreeNodeMock() {
    }

    public TreeNodeMock(final Item value, final TreeNodeMock parent, final Set<TreeNodeMock> children) {
        this.value = value;
        this.parent = parent;
        this.children = children;
    }

    public void addChild(Item child) {
        addChild(new TreeNodeMock(child, this, null));
    }

    public void addChild(TreeNodeMock treeNode) {
        if (this.children == null) {
            children = new HashSet<TreeNodeMock>();
        }
        this.children.add(treeNode);
    }

    public Item getValue() {
        return value;
    }

    public void setValue(final Item value) {
        this.value = value;
    }

    public TreeNodeMock getParent() {
        return parent;
    }

    public void setParent(final TreeNodeMock parent) {
        this.parent = parent;
    }

    public Set<TreeNodeMock> getChildren() {
        return children;
    }

    public void setChildren(final Set<TreeNodeMock> children) {
        this.children = children;
    }
}
