package com.mydb.exceptions;

/**
 * Thrown for corrupted or unreadable batch files.
 * <p>
 * These exceptions surface when a batch file fails validation or cannot be parsed, replacing the
 * previous "range" terminology to reflect the updated batching semantics.
 */
public class BatchFileException extends StorageException {

    /**
     * Creates a batch file exception with a message.
     *
     * @param message description of the corruption or read failure
     */
    public BatchFileException(String message) {
        super(message);
    }

    /**
     * Creates a batch file exception with a message and cause.
     *
     * @param message description of the corruption or read failure
     * @param cause root cause explaining the failure
     */
    public BatchFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
