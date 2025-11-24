package com.mydb.state;

import java.math.BigInteger;

/**
 * One line in state.csv representing either an OPEN or CLOSE operation for a batch file.
 */
public class StateEntry {

    /** Type of entry recorded in the state log. */
    public enum Type { OPEN, CLOSE }

    /** Whether the entry opens or closes a batch. */
    public Type type;
    /** Value associated with the open/close marker. */
    public BigInteger value;
    /** Name of the batch file affected by this entry. */
    public String fileName;
}
