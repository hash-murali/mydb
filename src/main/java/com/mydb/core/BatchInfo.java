package com.mydb.core;

import java.math.BigInteger;

/**
 * Represents start/end boundaries for a physical batch file.
 * <p>
 * The previous terminology referred to these as "range" files, but the concept has been
 * updated to "batch" files to emphasize the grouped, sequential nature of the stored values.
 * An undefined {@link #end} indicates the batch is still open for additional writes.
 */
public class BatchInfo {
    /**
     * Inclusive starting value of the batch.
     */
    public BigInteger start;

    /**
     * Inclusive ending value of the batch; {@code null} means the batch is currently active.
     */
    public BigInteger end;

    /**
     * File name of the batch file on disk.
     */
    public String fileName;

    /**
     * Indicates whether this batch remains open (no end yet recorded).
     *
     * @return {@code true} when {@link #end} is {@code null}, else {@code false}
     */
    public boolean isOpen() {
        return end == null;
    }
}
