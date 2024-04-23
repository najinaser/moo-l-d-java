package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.*;
import sml.exceptions.LabelDoesNotExistException;
import sml.instruction.JgeInstruction;

class JgeInstructionTest {
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
    void testShouldJumpWhenEqual() {
        machine.getFlags().setZF(false);
        machine.getFlags().setSF(false);
        machine.getLabels().addLabel("f3", 3);
        //jge f3
        Instruction instruction = new JgeInstruction(null, "f3");
        int size = instruction.execute(machine);
        Assertions.assertEquals(0, size);
    }

    @Test
    void testShouldJumpWhenLess() {
        machine.getFlags().setZF(true);
        machine.getFlags().setSF(false);
        machine.getLabels().addLabel("f3", 3);
        //jge f3
        Instruction instruction = new JgeInstruction(null, "f3");
        int size = instruction.execute(machine);
        Assertions.assertEquals(0, size);
    }

    @Test
    void executeNoJumpWHenGreater() {
        machine.getFlags().setZF(false);
        machine.getFlags().setSF(true);
        Instruction instruction = new JgeInstruction(null, "f3");
        int size = instruction.execute(machine);
        Assertions.assertEquals(1, size);
    }

    @Test
    void testEqualFunction() {
        JgeInstruction instruction = new JgeInstruction("label1", "destination1");

        // test same object
        Assertions.assertTrue(instruction.equals(instruction));

        // different label
        JgeInstruction instruction1 = new JgeInstruction("label", "destination1");
        Assertions.assertFalse(instruction.equals(instruction1));

        // Same operations
        JgeInstruction instruction2 = new JgeInstruction("label1", "destination1");
        Assertions.assertTrue(instruction.equals(instruction2));

        // different Operation
        JgeInstruction instruction4 = new JgeInstruction("label1", "destination2");
        Assertions.assertFalse(instruction.equals(instruction4));

        // String
        Assertions.assertFalse(instruction2.equals("instruction4"));

        // test null
        Assertions.assertFalse(instruction.equals(null));
    }

    @Test
    void testHashCodeFunction() {
        JgeInstruction instruction1 = new JgeInstruction("label1", "destination1");
        JgeInstruction instruction2 = new JgeInstruction("label2", "destination1");

        Assertions.assertEquals(instruction1.hashCode(), instruction1.hashCode());
        Assertions.assertEquals(instruction2.hashCode(), instruction2.hashCode());
        Assertions.assertFalse(instruction2.hashCode() == instruction1.hashCode());
    }

    @Test
    void executeLabelDoesnotExistException() {
        machine.getFlags().setZF(true);
        machine.getFlags().setSF(true);
        Instruction instruction = new JgeInstruction(null, "doesnotexist");

        Assertions.assertThrows(LabelDoesNotExistException.class, () -> {
            instruction.execute(machine);
        });
    }
}