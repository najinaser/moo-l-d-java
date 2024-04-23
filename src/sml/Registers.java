package sml;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// TODO: write a JavaDoc for the class
// DONE

/**
 * @author naji
 * <p>
 * Represents a set of registers in a virtual machine.
 * The class follows the singleton pattern, providing a single instance
 * that can be accessed using the static method {@link #getInstance()}.
 * <p>
 * The registers are named and mapped to their corresponding values.
 * The class provides methods for setting and retrieving register values.
 * <p>
 * I made this class singliton, so it will be initialised only one time
 * That's because some of the instruction need to access this class, so that they will have
 * the same registers as the one in machine. In normal machine, we only have one register to access the hardware.
 */
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
        //TODO: In general, it is not recommended to call
        //      methods of the class in constructors.
        //      Why is it? And why is it safe in this case?
        //      Write a short explanation.
        // DONE
        /**
         * It's generally discouraged to call functions from constructors for several reasons,
         * including potential issues with object initialization timing, testability, and inheretance
         * with sub classes.
         * However, in this specific case, calling the 'clear' method from the constructor is safe
         * and aligned with the requirements for the following reasons:
         *
         * 1. Initialization Timing: The 'clear' method is straightforward, initializing the
         *    internal state of the 'Registers' class. It doesn't introduce complexities related
         *    to incomplete object initialization.
         *
         * 2. Testability: The 'clear' method is simple and doesn't have external dependencies,
         *    making it relatively easy to test independently. This contributes to the overall
         *    testability of the class.
         *
         * 3. Inheritance and Overriding: 'Registers' is marked as 'final', preventing
         *    subclasses, concerns related to overriding methods are minimized. If inheritance is
         *    allowed, care must be taken to ensure that overridden methods don't unintentionally
         *    affect object state.
         *
         * 4. Singleton: The 'Registers' class effectively acts as a singleton,
         *    ensuring that there's only one set of registers in the system. This aligns with
         *    the intention to have a single instance of registers accessible throughout the system.
         *    In our case, the 'Registers' instance is crucial for effective
         *    memory management. Having more than one 'Registers' object could lead to unintended
         *    consequences, particularly when accessing shared memory locations. That's why, the reset button
         *    is called only one time, and that can be when we initialis the object
         */

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
                //TODO: The next line of code does not seem to do much
                //      (r is mapped to r).
                //      What is the purpose of the next line of code?
                //      Write a short explanation.
                // DONE
                /**
                 * This is to make sure we are filling the gabs, and ensuring the Types
                 * Or when Null happens
                 */
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
        //TODO: Explain what happens if register is not an instance of RegisterName.
        //      Consider, for example, a call of the form registers.get(() -> "NEW").
        // DONE
        /**
         *
         *  if register is not an instance of RegisterName, a ClassCastException will occur
         *  when attempting to cast it to RegisterNameImpl.
         *  This is because the code assumes that register is an instance of RegisterNameImpl
         *  and directly casts it.
         *
         *  Check test testNotFoundGet in RegistersTest
         */
        return registers.get((RegisterNameImpl) register);
    }

    // TODO: use pattern matching for instanceof
    // DONE
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
