package sml;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Registers.RegisterNameImpl;

public class RegistersTest {

    private Registers registers;

    @BeforeEach
    void setUp() {
        registers = Registers.getInstance();
    }

    @Test
    void testClear() {
        registers.set(RegisterNameImpl.AX, 42);
        registers.clear();
        Assertions.assertEquals(0, registers.get(RegisterNameImpl.AX));
    }

    @Test
    void testSetAndGet() {
        registers.set(RegisterNameImpl.BX, 100);
        Assertions.assertEquals(100, registers.get(RegisterNameImpl.BX));
    }

    @Test
    void testNotFoundGet() {
        Assertions.assertThrows(ClassCastException.class, () -> registers.get(() -> "NEW"));
    }

    @Test
    void testParseRegisterName() {
        Assertions.assertEquals(RegisterNameImpl.AX, registers.parseRegisterName("AX").orElse(null));
        Assertions.assertEquals(RegisterNameImpl.BP, registers.parseRegisterName("BP").orElse(null));
        Assertions.assertFalse(registers.parseRegisterName("INVALID").isPresent());
    }

    @Test
    void testEquals() {
        Registers otherRegisters = Registers.getInstance();
        otherRegisters.set(RegisterNameImpl.CX, 75);

        Assertions.assertEquals(registers, otherRegisters);
    }

    @Test
    void testHashCode() {
        Registers otherRegisters = Registers.getInstance();
        otherRegisters.set(RegisterNameImpl.DX, 50);

        Assertions.assertEquals(registers.hashCode(), otherRegisters.hashCode());
    }

    @Test
    void testToString() {
        registers.set(RegisterNameImpl.SP, 30);
        registers.set(RegisterNameImpl.DX, 20);

        String expected = "[AX = 0, BX = 0, CX = 0, DX = 20, SP = 30, BP = 0]";
        Assertions.assertEquals(expected, registers.toString());
    }
}
