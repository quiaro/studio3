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

package org.craftercms.studio.impl.audit;

import org.apache.commons.lang.RandomStringUtils;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.impl.AbstractServiceTest;
import org.junit.After;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Unit tests for Audit Manager implementation.
 *
 * @author Dejan Brkic
 */
public class AuditManagerImplTest extends AbstractServiceTest {

    @InjectMocks
    @Autowired
    private AuditServiceImpl auditManagerSUT;

    @Override
    protected void resetMocks() {

    }

    @Test(expected = StudioException.class)
    public void testGetActivities() throws Exception {
        this.auditManagerSUT.getActivities(null, RandomStringUtils.randomAlphabetic(10),
            createStringListMock());
    }

    @Test(expected = StudioException.class)
    public void testGetActivitiesInvalidSite() throws Exception {
        this.auditManagerSUT.getActivities(null, RandomStringUtils.randomAlphabetic(10),
            createStringListMock());
    }

    @Test(expected = StudioException.class)
    public void testLogActivity() throws Exception {
        this.auditManagerSUT.logActivity(null, RandomStringUtils.randomAlphabetic(10), createActivityMock());
    }

    @Test(expected = StudioException.class)
    public void testLogActivityInvalidSite() throws Exception {
        this.auditManagerSUT.logActivity(null, RandomStringUtils.randomAlphabetic(10), createActivityMock());
    }

    @Test(expected = StudioException.class)
    public void testLogActivityInvalidActivity() throws Exception {
        this.auditManagerSUT.logActivity(null, RandomStringUtils.randomAlphabetic(10), createActivityMock());
    }
}
