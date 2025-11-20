package com.mydb.range;

import com.mydb.exceptions.RangeFileException;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

/**
 * Represents a single binary range file.
 */
public interface RangeFile {

    void append(BigInteger number) throws IOException, RangeFileException;

    List<BigInteger> readAll() throws IOException, RangeFileException;

    List<BigInteger> readUpTo(BigInteger limit) throws IOException, RangeFileException;

    long getRecordCount();

    BigInteger getLastNumber();
}
