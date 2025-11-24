package com.mydb.batch;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * File-system bound {@link BatchFileManager} that stores batch files under a configured directory.
 */
public class LocalBatchFileManager implements BatchFileManager {

    private final Path batchDirectory;
    private final AtomicInteger nextBatchId;
    private BatchFile active;
    private String lastCreatedFileName;

    /**
     * @param batchDirectory directory to hold batch files
     * @param initialNextBatchId starting numeric identifier for batch files
     */
    public LocalBatchFileManager(Path batchDirectory, int initialNextBatchId) {
        this.batchDirectory = batchDirectory;
        this.nextBatchId = new AtomicInteger(initialNextBatchId);
    }

    /**
     * Registers an already open batch file as the active target after recovery.
     *
     * @param file existing batch file
     */
    public void resume(BatchFile file) {
        this.active = file;
        if (file instanceof SimpleBatchFile simpleBatchFile) {
            this.lastCreatedFileName = simpleBatchFile.getPath().getFileName().toString();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BatchFile getActiveBatchFile() {
        return active;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BatchFile createNewBatchFile(BigInteger startNumber) throws IOException {
        Files.createDirectories(batchDirectory);
        int id = nextBatchId.getAndIncrement();
        String fileName = fileNameFor(id);
        this.lastCreatedFileName = fileName;
        Path path = batchDirectory.resolve(fileName);
        BatchHeader header = new BatchHeader();
        header.magic = 0x4E55524D; // NURM magic constant
        header.version = 1;
        header.recordCount = 0;
        active = new SimpleBatchFile(path, header, true);
        ((SimpleBatchFile) active).append(startNumber);
        return active;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void closeBatchFile(BatchFile file, BigInteger endNumber) throws IOException {
        if (file instanceof SimpleBatchFile simple) {
            simple.close();
        }
        if (file == active) {
            active = null;
        }
    }

    /**
     * Returns the file name of the most recently created batch file.
     */
    public String getLastCreatedFileName() {
        return lastCreatedFileName;
    }

    private String fileNameFor(int id) {
        return String.format("batch-%05d.bin", id);
    }
}
