import sml.*;
//package sml;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class App {

    public static void main(String... args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("./applicationContext.xml");
        Translator translator = context.getBean("translator", Translator.class);

        Machine machine = context.getBean("machine", Machine.class);

        RunSml runSml = new RunSml(translator, machine);
        runSml.execute();
    }
}