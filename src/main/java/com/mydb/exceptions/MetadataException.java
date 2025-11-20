package com.mydb.exceptions;

/**
 * Thrown when loading or saving metadata.json fails.
 */
public class MetadataException extends StorageException {

    public MetadataException(String message, Throwable cause) {
        super(message, cause);
    }
}
