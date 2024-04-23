package sml.instruction;

import sml.*;

import java.util.Objects;

import static sml.Registers.RegisterNameImpl.AX;
import static sml.Registers.RegisterNameImpl.DX;

public class DivInstruction extends Instruction {
    private final InstructionDestination axResult;
    private final InstructionDestination dxResult;
    private final InstructionSource source;

    public static final String OP_CODE = "div";

    public DivInstruction(String label, InstructionSource source) {
        super(label, OP_CODE);
        this.axResult = new OperandRegister(AX, Registers.getInstance());
        this.dxResult = new OperandRegister(DX, Registers.getInstance());
        this.source = source;
    }

    @Override
    public int execute(Machine m) {
        int sourceValue = source.getValue();

        int axRegisterValue = axResult.getValue();
        int dxRegisterValue = dxResult.getValue();

        long combinedResult = ((long) dxRegisterValue << 32) | (axRegisterValue & 0xFFFFFFFFL);
        int quotient = (int) (combinedResult / sourceValue);
        int remainder = (int) (combinedResult % sourceValue);

        axResult.setValue(quotient);
        dxResult.setValue(remainder);
        return getSize();
    }

    @Override
    public int getSize() {
        return 1 + source.getSize() + axResult.getSize();
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + source;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DivInstruction that = (DivInstruction) obj;
        return Objects.equals(label, that.label)
                && Objects.equals(source, that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, source);
    }
}
