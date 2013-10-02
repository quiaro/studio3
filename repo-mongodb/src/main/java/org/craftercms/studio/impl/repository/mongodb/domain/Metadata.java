/*
 * Copyright (C) 2007-2013 Crafter Software Corporation.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.craftercms.studio.impl.repository.mongodb.domain;

import java.util.Map;

import javolution.util.FastMap;

/**
 * Node Metadata.
 */
public class Metadata implements Cloneable {

    /**
     * CoreMetadata Object.
     */
    private CoreMetadata core;
    /**
     * Pretty much everything else.
     */
    private Map<String, Object> additional;


    public Metadata(final CoreMetadata core, final Map<String, Object> additional) {
        this.core = core;
        this.additional = additional;
    }

    public Metadata() {
        core = new CoreMetadata();
        additional = new FastMap<>();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Metadata)) {
            return false;
        }

        final Metadata metadata = (Metadata)o;

        if (additional != null? !additional.equals(metadata.additional): metadata.additional != null) {
            return false;
        }
        if (!core.equals(metadata.core)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = core.hashCode();
        result = 31 * result + (additional != null? additional.hashCode(): 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Metadata{");
        sb.append("core=").append(core);
        sb.append(", additional=").append(additional);
        sb.append('}');
        return sb.toString();
    }

    public Metadata copy() {
        return new Metadata(this);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return copy();
    }


    private Metadata(final Metadata copyFrom){
        this.core = copyFrom.getCore().copy();
        this.additional = new FastMap<>(copyFrom.getAdditional());//copy/clone
    }

    public CoreMetadata getCore() {
        return core;
    }

    public void setCore(final CoreMetadata core) {
        this.core = core;
    }

    public Map<String, Object> getAdditional() {
        return additional;
    }

    public void setAdditional(final Map<String, Object> additional) {
        this.additional = additional;
    }


}
