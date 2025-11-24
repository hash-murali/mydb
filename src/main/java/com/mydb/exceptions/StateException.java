package com.mydb.exceptions;

/**
 * Thrown when the state log is corrupted or inconsistent.
 */
public class StateException extends StorageException {

    /**
     * Creates a state exception with a message.
     *
     * @param message description of state inconsistency
     */
    public StateException(String message) {
        super(message);
    }

    /**
     * Creates a state exception with a message and root cause.
     *
     * @param message description of state inconsistency
     * @param cause underlying problem triggering the failure
     */
    public StateException(String message, Throwable cause) {
        super(message, cause);
    }
}
