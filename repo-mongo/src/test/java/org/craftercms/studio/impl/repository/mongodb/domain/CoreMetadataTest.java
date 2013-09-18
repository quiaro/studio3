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

import java.util.Date;
import java.util.UUID;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * This test exist due CoreMetadataTest DTO does a bit
 * of logic (copy/clone) therefor has be be test.
 */
public class CoreMetadataTest {

    @Test
    public void testCopy() throws Exception {
        CoreMetadata coreMetadata1 = new CoreMetadata();
        coreMetadata1.setCreateDate(new Date());
        coreMetadata1.setCreator("Carlos Ortiz");
        coreMetadata1.setFileId(UUID.randomUUID().toString());
        coreMetadata1.setName("TestNode");
        coreMetadata1.setModifier("Dejan Brkic");
        coreMetadata1.setLastModifiedDate(new Date());
        // Now lets do the copies
        CoreMetadata coreMetadata2 = coreMetadata1.copy();
        // Now Clone it
        CoreMetadata coreMetadata3 = (CoreMetadata) coreMetadata2.clone();
        //Test that everybody is the same but different
        assertEquals(coreMetadata2, coreMetadata1);
        assertEquals(coreMetadata1, coreMetadata3);
        assertEquals(coreMetadata2, coreMetadata3);
        //They have to be a Diff mem Ref
        assertFalse(coreMetadata2==coreMetadata1);
        assertFalse(coreMetadata1==coreMetadata3);
        assertFalse(coreMetadata2==coreMetadata3);
        //For coverage  (and quadruple check) :D
        assertEquals(coreMetadata1.toString(), coreMetadata2.toString());
    }
}
