package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.*;

import static sml.Registers.RegisterNameImpl.*;

class CmpInstructionTest {
    private Machine machine;
    private Registers registers;

    @BeforeEach
    void setUp() {
        machine = new Machine(10);
        registers = machine.getRegisters();
    }

    @AfterEach
    void tearDown() {
        machine = null;
        registers = null;
    }

    @Test
    void executeNumbersAreEqual() {
        registers.set(AX, 30);
        Instruction instruction = new CmpInstruction(null, new OperandRegister(AX, registers), new OperandImmediate(30));
        instruction.execute(machine);
        Assertions.assertTrue(machine.getFlags().getZF());// zero
    }

    @Test
    void executeFirstSmallerThanSecond() {
        registers.set(AX, 3);
        Instruction instruction = new CmpInstruction(null, new OperandRegister(AX, registers), new OperandImmediate(4));
        instruction.execute(machine);
        Assertions.assertTrue(machine.getFlags().getSF());// sign
    }
    @Test
    void executeNoChange() {
        registers.set(AX, 3);
        Instruction instruction = new CmpInstruction(null, new OperandRegister(AX, registers), new OperandImmediate(2));
        instruction.execute(machine);
        Assertions.assertFalse(machine.getFlags().getSF());// sign
        Assertions.assertFalse(machine.getFlags().getZF());// zero
    }

    @Test
    void testEqualFunction() {
        OperandRegister operandRegister = new OperandRegister(DX, registers);
        OperandImmediate operandImmediate = new OperandImmediate(100);

        CmpInstruction instruction = new CmpInstruction("f3", operandRegister, operandImmediate);

        // test same object
        Assertions.assertTrue(instruction.equals(instruction));

        // different label
        CmpInstruction instruction1 = new CmpInstruction("label", operandRegister, operandRegister);
        Assertions.assertFalse(instruction.equals(instruction1));

        // Same operations
        CmpInstruction instruction2 = new CmpInstruction("f3", operandRegister, operandImmediate);
        Assertions.assertTrue(instruction.equals(instruction2));

        // different Operation
        CmpInstruction instruction4 = new CmpInstruction("label", operandRegister, new OperandRegister(CX, registers));
        Assertions.assertFalse(instruction.equals(instruction4));

        // different Instruction
        SubInstruction subInstruction = new SubInstruction("f3", operandRegister, operandImmediate);
        Assertions.assertFalse(instruction.equals(subInstruction));

        // String
        Assertions.assertFalse(instruction2.equals("instruction4"));

        // test null
        Assertions.assertFalse(instruction.equals(null));
    }

    @Test
    void testHashCodeFunction() {
        OperandRegister operandRegister = new OperandRegister(DX, registers);
        OperandImmediate operandImmediate = new OperandImmediate(100);

        CmpInstruction instruction1 = new CmpInstruction("label1", operandRegister, operandImmediate);
        CmpInstruction instruction2 = new CmpInstruction("label2", operandRegister, operandImmediate);

        // Since instruction1 and instruction2 have different labels, destinations, and sources,
        // their hash codes should be different.
        Assertions.assertEquals(instruction1.hashCode(), instruction1.hashCode());
        Assertions.assertEquals(instruction2.hashCode(), instruction2.hashCode());
        Assertions.assertFalse(instruction2.hashCode() == instruction1.hashCode());
    }
}