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

package org.craftercms.studio.impl.forms;

import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.craftercms.studio.commons.exception.NotImplementedException;
import org.craftercms.studio.impl.AbstractServiceTest;
import org.junit.After;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Unit test for Forms Manager implementation.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public class FormServiceImplTest extends AbstractServiceTest {

    @InjectMocks
    @Autowired
    private FormServiceImpl formsManagerSUT;

    @After
    public void tearDown() throws Exception {

    }

    @Test(expected = NotImplementedException.class)
    public void testList() throws Exception {
        this.formsManagerSUT.list(null, RandomStringUtils.randomAlphabetic(10), createStringListMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testListInvalidSite() throws Exception {
        this.formsManagerSUT.list(null, RandomStringUtils.randomAlphabetic(10), createStringListMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testUpdate() throws Exception {
        this.formsManagerSUT.update(null, RandomStringUtils.randomAlphabetic(10), createFormDefinitionMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testUpdateInvalidSite() throws Exception {
        this.formsManagerSUT.update(null, RandomStringUtils.randomAlphabetic(10), createFormDefinitionMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testUpdateInvalidFormDefinition() throws Exception {
        this.formsManagerSUT.update(null, RandomStringUtils.randomAlphabetic(10), createFormDefinitionMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testUpdateNewFormDefinition() throws Exception {
        this.formsManagerSUT.update(null, RandomStringUtils.randomAlphabetic(10), createFormDefinitionMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testUpdateExistingFormDefinition() throws Exception {
        this.formsManagerSUT.update(null, RandomStringUtils.randomAlphabetic(10), createFormDefinitionMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testRemove() throws Exception {
        this.formsManagerSUT.remove(null, RandomStringUtils.randomAlphabetic(10), UUID.randomUUID().toString());
    }

    @Test(expected = NotImplementedException.class)
    public void testRemoveInvalidSite() throws Exception {
        this.formsManagerSUT.remove(null, RandomStringUtils.randomAlphabetic(10), UUID.randomUUID().toString());
    }

    @Test(expected = NotImplementedException.class)
    public void testRemoveInvalidFormId() throws Exception {
        this.formsManagerSUT.remove(null, RandomStringUtils.randomAlphabetic(10), UUID.randomUUID().toString());
    }

    @Test(expected = NotImplementedException.class)
    public void testRemoveFormDoesNotExist() throws Exception {
        this.formsManagerSUT.remove(null, RandomStringUtils.randomAlphabetic(10), UUID.randomUUID().toString());
    }

    @Test(expected = NotImplementedException.class)
    public void testCopy() throws Exception {
        this.formsManagerSUT.copy(null, RandomStringUtils.randomAlphabetic(10),
            RandomStringUtils.randomAlphabetic(150), RandomStringUtils.randomAlphabetic(150));
    }

    @Test(expected = NotImplementedException.class)
    public void testCopyInvalidSite() throws Exception {
        this.formsManagerSUT.copy(null, RandomStringUtils.randomAlphabetic(10),
            RandomStringUtils.randomAlphabetic(150), RandomStringUtils.randomAlphabetic(150));
    }

    @Test(expected = NotImplementedException.class)
    public void testCopyInvalidSource() throws Exception {
        this.formsManagerSUT.copy(null, RandomStringUtils.randomAlphabetic(10),
            RandomStringUtils.randomAlphabetic(150), RandomStringUtils.randomAlphabetic(150));
    }

    @Test(expected = NotImplementedException.class)
    public void testCopyEmptySource() throws Exception {
        this.formsManagerSUT.copy(null, RandomStringUtils.randomAlphabetic(10),
            RandomStringUtils.randomAlphabetic(150), RandomStringUtils.randomAlphabetic(150));
    }

    @Test(expected = NotImplementedException.class)
    public void testCopyInvalidDestination() throws Exception {
        this.formsManagerSUT.copy(null, RandomStringUtils.randomAlphabetic(10),
            RandomStringUtils.randomAlphabetic(150), RandomStringUtils.randomAlphabetic(150));
    }

    @Test(expected = NotImplementedException.class)
    public void testCopyDestinationDoesNotExist() throws Exception {
        this.formsManagerSUT.copy(null, RandomStringUtils.randomAlphabetic(10),
            RandomStringUtils.randomAlphabetic(150), RandomStringUtils.randomAlphabetic(150));
    }

    @Test(expected = NotImplementedException.class)
    public void testCopySourceExistsAtDestination() throws Exception {
        this.formsManagerSUT.copy(null, RandomStringUtils.randomAlphabetic(10),
            RandomStringUtils.randomAlphabetic(150), RandomStringUtils.randomAlphabetic(150));
    }
}
