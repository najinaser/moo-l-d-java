package sml.instruction;

import sml.*;

import java.util.Objects;

import static sml.Registers.RegisterNameImpl.AX;
import static sml.Registers.RegisterNameImpl.DX;

public class MulInstruction extends Instruction {
    private final InstructionDestination axResult;
    private final InstructionDestination dxResult;
    private final InstructionSource source;

    public static final String OP_CODE = "mul";

    public MulInstruction(String label, InstructionSource source) {
        super(label, OP_CODE);
        this.axResult = new OperandRegister(AX, Registers.getInstance());
        this.dxResult = new OperandRegister(DX, Registers.getInstance());
        this.source = source;
    }

    /**
     * Multiply the contents of registers AX and reg and store the result in registers AX (lower 32 bits) and DX (upper 32 bits)
     * @param m the machine the instruction runs on
     * @return
     */
    @Override
    public int execute(Machine m) {
        int sourceValue = source.getValue();
        int axRegisterValue = axResult.getValue();

        long result = (long) axRegisterValue * sourceValue;

        // Extract lower 32 bits to store in AX
        int AXResult = (int) result;

        // Extract upper 32 bits to store in DX
        int DXResult = (int) (result >> 32);

//        int result = sourceValue * axRegisterValue;
        axResult.setValue(AXResult);
        dxResult.setValue(DXResult);
        return getSize();
    }

    @Override
    public int getSize() {
        return 1 + source.getSize() + axResult.getSize() + dxResult.getSize();
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + source;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MulInstruction that = (MulInstruction) obj;
        return Objects.equals(label, that.label)
                && Objects.equals(source, that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, source);
    }
}
