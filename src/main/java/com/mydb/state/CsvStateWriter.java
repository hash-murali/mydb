package com.mydb.state;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Append-only implementation of {@link StateWriter} that stores entries in CSV form.
 */
public class CsvStateWriter implements StateWriter {

    private final Path statePath;
    private final BufferedWriter writer;

    /**
     * @param statePath path to {@code state.csv}
     * @throws IOException when the file cannot be opened for append
     */
    public CsvStateWriter(Path statePath) throws IOException {
        this.statePath = statePath;
        Files.createDirectories(statePath.getParent());
        this.writer = Files.newBufferedWriter(statePath, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeOpen(BigInteger start, String fileName) throws IOException {
        write(StateEntry.Type.OPEN, start, fileName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeClose(BigInteger end, String fileName) throws IOException {
        write(StateEntry.Type.CLOSE, end, fileName);
    }

    private void write(StateEntry.Type type, BigInteger value, String fileName) throws IOException {
        writer.write(type.name());
        writer.write(',');
        writer.write(value.toString());
        writer.write(',');
        writer.write(fileName);
        writer.newLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flush() throws IOException {
        writer.flush();
    }

    /**
     * Closes the writer and ensures all bytes are flushed.
     */
    public void close() throws IOException {
        writer.flush();
        writer.close();
    }
}
