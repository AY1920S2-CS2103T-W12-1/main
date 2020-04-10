package seedu.delino.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String NEWLINE = System.lineSeparator();
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_EMPTY_PREFIXES =
            "Invalid command format! The following prefixes are missing: \n";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_ORDER_DISPLAYED_INDEX = "The order index provided is invalid.";
    public static final String MESSAGE_INVALID_RETURN_DISPLAYED_INDEX = "The return order index provided is invalid.";
    public static final String MESSAGE_MISSING_FLAG = "Please provide flag -o or -r";
    public static final String MESSAGE_MISMATCH_FLAG_WITH_TIMESTAMP = "Seems like you gave the wrong combination to "
        + "edit the date and time!" + NEWLINE + "Please provide flag -o with dts/"
        + " or -r with rts/ to edit the date and time."
        + NEWLINE + "Any other combinations are not allowed!";
    public static final String MESSAGE_NO_COD_FIELD_IN_RETURN_ORDER =
        "There is no cash on delivery field in Return order please check your input.";
    public static final String MESSAGE_ORDERS_LISTED_OVERVIEW = "%d order(s) listed!";
    public static final String MESSAGE_RETURN_ORDERS_LISTED_OVERVIEW = "%d return order(s) listed!";
    public static final String MESSAGE_MISSING_INDEX = "Please provide an index!";
    public static final String MESSAGE_INVALID_PREAMBLE = "Please make sure there is no value between "
        + "the flag and the valid prefixes!";
}