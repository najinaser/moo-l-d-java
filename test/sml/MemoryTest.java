package sml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MemoryTest {

    @Test
    void testMemoryOperations() {
        Memory memory1 = new Memory(5);
        Memory memory2 = new Memory(5);

        memory1.set(0, 10);
        memory1.set(2, 20);

        memory2.set(0, 10);
        memory2.set(2, 20);

        Assertions.assertEquals(20, memory2.get(2));

        Assertions.assertEquals(memory1, memory2);
        Assertions.assertEquals(memory1.hashCode(), memory2.hashCode());

        memory2.set(0, 99);

        Assertions.assertNotEquals(memory1, memory2);
        Assertions.assertNotEquals(memory1.hashCode(), memory2.hashCode());

    }
}

