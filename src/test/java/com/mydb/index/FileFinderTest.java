package com.mydb.index;

import com.mydb.core.RangeInfo;
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
 * Exercises the {@link FileFinder} logic responsible for mapping numeric values to range files.
 */
class FileFinderTest {

    /**
     * Builds a {@link RangeInfo} with the provided boundaries for use within tests.
     */
    private static RangeInfo range(String start, String end, String fileName) {
        RangeInfo info = new RangeInfo();
        info.start = new BigInteger(start);
        info.end = end == null ? null : new BigInteger(end);
        info.fileName = fileName;
        return info;
    }

    /**
     * Verifies that a value inside a closed range resolves to the correct entry.
     */
    @Test
    @DisplayName("findRangeFor locates closed range containing value")
    void findRangeForClosedRange() {
        List<RangeInfo> ranges = new ArrayList<>();
        ranges.add(range("0", "10", "r0"));
        ranges.add(range("11", "20", "r1"));

        FileFinder finder = new FileFinder(ranges);
        Optional<RangeInfo> result = finder.findRangeFor(new BigInteger("15"));

        assertTrue(result.isPresent(), "Range should be found");
        assertEquals("r1", result.get().fileName, "Correct file should be returned");
    }

    /**
     * Ensures values greater than all known ranges result in an empty lookup.
     */
    @Test
    @DisplayName("findRangeFor returns empty when no range matches")
    void findRangeForNoMatch() {
        List<RangeInfo> ranges = new ArrayList<>();
        ranges.add(range("0", "5", "r0"));
        FileFinder finder = new FileFinder(ranges);

        Optional<RangeInfo> result = finder.findRangeFor(new BigInteger("99"));
        assertFalse(result.isPresent(), "No range should match");
    }

    /**
     * Confirms open-ended ranges (no {@code end}) are treated as covering all subsequent values.
     */
    @Test
    @DisplayName("findRangeFor matches open-ended range")
    void findRangeForOpenRange() {
        List<RangeInfo> ranges = new ArrayList<>();
        ranges.add(range("100", null, "open"));
        FileFinder finder = new FileFinder(ranges);

        Optional<RangeInfo> result = finder.findRangeFor(new BigInteger("100000"));
        assertTrue(result.isPresent(), "Open range should match");
        assertEquals("open", result.get().fileName);
    }

    /**
     * Validates that sublists returned by {@link FileFinder#findRangesUpTo(BigInteger)} include
     * all ranges starting before or on the limit.
     */
    @Test
    @DisplayName("findRangesUpTo returns all ranges beginning before limit")
    void findRangesUpToReturnsExpectedSubset() {
        List<RangeInfo> ranges = new ArrayList<>();
        RangeInfo first = range("0", "10", "r0");
        RangeInfo second = range("11", "20", "r1");
        ranges.add(first);
        ranges.add(second);
        ranges.add(range("21", null, "open"));

        FileFinder finder = new FileFinder(ranges);
        List<RangeInfo> subset = finder.findRangesUpTo(new BigInteger("20"));

        assertEquals(2, subset.size(), "Two ranges should be included up to limit");
        assertSame(first, subset.get(0), "First element should be first range");
        assertSame(second, subset.get(1), "Second element should be second range");
    }
}
