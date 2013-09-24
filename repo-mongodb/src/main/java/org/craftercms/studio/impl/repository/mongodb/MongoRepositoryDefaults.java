package org.craftercms.studio.impl.repository.mongodb;

/**
 * Holds all Constants.
 */
public final class MongoRepositoryDefaults {

    /**
     * Default Repository username.
     */
    public static final String SYSTEM_USER_NAME = "System";
    /**
     * MongoDB Collection ID key for document.
     */
    public static final String MONGODB_ID_KEY = "_id";
    public static final String REPO_DEFAULT_CONTENT_FOLDER = "content";
    public static final String REPO_DEFAULT_CONFIG_FOLDER = "config";
    public static final String REPO_DEFAULT_PATH_SEPARATOR_CHAR = "/";

    /**
     * Make sure nobody creates a instance of this class.
     */
    private MongoRepositoryDefaults() {
    }
}
