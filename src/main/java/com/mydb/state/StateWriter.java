package com.mydb.state;

import java.io.IOException;
import java.math.BigInteger;

/**
 * Writes state OPEN/CLOSE events to the append-only state log.
 */
public interface StateWriter {

    /**
     * Records the opening of a new batch file.
     *
     * @param start first number contained in the new batch
     * @param fileName physical file name on disk
     * @throws IOException if the state log cannot be updated
     */
    void writeOpen(BigInteger start, String fileName) throws IOException;

    /**
     * Records the closing boundary of an existing batch file.
     *
     * @param end last number stored in the batch
     * @param fileName physical file name on disk
     * @throws IOException if the state log cannot be updated
     */
    void writeClose(BigInteger end, String fileName) throws IOException;

    /**
     * Flushes any buffered state output to disk.
     *
     * @throws IOException if flushing the log fails
     */
    void flush() throws IOException;
}
