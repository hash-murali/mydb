package com.mydb.core;

import com.mydb.exceptions.IOStorageException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.math.BigInteger;

/**
 * Responsible for binary encoding and decoding.
 */
public final class BinaryEncoder {

    private BinaryEncoder() {}

    public static void writeBigInteger(DataOutput out, BigInteger value) {
        try {
            // TODO
        } catch (IOException e) {
            throw new IOStorageException("Failed writing BigInteger", e);
        }
    }

    public static BigInteger readBigInteger(DataInput in) {
        try {
            // TODO
            return null;
        } catch (IOException e) {
            throw new IOStorageException("Failed reading BigInteger", e);
        }
    }
}
