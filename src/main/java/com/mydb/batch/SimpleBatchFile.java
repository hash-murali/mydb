package com.mydb.batch;

import com.mydb.core.BinaryEncoder;
import com.mydb.exceptions.BatchFileException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Simple binary {@link BatchFile} implementation backed by a {@link DataOutputStream}
 * for appends and {@link DataInputStream} for reads.
 */
public class SimpleBatchFile implements BatchFile {

    private static final int HEADER_SIZE = Integer.BYTES + Integer.BYTES + Long.BYTES;

    private final Path path;
    private final BatchHeader header;
    private final DataOutputStream output;
    private BigInteger lastNumber;

    /**
     * Opens or creates a batch file at the given path.
     *
     * @param path file location
     * @param header header values to write when creating a new file
     * @param newFile whether the file is being created
     * @throws IOException when the file cannot be opened
     */
    public SimpleBatchFile(Path path, BatchHeader header, boolean newFile) throws IOException {
        this.path = path;
        this.header = header;
        if (newFile) {
            Files.createDirectories(path.getParent());
            this.output = new DataOutputStream(Files.newOutputStream(path));
            writeHeader();
        } else {
            try (DataInputStream headerIn = new DataInputStream(Files.newInputStream(path))) {
                header.magic = headerIn.readInt();
                header.version = headerIn.readInt();
                header.recordCount = headerIn.readLong();
            }
            this.output = new DataOutputStream(Files.newOutputStream(path, java.nio.file.StandardOpenOption.APPEND));
            this.lastNumber = readLastNumber();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void append(BigInteger number) throws IOException, BatchFileException {
        try {
            BinaryEncoder.writeBigInteger(output, number);
            header.recordCount++;
            lastNumber = number;
        } catch (Exception e) {
            throw new BatchFileException("Failed to append number to batch file", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BigInteger> readAll() throws IOException, BatchFileException {
        return readUpTo(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BigInteger> readUpTo(BigInteger limit) throws IOException, BatchFileException {
        if (!Files.exists(path)) {
            return Collections.emptyList();
        }
        try (DataInputStream input = new DataInputStream(Files.newInputStream(path))) {
            // skip header
            input.skipNBytes(HEADER_SIZE);
            List<BigInteger> values = new ArrayList<>((int) Math.min(Integer.MAX_VALUE, header.recordCount));
            while (input.available() > 0) {
                BigInteger value = BinaryEncoder.readBigInteger(input);
                if (limit != null && value.compareTo(limit) > 0) {
                    break;
                }
                values.add(value);
            }
            return values;
        } catch (Exception e) {
            throw new BatchFileException("Failed reading batch file", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getRecordCount() {
        return header.recordCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigInteger getLastNumber() {
        return lastNumber;
    }

    /**
     * Exposes the underlying path for coordination and metadata updates.
     *
     * @return file system location of the batch file
     */
    public Path getPath() {
        return path;
    }

    /**
     * Persists the header with an updated record count and closes the output stream.
     *
     * @throws IOException if the header cannot be flushed
     */
    public synchronized void close() throws IOException {
        output.flush();
        try (var raf = new java.io.RandomAccessFile(path.toFile(), "rw")) {
            raf.seek(0);
            raf.writeInt(header.magic);
            raf.writeInt(header.version);
            raf.writeLong(header.recordCount);
        }
        output.close();
    }

    private void writeHeader() throws IOException {
        output.writeInt(header.magic);
        output.writeInt(header.version);
        output.writeLong(header.recordCount);
    }

    private BigInteger readLastNumber() throws IOException {
        if (!Files.exists(path) || Files.size(path) <= HEADER_SIZE) {
            return null;
        }
        try (DataInputStream input = new DataInputStream(Files.newInputStream(path))) {
            input.skipNBytes(HEADER_SIZE);
            BigInteger last = null;
            while (input.available() > 0) {
                last = BinaryEncoder.readBigInteger(input);
            }
            return last;
        }
    }
}
