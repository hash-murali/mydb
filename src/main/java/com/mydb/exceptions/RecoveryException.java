package com.mydb.exceptions;

/**
 * Thrown during failure in recovery operations.
 */
public class RecoveryException extends StorageException {

    public RecoveryException(String message, Throwable cause) {
        super(message, cause);
    }
}
