package org.craftercms.studio3.web;

import java.util.Date;

/**
 * a.
 */
public class TestObject {
    private String name;
    private Date date;
    private TestObject child;

    public TestObject(String name, Date date, TestObject child) {
        this.name = name;
        this.date = date;
        this.child = child;
    }

    public TestObject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TestObject getChild() {
        return child;
    }

    public void setChild(TestObject child) {
        this.child = child;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TestObject{");
        sb.append("name='").append(name).append('\'');
        sb.append(", date=").append(date);
        sb.append(", child=").append(child);
        sb.append('}');
        return sb.toString();
    }
}
