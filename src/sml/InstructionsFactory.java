package sml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class InstructionsFactory {

    private String instrcutionPackage;
    private static final InstructionsFactory instance = new InstructionsFactory();

    public static InstructionsFactory getInstance() {
        return instance;
    }

    private InstructionsFactory() {
        Properties props = new Properties();

        try (InputStream fis = InstructionsFactory.class.getResourceAsStream("resources/beans.properties")) {
            props.load(fis);
            instrcutionPackage = props.getProperty("instruction.package");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Class<?> getInstructionClass(String opcode) throws ClassNotFoundException {
        String className = this.getClassName(opcode);

        return Class.forName(className);
    }

    private String getClassName(String label) {
        // Capitalize the first letter of the label
        String capitalizedLabel = label.substring(0, 1).toUpperCase() + label.substring(1).toLowerCase();

        return instrcutionPackage + "." + capitalizedLabel + "Instruction";
    }
}
