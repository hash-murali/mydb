package com.mydb.meta;

import com.mydb.exceptions.MetadataException;

import java.io.IOException;

/**
 * Handles reading and writing {@link Metadata} records from {@code metadata.json}.
 */
public interface MetadataManager {

    /**
     * Loads persisted metadata from disk.
     *
     * @return populated {@link Metadata} instance
     * @throws IOException when the metadata file cannot be accessed
     * @throws MetadataException if the metadata payload cannot be parsed
     */
    Metadata load() throws IOException, MetadataException;

    /**
     * Persists the provided metadata to disk.
     *
     * @param metadata metadata payload to save
     * @throws IOException when the metadata file cannot be written
     * @throws MetadataException if serialization fails
     */
    void save(Metadata metadata) throws IOException, MetadataException;
}
