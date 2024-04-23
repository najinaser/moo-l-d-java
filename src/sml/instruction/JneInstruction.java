package sml.instruction;

import sml.Machine;

public class JneInstruction extends JumpInstruction {

    public static final String OP_CODE = "jne";

    public JneInstruction(String label, String destination) {
        super(label, OP_CODE, destination);
    }

    /**
     * If the ZF flag is not set ("not equal"),
     * then make the statement labeled L the next statement to execute;
     * otherwise, continue normally.
     */
    @Override
    protected boolean instructionCondition(Machine m) {
        boolean zf = m.getFlags().getZF();
        return !zf;
    }
}
