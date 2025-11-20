package com.mydb.index;

import com.mydb.core.RangeInfo;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * Performs binary search on loaded ranges.
 */
public class FileFinder {

    private final List<RangeInfo> ranges;

    public FileFinder(List<RangeInfo> ranges) {
        this.ranges = ranges;
    }

    public Optional<RangeInfo> findRangeFor(BigInteger n) {
        // TODO
        return Optional.empty();
    }

    public List<RangeInfo> findRangesUpTo(BigInteger limit) {
        // TODO
        return null;
    }
}
