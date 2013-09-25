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

import java.io.InputStream;
import com.mongodb.gridfs.GridFSFile;
import org.craftercms.studio.impl.repository.mongodb.exceptions.MongoRepositoryException;

/**
 * Definition of GridFS service. <br/>
 * <b>This class is intended to be use internally only
 * usage outside this artifact could break or corrupt the repository</b>
 */
public interface GridFSService {

    /**
     * Saves a File in the GridFS.
     *
     * @param fileName File name
     * @param file     file input Stream
     * @return the GridFSFile representing the newly save file.
     * @throws MongoRepositoryException if something goes wrong while saving the file.
     * @throws IllegalArgumentException if filename is empty, null or blank <br/> or
     *                                  if InputStream is null.
     */
    GridFSFile saveFile(String fileName, InputStream file) throws MongoRepositoryException;

    /**
     * Gets a saved file InputStream.
     * @param fileId Id of the saved file, can't be null, empty or blank.
     * @return the files InputStream if found<br/> null if file with that id can't be found.
     * @throws IllegalArgumentException if filename is empty, null or blank
     * @throws MongoRepositoryException if unable to retrieve the file for store.
     */
    InputStream getFile(String fileId) throws MongoRepositoryException;
}
