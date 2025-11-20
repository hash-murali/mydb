package com.mydb.core;

import java.math.BigInteger;

/**
 * Represents start/end range metadata for a physical range file.
 */
public class RangeInfo {
    public BigInteger start;
    public BigInteger end; // null means active/unclosed
    public String fileName;

    public boolean isOpen() {
        return end == null;
    }
}
