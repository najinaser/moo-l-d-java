package sml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sml.exceptions.LabelDoesNotExistException;
import sml.instruction.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.io.File;

import static sml.Registers.RegisterNameImpl.*;

import java.io.IOException;
import java.util.*;

public class TranslateTest {

    String absolutePath = Paths.get("").toAbsolutePath().toString() + File.separator;
    String allInstructionsFilePath = absolutePath + "resources/test-all-instructions.sml";
    String labelDoesNotExistTestPath = absolutePath + "resources/test-label-does-not-exist.sml";
    String firstTestFilePath = absolutePath + "resources/test1.sml";
    String secondTestFilePath = absolutePath + "resources/test2.sml";

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Test
    void testTranslatorShouldKnowAllInstructions() {
        System.setOut(new PrintStream(outputStreamCaptor));
        Translator t = new Translator(allInstructionsFilePath);
        Machine m = new Machine(0x40_000);
        try {
            t.readAndTranslate(m);

            System.setOut(System.out);
            Assertions.assertEquals(9, m.getProgram().size());
            Assertions.assertTrue(m.getProgram().get(0) instanceof MovInstruction);
            Assertions.assertTrue(m.getProgram().get(2) instanceof AddInstruction);
            Assertions.assertTrue(m.getProgram().get(4) instanceof SubInstruction);
            Assertions.assertTrue(m.getProgram().get(6) instanceof CmpInstruction);
            Assertions.assertTrue(m.getProgram().get(8) instanceof MulInstruction);
            Assertions.assertTrue(m.getProgram().get(9) instanceof DivInstruction);
            Assertions.assertTrue(m.getProgram().get(10) instanceof JgeInstruction);
            Assertions.assertTrue(m.getProgram().get(11) instanceof JneInstruction);
            Assertions.assertTrue(m.getProgram().get(12) instanceof JleInstruction);
            Assertions.assertTrue(outputStreamCaptor.toString().trim().contains("Unknown instruction: fak"));
        } catch (IOException e) {
            Assertions.assertTrue(false);
            throw new RuntimeException(e);
        }
    }
    @Test
    void testFirstFile() throws IOException {
        /**
         *     mov AX, 1    ax=6
         *     mov CX, 6    cx=6
         * f3: mul CX       ax=6, dx=6 (2) ax=30, dx=30 (3) ax = 120, dx=120 (4) 360  (5) 720
         *     sub CX, 1    cx=5,      (2) cx=4,        (3) cx = 3           (4) cx=2 (5) cx=1
         *     cmp CX, 1                                                              (5) zf=1
         *     jge f3
         */
        Translator t = new Translator(firstTestFilePath);
        Machine m = new Machine(0x40_000);
        t.readAndTranslate(m);

        m.execute();

        Assertions.assertEquals(720, m.getRegisters().get(AX));
        Assertions.assertEquals(0, m.getRegisters().get(DX));
        Assertions.assertEquals(0, m.getRegisters().get(BP));
        Assertions.assertEquals(0, m.getRegisters().get(BX));
        Assertions.assertEquals(0, m.getRegisters().get(SP));
        Assertions.assertEquals(0, m.getRegisters().get(CX));

        Assertions.assertFalse(m.getFlags().getZF());
        Assertions.assertTrue(m.getFlags().getSF());
    }

    @Test
    void testSecondFile() throws IOException {
        Translator t = new Translator(secondTestFilePath);
        Machine m = new Machine(0x40_000);

        t.readAndTranslate(m);
        m.execute();

        Assertions.assertEquals(32, m.getProgram().size());

        Assertions.assertEquals(42, m.getRegisters().get(AX));
        Assertions.assertEquals(0, m.getRegisters().get(BX));
        Assertions.assertEquals(11, m.getRegisters().get(CX));
        Assertions.assertEquals(100, m.getRegisters().get(DX));
        Assertions.assertEquals(0, m.getRegisters().get(SP));
        Assertions.assertEquals(110, m.getRegisters().get(BP));
    }

    @Test
    void testLabelDoesNotExist() {
        Translator t = new Translator(labelDoesNotExistTestPath);
        Machine m = new Machine(0x40_000);

        Assertions.assertThrows(LabelDoesNotExistException.class, () -> {
            t.readAndTranslate(m);
            m.execute();
        });
    }
}
