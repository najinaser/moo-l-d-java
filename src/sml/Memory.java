package sml;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Objects;
import java.util.stream.Collectors;

public class Memory {
    private final int[] contents;
    private final BitSet usedCells = new BitSet();

    /**
     * Constructs a Memory object with the specified size.
     *
     * @param size The size of the memory (number of cells).
     */
    public Memory(int size) {
        this.contents = new int[size];
    }

    /**
     * Gets the value at the specified memory address.
     *
     * @param address of the memory address.
     * @return The value at the specified address.
     */
    public int get(int address) {
        usedCells.set(address);
        return contents[address];
    }

    /**
     * Sets the value at the specified memory address
     *
     * @param address of the memory address
     * @param value of the value to be set.
     */
    public void set(int address, int value) {
        usedCells.set(address);
        contents[address] = value;
    }

    /**
     * Return a string representation of the used cells and their contents in the memory.
     *
     * @return  A string representing the used cells and their contents.
     */
    @Override
    public String toString() {
        return usedCells.stream()
                .mapToObj(i -> "[" + i + "] = " + contents[i])
                .collect(Collectors.joining("\n"));
    }

    /**
     *
     * @param obj to compare with
     * @return boolean, true when they are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Memory memory = (Memory) obj;
        return Arrays.equals(contents, memory.contents);
    }

    /**
     *
     * @return int of the hash result
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(contents);
    }
}
