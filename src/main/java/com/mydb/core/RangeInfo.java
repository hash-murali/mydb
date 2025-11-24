package com.mydb.core;

import java.math.BigInteger;

/**
 * Represents start/end range metadata for a physical range file.
 * Each instance maps to a single range file with a start and optional end value.
 */
public class RangeInfo {
    /** Starting value stored within the range file. */
    public BigInteger start;
    /** Inclusive ending value; {@code null} when the range file is still open. */
    public BigInteger end; // null means active/unclosed
    /** File name on disk associated with this range. */
    public String fileName;

    /**
     * Indicates whether the range file has not been closed yet.
     *
     * @return {@code true} when {@link #end} is {@code null}; otherwise {@code false}
     */
    public boolean isOpen() {
        return end == null;
    }
}
