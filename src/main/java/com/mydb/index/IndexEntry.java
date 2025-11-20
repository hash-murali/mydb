package com.mydb.index;

import java.math.BigInteger;

/**
 * One line in index.csv representing either an OPEN or CLOSE operation.
 */
public class IndexEntry {

    /** Type of entry recorded in the index log. */
    public enum Type { OPEN, CLOSE }

    /** Whether the entry opens or closes a range. */
    public Type type;
    /** Value associated with the open/close marker. */
    public BigInteger value;
    /** Name of the range file affected by this entry. */
    public String fileName;
}
