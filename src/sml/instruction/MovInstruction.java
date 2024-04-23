package sml.instruction;

import sml.Instruction;
import sml.InstructionDestination;
import sml.InstructionSource;
import sml.Machine;

import java.util.Objects;

public class MovInstruction extends Instruction {
    private final InstructionDestination result;
    private final InstructionSource source;

    public static final String OP_CODE = "mov";

    public MovInstruction(String label, InstructionDestination result, InstructionSource source) {
        super(label, OP_CODE);
        this.result = result;
        this.source = source;
    }

    @Override
    public int execute(Machine m) {
        int value = source.getValue();
        result.setValue(value);
        return getSize();
    }

    @Override
    public int getSize() {
        return 1 + source.getSize() + result.getSize();
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + ", " + source;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MovInstruction that = (MovInstruction) obj;

        return Objects.equals(label, that.label)
                && Objects.equals(result, that.result)
                && Objects.equals(source, that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, result, source);
    }
}
