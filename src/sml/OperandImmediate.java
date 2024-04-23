package sml;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class OperandImmediate implements InstructionSource {
    private final int value;

    private static final Pattern pattern = Pattern.compile("^\\d+$");

    public static Optional<OperandImmediate> parseOperandImmediate(String s) {
        if (pattern.matcher(s).find())
            return Optional.of(new OperandImmediate(Integer.parseInt(s)));

        return Optional.empty();
    }

    public OperandImmediate(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        OperandImmediate that = (OperandImmediate) obj;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
