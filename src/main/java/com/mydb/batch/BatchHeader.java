package com.mydb.batch;

/**
 * Header for each batch file containing format version, magic constant, and record count.
 */
public class BatchHeader {
    /** Magic constant used to validate the file structure. */
    public int magic;
    /** Version number for the batch file format. */
    public int version;
    /** Number of records stored in the batch file. */
    public long recordCount;
}
