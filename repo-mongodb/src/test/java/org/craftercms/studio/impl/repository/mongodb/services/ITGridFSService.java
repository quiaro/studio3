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

package org.craftercms.studio.impl.repository.mongodb.services;


import java.io.IOException;
import java.io.InputStream;

import com.mongodb.gridfs.GridFSFile;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Integration Test for GridFSServiceTest
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/craftercms/studio/craftercms-mongo-repository.xml")
public class ITGridFSService implements ApplicationContextAware {


    /**
     *  Ruler of the planet Omicron Persei 8
     */
    private static final String FILE_NAME="Lrrr";

    private ApplicationContext applicationContext;
    private GridFSService gridFSService;



    @Before
    public void setUp() throws Exception {
        gridFSService = applicationContext.getBean(GridFSService.class);
    }


    @Test
    public void testSaveFile() throws Exception {
        InputStream testInput = NodeServiceCreateFileTest.class.getResourceAsStream("/files/index.xml");
        testInput.mark(Integer.MAX_VALUE);
        String currentMD5=getMD5(testInput);
        testInput.reset();
        String fileId = gridFSService.saveFile(FILE_NAME, testInput);
        Assert.assertNotNull(fileId);
        InputStream stream = gridFSService.getFile(fileId);
        Assert.assertNotNull(stream);
        Assert.assertEquals(currentMD5,getMD5(stream));
    }


    private  String getMD5(InputStream io) throws IOException {
        Assert.assertNotNull(io);
     //   io.mark(Integer.MAX_VALUE);
        String MD5 = DigestUtils.md5Hex(io);
        return MD5;
    }
    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
