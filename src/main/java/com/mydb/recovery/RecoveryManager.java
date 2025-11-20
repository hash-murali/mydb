package com.mydb.recovery;

import com.mydb.exceptions.RecoveryException;

import java.io.IOException;

/**
 * Handles startup recovery procedures.
 */
public interface RecoveryManager {

    RecoveryResult recover() throws IOException, RecoveryException;
}
