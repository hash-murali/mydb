package com.mydb.index;

import com.mydb.core.RangeInfo;
import com.mydb.exceptions.IndexException;

import java.io.IOException;
import java.util.List;

/**
 * Loads and reconstructs {@link RangeInfo} entries from {@code index.csv}.
 */
public interface IndexReader {

    /**
     * Reads the index log from the beginning and replays it into ordered range metadata.
     *
     * @return reconstructed list of {@link RangeInfo} objects
     * @throws IOException when index data cannot be read from disk
     * @throws IndexException if the index structure is invalid or corrupted
     */
    List<RangeInfo> loadIndex() throws IOException, IndexException;
}
