package com.mydb.index;

import com.mydb.core.RangeInfo;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * Performs binary search on loaded ranges to locate files for specific values or limits.
 */
public class FileFinder {

    /** Ordered collection of ranges to search across. */
    private final List<RangeInfo> ranges;

    /**
     * Creates a finder bound to a list of ranges.
     *
     * @param ranges chronologically ordered range descriptors
     */
    public FileFinder(List<RangeInfo> ranges) {
        this.ranges = ranges;
    }

    /**
     * Finds the range containing the provided number.
     *
     * @param n number to locate
     * @return optional range info when a covering range exists; inclusive of {@link RangeInfo#end}
     */
    public Optional<RangeInfo> findRangeFor(BigInteger n) {
        int low = 0;
        int high = ranges.size() - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            RangeInfo candidate = ranges.get(mid);
            BigInteger start = candidate.start;
            BigInteger end = candidate.end;
            if (n.compareTo(start) < 0) {
                high = mid - 1;
            } else if (end == null || n.compareTo(end) <= 0) {
                return Optional.of(candidate);
            } else {
                low = mid + 1;
            }
        }
        return Optional.empty();
    }

    /**
     * Returns all ranges containing values less than or equal to the provided limit.
     *
     * @param limit inclusive upper bound for results
     * @return list view of ranges up to the limit (shares backing list)
     */
    public List<RangeInfo> findRangesUpTo(BigInteger limit) {
        int index = 0;
        while (index < ranges.size() && ranges.get(index).start.compareTo(limit) <= 0) {
            index++;
        }
        return ranges.subList(0, index);
    }
}
