package com.mydb.recovery;

import com.mydb.core.RangeInfo;
import com.mydb.meta.Metadata;

import java.util.List;

/**
 * Output of the recovery procedure containing recovered metadata and valid ranges.
 */
public class RecoveryResult {
    /** Latest metadata reconstructed from disk. */
    public Metadata metadata;
    /** Ordered list of ranges validated during recovery. */
    public List<RangeInfo> ranges;
}
