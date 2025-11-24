package com.mydb.batch;

import com.mydb.exceptions.BatchFileException;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

/**
 * Represents a single binary batch file that can be appended to and read from.
 */
public interface BatchFile {

    /**
     * Appends a new {@link BigInteger} record to the file.
     *
     * @param number value to append
     * @throws IOException when file IO fails
     * @throws BatchFileException when the batch format is invalid
     */
    void append(BigInteger number) throws IOException, BatchFileException;

    /**
     * Reads all records stored in this file.
     *
     * @return list of {@link BigInteger} values in write order
     * @throws IOException when file IO fails
     * @throws BatchFileException when parsing fails
     */
    List<BigInteger> readAll() throws IOException, BatchFileException;

    /**
     * Reads records up to an inclusive limit.
     *
     * @param limit maximum value to include in the response
     * @return list of values not exceeding the limit
     * @throws IOException when file IO fails
     * @throws BatchFileException when parsing fails
     */
    List<BigInteger> readUpTo(BigInteger limit) throws IOException, BatchFileException;

    /**
     * Returns the number of records currently stored.
     *
     * @return count of written records
     */
    long getRecordCount();

    /**
     * Returns the last written number.
     *
     * @return most recent value appended to the file
     */
    BigInteger getLastNumber();
}
