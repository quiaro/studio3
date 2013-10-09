/*
 * Copyright (C) 2007-2013 Crafter Software Corporation.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.craftercms.studio.impl.version;

import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.craftercms.studio.api.content.VersionService;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.exception.NotImplementedException;
import org.craftercms.studio.impl.AbstractManagerTest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Unit test for Version Manager implementation.
 *
 * @author Dejan Brkic
 */
public class VersionManagerImplTest extends AbstractManagerTest {

    @Autowired
    @Mock
    private VersionService versionServiceMock;

    @Autowired
    @InjectMocks
    private VersionManagerImpl versionManagerSUT;

    @Test(expected = NotImplementedException.class)
    public void testHistory() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        this.versionManagerSUT.history(new Context(), testItemId);
    }

    @Test(expected = NotImplementedException.class)
    public void testRevert() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        String testVersion = RandomStringUtils.randomAlphanumeric(3);
        this.versionManagerSUT.revert(new Context(), testItemId, testVersion);
    }

    public void testDiff() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        String testVersion1 = RandomStringUtils.randomAlphanumeric(3);
        String testVersion2 = RandomStringUtils.randomAlphanumeric(3);
        this.versionManagerSUT.diff(new Context(), testItemId, testVersion1, testVersion2);
    }
}
