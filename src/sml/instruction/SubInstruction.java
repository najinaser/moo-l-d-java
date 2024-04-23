package sml.instruction;

import sml.Instruction;
import sml.InstructionDestination;
import sml.InstructionSource;
import sml.Machine;

import java.util.Objects;

//Subtract the contents of register reg2 from the contents of reg1 and store the result in register reg1
public class SubInstruction extends Instruction {
    private final InstructionDestination result;
    private final InstructionSource source;

    public static final String OP_CODE = "sub";

    public SubInstruction(String label, InstructionDestination result, InstructionSource source) {
        super(label, OP_CODE);
        this.result = result;
        this.source = source;
    }

    @Override
    public int execute(Machine m) {
        int sourceValue = source.getValue();
        result.setValue(result.getValue() - sourceValue);
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
        SubInstruction that = (SubInstruction) obj;
        return Objects.equals(label, that.label)
                && Objects.equals(result, that.result)
                && Objects.equals(source, that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, result, source);
    }
}
