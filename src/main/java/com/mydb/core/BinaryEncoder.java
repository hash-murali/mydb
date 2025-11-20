package com.mydb.core;

import com.mydb.exceptions.IOStorageException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.math.BigInteger;

/**
 * Responsible for binary encoding and decoding of supported numeric types.
 */
public final class BinaryEncoder {

    private BinaryEncoder() {}

    /**
     * Writes a {@link BigInteger} to a {@link DataOutput} in the format
     * {@code [int byteLength][byte[byteLength]]}.
     *
     * @param out destination data output stream
     * @param value value to encode
     * @implNote The method preserves sign information through {@link BigInteger#toByteArray()} and
     * writes the byte length first to allow simple decoding.
     * @throws IOStorageException if underlying IO operations fail
     */
    public static void writeBigInteger(DataOutput out, BigInteger value) {
        try {
            byte[] bytes = value.toByteArray();
            out.writeInt(bytes.length);
            out.write(bytes);
        } catch (IOException e) {
            throw new IOStorageException("Failed writing BigInteger", e);
        }
    }

    /**
     * Reads a {@link BigInteger} previously written by {@link #writeBigInteger(DataOutput, BigInteger)}.
     *
     * @param in source data input stream
     * @return decoded {@link BigInteger} value
     * @implNote A negative byte length is treated as corruption and results in an
     * {@link IOStorageException}.
     * @throws IOStorageException if the stream cannot be read
     */
    public static BigInteger readBigInteger(DataInput in) {
        try {
            int length = in.readInt();
            if (length < 0) {
                throw new IOStorageException("Negative BigInteger length encountered", null);
            }
            byte[] bytes = new byte[length];
            in.readFully(bytes);
            return new BigInteger(bytes);
        } catch (IOException e) {
            throw new IOStorageException("Failed reading BigInteger", e);
        }
    }
}
