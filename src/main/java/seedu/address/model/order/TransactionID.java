package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Order's Transaction ID in the Order List.
 * Guarantees: immutable; is valid as declared in {@link #isValidTID(String)}
 */
public class TransactionID {

    public static final String MESSAGE_CONSTRAINTS =
            "Transaction ID should not be empty.";
    /*
     * Check if first character of given input is whitespace.
     * Otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String tid;

    /**
     * Constructs an {@code TransactionID}.
     *
     * @param tid A valid transaction ID.
     */
    public TransactionID(String tid) {
        requireNonNull(tid);
        checkArgument(isValidTID(tid), MESSAGE_CONSTRAINTS);
        this.tid = tid;
    }

    /**
     * Returns true if a given string is a valid transaction ID.
     */
    public static boolean isValidTID(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        return tid.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
                || (obj instanceof TransactionID // instanceof handles nulls
                && tid.equals(((TransactionID) obj).tid)); // state check
    }

    @Override
    public String toString() {
        return tid;
    }
}
