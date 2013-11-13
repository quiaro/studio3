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

package org.craftercms.studio.impl.repository.mongodb.data;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import org.apache.commons.lang3.StringUtils;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.springframework.beans.factory.FactoryBean;


public class JongoCollectionFactory {


    private MongoClient mongo;
    private String database;
    private DB db;
    private String userName;
    private String password;
    private Jongo jongo;


    public MongoCollection getCollection(final String collectionName){
        return jongo.getCollection(collectionName);
    }

    public void init() {
        db = mongo.getDB(database);
        if (!StringUtils.isBlank(password)) {
            boolean authenticated = db.authenticate(userName, password.toCharArray());
            if (!authenticated) {
                throw new MongoException("Authentication fail for database " + database);
            }
        }
        jongo = new Jongo(db);
    }

    public DB getDatabase(){
        return db;
    }

    public void setMongo(MongoClient mongo) {
        this.mongo = mongo;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
