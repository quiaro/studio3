package org.craftercms.studio.impl.repository.mongodb.exceptions;

import org.craftercms.studio.commons.exception.StudioException;

/**
 * Base for all Repocitory Exceptions.
 */
public class MongoRepositoryException extends StudioException{


    public MongoRepositoryException(final String message, final Object... args) {
        super(message, args);
    }

    public MongoRepositoryException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
