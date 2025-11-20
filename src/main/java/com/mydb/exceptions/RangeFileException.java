package com.mydb.exceptions;

/**
 * Thrown for corrupted or unreadable range files.
 */
public class RangeFileException extends StorageException {

    public RangeFileException(String message) {
        super(message);
    }

    public RangeFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
