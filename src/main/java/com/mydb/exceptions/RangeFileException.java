package com.mydb.exceptions;

/**
 * Thrown for corrupted or unreadable range files.
 */
public class RangeFileException extends StorageException {

    /**
     * Creates a range file exception with a message.
     *
     * @param message description of the corruption or read failure
     */
    public RangeFileException(String message) {
        super(message);
    }

    /**
     * Creates a range file exception with a message and cause.
     *
     * @param message description of the corruption or read failure
     * @param cause root cause explaining the failure
     */
    public RangeFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
