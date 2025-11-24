package com.mydb.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit coverage for {@link RangeInfo} helper behaviors.
 */
class RangeInfoTest {

    /**
     * Confirms {@link RangeInfo#isOpen()} returns true when {@link RangeInfo#end} is undefined.
     */
    @Test
    @DisplayName("isOpen returns true when end is null")
    void isOpenWhenEndMissing() {
        RangeInfo info = new RangeInfo();
        info.start = BigInteger.ZERO;
        info.end = null;

        assertTrue(info.isOpen(), "Open ranges should report true");
    }

    /**
     * Confirms {@link RangeInfo#isOpen()} returns false when an end value exists.
     */
    @Test
    @DisplayName("isOpen returns false when end is set")
    void isOpenWhenEndPresent() {
        RangeInfo info = new RangeInfo();
        info.start = BigInteger.ZERO;
        info.end = BigInteger.TEN;

        assertFalse(info.isOpen(), "Closed ranges should report false");
    }
}
