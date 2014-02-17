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

package org.craftercms.studio.impl.blueprints;

import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.craftercms.studio.commons.exception.StudioException;
import org.craftercms.studio.impl.AbstractServiceTest;
import org.junit.After;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Unit tests for Blueprints Manager implementation.
 *
 * @author Dejan Brkic
 */
public class BlueprintsManagerImplTest extends AbstractServiceTest {

    @InjectMocks
    @Autowired
    private BlueprintsManagerImpl blueprintsManagerSUT;

    @After
    public void tearDown() throws Exception {

    }

    @Test(expected = StudioException.class)
    public void testList() throws Exception {
        this.blueprintsManagerSUT.list(null, createMapMock());
    }

    @Test(expected = StudioException.class)
    public void testRemove() throws Exception {
        this.blueprintsManagerSUT.remove(null, UUID.randomUUID().toString());
    }

    @Test(expected = StudioException.class)
    public void testRemoveInvalidBlueprintId() throws Exception {
        this.blueprintsManagerSUT.remove(null, UUID.randomUUID().toString());
    }

    @Test(expected = StudioException.class)
    public void testRemoveBlueprintDoesNotExist() throws Exception {
        this.blueprintsManagerSUT.remove(null, UUID.randomUUID().toString());
    }

    @Test(expected = StudioException.class)
    public void testInstall() throws Exception {
        this.blueprintsManagerSUT.install(null, RandomStringUtils.randomAlphabetic(20));
    }

    @Test(expected = StudioException.class)
    public void testCreateBlueprintFromSite() throws Exception {
        this.blueprintsManagerSUT.createBlueprintFromSite(null, RandomStringUtils.randomAlphabetic(10),
            RandomStringUtils.randomAlphabetic(20), RandomStringUtils.randomAlphabetic(150));
    }

    @Test(expected = StudioException.class)
    public void testCreateBlueprintFromSiteInvalidSite() throws Exception {
        this.blueprintsManagerSUT.createBlueprintFromSite(null, RandomStringUtils.randomAlphabetic(10),
            RandomStringUtils.randomAlphabetic(20), RandomStringUtils.randomAlphabetic(150));
    }

    @Test(expected = StudioException.class)
    public void testCreateBlueprintFromSiteInvalidBlueprintName() throws Exception {
        this.blueprintsManagerSUT.createBlueprintFromSite(null, RandomStringUtils.randomAlphabetic(10),
            RandomStringUtils.randomAlphabetic(20), RandomStringUtils.randomAlphabetic(150));
    }

    @Test(expected = StudioException.class)
    public void testCreateBlueprintFromSiteInvalidDestination() throws Exception {
        this.blueprintsManagerSUT.createBlueprintFromSite(null, RandomStringUtils.randomAlphabetic(10),
            RandomStringUtils.randomAlphabetic(20), RandomStringUtils.randomAlphabetic(150));
    }

    @Test(expected = StudioException.class)
    public void testCreateBlueprintFromSiteDestinationDoesNotExist() throws Exception {
        this.blueprintsManagerSUT.createBlueprintFromSite(null, RandomStringUtils.randomAlphabetic(10),
            RandomStringUtils.randomAlphabetic(20), RandomStringUtils.randomAlphabetic(150));
    }

    @Test(expected = StudioException.class)
    public void testCreateBlueprintFromSiteBlueprintExistsOnDestination() throws Exception {
        this.blueprintsManagerSUT.createBlueprintFromSite(null, RandomStringUtils.randomAlphabetic(10),
            RandomStringUtils.randomAlphabetic(20), RandomStringUtils.randomAlphabetic(150));
    }
}
