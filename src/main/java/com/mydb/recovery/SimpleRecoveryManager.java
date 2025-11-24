package com.mydb.recovery;

import com.mydb.core.BatchInfo;
import com.mydb.exceptions.RecoveryException;
import com.mydb.meta.Metadata;
import com.mydb.meta.MetadataManager;
import com.mydb.state.StateReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;

/**
 * Minimal recovery implementation that loads metadata, reconstructs batch info from the state log,
 * and ensures an initial metadata record exists.
 */
public class SimpleRecoveryManager implements RecoveryManager {

    private final MetadataManager metadataManager;
    private final StateReader stateReader;
    private final Path batchDirectory;

    /**
     * @param metadataManager metadata handler
     * @param stateReader state loader
     * @param batchDirectory directory where batch files reside
     */
    public SimpleRecoveryManager(MetadataManager metadataManager, StateReader stateReader, Path batchDirectory) {
        this.metadataManager = metadataManager;
        this.stateReader = stateReader;
        this.batchDirectory = batchDirectory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RecoveryResult recover() throws IOException, RecoveryException {
        try {
            Metadata metadata = metadataManager.load();
            if (metadata == null) {
                metadata = createDefaultMetadata();
            }
            Files.createDirectories(batchDirectory);
            List<BatchInfo> batches = stateReader.loadState();
            RecoveryResult result = new RecoveryResult();
            result.metadata = metadata;
            result.batches = batches;
            return result;
        } catch (Exception e) {
            throw new RecoveryException("Recovery failed", e);
        }
    }

    private Metadata createDefaultMetadata() throws IOException {
        Metadata metadata = new Metadata();
        metadata.version = 1;
        metadata.lastBatchFile = null;
        metadata.nextBatchId = 1;
        metadata.maxRecordsPerBatch = 1000;
        metadata.createdAt = Instant.now().toString();
        metadataManager.save(metadata);
        return metadata;
    }
}
