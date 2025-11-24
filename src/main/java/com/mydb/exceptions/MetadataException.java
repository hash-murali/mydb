package com.mydb.exceptions;

/**
 * Thrown when loading or saving metadata.json fails.
 */
public class MetadataException extends StorageException {

    /**
     * Constructs a metadata exception with context and root cause.
     *
     * @param message description of the metadata failure
     * @param cause underlying exception thrown during processing
     */
    public MetadataException(String message, Throwable cause) {
        super(message, cause);
    }
}
