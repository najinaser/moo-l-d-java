package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sml.*;

import static sml.Registers.RegisterNameImpl.*;

class MovInstructionTest {
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
    void executeValid() {
        registers.set(AX, 5);
        registers.set(BX, 6);
        Instruction instruction = new MovInstruction(null, new OperandRegister(BX, registers), new OperandRegister(AX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(5, machine.getRegisters().get(BX));
    }

    @Test
    void executeValidTwo() {
        registers.set(AX, 5);
        registers.set(BX, 6);
        Instruction instruction = new MovInstruction(null, new OperandMemoryWithBase(2, machine.getMemory(), BX, registers), new OperandRegister(AX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(5, machine.getMemory().get(8));
    }

    @Test
    void executeValidThree() {
        registers.set(DX, 6);
        Instruction instruction = new MovInstruction(null, new OperandRegister(DX, registers), new OperandImmediate(100));
        instruction.execute(machine);
        Assertions.assertEquals(100, machine.getRegisters().get(DX));
    }

    @Test
    void testToString() {
        registers.set(DX, 6);
        Instruction instruction = new MovInstruction("F3", new OperandRegister(DX, registers), new OperandImmediate(100));
        Assertions.assertEquals("F3: mov DX, 100", instruction.toString());
    }

    @Test
    void testEqualFunction() {
        OperandRegister operandRegister = new OperandRegister(DX, registers);
        OperandImmediate operandImmediate = new OperandImmediate(100);

        MovInstruction instruction = new MovInstruction("f3", operandRegister, operandImmediate);

        // test same object
        Assertions.assertTrue(instruction.equals(instruction));

        // different label
        MovInstruction instruction1 = new MovInstruction("label", operandRegister, operandImmediate);
        Assertions.assertFalse(instruction.equals(instruction1));

        // Same operations
        MovInstruction instruction2 = new MovInstruction("f3", operandRegister, operandImmediate);
        Assertions.assertTrue(instruction.equals(instruction2));

        // different Operation
        MovInstruction instruction4 = new MovInstruction("label", operandRegister, new OperandRegister(CX, registers));
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
        MovInstruction instruction1 = new MovInstruction("label1", new OperandRegister(DX, registers), new OperandImmediate(100));
        MovInstruction instruction2 = new MovInstruction("label2", new OperandRegister(DX, registers), new OperandImmediate(100));

        // Since instruction1 and instruction2 have different labels, destinations, and sources,
        // their hash codes should be different.
        Assertions.assertEquals(instruction1.hashCode(), instruction1.hashCode());
        Assertions.assertEquals(instruction2.hashCode(), instruction2.hashCode());
        Assertions.assertFalse(instruction2.hashCode() == instruction1.hashCode());
    }
}