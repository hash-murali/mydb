package com.mydb.meta;

/**
 * Small JSON-serializable metadata stored as {@code metadata.json}.
 */
public class Metadata {
    /** Schema version number. */
    public int version;
    /** File name of the most recently active batch file. */
    public String lastBatchFile;
    /** Next identifier to use when creating a batch file. */
    public int nextBatchId;
    /** Maximum records permitted per batch. */
    public int maxRecordsPerBatch;
    /** Creation timestamp for the datastore. */
    public String createdAt;
}
