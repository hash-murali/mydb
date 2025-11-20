package com.mydb.index;

import com.mydb.core.RangeInfo;
import com.mydb.exceptions.IndexException;

import java.io.IOException;
import java.util.List;

/**
 * Loads and reconstructs RangeInfo list from index.csv.
 */
public interface IndexReader {

    List<RangeInfo> loadIndex() throws IOException, IndexException;
}
