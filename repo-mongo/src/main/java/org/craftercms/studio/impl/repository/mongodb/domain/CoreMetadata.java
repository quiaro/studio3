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
     * Name.
     */
    private String name;
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
        this.name = metadata.getName();
        this.lastModifiedDate = metadata.getLastModifiedDate();
        this.modifier = metadata.getModifier();
        this.createDate = metadata.getCreateDate();
        this.creator = metadata.getCreator();
        this.size = metadata.getSize();
        this.fileId = metadata.getFileId();
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
        if (o == null || getClass() != o.getClass()) {
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
        if (!fileId.equals(that.fileId)) {
            return false;
        }
        if (!lastModifiedDate.equals(that.lastModifiedDate)) {
            return false;
        }
        if (!modifier.equals(that.modifier)) {
            return false;
        }
        if (!name.equals(that.name)) {
            return false;
        }

        return true;
    }


    @Override
    public int hashCode() {
        int magicNumberForHash = 31;
        int result = name != null? name.hashCode(): 0;
        result = magicNumberForHash * result + (lastModifiedDate != null? lastModifiedDate.hashCode(): 0);
        result = magicNumberForHash * result + (modifier != null? modifier.hashCode(): 0);
        result = magicNumberForHash * result + (createDate != null? createDate.hashCode(): 0);
        result = magicNumberForHash * result + (creator != null? creator.hashCode(): 0);
        result = magicNumberForHash * result + (int)(size ^ (size >>> 32));
        result = magicNumberForHash * result + (fileId != null? fileId.hashCode(): 0);
        return result;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return copy();
    }



    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CoreMetadata{");
        sb.append("name='").append(name).append('\'');
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

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }


}
