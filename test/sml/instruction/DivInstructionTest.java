package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.*;

import static sml.Registers.RegisterNameImpl.*;

class DivInstructionTest {
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
    void executeValidDivRegister() {
        registers.set(AX, 30);
        registers.set(BX, 30);
        registers.set(CX, 3);
        Instruction instruction = new DivInstruction(null, new OperandRegister(CX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(10, machine.getRegisters().get(AX));
        Assertions.assertEquals(0, machine.getRegisters().get(DX));
    }

    @Test
    void executeBigNumberDivRegister() {
        registers.set(AX, -2);
        registers.set(DX, 0);
        registers.set(CX, 2);
        Instruction instruction = new DivInstruction(null, new OperandRegister(CX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(2147483647, machine.getRegisters().get(AX));
        Assertions.assertEquals(0, machine.getRegisters().get(DX));
    }

    @Test
    void executeValidDivRegisterWIthRemaining() {
        registers.set(AX, 21);
        registers.set(DX, 0);
        registers.set(CX, 3);
        Instruction instruction = new DivInstruction(null, new OperandRegister(CX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(7, machine.getRegisters().get(AX));
        Assertions.assertEquals(0, machine.getRegisters().get(DX));
    }

    @Test
    void executeValidMulMem() {
        registers.set(AX, 40);
        registers.set(DX, 0);
        registers.set(BX, 7);
        machine.getMemory().set(9, 8);
        Instruction instruction = new DivInstruction(null, new OperandMemoryWithBase(2, machine.getMemory(), BX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(5, machine.getRegisters().get(AX));
        Assertions.assertEquals(0, machine.getRegisters().get(DX));
    }

    @Test
    void testEqualFunction() {
        OperandRegister operandRegister = new OperandRegister(DX, registers);
        OperandImmediate operandImmediate = new OperandImmediate(100);

        DivInstruction instruction = new DivInstruction("f3", operandRegister);

        // test same object
        Assertions.assertTrue(instruction.equals(instruction));

        // different label
        DivInstruction instruction1 = new DivInstruction("label", operandRegister);
        Assertions.assertFalse(instruction.equals(instruction1));

        // Same operations
        DivInstruction instruction2 = new DivInstruction("f3", operandRegister);
        Assertions.assertTrue(instruction.equals(instruction2));

        // different Operation
        DivInstruction instruction4 = new DivInstruction("label", new OperandRegister(CX, registers));
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

        DivInstruction instruction1 = new DivInstruction("label1", operandRegister);
        DivInstruction instruction2 = new DivInstruction("label2", operandRegister);

        Assertions.assertEquals(instruction1.hashCode(), instruction1.hashCode());
        Assertions.assertEquals(instruction2.hashCode(), instruction2.hashCode());
        Assertions.assertFalse(instruction2.hashCode() == instruction1.hashCode());
    }
}