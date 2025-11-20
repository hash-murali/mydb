package com.mydb.range;

import java.io.IOException;
import java.math.BigInteger;

/**
 * Responsible for managing lifecycle of range files: creation, retrieval, rotation, and closing.
 */
public interface RangeFileManager {

    /**
     * Returns the active (open) range file where new writes go.
     *
     * @return range file currently accepting writes
     */
    RangeFile getActiveRangeFile();

    /**
     * Creates a new range file starting with the given number.
     *
     * @param startNumber the first number to be placed in the file
     * @return newly created {@link RangeFile}
     * @throws IOException when the file cannot be created
     */
    RangeFile createNewRangeFile(BigInteger startNumber) throws IOException;

    /**
     * Closes the range file and writes final metadata.
     *
     * @param file range file to close
     * @param endNumber final value written to the range file
     * @throws IOException when closing or persisting metadata fails
     */
    void closeRangeFile(RangeFile file, BigInteger endNumber) throws IOException;
}
