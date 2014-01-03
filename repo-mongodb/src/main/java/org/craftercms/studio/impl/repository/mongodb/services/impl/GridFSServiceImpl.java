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

package org.craftercms.studio.impl.repository.mongodb.services.impl;

import java.io.InputStream;

import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.craftercms.studio.impl.repository.mongodb.data.JongoCollectionFactory;
import org.craftercms.studio.impl.repository.mongodb.exceptions.MongoRepositoryException;
import org.craftercms.studio.impl.repository.mongodb.services.GridFSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default GridFS implementation.
 */
public class GridFSServiceImpl implements GridFSService {

    /**
     * Logger.
     */
    private Logger log = LoggerFactory.getLogger(GridFSServiceImpl.class);
    /**
     * Mongo client. Jongo does not have interface for GridFS therefor all
     * GridFs actions are done using the Driver.
     */
    private GridFS gridFs;

    /**
     * Spring GridFsTemplate.
     */

    @Override
    public String saveFile(final String fileName, final InputStream fileInputStream) throws
        MongoRepositoryException {
        if (StringUtils.isBlank(fileName)) {
            log.error("Given fileInputStream name is null, empty or blank");
            throw new IllegalArgumentException("File name is either null,empty or blank");
        }
        if (fileInputStream == null) {
            log.error("Given inputStream is null");
            throw new IllegalArgumentException("Given File inputStream is null");
        }
        try {
            GridFSInputFile file = gridFs.createFile(fileInputStream, fileName, true);
            file.save();
            return file.getId().toString();
        } catch (MongoException ex) {
            log.error("Unable to save \"" + fileName + "\"file in GridFs due a error", ex);
            throw new MongoRepositoryException("Unable to save File", ex);
        }
    }

    @Override
    public InputStream getFile(final String fileId) throws MongoRepositoryException {
        if (StringUtils.isBlank(fileId)) {
            log.error("Given Id for getting a fileInputStream is null, empty or blank, check if node is a Folder");
            throw new IllegalArgumentException("File id is either null,empty or blank");
        }
        log.debug("Getting fileInputStream with Id {}", fileId);
        try {
            GridFSDBFile foundFile = gridFs.findOne(new ObjectId(fileId));
            if (foundFile == null) {
                log.debug("Unable to find a fileInputStream with id {}", fileId);
                log.debug("If this Id was obtain using a node, this node may be broken");
                return null;
            } else {
                log.debug("Found fileInputStream with id {}, named {} and md5 is {}", fileId,
                    foundFile.getFilename(), foundFile.getMD5());
                return foundFile.getInputStream();
            }
        } catch (MongoException ex) {
            log.error("Unable to get File with id {} due a MongoException {} ", fileId, ex.getMessage());
            log.error("DataAccessException is ", ex);
            throw new MongoRepositoryException("Unable to get File", ex);
        }
    }

    public void setJongoCollectionFactory(JongoCollectionFactory jongoCollectionFactory) {
        this.gridFs = new GridFS(jongoCollectionFactory.getDatabase());
    }
}
