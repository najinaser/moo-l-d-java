package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.exceptions.LabelDoesNotExistException;

import java.util.Objects;

public class JleInstruction extends JumpInstruction {

    public static final String OP_CODE = "jle";

    public JleInstruction(String label, String destination) {
        super(label, OP_CODE, destination);
    }

    /**
     * If the SF or the ZF flag is set ("less than or equal"),
     * then make the statement labeled L the next statement to execute;
     * otherwise, continue normally.
     */
    @Override
    protected boolean instructionCondition(Machine m) {
        boolean zf = m.getFlags().getZF();
        boolean sf = m.getFlags().getSF();
        return sf || zf;
    }
}
