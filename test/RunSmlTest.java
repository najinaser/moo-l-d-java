//import org.junit.jupiter.api.Test;
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//import java.nio.file.Paths;
//import org.junit.jupiter.api.Assertions;
//
//public class RunSmlTest {
//    @Test
//    public void testRunSml() {
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        PrintStream originalOut = System.out;
//        System.setOut(new PrintStream(outputStream));
//
//        String instructionFileName = Paths.get("src", "test", "resources", "test1.sml").toString();
//
//        RunSml.main(instructionFileName);
//
//        System.setOut(originalOut);
//
//        String output = outputStream.toString();
//
//        Assertions.assertTrue(output.trim().contains("Contents of memory after program termination"));
//    }
//}
