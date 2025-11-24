package com.mydb.cli;

import com.mydb.core.SegmentedNumberStore;

import java.math.BigInteger;

/**
 * Standalone CLI entrypoint for interacting with the number store.
 */
public class MainCLI {

    /**
     * Parses user arguments and delegates to the {@link SegmentedNumberStore} API.
     *
     * @param args raw command-line arguments
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            printUsage();
            return;
        }

        String command = args[0];
        String path = args[1];
        try (SegmentedNumberStore store = new SegmentedNumberStore(path)) {
            switch (command) {
                case "write" -> handleWrite(store, args);
                case "find" -> handleFind(store, args);
                case "read" -> handleRead(store, args);
                default -> printUsage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleWrite(SegmentedNumberStore store, String[] args) throws Exception {
        if (args.length < 3) {
            printUsage();
            return;
        }
        BigInteger value = new BigInteger(args[2]);
        store.write(value);
        System.out.println("Wrote " + value);
    }

    private static void handleFind(SegmentedNumberStore store, String[] args) throws Exception {
        if (args.length < 3) {
            printUsage();
            return;
        }
        BigInteger value = new BigInteger(args[2]);
        boolean exists = store.findExact(value).isPresent();
        System.out.println(exists ? "FOUND" : "NOT FOUND");
    }

    private static void handleRead(SegmentedNumberStore store, String[] args) throws Exception {
        if (args.length < 4) {
            printUsage();
            return;
        }
        BigInteger limit = new BigInteger(args[2]);
        int max = Integer.parseInt(args[3]);
        store.readBelow(limit, max).forEach(System.out::println);
    }

    private static void printUsage() {
        System.out.println("Usage: <write|find|read> <path> <number> [max]");
        System.out.println("Examples:");
        System.out.println("  write /tmp/mydb 12345");
        System.out.println("  find /tmp/mydb 12345");
        System.out.println("  read /tmp/mydb 500 10");
    }
}
