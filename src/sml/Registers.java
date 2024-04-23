package sml;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Registers {

    private static final Registers INSTANCE = new Registers();

    // public method to access the singleton instance
    public static Registers getInstance() {
        return INSTANCE;
    }

    private final Map<RegisterNameImpl, Integer> registers = new HashMap<>();

    public enum RegisterNameImpl implements RegisterName {
        AX, BX, CX, DX, SP, BP;
    }

    private Registers() {
        clear();
    }

    /**
     * Clears all registers, setting their values to 0.
     */
    public void clear() {
        for (RegisterNameImpl register : RegisterNameImpl.values())
            registers.put(register, 0);
    }

    /**
     * Parses a string to find the corresponding register name.
     *
     * @param s the string representation of the register name
     * @return an optional containing the parsed register name
     */
    public Optional<RegisterName> parseRegisterName(String s) {
        return Stream.of(RegisterNameImpl.values())
                .filter(r -> r.name().equals(s))
                .<RegisterName>map(r -> r)
                .findAny();
    }

    /**
     * Sets the given register to the value.
     *
     * @param register register name
     * @param value    new value
     */
    public void set(RegisterName register, int value) {
        registers.put((RegisterNameImpl) register, value);
    }

    /**
     * Returns the value stored in the register.
     *
     * @param register register name
     * @return value
     */
    public int get(RegisterName register) {
        return registers.get((RegisterNameImpl) register);
    }

    // https://docs.oracle.com/en/java/javase/14/language/pattern-matching-instanceof-operator.html
    @Override
    public boolean equals(Object o) {
        /**
         * The pattern matching instanceof combines both the type check and the cast in a single line.
         */
        if (o instanceof Registers other) {
            return registers.equals(other.registers);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return registers.hashCode();
    }

    @Override
    public String toString() {
        return registers.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + " = " + e.getValue())
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
