package com.mydb.api;

import com.mydb.exceptions.StorageException;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * Public API for the number storage engine.
 */
public interface NumberStore {

    /**
     * Writes a number to the store.
     *
     * @throws StorageException if file operations fail
     */
    void write(BigInteger number) throws IOException;

    /**
     * Finds if a number belongs to any range.
     *
     * @throws StorageException on index corruption
     */
    Optional<BigInteger> findExact(BigInteger number) throws IOException;

    /**
     * Reads all numbers <= limit.
     *
     * @throws StorageException when reading ranges fails
     */
    List<BigInteger> readBelow(BigInteger limit, int maxCount) throws IOException;

    /**
     * Close underlying resources.
     */
    void close() throws IOException;
}
