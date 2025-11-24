package com.mydb.exceptions;

import java.io.IOException;

/**
 * Wraps IOException into a storage-specific runtime exception.
 * Used when file operations fail.
 */
public class IOStorageException extends StorageException {

    /**
     * Constructs a new IO exception wrapper.
     *
     * @param message description of the IO failure context
     * @param cause original {@link IOException} thrown by the system
     */
    public IOStorageException(String message, IOException cause) {
        super(message, cause);
    }
}
