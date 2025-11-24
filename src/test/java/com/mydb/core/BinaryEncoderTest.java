package com.mydb.core;

import com.mydb.exceptions.IOStorageException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Validates binary encoding and decoding of {@link BigInteger} values to ensure
 * persisted bytes can be faithfully reconstructed.
 */
class BinaryEncoderTest {

    /**
     * Ensures positive numbers survive a write-read round trip without mutation.
     */
    @Test
    @DisplayName("Round trip preserves positive BigInteger")
    void roundTripPositive() throws IOException {
        BigInteger input = new BigInteger("12345678901234567890");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BinaryEncoder.writeBigInteger(new DataOutputStream(baos), input);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        BigInteger decoded = BinaryEncoder.readBigInteger(new DataInputStream(bais));

        assertEquals(input, decoded, "Positive value should decode exactly");
    }

    /**
     * Verifies encoding preserves sign for negative numbers.
     */
    @Test
    @DisplayName("Round trip preserves negative BigInteger")
    void roundTripNegative() throws IOException {
        BigInteger input = new BigInteger("-98765432109876543210");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BinaryEncoder.writeBigInteger(new DataOutputStream(baos), input);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        BigInteger decoded = BinaryEncoder.readBigInteger(new DataInputStream(bais));

        assertEquals(input, decoded, "Negative value should decode exactly");
    }

    /**
     * Confirms zero is stored and recovered consistently.
     */
    @Test
    @DisplayName("Round trip preserves zero BigInteger")
    void roundTripZero() throws IOException {
        BigInteger input = BigInteger.ZERO;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BinaryEncoder.writeBigInteger(new DataOutputStream(baos), input);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        BigInteger decoded = BinaryEncoder.readBigInteger(new DataInputStream(bais));

        assertEquals(input, decoded, "Zero value should decode exactly");
    }

    /**
     * Validates that write operations failing at the stream layer surface as {@link IOStorageException}.
     */
    @Test
    @DisplayName("Write failure is wrapped in IOStorageException")
    void writeFailureWrapped() {
        OutputStream failingStream = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                throw new IOException("sink closed");
            }
        };

        assertThrows(IOStorageException.class, () ->
                BinaryEncoder.writeBigInteger(new DataOutputStream(failingStream), BigInteger.ONE),
            "IO failures should be wrapped");
    }

    /**
     * Ensures corrupted or invalid lengths trigger an {@link IOStorageException} during decode.
     */
    @Test
    @DisplayName("Negative length triggers IOStorageException on read")
    void negativeLengthReadThrows() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (DataOutputStream dos = new DataOutputStream(baos)) {
            dos.writeInt(-1);
        }

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        assertThrows(IOStorageException.class, () ->
                BinaryEncoder.readBigInteger(new DataInputStream(bais)),
            "Negative lengths should be rejected");
    }

    /**
     * Confirms the encoded byte array matches the expected BigInteger byte representation.
     */
    @Test
    @DisplayName("Encoded bytes include length prefix")
    void encodedBytesMatchExpectedFormat() throws IOException {
        BigInteger input = new BigInteger("1024");
        byte[] expectedPayload = input.toByteArray();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BinaryEncoder.writeBigInteger(new DataOutputStream(baos), input);

        byte[] stored = baos.toByteArray();
        // first four bytes are length prefix
        int lengthPrefix = new DataInputStream(new ByteArrayInputStream(stored)).readInt();
        byte[] payload = new byte[stored.length - Integer.BYTES];
        System.arraycopy(stored, Integer.BYTES, payload, 0, payload.length);

        assertEquals(expectedPayload.length, lengthPrefix, "Length prefix should equal payload size");
        assertArrayEquals(expectedPayload, payload, "Payload bytes should match BigInteger representation");
    }
}
