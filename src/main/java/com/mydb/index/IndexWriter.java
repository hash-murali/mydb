package com.mydb.index;

import com.mydb.exceptions.IOStorageException;

import java.io.IOException;
import java.math.BigInteger;

/**
 * Writes index OPEN/CLOSE events to the append-only index log.
 */
public interface IndexWriter {

    /**
     * Records the opening of a new range file.
     *
     * @param start first number contained in the new range
     * @param fileName physical file name on disk
     * @throws IOException if the index log cannot be updated
     */
    void writeOpen(BigInteger start, String fileName) throws IOException;

    /**
     * Records the closing boundary of an existing range file.
     *
     * @param end last number stored in the range
     * @param fileName physical file name on disk
     * @throws IOException if the index log cannot be updated
     */
    void writeClose(BigInteger end, String fileName) throws IOException;

    /**
     * Flushes any buffered index output to disk.
     *
     * @throws IOException if flushing the log fails
     */
    void flush() throws IOException;
}
