package com.mydb.state;

import com.mydb.core.BatchInfo;
import com.mydb.exceptions.StateException;

import java.io.IOException;
import java.util.List;

/**
 * Loads and reconstructs {@link BatchInfo} entries from {@code state.csv}.
 */
public interface StateReader {

    /**
     * Reads the state log from the beginning and replays it into ordered batch metadata.
     *
     * @return reconstructed list of {@link BatchInfo} objects
     * @throws IOException when state data cannot be read from disk
     * @throws StateException if the state structure is invalid or corrupted
     */
    List<BatchInfo> loadState() throws IOException, StateException;
}
