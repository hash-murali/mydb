package com.mydb.range;

/**
 * Header for each range file.
 * Contains format version, magic constant, record count.
 */
public class RangeHeader {
    public int magic;
    public int version;
    public long recordCount;
}
