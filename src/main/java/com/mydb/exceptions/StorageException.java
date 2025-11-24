package com.mydb.exceptions;

/**
 * Base class for all storage engine runtime exceptions.
 * All other custom exceptions extend this.
 */
public class StorageException extends RuntimeException {

    /**
     * Creates a new storage exception with a descriptive message.
     *
     * @param message explanation of the failure
     */
    public StorageException(String message) {
        super(message);
    }

    /**
     * Creates a new storage exception with a descriptive message and root cause.
     *
     * @param message explanation of the failure
     * @param cause underlying triggering exception
     */
    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
