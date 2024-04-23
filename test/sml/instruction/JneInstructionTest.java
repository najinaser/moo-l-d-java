package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.exceptions.LabelDoesNotExistException;
import sml.instruction.JneInstruction;

class JneInstructionTest {
    private Machine machine;

    @BeforeEach
    void setUp() {
        machine = new Machine(10);
    }

    @AfterEach
    void tearDown() {
        machine = null;
    }

    @Test
    void executeJumpToLabelValid() {
        machine.getFlags().setZF(false);
        machine.getLabels().addLabel("f3", 3);

        Instruction instruction = new JneInstruction(null, "f3");
        int size = instruction.execute(machine);
        Assertions.assertEquals(0, size);
    }

    @Test
    void executeNoJumpValid() {
        machine.getFlags().setZF(true);
        Instruction instruction = new JneInstruction(null, "f3");
        int size = instruction.execute(machine);
        Assertions.assertEquals(instruction.getSize(), size);
    }

    @Test
    void testEqualFunction() {
        JneInstruction instruction = new JneInstruction("label", "destination");
        JneInstruction instruction2 = new JneInstruction("label", "destination");
        Assertions.assertTrue(instruction.equals(instruction2));

        JneInstruction instruction3 = new JneInstruction("label1", "destination");
        Assertions.assertFalse(instruction.equals(instruction3));

        JneInstruction instruction4 = new JneInstruction("label", "destination2");
        Assertions.assertFalse(instruction.equals(instruction4));

        Assertions.assertFalse(instruction.equals(null));
    }

    @Test
    void testHashCodeFunction() {
        JneInstruction instruction1 = new JneInstruction("label", "destination");
        JneInstruction instruction2 = new JneInstruction("label", "destination");

        Assertions.assertEquals(instruction1.hashCode(), instruction2.hashCode());
    }

    @Test
    void executeLabelDoesnotExistException() {
        machine.getFlags().setZF(false);

        Instruction instruction = new JneInstruction(null, "doesnotexist");
        Assertions.assertThrows(LabelDoesNotExistException.class, () -> {
            instruction.execute(machine);
        });
    }
}