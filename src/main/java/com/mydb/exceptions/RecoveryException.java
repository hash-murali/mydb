package com.mydb.exceptions;

/**
 * Thrown during failure in recovery operations.
 */
public class RecoveryException extends StorageException {

    /**
     * Creates a recovery exception with context and cause.
     *
     * @param message description of the recovery failure
     * @param cause underlying problem encountered during recovery
     */
    public RecoveryException(String message, Throwable cause) {
        super(message, cause);
    }
}
