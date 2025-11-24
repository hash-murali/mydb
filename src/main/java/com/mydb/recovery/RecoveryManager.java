package com.mydb.recovery;

import com.mydb.exceptions.RecoveryException;

import java.io.IOException;

/**
 * Handles startup recovery procedures that rebuild in-memory state from persisted artifacts.
 */
public interface RecoveryManager {

    /**
     * Executes recovery and returns the reconstructed state ready for use by the store.
     *
     * @return container holding metadata and discovered batches
     * @throws IOException when persisted files cannot be read
     * @throws RecoveryException when the recovery process fails for logical reasons
     */
    RecoveryResult recover() throws IOException, RecoveryException;
}
