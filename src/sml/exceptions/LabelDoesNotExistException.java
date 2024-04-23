package sml.exceptions;

/**
 * Exception will be thrown when the label does not exist
 */
public class LabelDoesNotExistException extends RuntimeException {

    public LabelDoesNotExistException(String label) {
        super("Label does not exist: " + label);
    }
}