package com.mydb.meta;

/**
 * Small JSON-serializable metadata stored as metadata.json.
 */
public class Metadata {
    public int version;
    public String lastRangeFile;
    public int nextRangeId;
    public int maxRecordsPerRange;
    public String createdAt;
}
