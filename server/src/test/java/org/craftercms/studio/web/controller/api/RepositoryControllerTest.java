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
package org.craftercms.studio.web.controller.api;

import org.apache.commons.io.IOUtils;
import org.craftercms.studio.api.content.ContentManager;
import org.craftercms.studio.api.dto.Context;

import org.craftercms.studio.controller.services.rest.RepositoryController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Unit test for RepositoryController.
 *
 * @author Sumer Jabri
 * @author Dejan Brkic
 * @author Carlos Ortiz
 */
public class RepositoryControllerTest {

    private ContentManager contentManager;

    private RepositoryController repositoryController;

    @Before
    public void setup() {
        this.repositoryController = new RepositoryController();
        this.contentManager = mock(ContentManager.class);
    }

    @Test
    public void testGetContent() throws Exception {
        when(this.contentManager.read((Context) Mockito.any(), (String)Mockito.any())).thenReturn(IOUtils.toInputStream("TEST"));

        standaloneSetup(this.repositoryController)
                .build()
                .perform(
                        get("/api/1/repository/read?itemId=1&version=1").accept(MediaType.ALL))
                .andExpect(status().isOk());

        verify(this.contentManager, times(1)).read((Context)Mockito.any(), Mockito.anyString());
    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testOpenForEdit() throws Exception {

    }

    @Test
    public void testSaveContent() throws Exception {

    }

    @Test
    public void testClose() throws Exception {

    }

    @Test
    public void testDeleteContent() throws Exception {

    }

    @Test
    public void testCopy() throws Exception {

    }

    @Test
    public void testMove() throws Exception {

    }

    @Test
    public void testLock() throws Exception {

    }

    @Test
    public void testUnlock() throws Exception {

    }

    @Test
    public void testGetLockStatus() throws Exception {

    }

    @Test
    public void testGetChildren() throws Exception {

    }

    @Test
    public void testGetTree() throws Exception {

    }

    @Test
    public void testGetSites() throws Exception {

    }
}
