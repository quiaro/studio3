package org.craftercms.studio.impl.repository.mongodb.exceptions;

import org.craftercms.studio.repo.RepositoryException;

/**
 * Base for all Repository Exceptions.
 */
public class MongoRepositoryException extends RepositoryException{

    private static final long serialVersionUID = 5839280102793236386L;

    public MongoRepositoryException(final Throwable cause, String ... args) {
        super(cause, args);
    }

    public MongoRepositoryException(String ... args) {
        super(args);
    }
}
