/*
 * Copyright (C) 2007-2013 Crafter Software Corporation.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.craftercms.studio.api.dto;

/**
 * Version.
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public class Version implements Comparable<Version> {

    /**
     * Label.
     */
    private String label;

    /**
     * Comment.
     */
    private String comment;

    /**
     * Default constructor.
     */
    public Version() { }

    /**
     * Parametrized constructor.
     * @param label label
     * @param comment comment
     */
    public Version(final String label, final String comment) {
        this.label = label;
        this.comment = comment;
    }

    /**
     * Label getter.
     * @return label value
     */
    public final String getLabel() {
        return label;
    }

    /**
     * Label setter.
     * @param label label
     */
    public final void setLabel(final String label) {
        this.label = label;
    }

    /**
     * Comment getter.
     * @return comment
     */
    public final String getComment() {
        return comment;
    }

    /**
     * Comment setter
     * @param comment comment
     */
    public final void setComment(final String comment) {
        this.comment = comment;
    }

    /**
     * Compare version.
     * @param version  version
     * @return comparison value
     */
    @Override
    public final int compareTo(final Version version) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
