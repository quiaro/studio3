package org.craftercms.studio.impl.repository.mongodb.domain;

import java.util.Date;

/**
 * A nodes Metadata.
 * This object is designed to be embedded in {@link Node} collection therefor
 * <ul>
 * <li>Does not have a Id</li>
 * <li>Does not have its own data repository nor services</li>
 * </ul>
 */
public class CoreMetadata implements Cloneable {

    /**
     * This nodeName is used for path calculation. <br/>
     * <b>(can have non ASCII chars and spaces)</b>
     */
    private String nodeName;

    /**
     * Last Modified Date.
     */
    private Date lastModifiedDate;
    /**
     * Who modified the file.
     */
    private String modifier;
    /**
     * When was created.
     */
    private Date createDate;
    /**
     * Who created the file.
     */
    private String creator;
    /**
     * File Size (always 0 if a Folder).
     */
    private long size;
    /**
     * Reference, Id of the actual file.
     */
    private String fileId;

    /**
     * Node Label.
     */
    private String label;

    /**
     * Empty default CTOR.
     */
    public CoreMetadata() {
    }

    /**
     * Internal CTOR use to copy/clone this object.
     *
     * @param metadata CoreMetadata to be use a base.
     */
    protected CoreMetadata(final CoreMetadata metadata) {
        this.nodeName = metadata.getNodeName();
        this.lastModifiedDate = metadata.getLastModifiedDate();
        this.modifier = metadata.getModifier();
        this.createDate = metadata.getCreateDate();
        this.creator = metadata.getCreator();
        this.size = metadata.getSize();
        this.fileId = metadata.getFileId();
        this.label = metadata.getLabel();

    }

    /**
     * Creates and sets a new copy of CoreMetadata instance.
     *
     * @return a new 1 to 1 copy (but different memory ref) of current CoreMetadata instance.
     */
    public CoreMetadata copy() {
        return new CoreMetadata(this);
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CoreMetadata)) {
            return false;
        }

        final CoreMetadata that = (CoreMetadata)o;

        if (size != that.size) {
            return false;
        }
        if (!createDate.equals(that.createDate)) {
            return false;
        }
        if (!creator.equals(that.creator)) {
            return false;
        }
        if (fileId != null? !fileId.equals(that.fileId): that.fileId != null) {
            return false;
        }
        if (!lastModifiedDate.equals(that.lastModifiedDate)) {
            return false;
        }
        if (!modifier.equals(that.modifier)) {
            return false;
        }
        if (!nodeName.equals(that.nodeName)) {
            return false;
        }
        if (!label.equals(that.label)) {
            return false;
        }


        return true;
    }

    @Override
    public int hashCode() {
        int result = nodeName.hashCode();
        result = 31 * result + (lastModifiedDate != null? lastModifiedDate.hashCode(): 0);
        result = 31 * result + (modifier != null? modifier.hashCode(): 0);
        result = 31 * result + createDate.hashCode();
        result = 31 * result + label.hashCode();
        result = 31 * result + creator.hashCode();
        result = 31 * result + (int)(size ^ (size >>> 32));
        result = 31 * result + fileId.hashCode();
        return result;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return copy();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CoreMetadata{");
        sb.append("nodeName='").append(nodeName).append('\'');
        sb.append(" label='").append(label).append('\'');
        sb.append(", lastModifiedDate=").append(lastModifiedDate);
        sb.append(", modifier='").append(modifier).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", creator='").append(creator).append('\'');
        sb.append(", size=").append(size);
        sb.append(", fileId='").append(fileId).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(final String fileId) {
        this.fileId = fileId;
    }

    public long getSize() {
        return size;
    }

    public void setSize(final long size) {
        this.size = size;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(final String creator) {
        this.creator = creator;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(final Date createDate) {
        this.createDate = createDate;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(final String modifier) {
        this.modifier = modifier;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(final Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(final String nodeName) {
        this.nodeName = nodeName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }
}
