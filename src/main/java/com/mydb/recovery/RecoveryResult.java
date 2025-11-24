package com.mydb.recovery;

import com.mydb.core.BatchInfo;
import com.mydb.meta.Metadata;

import java.util.List;

/**
 * Output of the recovery procedure containing recovered metadata and valid batches.
 */
public class RecoveryResult {
    /** Latest metadata reconstructed from disk. */
    public Metadata metadata;
    /** Ordered list of batches validated during recovery. */
    public List<BatchInfo> batches;
}
