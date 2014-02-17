package org.craftercms.studio.impl.repository.mongodb.data;


import com.mongodb.CommandResult;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.craftercms.studio.impl.repository.mongodb.MongoRepositoryQueries;
import org.craftercms.studio.impl.repository.mongodb.domain.Node;
import org.craftercms.studio.impl.repository.mongodb.exceptions.MongoRepositoryException;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple interface to interact with Jongo/MongoDB.<br/>
 * Changes MongoException in to MongoRepositoryException (which is a checked exception).As well if Command
 * result is not ok (CommandResult#isOk) return false a exception will be thrown the message for that exception will
 * be CommandResult#getErrorMessage.<br/>
 * Some of the find and insert methods use a template queryName. this means that the string
 * can contain placeholders ('#') this will allow the user to have predefine json strings
 * that will be substitute with the given params.<b>Params are not Name</b> therefor if the same value is needed
 * multiple times for now it has to be send multiple times.<b>Order of the params</b> should match the same in
 * the json string.
 */
public class MongodbDataService {


    /**
     * JongoCollectionFactory.
     */
    private JongoCollectionFactory jongoCollectionFactory;
    /**
     * Logger.
     */
    private Logger log = LoggerFactory.getLogger(MongodbDataService.class);
    private JongoQueries jongoQueries;

    /**
     * Saves in the given collection , the given Pojo.
     *
     * @param collectionName Collection where the Pojo will be save.
     * @param toSave         Object in the given collection.
     * @param <T>            Type of the Object (should be a simple POJO.
     * @throws MongoRepositoryException If any error happen while saving or if
     *                                  CommandResult.ok return false.
     */
    public <T> void save(final String collectionName, final T toSave) throws MongoRepositoryException {
        try {
            log.debug("About to save {} in to collection {}", toSave, collectionName);
            WriteResult writeResult = jongoCollectionFactory.getCollection(collectionName).save(toSave);
            log.debug("Saving send to mongodb checking result");
            checkComandResult(writeResult.getLastError());
        } catch (MongoException ex) {
            log.debug("Something went wrong while trying to save into mongodb ", ex);
            throw new MongoRepositoryException(ex);
        }
    }

    /**
     * Inserts in the given collection the json <i>"As Is"</i><br/>
     * Json String can contain placeholders this will allow the user to have predefine json strings<br/>
     * that will be substitute with the given params.<b>Params are not Name</b> therefor if the same value is needed
     * multiple times for now it has to be send multiple times.<b>Order of the params</b> should match the same in
     * the json string.<br/>Example<br/>
     * <code>
     * String Json ="{name:#,address: #,age:#}" <br/>
     * save("testCollection",Json,"Dr. John A. Zoidberg", new Address(), 125);
     * </code>
     *
     * @param collectionName Collection where the json string will be save
     * @param queryName      Name of the Query to be look in default-queries.xml or custom-queries.properties
     * @param params         Params of the json.
     * @throws MongoRepositoryException
     */
    public void save(String collectionName, final String queryName, final Object... params) throws
        MongoRepositoryException {
        try {

            log.debug("About to save {} in to collection {}", queryName, collectionName);
            WriteResult writeResult = jongoCollectionFactory.getCollection(collectionName).insert(getQuery(queryName)
                , params);
            checkComandResult(writeResult.getLastError());
        } catch (MongoException ex) {
            log.debug("Something went wrong while trying to save into mongodb ", ex);
            throw new MongoRepositoryException(ex);
        }
    }

    /**
     * Gets all documents of a the given collection. Tries to convert them in to Instances of the given class.
     * <b>Order of the params</b> should match the same in
     * the json string.<br/>Example<br/>
     * <code>
     * String Json ="{name:#,address: #,age:#}" <br/>
     * find("testCollection",Json,"Dr. John A. Zoidberg", new Address(), 125);
     * </code>
     *
     * @param collectionName Collection where the documents will be retrieve from.
     * @param clazz          Class in which documents will be map to.
     * @param <T>            Type of the expected result.
     * @return A Iterable Instance of all the documents.<b>This is lazy loaded</b>
     * @throws MongoRepositoryException If couldn't search for the documents. or a mapping exception happen.
     */
    public <T> Iterable<T> findAll(final String collectionName, final Class<T> clazz) throws MongoRepositoryException {
        return internalFind(collectionName, clazz, null);
    }

    /**
     * Search all documents in the given collection that match the queryName.
     *
     * @param collectionName Collection where the queryName will be run.
     * @param queryName      Name of the Query to be look in default-queries.xml or custom-queries.properties
     * @param clazz          Class in which documents will be map to.
     * @param <T>            Type of the expected result.
     * @return A Iterable Instance of all the documents that match the queryName.<b>This is lazy loaded</b>
     * @throws MongoRepositoryException
     */
    public <T> Iterable<T> find(final String collectionName, final String queryName,
                                final Class<T> clazz) throws MongoRepositoryException {
        return internalFind(collectionName, clazz, queryName);
    }

    /**
     * Finds all documents of the given collection that match the template queryName<br/>
     *
     * @param collectionName Collection where the queryName will be run.
     * @param queryName      Name of the  Template Query to be look in default-queries.xml or custom-queries
     *                       .properties
     * @param params         Params to be use in the template queryName. Must match the order of the templates
     *                       .<br/><b>Template
     *                       params are not named, therefor they have to be send multiple times if needed</b>
     * @param clazz          Class in which documents will be map to.
     * @param <T>            Type of the expected result.
     * @return A Iterable Instance of all the documents.<b>This is lazy loaded</b>
     * @throws MongoRepositoryException If couldn't search for the documents. or a mapping exception happen.
     */
    public <T> Iterable<T> find(final String collectionName, final Class<T> clazz, final String queryName,
                                Object... params) throws MongoRepositoryException {
        return internalFind(collectionName, clazz, queryName, params);
    }

    /**
     * Search for documents of the given collection that match the queryName.<br>In only return the first document</br>
     *
     * @param collectionName Collection where the documents will be search for.
     * @param queryName      Name of the  Template Query to be look in default-queries.xml or custom-queries
     *                       .properties
     * @param clazz          Class in which documents will be map to.
     * @return A instance of the given class. Null if nothing is found.
     * @throws MongoRepositoryException If couldn't search for the documents. or a mapping exception happen.
     */
    public Node findOne(final String collectionName, final Class<Node> clazz, final String queryName,
                        Object... params) throws MongoRepositoryException {
        try {
            return jongoCollectionFactory.getCollection(collectionName).findOne(getQuery(queryName), params).as(clazz);
        } catch (MongoException ex) {
            log.error("Unable to find one with queryName {}", queryName);
            throw new MongoRepositoryException(ex);
        }
    }

    /**
     * Search for documents of the given collection that match the queryName.<br>In only return the first document</br>
     *
     * @param collectionName Collection where the documents will be search for.
     * @param queryName      Name of the  Template Query to be look in default-queries.xml or custom-queries
     *                       .properties
     * @param clazz          Class in which documents will be map to.
     * @return A instance of the given class. Null if nothing is found.
     * @throws MongoRepositoryException If couldn't search for the documents. or a mapping exception happen.
     */
    public Node findOne(final String collectionName, final String queryName, final Class<Node> clazz) throws
        MongoRepositoryException {
        try {
            return jongoCollectionFactory.getCollection(collectionName).findOne(getQuery(queryName)).as(clazz);
        } catch (MongoException ex) {
            log.error("Unable to find one with queryName {}", queryName);
            throw new MongoRepositoryException(ex, queryName);
        }
    }

    /**
     * Search for a document with the given Object Id in the given collection.<br/>
     * <i>And Object id is the default BSON ObjectId</i>
     *
     * @param collectionName Collection where the documents will be search for.
     * @param objectId       objectId of the object to be retrieve from the collection.
     * @param clazz          Class in which documents will be map to.
     * @return A instance of the given class. Null if nothing is found.
     * @throws MongoRepositoryException If couldn't search for the documents. or a mapping exception happen.
     */
    public Node findByObjectId(final String collectionName, final String objectId,
                               final Class<Node> clazz) throws MongoRepositoryException {
        try {
            return jongoCollectionFactory.getCollection(collectionName).findOne(new ObjectId(objectId)).as(clazz);
        } catch (MongoException ex) {
            log.error("Unable to find one with queryName {}", objectId);
            throw new MongoRepositoryException(ex);
        }
    }

    /**
     * Search for a document with the given Id in the given collection.<br/>
     * <i>Id is a system generate Id, <b>this is not a ObjectId</b></i>
     *
     * @param collectionName Collection where the documents will be search for.
     * @param id             Gid of the object to be retrieve from the collection.
     * @param clazz          Class in which documents will be map to.
     * @return A instance of the given class. Null if nothing is found.
     * @throws MongoRepositoryException If couldn't search for the documents. or a mapping exception happen.
     */
    public Node findById(final String collectionName, final String id, final Class<Node> clazz) throws
        MongoRepositoryException {
        try {
            return jongoCollectionFactory.getCollection(collectionName).findOne(getQuery(MongoRepositoryQueries
                .GET_BY_GEN_ID), id).as(clazz);
        } catch (MongoException ex) {
            log.error("Unable to find one with queryName {}", id);
            throw new MongoRepositoryException(ex, id);
        }
    }


    /**
     * Search all documents in the given collection that match the queryName.<ul>
     * <li>If params are null or empty it assumes that its not a template queryName</li>
     * <li>If queryName is empty or null will preform a findAll</li>
     * <li>If Query is not null or empty and Params length is atleast one will assume that is a tempalte queryName</li>
     * </ul>
     *
     * @param collectionName Collection where the documents will be search for.
     * @param clazz          Class in which documents will be map to.
     * @param queryName      Name of the Query to be look in default-queries.xml or custom-queries.properties
     * @param params         Params to be use in the template queryName. Must match the order of the templates
     *                       .<br/><b>Template
     *                       params are not named, therefor they have to be send multiple times if needed</b>
     * @param <T>            Type of the expected result.
     * @return A Iterable Instance of all the documents.<b>This is lazy loaded</b>
     * @throws MongoRepositoryException If couldn't search for the documents. or a mapping exception happen.
     */
    protected <T> Iterable<T> internalFind(final String collectionName, final Class<T> clazz, final String queryName,
                                           final Object... params) throws MongoRepositoryException {
        try {
            MongoCollection collection = jongoCollectionFactory.getCollection(collectionName);
            if (StringUtils.isBlank(queryName)) {
                return collection.find().as(clazz);
            } else if (params == null || params.length == 0) {
                return collection.find(getQuery(queryName)).as(clazz);
            } else {
                return collection.find(getQuery(queryName), params).as(clazz);
            }
        } catch (MongoException ex) {
            log.debug("Unable to search due a error ", ex);
            throw new MongoRepositoryException(ex);
        }
    }


    public <T> Iterable<T> aggregation(final String collectionName, final Class<T> clazz, final String queryName,
                                       final String sortQuery, final Object... params) throws MongoRepositoryException {
        try {
            MongoCollection collection = jongoCollectionFactory.getCollection(collectionName);
            return collection.aggregate(getQuery(queryName), params).and(getQuery(sortQuery)).as(clazz);
        } catch (MongoException ex) {
            throw new MongoRepositoryException(ex);
        }

    }


    /**
     * Internal checks if the CommandResult is ok , if not will throw a MongoRepositoryException with the last error
     * message given by CommandResult#getErrorMessage as the exception  message.
     *
     * @param lastError Command to be check.
     * @throws MongoRepositoryException if CommandResult#Ok is false.
     */
    private void checkComandResult(final CommandResult lastError) throws MongoRepositoryException {
        log.debug("Saving send to mongodb checking result");
        log.debug("Result is {}", lastError.ok()? "OK": lastError.getErrorMessage());
        if (!lastError.ok()) {
            log.error("Unable to save into mongodb due " + lastError.getErrorMessage(), lastError.getException());
            throw new MongoRepositoryException(lastError.getException());
        }

    }

    public String getQuery(String queryName) throws MongoRepositoryException {
        String query = jongoQueries.get(queryName);
        if (StringUtils.isBlank(query)) {
            log.debug("Query with name {} can't be found or is empty", queryName);
            throw new MongoRepositoryException();
        } else {
            return query.trim();
        }
    }

    /**
     * Sets the JongoCollection Factory.
     *
     * @param jongoCollectionFactory
     */
    public void setJongoCollectionFactory(JongoCollectionFactory jongoCollectionFactory) {
        this.jongoCollectionFactory = jongoCollectionFactory;
    }


    public void setJongoQueries(JongoQueries jongoQueries) {
        this.jongoQueries = jongoQueries;
    }


    public MongoCollection getCollection(final String nodesCollection) {
        return jongoCollectionFactory.getCollection(nodesCollection);
    }
}
