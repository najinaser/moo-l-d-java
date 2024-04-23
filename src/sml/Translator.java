package sml;

import sml.instruction.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;

public final class Translator {

    private final String fileName; // source file of SML code

    // line contains the characters in the current line that's not been processed yet
    private String line = "";

    public Translator(String fileName) {
        this.fileName = fileName;
    }

    public void readAndTranslate(Machine machine) throws IOException {
        Labels labels = machine.getLabels();
        Map<Integer, Instruction> program = machine.getProgram();

        // translate the small program in the file into
        // the labels and the program

        try (var sc = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
            labels.reset();
            program.clear();
            int programCounter = 0;

            // each iteration processes the contents of line
            // and reads the next input line into "line"
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String label = getLabel();

                Instruction instruction = getInstruction(label, machine);
                if (instruction != null) {
                    if (label != null)
                        labels.addLabel(label, programCounter);
                    program.put(programCounter, instruction);
                    programCounter += instruction.getSize();
                }
            }
        }
    }

    /**
     * Translates the current line into an instruction with the given label
     *
     * @param label the instruction label
     * @return the new instruction
     * <p>
     * The input line should consist of a single SML instruction,
     * with its label already removed.
     */
    private Instruction getInstruction(String label, Machine machine) {
        if (line.isEmpty())
            return null;

        String opcode = scan(false);

        InstructionsFactory instructionsFactory = InstructionsFactory.getInstance();
        initParameterFunctionMap(machine);

        try {
            Class<?> instructionClass = instructionsFactory.getInstructionClass(opcode);

            for (Constructor<?> candidateConstructor : instructionClass.getConstructors()) {
                Class<?>[] parametersTypes = candidateConstructor.getParameterTypes();
                Object[] parameterObjs = prepareParameterObjects(parametersTypes, label, machine);
                return (Instruction) candidateConstructor.newInstance(parameterObjs);
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Unknown instruction: " + opcode);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    // Map to store functions for converting parameter values based on their types
    private Map<Class<?>, Function<String, ?>> typeFunctionMap = new HashMap<>();
    /**
     * Initializes the typeFunctionMap with default mappings.
     * @param machine The machine instance used in the conversions.
     */

    private void initParameterFunctionMap(Machine machine) {
        typeFunctionMap.put(String.class, Function.identity());
        typeFunctionMap.put(InstructionDestination.class, s -> getDestination(s, machine));
        typeFunctionMap.put(InstructionSource.class, s -> getSource(s, machine));
        // We can add more as much as we need
    }
    /**
     * Prepares an array of parameter objects based on the provided parameter types,
     * label, and machine. Utilizes the typeFunctionMap for converting parameter values.
     *
     * @param parameterTypes The array of parameter types.
     * @param label The label value.
     * @param machine The machine instance.
     * @return An array of parameter objects.
     * @throws IllegalArgumentException If an unsupported parameter type is encountered.
     */
    private Object[] prepareParameterObjects(Class<?>[] parameterTypes, String label, Machine machine) {
        Object[] parameterObjs = new Object[parameterTypes.length];
        parameterObjs[0] = label;

        for (int i = 1; i < parameterTypes.length; i++) {
            boolean isLastParameter = (i == parameterTypes.length - 1);
            String parameterValue = scan(!isLastParameter);
            Class<?> parameterType = parameterTypes[i];

            if (typeFunctionMap.containsKey(parameterType)) {
                parameterObjs[i] = typeFunctionMap.get(parameterType).apply(parameterValue);
            } else {
                // Handle unsupported parameterType or throw an exception
                throw new IllegalArgumentException("Unsupported parameter type: " + parameterType);
            }
        }

        return parameterObjs;
    }

    /**
     * s= BP, returns Operandregister
     * s= [BP] , returns operandMemoryWithBase
     * s= 100, returns OperandImmediate
     *
     * @param s
     * @param machine
     * @return
     */

    private InstructionSource getSource(String s, Machine machine) {
        return Optional.<InstructionSource>empty()
                .or(() -> OperandImmediate.parseOperandImmediate(s))
                .or(() -> OperandMemory.parseOperandMemory(s, machine.getMemory()))
                .or(() -> OperandMemoryWithBase.parseOperandMemoryWithBase(s, machine.getMemory(), machine.getRegisters()))
                .or(() -> OperandRegister.parseOperandRegister(s, machine.getRegisters()))
                .orElseThrow(() -> new IllegalArgumentException("invalid instruction source: " + s));
    }

    private InstructionDestination getDestination(String s, Machine machine) {
        return Optional.<InstructionDestination>empty()
                .or(() -> OperandMemory.parseOperandMemory(s, machine.getMemory()))
                .or(() -> OperandMemoryWithBase.parseOperandMemoryWithBase(s, machine.getMemory(), machine.getRegisters()))
                .or(() -> OperandRegister.parseOperandRegister(s, machine.getRegisters()))
                .orElseThrow(() -> new IllegalArgumentException("invalid instruction destination: " + s));
    }

    private String getLabel() {
        String word = scan(false);
        if (word.endsWith(":"))
            return word.substring(0, word.length() - 1);

        // undo scanning the word
        line = word + " " + line;
        return null;
    }

    /**
     * Return the first word of line and remove it from line.
     * If there is no word, return "".
     *
     * @param comma remove the trailing comma if set to true
     */
    private String scan(boolean comma) {
        line = line.trim();

        int whiteSpacePosition = 0;
        while (whiteSpacePosition < line.length()) {
            if (Character.isWhitespace(line.charAt(whiteSpacePosition)))
                break;
            whiteSpacePosition++;
        }

        String word = line.substring(0, whiteSpacePosition);
        line = line.substring(whiteSpacePosition);
        if (comma) {
            if (word.endsWith(","))
                return word.substring(0, word.length() - 1);
            throw new IllegalArgumentException("Expected a comma after " + word);
        }
        return word;
    }
}