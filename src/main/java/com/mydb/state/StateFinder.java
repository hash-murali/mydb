package com.mydb.state;

import com.mydb.core.BatchInfo;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * Performs binary search on loaded batch boundaries to locate files for specific values or limits.
 */
public class StateFinder {

    /** Ordered collection of batches to search across. */
    private final List<BatchInfo> batches;

    /**
     * Creates a finder bound to a list of batches.
     *
     * @param batches chronologically ordered batch descriptors
     */
    public StateFinder(List<BatchInfo> batches) {
        this.batches = batches;
    }

    /**
     * Finds the batch containing the provided number.
     *
     * @param n number to locate
     * @return optional batch info when a covering batch exists; inclusive of {@link BatchInfo#end}
     */
    public Optional<BatchInfo> findBatchFor(BigInteger n) {
        int low = 0;
        int high = batches.size() - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            BatchInfo candidate = batches.get(mid);
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
     * Returns all batches containing values less than or equal to the provided limit.
     *
     * @param limit inclusive upper bound for results
     * @return list view of batches up to the limit (shares backing list)
     */
    public List<BatchInfo> findBatchesUpTo(BigInteger limit) {
        int index = 0;
        while (index < batches.size() && batches.get(index).start.compareTo(limit) <= 0) {
            index++;
        }
        return batches.subList(0, index);
    }
}
