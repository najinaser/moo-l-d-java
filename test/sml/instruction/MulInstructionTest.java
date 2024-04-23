package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.*;

import static sml.Registers.RegisterNameImpl.*;

class MulInstructionTest {
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
    void executeValidMulRegister() {
        registers.set(AX, 5);
        registers.set(CX, 3);
        Instruction instruction = new MulInstruction(null,new OperandRegister(CX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(15, machine.getRegisters().get(AX));
        Assertions.assertEquals(0, machine.getRegisters().get(DX));
        Assertions.assertEquals(3, machine.getRegisters().get(CX));
    }

    @Test
    void executeNegativeNumbers() {
        registers.set(AX, 5);
        registers.set(CX, -3);
        Instruction instruction = new MulInstruction(null, new OperandRegister(CX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(-15, machine.getRegisters().get(AX));
        Assertions.assertEquals(-1, machine.getRegisters().get(DX));
        Assertions.assertEquals(-3, machine.getRegisters().get(CX));
    }

    @Test
    void executeBigNumbers() {
        registers.set(AX, 2147483647);
        registers.set(CX, 2);
        Instruction instruction = new MulInstruction(null, new OperandRegister(CX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(-2, machine.getRegisters().get(AX));
        Assertions.assertEquals(0, machine.getRegisters().get(DX));
        Assertions.assertEquals(2, machine.getRegisters().get(CX));
    }

    @Test
    void executeValidMulMem() {
        registers.set(AX, 5);
        registers.set(BX, 7);
        machine.getMemory().set(9, 8);
        Instruction instruction = new MulInstruction(null, new OperandMemoryWithBase(2, machine.getMemory(), BX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(40, machine.getRegisters().get(AX));
        Assertions.assertEquals(0, machine.getRegisters().get(DX));
    }

    @Test
    void testEqualFunction() {
        OperandRegister operandRegister = new OperandRegister(DX, registers);
        OperandImmediate operandImmediate = new OperandImmediate(100);

        MulInstruction instruction = new MulInstruction("f3", operandRegister);

        // test same object
        Assertions.assertTrue(instruction.equals(instruction));

        // different label
        MulInstruction instruction1 = new MulInstruction("label", operandRegister);
        Assertions.assertFalse(instruction.equals(instruction1));

        // Same operations
        MulInstruction instruction2 = new MulInstruction("f3", operandRegister);
        Assertions.assertTrue(instruction.equals(instruction2));

        // different Operation
        MulInstruction instruction4 = new MulInstruction("label", new OperandRegister(CX, registers));
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

        MulInstruction instruction1 = new MulInstruction("label1", operandRegister);
        MulInstruction instruction2 = new MulInstruction("label2", operandRegister);

        Assertions.assertEquals(instruction1.hashCode(), instruction1.hashCode());
        Assertions.assertEquals(instruction2.hashCode(), instruction2.hashCode());
        Assertions.assertFalse(instruction2.hashCode() == instruction1.hashCode());
    }
}