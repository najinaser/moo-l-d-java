import sml.Machine;
import sml.Translator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class RunSml {

    private Translator translator;

    RunSml(Translator t) {
        this.translator = t;
    }

    public void execute() {

        try {
            Machine m = new Machine(0x40_000);
            this.translator.readAndTranslate(m);

            System.out.println("Here is the program; it has " + m.getProgram().size() + " instructions.");
            System.out.println(m);

            System.out.println("Beginning program execution.");
            m.execute();
            System.out.println("Ending program execution.");

            System.out.println("Values of registers at program termination: " + m.getRegisters() + ".");
            System.out.println("Contents of memory after program termination:\n" + m.getMemory());
        }
        catch (IOException e) {
            System.out.println("Error reading the program");
        }
    }
}
