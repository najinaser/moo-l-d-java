package sml;

public abstract class Instruction {
    /**
     * The optional label associated with the instruction.
     */
    protected final String label;
    /**
     * The opcode representing the operation of the instruction.
     */
    protected final String opcode;

    /**
     * Constructor: an instruction with a label and an opcode
     * (opcode must be an operation of the language)
     *
     * @param label optional label (can be null)
     * @param opcode operation name
     */
    public Instruction(String label, String opcode) {
        this.label = label;
        this.opcode = opcode;
    }

    /**
     * Gets the label associated with the instruction.
     *
     * @return the label of the instruction
     */
    public String getLabel() {
        return label;
    }

    /**
     * Gets the opcode representing the operation of the instruction.
     *
     * @return the opcode of the instruction
     */
    public String getOpcode() {
        return opcode;
    }

    /**
     * Gets the size of the instruction.
     *
     * @return the size of the instruction in the program
     */
    public abstract int getSize();

    /**
     * Executes the instruction in the given machine. Each instruction will have different
     * execution.
     *
     * @param machine the machine the instruction runs on
     * @return the new program counter
     */

    public abstract int execute(Machine machine);

    /**
     * Gets a formatted string representation of the label, including a colon (if label present).
     * If the label is null, an empty string is returned.
     *
     * @return a formatted string representation of the label
     */
    protected String getLabelString() {
        return (getLabel() == null) ? "" : getLabel() + ": ";
    }

    /**
     * @return a string representation of the instruction
     */
    @Override
    public abstract String toString();

    /**
     * Checks if this instruction is equal to another object.
     *
     * @param obj the object to compare with this instruction
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public abstract boolean equals(Object obj);

    /**
     * Computes a hash code value for this instruction.
     *
     * @return the hash code value for this instruction
     */
    @Override
    public abstract int hashCode();
}
