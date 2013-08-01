package org.craftercms.studio.api.version;

public class Version implements Comparable<Version> {

    private String label;
    private String comment;

    public Version() { }

    public Version(String label, String comment) {
        this.label = label;
        this.comment = comment;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int compareTo(Version o) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
