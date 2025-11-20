package com.mydb.recovery;

import com.mydb.core.RangeInfo;
import com.mydb.meta.Metadata;

import java.util.List;

/**
 * Output of the recovery procedure.
 * Contains recovered metadata and valid ranges.
 */
public class RecoveryResult {
    public Metadata metadata;
    public List<RangeInfo> ranges;
}
