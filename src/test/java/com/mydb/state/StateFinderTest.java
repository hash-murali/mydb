package com.mydb.state;

import com.mydb.core.BatchInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Exercises the {@link StateFinder} logic responsible for mapping numeric values to batch files.
 */
class StateFinderTest {

    /**
     * Builds a {@link BatchInfo} with the provided boundaries for use within tests.
     */
    private static BatchInfo batch(String start, String end, String fileName) {
        BatchInfo info = new BatchInfo();
        info.start = new BigInteger(start);
        info.end = end == null ? null : new BigInteger(end);
        info.fileName = fileName;
        return info;
    }

    /**
     * Verifies that a value inside a closed batch resolves to the correct entry.
     */
    @Test
    @DisplayName("findBatchFor locates closed batch containing value")
    void findBatchForClosedRange() {
        List<BatchInfo> batches = new ArrayList<>();
        batches.add(batch("0", "10", "b0"));
        batches.add(batch("11", "20", "b1"));

        StateFinder finder = new StateFinder(batches);
        Optional<BatchInfo> result = finder.findBatchFor(new BigInteger("15"));

        assertTrue(result.isPresent(), "Batch should be found");
        assertEquals("b1", result.get().fileName, "Correct file should be returned");
    }

    /**
     * Ensures values greater than all known batches result in an empty lookup.
     */
    @Test
    @DisplayName("findBatchFor returns empty when no batch matches")
    void findBatchForNoMatch() {
        List<BatchInfo> batches = new ArrayList<>();
        batches.add(batch("0", "5", "b0"));
        StateFinder finder = new StateFinder(batches);

        Optional<BatchInfo> result = finder.findBatchFor(new BigInteger("99"));
        assertFalse(result.isPresent(), "No batch should match");
    }

    /**
     * Confirms open-ended batches (no {@code end}) are treated as covering all subsequent values.
     */
    @Test
    @DisplayName("findBatchFor matches open-ended batch")
    void findBatchForOpenRange() {
        List<BatchInfo> batches = new ArrayList<>();
        batches.add(batch("100", null, "open"));
        StateFinder finder = new StateFinder(batches);

        Optional<BatchInfo> result = finder.findBatchFor(new BigInteger("100000"));
        assertTrue(result.isPresent(), "Open batch should match");
        assertEquals("open", result.get().fileName);
    }

    /**
     * Validates that sublists returned by {@link StateFinder#findBatchesUpTo(BigInteger)} include
     * all batches starting before or on the limit.
     */
    @Test
    @DisplayName("findBatchesUpTo returns all batches beginning before limit")
    void findBatchesUpToReturnsExpectedSubset() {
        List<BatchInfo> batches = new ArrayList<>();
        BatchInfo first = batch("0", "10", "b0");
        BatchInfo second = batch("11", "20", "b1");
        batches.add(first);
        batches.add(second);
        batches.add(batch("21", null, "open"));

        StateFinder finder = new StateFinder(batches);
        List<BatchInfo> subset = finder.findBatchesUpTo(new BigInteger("20"));

        assertEquals(2, subset.size(), "Two batches should be included up to limit");
        assertSame(first, subset.get(0), "First element should be first batch");
        assertSame(second, subset.get(1), "Second element should be second batch");
    }
}
