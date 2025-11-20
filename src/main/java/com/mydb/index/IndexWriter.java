package com.mydb.index;

import com.mydb.exceptions.IOStorageException;

import java.io.IOException;
import java.math.BigInteger;

/**
 * Writes index OPEN/CLOSE events.
 */
public interface IndexWriter {

    void writeOpen(BigInteger start, String fileName) throws IOException;

    void writeClose(BigInteger end, String fileName) throws IOException;

    void flush() throws IOException;
}
