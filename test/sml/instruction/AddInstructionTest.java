package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.*;

import static sml.Registers.RegisterNameImpl.*;

class AddInstructionTest {
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
    void executeValidRegisterAndRegister() {
        registers.set(AX, 5);
        registers.set(BX, 6);
        Instruction instruction = new AddInstruction(null, new OperandRegister(BX, registers), new OperandRegister(AX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(11, machine.getRegisters().get(BX));
    }

    /**
     * How ti works.
     * Add mem register
     * register AX have value 5
     * Memory position 9 have value 8
     * The process will be,
     * memory of value BX + offset (7+2 = 9)
     * the value in mem(9) = 8
     * 8+5 = 13
     */
    @Test
    void executeValidMemoryAndRegister() {
        registers.set(AX, 5);
        registers.set(BX, 7);
        machine.getMemory().set(9, 8);
        Instruction instruction = new AddInstruction(null, new OperandMemoryWithBase(2, machine.getMemory(), BX, registers), new OperandRegister(AX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(13, machine.getMemory().get(9));

    }

    @Test
    void executeValidRegisterAndMemory() {
        registers.set(DX, 12);
        Instruction instruction = new AddInstruction(null, new OperandRegister(DX, registers), new OperandImmediate(10));
        instruction.execute(machine);
        Assertions.assertEquals(22, machine.getRegisters().get(DX));
    }

    @Test
    void testEqualFunction() {

        OperandRegister operandRegister = new OperandRegister(DX, registers);
        OperandImmediate operandImmediate = new OperandImmediate(100);
        // test same object
        AddInstruction instruction = new AddInstruction("f3", operandRegister, operandImmediate);
        Assertions.assertTrue(instruction.equals(instruction));

        Instruction instructionMemory = new AddInstruction("f3", new OperandMemoryWithBase(2, machine.getMemory(), BX, registers), new OperandImmediate(100));
        Assertions.assertFalse(instruction.equals(instructionMemory));

        // different Operation
        AddInstruction instruction1 = new AddInstruction("label", new OperandRegister(BX, registers), new OperandImmediate(100));
        Assertions.assertFalse(instruction.equals(instruction1));

        // same object
        AddInstruction instruction2 = new AddInstruction("f3", operandRegister, operandImmediate);
        Assertions.assertTrue(instruction.equals(instruction2));

        AddInstruction instruction3 = new AddInstruction("label2", new OperandRegister(DX, registers), new OperandImmediate(100));
        Assertions.assertFalse(instruction2.equals(instruction3));

        // different Operation
        AddInstruction instruction4 = new AddInstruction("label", new OperandRegister(BX, registers), new OperandRegister(CX, registers));
        Assertions.assertFalse(instruction.equals(instruction4));

        // different Instruction
        SubInstruction subInstruction = new SubInstruction("f3", new OperandRegister(DX, registers), new OperandImmediate(100));
        Assertions.assertFalse(instruction.equals(subInstruction));

        // String
        Assertions.assertFalse(instruction2.equals("instruction4"));

        // test null
        Assertions.assertFalse(instruction.equals(null));

        AddInstruction instruction1Null = new AddInstruction(null, operandRegister, operandImmediate);
        AddInstruction instruction2Null = new AddInstruction(null, operandRegister, operandImmediate);
        Assertions.assertTrue(instruction1Null.equals(instruction2Null));
    }

    @Test
    void testHashCodeFunction() {
        AddInstruction instruction1 = new AddInstruction("label1", new OperandRegister(DX, registers), new OperandImmediate(100));
        AddInstruction instruction2 = new AddInstruction("label2", new OperandRegister(DX, registers), new OperandImmediate(100));

        // Since instruction1 and instruction2 have different labels, destinations, and sources,
        // their hash codes should be different.
        Assertions.assertEquals(instruction1.hashCode(), instruction1.hashCode());
        Assertions.assertEquals(instruction2.hashCode(), instruction2.hashCode());
        Assertions.assertFalse(instruction2.hashCode() == instruction1.hashCode());
    }
}