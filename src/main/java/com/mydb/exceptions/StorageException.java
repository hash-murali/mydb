package com.mydb.exceptions;

/**
 * Base class for all storage engine runtime exceptions.
 * All other custom exceptions extend this.
 */
public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
