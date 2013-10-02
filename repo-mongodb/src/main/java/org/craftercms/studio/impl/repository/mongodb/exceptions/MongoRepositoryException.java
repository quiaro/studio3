package org.craftercms.studio.impl.repository.mongodb.exceptions;

import org.craftercms.studio.api.RepositoryException;

/**
 * Base for all Repository Exceptions.
 */
public class MongoRepositoryException extends RepositoryException{


    public MongoRepositoryException(final String message, final Object... args) {
        super(message, args);
    }

    public MongoRepositoryException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
