package sml.instruction;

import sml.*;

import java.util.Objects;

public class CmpInstruction extends Instruction {
    private final InstructionDestination destination;
    private final InstructionSource source;

    public static final String OP_CODE = "cmp";

    public CmpInstruction(String label, InstructionDestination destination, InstructionSource source) {
        super(label, OP_CODE);
        this.destination = destination;
        this.source = source;
    }

    /** Example
     * cmp reg1, reg2. ex: cmp CX, 1
     *
     * destinationValue = 5
     * sourcevalue = 1
     *
     * destination = OperandRegisterd
     * source is OperandImmediate
     *
     * Compare the contents of registers reg1 and reg2 and set flags ZF and SF:
     * ZF is set if the numbers are equal
     * and SF is set if the first is smaller than the second
     */

    @Override
    public int execute(Machine m) {
        // reset
        m.getFlags().setZF(false);
        m.getFlags().setSF(false);

        int sourceValue = source.getValue();
        int destinationValue = destination.getValue();

        if (destinationValue < sourceValue) {
            m.getFlags().setSF(true);
        } else if (sourceValue == destinationValue) {
            m.getFlags().setZF(true);
        }
        return getSize();
    }

    @Override
    public int getSize() {
        return 1 + source.getSize() + destination.getSize();
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + destination + ", " + source;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CmpInstruction that = (CmpInstruction) obj;
        return Objects.equals(label, that.label)
                && Objects.equals(destination, that.destination)
                && Objects.equals(source, that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, destination, source);
    }
}
