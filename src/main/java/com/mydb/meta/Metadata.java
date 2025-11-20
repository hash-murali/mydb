package com.mydb.meta;

/**
 * Small JSON-serializable metadata stored as {@code metadata.json}.
 */
public class Metadata {
    /** Schema version number. */
    public int version;
    /** File name of the most recently active range file. */
    public String lastRangeFile;
    /** Next identifier to use when creating a range file. */
    public int nextRangeId;
    /** Maximum records permitted per range. */
    public int maxRecordsPerRange;
    /** Creation timestamp for the datastore. */
    public String createdAt;
}
