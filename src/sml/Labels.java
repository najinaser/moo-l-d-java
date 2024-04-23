package sml;

import sml.exceptions.DuplicateLabelException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

// TODO: write a JavaDoc for the class
// DONE

/**
 * @author naji
 *
 * Represents a collection of labels and their corresponding addresses in a machine.
 * Labels are used in machine programs to mark specific points in the program
 * that can be referred to by instructions.
 * <p>
 * This class provides methods for adding labels, retrieving addresses based on labels,
 * resetting the label collection, and generating a string representation of the labels.
 * </p>
 * <p>
 * The class is designed to prevent the addition of duplicate labels.
 * If an attempt is made to add a label that already exists, the existing label's address
 * will be updated with the new address.
 * </p>
 *
 */
public final class Labels {
    private final Map<String, Integer> labels = new HashMap<>();

    /**
     * Adds a label with the associated address to the map.
     *
     * @param label the label
     * @param address the address the label refers to
     */
    public void addLabel(String label, int address) {
        Objects.requireNonNull(label);
        // TODO: Add a check that ensures that label duplicates are not simply ignored.
        //       What is the best way of handling duplicate labels?
        // DONE
        /**
         *  The label serves as a unique identifier for a specific memory address or instruction,
         *  and having multiple labels with the same name can lead to ambiguity and unexpected behavior.
         *
         *  There is 2 way of handeling this situation:
         *  1- Throw an exception indicating that a duplicate label is not allowed.
         *  The advantage if this approach, it ensures that the program is aware of the attempt to add a
         *  duplicate label and can handle it appropriately.
         *
         *  2- Override the previous value with the new one.
         *  This can lead to confusion and unintended consequences, and it have to be used carefully.
         *
         *  For this reason, I will chose the first approach
         */

        // Check if the label already exists in the map.
        if (labels.containsKey(label)) {
            throw new DuplicateLabelException(label);
        }

        labels.put(label, address);
    }

    /**
     * Returns the address associated with the label.
     *
     * @param label the label
     * @return the address the label refers to
     */
    public int getAddress(String label) {
        /**
         * Maybe the label is after, so that is not loop?
         */
        // TODO: A NullPointerException can be thrown in the code line below
        //       even when the label is not null. Why can it happen?
        //       (Write an explanation.)
        //       What is the best way of dealing with non-existing labels?
        //       Add code to deal with them.
        // DONE

        /**
         *  labels.get(label) will return null when the label does not exist in the labels hashmap, this will happen
         *  when the labels does not have the value of the label, regardless if it's null or non-existing string.
         *
         *  However, It will throw NullPointerException because we are trying to return null whereas we should return int.
         *
         *  I think the best way to handle non-existing labels more carefully is to return a special value, such as -1,
         *  to indicate that the label doesn't exist. This way, the calling code (the client) can check for this special value and handle it accordingly.
         *
         *  Other solution could be is to throw a exception, e.g LabelDoesNotExist exception. and the client can catch this
         *  exception if they need to
         *
         *  I have doen some checks and exception to throw an error in the clint side (the code which is using this code)
         */
        Integer address = labels.get(label);
        return (address != null) ? address : -1;
    }

    /**
     * representation of this instance,
     * in the form "[label -> address, label -> address, ..., label -> address]"
     *
     * @return the string representation of the labels map
     */
    @Override
    public String toString() {
        return labels.entrySet().stream()
                .map(entry -> entry.getKey() + " -> " + entry.getValue())
                .collect(Collectors.joining(", ", "[", "]"));
    }

    // TODO: Implement methods .equals and .hashCode (needed in class Machine).
    // DONE

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Labels other = (Labels) obj;
        return Objects.equals(labels, other.labels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(labels);
    }

    /**
     * Removes the labels
     */
    public void reset() {
        labels.clear();
    }
}
