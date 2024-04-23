package sml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.exceptions.DuplicateLabelException;

class LabelsTest {

    private Labels labels;

    @BeforeEach
    void setUp() {
        labels = new Labels();
    }

    @Test
    void testAddLabel() {
        labels.addLabel("Label1", 10);
        labels.addLabel("Label2", 20);

        Assertions.assertEquals(10, labels.getAddress("Label1"));
        Assertions.assertEquals(20, labels.getAddress("Label2"));
    }

    @Test
    void testDuplicateLabels() {
        labels.addLabel("Label1", 10);

        DuplicateLabelException exception = Assertions.assertThrows(DuplicateLabelException.class, () -> {
            labels.addLabel("Label1", 20);
        });

        Assertions.assertEquals("Duplicate label: Label1", exception.getMessage());
    }

    @Test
    void testGetAddressNonExistingLabel() {
        Assertions.assertEquals(-1, labels.getAddress("NonExistingLabel"));
    }

    @Test
    void testToString() {
        labels.addLabel("Label1", 10);
        labels.addLabel("Label2", 20);

        String res = labels.toString();
        String expected = "[Label1 -> 10, Label2 -> 20]";
        Assertions.assertEquals(expected, labels.toString());
    }

    @Test
    void testReset() {
        labels.addLabel("Label1", 10);
        labels.addLabel("Label2", 20);

        labels.reset();

        Assertions.assertEquals(-1, labels.getAddress("Label1"));
        Assertions.assertEquals(-1, labels.getAddress("Label2"));
    }

    @Test
    void testEqualsAndHashCode() {
        Labels labels1 = new Labels();
        labels1.addLabel("Label1", 10);
        labels1.addLabel("Label2", 20);

        Labels labels2 = new Labels();
        labels2.addLabel("Label1", 10);
        labels2.addLabel("Label2", 20);

        Labels labels3 = new Labels();
        labels3.addLabel("Label1", 15);
        labels3.addLabel("Label2", 20);

        Assertions.assertEquals(labels1, labels2);
        Assertions.assertNotEquals(labels1, labels3);

        // Hash codes should be equal for equal objects
        Assertions.assertEquals(labels1.hashCode(), labels2.hashCode());
        Assertions.assertNotEquals(labels1.hashCode(), labels3.hashCode());
    }
}
