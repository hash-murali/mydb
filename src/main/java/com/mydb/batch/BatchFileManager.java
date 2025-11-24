package com.mydb.batch;

import java.io.IOException;
import java.math.BigInteger;

/**
 * Responsible for managing lifecycle of batch files: creation, retrieval, rotation, and closing.
 */
public interface BatchFileManager {

    /**
     * Returns the active (open) batch file where new writes go.
     *
     * @return batch file currently accepting writes
     */
    BatchFile getActiveBatchFile();

    /**
     * Creates a new batch file starting with the given number.
     *
     * @param startNumber the first number to be placed in the file
     * @return newly created {@link BatchFile}
     * @throws IOException when the file cannot be created
     */
    BatchFile createNewBatchFile(BigInteger startNumber) throws IOException;

    /**
     * Closes the batch file and writes final metadata.
     *
     * @param file batch file to close
     * @param endNumber final value written to the batch file
     * @throws IOException when closing or persisting metadata fails
     */
    void closeBatchFile(BatchFile file, BigInteger endNumber) throws IOException;
}
