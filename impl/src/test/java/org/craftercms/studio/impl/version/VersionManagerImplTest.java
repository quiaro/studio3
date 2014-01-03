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
import org.apache.commons.lang.StringUtils;
import org.craftercms.studio.api.content.VersionService;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.exception.ItemNotFoundException;
import org.craftercms.studio.commons.exception.NotImplementedException;
import org.craftercms.studio.impl.AbstractManagerTest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        when(this.versionServiceMock.getAllVersions(Mockito.anyString(), Mockito.anyString())).thenReturn
            (createItemListMock());

        String testItemId = UUID.randomUUID().toString();
        this.versionManagerSUT.history(null, testItemId);

        verify(this.versionServiceMock, times(1)).getAllVersions(Mockito.anyString(), Mockito.anyString());
    }

    //@Test(expected = ItemNotFoundException.class)
    public void testHistoryItemDoesNotExist() throws Exception {
        when(this.versionServiceMock.getAllVersions(Mockito.anyString(), Mockito.anyString())).thenThrow
            (ItemNotFoundException.class);

        String testItemId = UUID.randomUUID().toString();
        this.versionManagerSUT.history(null, testItemId);

        verify(this.versionServiceMock, times(1)).getAllVersions(Mockito.anyString(), Mockito.anyString());
    }

    //@Test(expected = IllegalArgumentException.class)
    public void testHistoryInvalidItemId() throws Exception {
        String testItemId = StringUtils.EMPTY;
        this.versionManagerSUT.history(null, testItemId);

        verify(this.versionServiceMock, times(0)).getAllVersions(Mockito.anyString(), Mockito.anyString());
    }

    //@Test(expected = NotImplementedException.class)
    public void testRevert() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        String testVersion = RandomStringUtils.randomAlphanumeric(3);
        this.versionManagerSUT.revert(null, testItemId, testVersion);
    }

    //@Test(expected = NotImplementedException.class)
    public void testRevertItemDoesNotExist() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        String testVersion = RandomStringUtils.randomAlphanumeric(3);
        this.versionManagerSUT.revert(null, testItemId, testVersion);
    }

    //@Test(expected = NotImplementedException.class)
    public void testRevertInvalidItemId() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        String testVersion = RandomStringUtils.randomAlphanumeric(3);
        this.versionManagerSUT.revert(null, testItemId, testVersion);
    }

    //@Test(expected = NotImplementedException.class)
    public void testRevertVersionDoesNotExist() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        String testVersion = RandomStringUtils.randomAlphanumeric(3);
        this.versionManagerSUT.revert(null, testItemId, testVersion);
    }

    //@Test(expected = NotImplementedException.class)
    public void testRevertInvalidVersion() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        String testVersion = RandomStringUtils.randomAlphanumeric(3);
        this.versionManagerSUT.revert(null, testItemId, testVersion);
    }

    //@Test(expected = NotImplementedException.class)
    public void testDiff() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        String testVersion1 = RandomStringUtils.randomAlphanumeric(3);
        String testVersion2 = RandomStringUtils.randomAlphanumeric(3);
        this.versionManagerSUT.diff(null, testItemId, testVersion1, testVersion2);
    }

    //@Test(expected = NotImplementedException.class)
    public void testDiffItemDoesNotExist() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        String testVersion1 = RandomStringUtils.randomAlphanumeric(3);
        String testVersion2 = RandomStringUtils.randomAlphanumeric(3);
        this.versionManagerSUT.diff(null, testItemId, testVersion1, testVersion2);
    }

    //@Test(expected = NotImplementedException.class)
    public void testDiffInvalidItemId() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        String testVersion1 = RandomStringUtils.randomAlphanumeric(3);
        String testVersion2 = RandomStringUtils.randomAlphanumeric(3);
        this.versionManagerSUT.diff(null, testItemId, testVersion1, testVersion2);
    }

    //@Test(expected = NotImplementedException.class)
    public void testDiffVersionDoesNotExist() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        String testVersion1 = RandomStringUtils.randomAlphanumeric(3);
        String testVersion2 = RandomStringUtils.randomAlphanumeric(3);
        this.versionManagerSUT.diff(null, testItemId, testVersion1, testVersion2);
    }

    //@Test(expected = NotImplementedException.class)
    public void testDiffInvalidVersion() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        String testVersion1 = RandomStringUtils.randomAlphanumeric(3);
        String testVersion2 = RandomStringUtils.randomAlphanumeric(3);
        this.versionManagerSUT.diff(null, testItemId, testVersion1, testVersion2);
    }

    //@Test(expected = NotImplementedException.class)
    public void testDiffSameVersion() throws Exception {
        String testItemId = UUID.randomUUID().toString();
        String testVersion1 = RandomStringUtils.randomAlphanumeric(3);
        String testVersion2 = RandomStringUtils.randomAlphanumeric(3);
        this.versionManagerSUT.diff(null, testItemId, testVersion1, testVersion2);
    }
}
