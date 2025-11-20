package com.mydb.range;

/**
 * Header for each range file containing format version, magic constant, and record count.
 */
public class RangeHeader {
    /** Magic constant used to validate the file structure. */
    public int magic;
    /** Version number for the range file format. */
    public int version;
    /** Number of records stored in the range file. */
    public long recordCount;
}
