package com.mydb.range;

import java.io.IOException;
import java.math.BigInteger;

/**
 * Responsible for managing lifecycle of range files:
 * creation, retrieval of the active file, rotation, closing.
 */
public interface RangeFileManager {

    /**
     * Returns the active (open) range file where new writes go.
     */
    RangeFile getActiveRangeFile();

    /**
     * Creates a new range file starting with the given number.
     *
     * @param startNumber the first number to be placed in the file
     */
    RangeFile createNewRangeFile(BigInteger startNumber) throws IOException;

    /**
     * Closes the range file and writes final metadata.
     */
    void closeRangeFile(RangeFile file, BigInteger endNumber) throws IOException;
}
