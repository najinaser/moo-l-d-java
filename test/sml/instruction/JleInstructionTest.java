package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.exceptions.LabelDoesNotExistException;
import sml.instruction.JleInstruction;

class JleInstructionTest {
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
    void executeJumpToLabelValidWhenBothAreSet() {
        machine.getFlags().setZF(true);
        machine.getFlags().setSF(true);
        machine.getLabels().addLabel("f3", 3);

        Instruction instruction = new JleInstruction(null, "f3");
        int size = instruction.execute(machine);
        Assertions.assertEquals(0, size);
    }
    @Test
    void executeJumpToLabelValidWhenZFIsSet() {
        machine.getFlags().setZF(true);
        machine.getFlags().setSF(false);
        machine.getLabels().addLabel("f3", 3);

        Instruction instruction = new JleInstruction(null, "f3");
        int size = instruction.execute(machine);
        Assertions.assertEquals(0, size);
    }

    @Test
    void executeJumpToLabelValidWhenSFIsSet() {
        machine.getFlags().setZF(false);
        machine.getFlags().setSF(true);
        machine.getLabels().addLabel("f3", 3);

        Instruction instruction = new JleInstruction(null, "f3");
        int size = instruction.execute(machine);
        Assertions.assertEquals(0, size);
    }

    @Test
    void executeNoJumpValid() {
        machine.getFlags().setZF(false);
        machine.getFlags().setSF(false);
        Instruction instruction = new JleInstruction(null, "f3");
        int size = instruction.execute(machine);
        Assertions.assertEquals(instruction.getSize(), size);
    }
    @Test
    void testEqualFunction() {
        JleInstruction instruction = new JleInstruction("label1", "destination1");
        JleInstruction sameInstruction = new JleInstruction("label1", "destination1");
        JleInstruction differentLabel = new JleInstruction("label2", "destination1");
        JleInstruction differentDestination = new JleInstruction("label1", "destination2");

        Assertions.assertTrue(instruction.equals(sameInstruction));
        Assertions.assertFalse(instruction.equals(differentLabel));
        Assertions.assertFalse(instruction.equals(differentDestination));
        Assertions.assertFalse(instruction.equals(null));
        Assertions.assertFalse(instruction.equals("not an instruction"));
    }

    @Test
    void testHashCodeFunction() {
        JleInstruction instruction1 = new JleInstruction("label1", "destination1");
        JleInstruction instruction2 = new JleInstruction("label2", "destination2");

        Assertions.assertEquals(instruction1.hashCode(), instruction1.hashCode());
        Assertions.assertEquals(instruction2.hashCode(), instruction2.hashCode());
        Assertions.assertFalse(instruction1.hashCode() == instruction2.hashCode());
    }

    @Test
    void executeLabelDoesnotExistException() {
        machine.getFlags().setZF(false);
        machine.getFlags().setSF(true);

        Instruction instruction = new JleInstruction(null, "doesnotexist");
        Assertions.assertThrows(LabelDoesNotExistException.class, () -> {
            instruction.execute(machine);
        });
    }
}