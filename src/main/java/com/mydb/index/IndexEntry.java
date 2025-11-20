package com.mydb.index;

import java.math.BigInteger;

/**
 * One line in index.csv.
 * Represents either OPEN or CLOSE operation.
 */
public class IndexEntry {

    public enum Type { OPEN, CLOSE }

    public Type type;
    public BigInteger value;
    public String fileName;
}
