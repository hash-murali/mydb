package com.mydb.state;

import com.mydb.core.BatchInfo;
import com.mydb.exceptions.StateException;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV-based {@link StateReader} paired with {@link CsvStateWriter}.
 */
public class CsvStateReader implements StateReader {

    private final Path statePath;

    /**
     * @param statePath path to {@code state.csv}
     */
    public CsvStateReader(Path statePath) {
        this.statePath = statePath;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BatchInfo> loadState() throws IOException, StateException {
        List<BatchInfo> batches = new ArrayList<>();
        if (!Files.exists(statePath)) {
            return batches;
        }
        try (BufferedReader reader = Files.newBufferedReader(statePath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 3) {
                    throw new StateException("Corrupted state entry: " + line);
                }
                StateEntry.Type type = StateEntry.Type.valueOf(parts[0]);
                BigInteger value = new BigInteger(parts[1]);
                String fileName = parts[2];
                if (type == StateEntry.Type.OPEN) {
                    BatchInfo info = new BatchInfo();
                    info.start = value;
                    info.fileName = fileName;
                    batches.add(info);
                } else {
                    BatchInfo last = batches.isEmpty() ? null : batches.get(batches.size() - 1);
                    if (last == null || last.end != null) {
                        throw new StateException("State CLOSE without OPEN for file: " + fileName);
                    }
                    last.end = value;
                }
            }
        }
        return batches;
    }
}
