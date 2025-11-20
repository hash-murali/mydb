package com.mydb.api;

import com.mydb.exceptions.StorageException;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * Public API for the number storage engine.
 * <p>
 * Implementations are responsible for persisting {@link BigInteger} values, exposing
 * exact lookups, range-based reads, and lifecycle management of underlying resources.
 */
public interface NumberStore {

    /**
     * Writes a number to the store.
     *
     * @param number value to persist
     * @throws StorageException if file operations fail
     */
    void write(BigInteger number) throws IOException;

    /**
     * Finds if a number belongs to any range.
     *
     * @param number value to locate
     * @throws StorageException on index corruption
     */
    Optional<BigInteger> findExact(BigInteger number) throws IOException;

    /**
     * Reads all numbers <= limit.
     *
     * @param limit maximum value (inclusive)
     * @param maxCount maximum number of records to retrieve
     * @throws StorageException when reading ranges fails
     */
    List<BigInteger> readBelow(BigInteger limit, int maxCount) throws IOException;

    /**
     * Close underlying resources.
     */
    void close() throws IOException;
}
