package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.exceptions.LabelDoesNotExistException;

import java.util.Objects;

public abstract class JumpInstruction extends Instruction {
    private final String destination;

    public JumpInstruction(String label, String opcode, String destination) {
        super(label, opcode);
        this.destination = destination;
    }

    protected abstract boolean instructionCondition(Machine m);

    @Override
    public int execute(Machine m) {
        boolean sf = m.getFlags().getSF();
        boolean zf = m.getFlags().getZF();

        if(instructionCondition(m)) {
            int labelAddress = m.getLabels().getAddress(destination);
            if (labelAddress == -1) {
                throw new LabelDoesNotExistException(destination);
            }
            m.setProgramCounter(labelAddress);
            return 0;
        }

        return getSize();
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + destination;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        JumpInstruction that = (JumpInstruction) obj;
        return Objects.equals(label, that.label) && Objects.equals(destination, that.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, destination);
    }
}
