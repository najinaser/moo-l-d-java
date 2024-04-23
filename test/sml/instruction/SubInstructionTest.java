package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.*;

import static sml.Registers.RegisterNameImpl.*;

class SubInstructionTest {
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
        Instruction instruction = new SubInstruction(null, new OperandRegister(BX, registers), new OperandRegister(AX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(1, machine.getRegisters().get(BX));
    }

    /**
     * How ti works.
     * Sub mem register
     * register AX have value 5
     * Memory position 9 have value 8
     * <p>
     * The process will be,
     * memory of value BX + offset (7+2 = 9)
     * the value in mem(9) = 8
     * 8-5 = 3
     */
    @Test
    void executeValidTwo() {
        registers.set(AX, 5);
        registers.set(BX, 7);
        machine.getMemory().set(9, 8);
        Instruction instruction = new SubInstruction(null, new OperandMemoryWithBase(2, machine.getMemory(), BX, registers), new OperandRegister(AX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(3, machine.getMemory().get(9));

    }

    @Test
    void executeValidThree() {
        registers.set(DX, 12);
        Instruction instruction = new SubInstruction(null, new OperandRegister(DX, registers), new OperandImmediate(10));
        instruction.execute(machine);
        Assertions.assertEquals(2, machine.getRegisters().get(DX));
    }

    @Test
    void testEqualFunction() {
        OperandRegister operandRegister = new OperandRegister(DX, registers);
        SubInstruction instruction = new SubInstruction("f3", operandRegister, operandRegister);

        // test same object
        Assertions.assertTrue(instruction.equals(instruction));

        // different label
        SubInstruction instruction1 = new SubInstruction("label", operandRegister, operandRegister);
        Assertions.assertFalse(instruction.equals(instruction1));

        // Same operations
        SubInstruction instruction2 = new SubInstruction("f3", operandRegister, operandRegister);
        Assertions.assertTrue(instruction.equals(instruction2));

        // different Operation
        SubInstruction instruction4 = new SubInstruction("label", operandRegister, new OperandRegister(CX, registers));
        Assertions.assertFalse(instruction.equals(instruction4));

        // different Instruction
        DivInstruction divInstruction = new DivInstruction("f3", operandRegister);
        Assertions.assertFalse(instruction.equals(divInstruction));

        // String
        Assertions.assertFalse(instruction2.equals("instruction4"));

        // test null
        Assertions.assertFalse(instruction.equals(null));
    }

    @Test
    void testHashCodeFunction() {
        OperandRegister operandRegister = new OperandRegister(DX, registers);
        SubInstruction instruction1 = new SubInstruction("label1", operandRegister, operandRegister);
        SubInstruction instruction2 = new SubInstruction("label2", operandRegister, operandRegister);

        Assertions.assertEquals(instruction1.hashCode(), instruction1.hashCode());
        Assertions.assertEquals(instruction2.hashCode(), instruction2.hashCode());
        Assertions.assertFalse(instruction2.hashCode() == instruction1.hashCode());
    }
}