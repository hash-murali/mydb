package com.mydb.exceptions;

import java.io.IOException;

/**
 * Wraps IOException into a storage-specific runtime exception.
 * Used when file operations fail.
 */
public class IOStorageException extends StorageException {

    public IOStorageException(String message, IOException cause) {
        super(message, cause);
    }
}
