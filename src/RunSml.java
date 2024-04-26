import sml.Machine;
import sml.Translator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class RunSml {

    private Translator translator;

    private Machine machine;

    RunSml(Translator t, Machine m) {
        this.translator = t;
        this.machine = m;
    }

    public void execute() {

        try {
            this.translator.readAndTranslate(this.machine);

            System.out.println("Here is the program; it has " + this.machine.getProgram().size() + " instructions.");
            System.out.println(this.machine);

            System.out.println("Beginning program execution.");
            this.machine.execute();
            System.out.println("Ending program execution.");

            System.out.println("Values of registers at program termination: " + this.machine.getRegisters() + ".");
            System.out.println("Contents of memory after program termination:\n" + this.machine.getMemory());
        }
        catch (IOException e) {
            System.out.println("Error reading the program");
        }
    }
}
