package com.mydb.exceptions;

/**
 * Thrown when index.csv is corrupted or inconsistent.
 */
public class IndexException extends StorageException {

    public IndexException(String message) {
        super(message);
    }

    public IndexException(String message, Throwable cause) {
        super(message, cause);
    }
}
