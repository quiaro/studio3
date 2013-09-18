package org.craftercms.studio.api;

/**
 * Throws an exception when repo does not support a given operation.
 */
public class NotImplementedException extends RuntimeException{
    /**
     * CTOR uses String#format to create Exception message.
     * @param message Format
     * @param args Format Args
     */
    public NotImplementedException(String message, Object... args) {
        super(String.format(message, args));
    }

    /**
     * CTOR.
     * @param message Message of the Exception
     */
    public NotImplementedException(final String message) {
        super(message);
    }
}
