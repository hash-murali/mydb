package com.mydb.range;

import com.mydb.exceptions.RangeFileException;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

/**
 * Represents a single binary range file that can be appended to and read from.
 */
public interface RangeFile {

    /**
     * Appends a new {@link BigInteger} record to the file.
     *
     * @param number value to append
     * @throws IOException when file IO fails
     * @throws RangeFileException when the range format is invalid
     */
    void append(BigInteger number) throws IOException, RangeFileException;

    /**
     * Reads all records stored in this file.
     *
     * @return list of {@link BigInteger} values in write order
     * @throws IOException when file IO fails
     * @throws RangeFileException when parsing fails
     */
    List<BigInteger> readAll() throws IOException, RangeFileException;

    /**
     * Reads records up to an inclusive limit.
     *
     * @param limit maximum value to include in the response
     * @return list of values not exceeding the limit
     * @throws IOException when file IO fails
     * @throws RangeFileException when parsing fails
     */
    List<BigInteger> readUpTo(BigInteger limit) throws IOException, RangeFileException;

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
