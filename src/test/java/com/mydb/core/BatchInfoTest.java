package com.mydb.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit coverage for {@link BatchInfo} helper behaviors.
 */
class BatchInfoTest {

    /**
     * Confirms {@link BatchInfo#isOpen()} returns true when {@link BatchInfo#end} is undefined.
     */
    @Test
    @DisplayName("isOpen returns true when end is null")
    void isOpenWhenEndMissing() {
        BatchInfo info = new BatchInfo();
        info.start = BigInteger.ZERO;
        info.end = null;

        assertTrue(info.isOpen(), "Open batches should report true");
    }

    /**
     * Confirms {@link BatchInfo#isOpen()} returns false when an end value exists.
     */
    @Test
    @DisplayName("isOpen returns false when end is set")
    void isOpenWhenEndPresent() {
        BatchInfo info = new BatchInfo();
        info.start = BigInteger.ZERO;
        info.end = BigInteger.TEN;

        assertFalse(info.isOpen(), "Closed batches should report false");
    }
}
