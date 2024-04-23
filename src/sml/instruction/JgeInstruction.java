package sml.instruction;

import sml.Machine;

public class JgeInstruction extends JumpInstruction {

    public static final String OP_CODE = "jge";

    public JgeInstruction(String label, String destination) {
        super(label, OP_CODE, destination);
    }

    /**
     * If the SF flag is not set or the ZF flag is set ("greater than or equal"),
     * then make the statement labeled L the next statement to execute;
     * otherwise, continue normally.
     */
    @Override
    protected boolean instructionCondition(Machine m) {
        boolean sf = m.getFlags().getSF();
        boolean zf = m.getFlags().getZF();
        return !sf || zf;
    }
}
