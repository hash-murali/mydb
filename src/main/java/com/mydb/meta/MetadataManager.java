package com.mydb.meta;

import com.mydb.exceptions.MetadataException;

import java.io.IOException;

/**
 * Handles reading/writing metadata.json.
 */
public interface MetadataManager {

    Metadata load() throws IOException, MetadataException;

    void save(Metadata metadata) throws IOException, MetadataException;
}
