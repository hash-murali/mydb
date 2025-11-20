package com.mydb.core;

import com.mydb.api.NumberStore;
import com.mydb.exceptions.StorageException;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public class SegmentedNumberStore implements NumberStore {

    public SegmentedNumberStore(String rootPath) throws IOException {
        // Perform initialization + recovery
    }

    @Override
    public void write(BigInteger number) throws IOException {
        // TODO — may throw StorageException
    }

    @Override
    public Optional<BigInteger> findExact(BigInteger number) throws IOException {
        return Optional.empty();
    }

    @Override
    public List<BigInteger> readBelow(BigInteger limit, int maxCount) throws IOException {
        return null;
    }

    @Override
    public void close() throws IOException {
        // TODO
    }
}
