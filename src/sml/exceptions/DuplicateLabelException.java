package sml.exceptions;

/**
 * @author naji
 * label to be thrown when we add the same label more than one time (Label duplication)
 */
public class DuplicateLabelException extends RuntimeException {
    public DuplicateLabelException(String label) {
        super("Duplicate label: " + label);
    }
}
