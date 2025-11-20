package com.mydb.exceptions;

/**
 * Thrown when index.csv is corrupted or inconsistent.
 */
public class IndexException extends StorageException {

    /**
     * Creates an index exception with a message.
     *
     * @param message description of index inconsistency
     */
    public IndexException(String message) {
        super(message);
    }

    /**
     * Creates an index exception with a message and root cause.
     *
     * @param message description of index inconsistency
     * @param cause underlying problem triggering the failure
     */
    public IndexException(String message, Throwable cause) {
        super(message, cause);
    }
}
