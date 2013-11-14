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

package org.craftercms.studio.impl.repository.mongodb;

public class MongoRepositoryQueries {

    /**
     * Gets node where ancestors $size is 0.
     */
    public static final String GET_ROOT_NODE = "studio.repo.mongodb.query.getRoot";
    /**
     * Gets a node where _id = a given string. Do is not the same as ObjectId.
     */
    public static final String GET_BY_GEN_ID = "studio.repo.mongodb.query";
    /**
     * Gets Nodes with the same parent.
     */
    public static final String GET_BY_ANCESTORS = "studio.repo.mongodb.query.getByAncestors";

    /**
     * Gets Nodes with a given tree path and a name.
     */
    public static final String GET_BY_ANCESTORS_AND_NAME = "studio.repo.mongodb.query.getByAncestorsAndName";
    public static final String GET_ANCESTORS = "studio.repo.mongodb.query.getAncestors";


    private MongoRepositoryQueries(){}
}
