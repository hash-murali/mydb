package com.mydb.core;

import com.mydb.api.NumberStore;
import com.mydb.batch.BatchFile;
import com.mydb.batch.BatchFileManager;
import com.mydb.batch.BatchHeader;
import com.mydb.batch.LocalBatchFileManager;
import com.mydb.batch.SimpleBatchFile;
import com.mydb.meta.JsonMetadataManager;
import com.mydb.meta.Metadata;
import com.mydb.meta.MetadataManager;
import com.mydb.recovery.RecoveryManager;
import com.mydb.recovery.RecoveryResult;
import com.mydb.recovery.SimpleRecoveryManager;
import com.mydb.state.CsvStateReader;
import com.mydb.state.CsvStateWriter;
import com.mydb.state.StateFinder;
import com.mydb.state.StateReader;
import com.mydb.state.StateWriter;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * Default implementation coordinating batch files, state log, metadata, and recovery.
 * <p>
 * This class wires together all subsystems and ensures writes, reads, and lookups
 * follow the configured persistence model.
 */
public class SegmentedNumberStore implements NumberStore {

    private final Path root;
    private final Path batchDir;
    private final Path metadataPath;
    private final Path statePath;

    private final BatchFileManager batchManager;
    private final StateWriter stateWriter;
    private final MetadataManager metadataManager;
    private final RecoveryManager recoveryManager;
    private List<BatchInfo> batches;
    private StateFinder finder;
    private Metadata metadata;

    /**
     * Creates the store bound to the provided root path and performs any necessary
     * startup recovery.
     *
     * @param rootPath filesystem location for data, metadata, and state files
     * @throws IOException if initialization resources cannot be accessed
     */
    public SegmentedNumberStore(String rootPath) throws IOException {
        this.root = Path.of(rootPath);
        this.batchDir = root.resolve("batches");
        this.metadataPath = root.resolve("metadata.json");
        this.statePath = root.resolve("state.csv");

        this.metadataManager = new JsonMetadataManager(metadataPath);
        StateReader reader = new CsvStateReader(statePath);
        this.stateWriter = new CsvStateWriter(statePath);
        this.recoveryManager = new SimpleRecoveryManager(metadataManager, reader, batchDir);

        RecoveryResult result = recoveryManager.recover();
        this.metadata = result.metadata;
        this.batches = result.batches;
        this.batchManager = new LocalBatchFileManager(batchDir, metadata.nextBatchId);
        this.finder = new StateFinder(batches);

        BatchInfo openBatch = batches.stream().filter(BatchInfo::isOpen).reduce((a, b) -> b).orElse(null);
        if (openBatch != null) {
            BatchHeader header = new BatchHeader();
            SimpleBatchFile file = new SimpleBatchFile(batchDir.resolve(openBatch.fileName), header, false);
            ((LocalBatchFileManager) batchManager).resume(file);
            metadata.lastBatchFile = openBatch.fileName;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(BigInteger number) throws IOException {
        BatchFile active = batchManager.getActiveBatchFile();
        BatchInfo activeInfo = getActiveBatchInfo();

        if (active != null && active.getRecordCount() >= metadata.maxRecordsPerBatch) {
            BigInteger last = active.getLastNumber();
            if (activeInfo != null) {
                activeInfo.end = last;
                stateWriter.writeClose(last, activeInfo.fileName);
            }
            batchManager.closeBatchFile(active, last);
            metadata.lastBatchFile = null;
        }

        active = batchManager.getActiveBatchFile();
        if (active == null) {
            BatchFile file = batchManager.createNewBatchFile(number);
            String fileName = ((SimpleBatchFile) file).getPath().getFileName().toString();
            BatchInfo info = new BatchInfo();
            info.start = number;
            info.fileName = fileName;
            batches.add(info);
            finder = new StateFinder(batches);
            stateWriter.writeOpen(number, fileName);
            metadata.lastBatchFile = fileName;
            metadata.nextBatchId++;
            metadataManager.save(metadata);
            stateWriter.flush();
            return;
        }

        active.append(number);
        stateWriter.flush();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<BigInteger> findExact(BigInteger number) throws IOException {
        return finder.findBatchFor(number).map(r -> number);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BigInteger> readBelow(BigInteger limit, int maxCount) throws IOException {
        List<BatchInfo> applicable = finder.findBatchesUpTo(limit);
        List<BigInteger> results = new java.util.ArrayList<>();
        for (BatchInfo batch : applicable) {
            SimpleBatchFile file = new SimpleBatchFile(batchDir.resolve(batch.fileName), new BatchHeader(), false);
            for (BigInteger value : file.readUpTo(limit)) {
                if (results.size() >= maxCount) {
                    return results;
                }
                results.add(value);
            }
        }
        return results;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {
        BatchFile active = batchManager.getActiveBatchFile();
        BatchInfo activeInfo = getActiveBatchInfo();
        if (active != null) {
            BigInteger last = active.getLastNumber();
            batchManager.closeBatchFile(active, last);
            if (activeInfo != null && activeInfo.end == null) {
                activeInfo.end = last;
                stateWriter.writeClose(last, activeInfo.fileName);
            }
        }
        metadataManager.save(metadata);
        stateWriter.flush();
    }

    private BatchInfo getActiveBatchInfo() {
        if (batches.isEmpty()) {
            return null;
        }
        BatchInfo last = batches.get(batches.size() - 1);
        return last.isOpen() ? last : null;
    }
}
