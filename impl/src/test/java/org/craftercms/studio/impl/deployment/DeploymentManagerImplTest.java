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

package org.craftercms.studio.impl.deployment;

import org.apache.commons.lang.RandomStringUtils;
import org.craftercms.studio.commons.dto.Context;
import org.craftercms.studio.commons.exception.NotImplementedException;
import org.craftercms.studio.impl.AbstractManagerTest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Unit tests for Deployment Manager implementation
 */
public class DeploymentManagerImplTest extends AbstractManagerTest {

    @Autowired
    @InjectMocks
    private DeploymentManagerImpl deploymentManagerSUT;

    @Test(expected = NotImplementedException.class)
    public void testHistory() throws Exception {
        this.deploymentManagerSUT.history(null, RandomStringUtils.randomAlphabetic(10),
            createStringListMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testHistoryInvalidSite() throws Exception {
        this.deploymentManagerSUT.history(null, RandomStringUtils.randomAlphabetic(10),
            createStringListMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testChannels() throws Exception {
        this.deploymentManagerSUT.channels(null, RandomStringUtils.randomAlphabetic(10),
            RandomStringUtils.randomAlphabetic(10));
    }

    @Test(expected = NotImplementedException.class)
    public void testChannelsInvalidSite() throws Exception {
        this.deploymentManagerSUT.channels(null, RandomStringUtils.randomAlphabetic(10),
            RandomStringUtils.randomAlphabetic(10));
    }

    @Test(expected = NotImplementedException.class)
    public void testChannelsInvalidEnvironment() throws Exception {
        this.deploymentManagerSUT.channels(null, RandomStringUtils.randomAlphabetic(10),
            RandomStringUtils.randomAlphabetic(10));
    }

    @Test(expected = NotImplementedException.class)
    public void testUpdateChannel() throws Exception {
        this.deploymentManagerSUT.updateChannel(null, RandomStringUtils.randomAlphabetic(10),
            createDeploymentChannelMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testUpdateChannelInvalidSite() throws Exception {
        this.deploymentManagerSUT.updateChannel(null, RandomStringUtils.randomAlphabetic(10),
            createDeploymentChannelMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testUpdateChannelNewChannel() throws Exception {
        this.deploymentManagerSUT.updateChannel(null, RandomStringUtils.randomAlphabetic(10),
            createDeploymentChannelMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testUpdateChannelExistingChannel() throws Exception {
        this.deploymentManagerSUT.updateChannel(null, RandomStringUtils.randomAlphabetic(10),
            createDeploymentChannelMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testUpdateChannelInvalidChannel() throws Exception {
        this.deploymentManagerSUT.updateChannel(null, RandomStringUtils.randomAlphabetic(10),
            createDeploymentChannelMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testRemoveChannel() throws Exception {
        this.deploymentManagerSUT.removeChannel(null, RandomStringUtils.randomAlphabetic(10),
            createDeploymentChannelMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testRemoveChannelInvalidSite() throws Exception {
        this.deploymentManagerSUT.removeChannel(null, RandomStringUtils.randomAlphabetic(10),
            createDeploymentChannelMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testRemoveChannelInvalidChannel() throws Exception {
        this.deploymentManagerSUT.removeChannel(null, RandomStringUtils.randomAlphabetic(10),
            createDeploymentChannelMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testRemoveChannelChannelDoesNotExist() throws Exception {
        this.deploymentManagerSUT.removeChannel(null, RandomStringUtils.randomAlphabetic(10),
            createDeploymentChannelMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testDeploy() throws Exception {
        this.deploymentManagerSUT.deploy(null, RandomStringUtils.randomAlphabetic(10), createItemIdListMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testDeployInvalidSite() throws Exception {
        this.deploymentManagerSUT.deploy(null, RandomStringUtils.randomAlphabetic(10), createItemIdListMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testDeployInvalidItemList() throws Exception {
        this.deploymentManagerSUT.deploy(null, RandomStringUtils.randomAlphabetic(10), createItemIdListMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testDeployEmptyItemList() throws Exception {
        this.deploymentManagerSUT.deploy(null, RandomStringUtils.randomAlphabetic(10), createItemIdListMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testDeployItemDoesNotExist() throws Exception {
        this.deploymentManagerSUT.deploy(null, RandomStringUtils.randomAlphabetic(10), createItemIdListMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testStatus() throws Exception {
        this.deploymentManagerSUT.status(null, RandomStringUtils.randomAlphabetic(10),
            createDeploymentChannelMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testStatusInvalidSite() throws Exception {
        this.deploymentManagerSUT.status(null, RandomStringUtils.randomAlphabetic(10),
            createDeploymentChannelMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testStatusInvalidChannel() throws Exception {
        this.deploymentManagerSUT.status(null, RandomStringUtils.randomAlphabetic(10),
            createDeploymentChannelMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testStatusChannelUnavailable() throws Exception {
        this.deploymentManagerSUT.status(null, RandomStringUtils.randomAlphabetic(10),
            createDeploymentChannelMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testVersion() throws Exception {
        this.deploymentManagerSUT.version(null, RandomStringUtils.randomAlphabetic(10),
            createDeploymentChannelMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testVersionInvalidSite() throws Exception {
        this.deploymentManagerSUT.version(null, RandomStringUtils.randomAlphabetic(10),
            createDeploymentChannelMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testVersionInvalidChannel() throws Exception {
        this.deploymentManagerSUT.version(null, RandomStringUtils.randomAlphabetic(10),
            createDeploymentChannelMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testVersionChannelUnavailable() throws Exception {
        this.deploymentManagerSUT.version(null, RandomStringUtils.randomAlphabetic(10),
            createDeploymentChannelMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testAbort() throws Exception {
        this.deploymentManagerSUT.abort(null, RandomStringUtils.randomAlphabetic(10),
            createDeploymentChannelMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testAbortInvalidSite() throws Exception {
        this.deploymentManagerSUT.abort(null, RandomStringUtils.randomAlphabetic(10),
            createDeploymentChannelMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testAbortInvalidChannel() throws Exception {
        this.deploymentManagerSUT.abort(null, RandomStringUtils.randomAlphabetic(10),
            createDeploymentChannelMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testAbortChannelUnavailable() throws Exception {
        this.deploymentManagerSUT.abort(null, RandomStringUtils.randomAlphabetic(10),
            createDeploymentChannelMock());
    }
}
