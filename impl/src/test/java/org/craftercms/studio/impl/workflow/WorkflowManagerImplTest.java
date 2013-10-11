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

package org.craftercms.studio.impl.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.craftercms.studio.commons.exception.NotImplementedException;
import org.craftercms.studio.impl.AbstractManagerTest;
import org.junit.After;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Unit tests for Workflow Manager Implementation.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public class WorkflowManagerImplTest extends AbstractManagerTest {

    @Autowired
    @InjectMocks
    private WorkflowManagerImpl workflowManagerSUT;

    @After
    public void tearDown() throws Exception {

    }

    @Test(expected = NotImplementedException.class)
    public void testStart() throws Exception {
        this.workflowManagerSUT.start(RandomStringUtils.randomAlphabetic(10), createComments(), createItemListMock());
    }

    private List<String> createComments() {
        List<String> comments = new ArrayList<String>();
        for (int i = 0;  i < 1 + Math.random() * (10 * Math.random()); i++) {
            comments.add(RandomStringUtils.randomAlphanumeric((int)(50 + Math.random() * 150)));
        }
        return comments;
    }

    @Test(expected = NotImplementedException.class)
    public void testStartInvalidPackage() throws Exception {
        this.workflowManagerSUT.start(RandomStringUtils.randomAlphabetic(10), createComments(), createItemListMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testStartInvalidItem() throws Exception {
        this.workflowManagerSUT.start(RandomStringUtils.randomAlphabetic(10), createComments(), createItemListMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testGetPackage() throws Exception {
        this.workflowManagerSUT.getPackage(RandomStringUtils.randomAlphabetic(10));
    }

    @Test(expected = NotImplementedException.class)
    public void testGetPackageInvalidPackageId() throws Exception {
        this.workflowManagerSUT.getPackage(RandomStringUtils.randomAlphabetic(10));
    }

    @Test(expected = NotImplementedException.class)
    public void testGetPackagePackageDoesNotExist() throws Exception {
        this.workflowManagerSUT.getPackage(RandomStringUtils.randomAlphabetic(10));
    }

    @Test(expected = NotImplementedException.class)
    public void testGetPackages() throws Exception {
        this.workflowManagerSUT.getPackages(RandomStringUtils.randomAlphabetic(10), createWorkflowPackageListMock());
    }

    @Test(expected = NotImplementedException.class)
    public void testGetTransitions() throws Exception {
        this.workflowManagerSUT.getTransitions(RandomStringUtils.randomAlphabetic(10));
    }

    @Test(expected = NotImplementedException.class)
    public void testGetTransitionsInvalidPackageId() throws Exception {
        this.workflowManagerSUT.getTransitions(RandomStringUtils.randomAlphabetic(10));
    }

    @Test(expected = NotImplementedException.class)
    public void testGetTransitionsPackageDoesNotExist() throws Exception {
        this.workflowManagerSUT.getTransitions(RandomStringUtils.randomAlphabetic(10));
    }

    @Test(expected = NotImplementedException.class)
    public void testTransition() throws Exception {
        this.workflowManagerSUT.transition(RandomStringUtils.randomAlphabetic(10), createWorkflowTransitionMock(),
            createWorkflowParameters());
    }

    private Map<String, Object> createWorkflowParameters() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("param1", "param1");
        params.put("param2", "param2");
        return params;
    }

    @Test(expected = NotImplementedException.class)
    public void testTransitionPackageDoesNotExist() throws Exception {
        this.workflowManagerSUT.transition(RandomStringUtils.randomAlphabetic(10), createWorkflowTransitionMock(),
            createWorkflowParameters());
    }

    @Test(expected = NotImplementedException.class)
    public void testTransitionInvalidPackageId() throws Exception {
        this.workflowManagerSUT.transition(RandomStringUtils.randomAlphabetic(10), createWorkflowTransitionMock(),
            createWorkflowParameters());
    }

    @Test(expected = NotImplementedException.class)
    public void testTransitionInvalidTransition() throws Exception {
        this.workflowManagerSUT.transition(RandomStringUtils.randomAlphabetic(10), createWorkflowTransitionMock(),
            createWorkflowParameters());
    }

    @Test(expected = NotImplementedException.class)
    public void testTransitionIllegalTransition() throws Exception {
        this.workflowManagerSUT.transition(RandomStringUtils.randomAlphabetic(10), createWorkflowTransitionMock(),
            createWorkflowParameters());
    }

    @Test(expected = NotImplementedException.class)
    public void testCancel() throws Exception {
        this.workflowManagerSUT.cancel(RandomStringUtils.randomAlphabetic(10));
    }

    @Test(expected = NotImplementedException.class)
    public void testCancelPackageDoesNotExist() throws Exception {
        this.workflowManagerSUT.cancel(RandomStringUtils.randomAlphabetic(10));
    }

    @Test(expected = NotImplementedException.class)
    public void testCancelInvalidPackageId() throws Exception {
        this.workflowManagerSUT.cancel(RandomStringUtils.randomAlphabetic(10));
    }
}
