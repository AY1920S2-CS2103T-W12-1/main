package seedu.delino.model.parcel.parcelattributes;

import static java.util.Objects.requireNonNull;
import static seedu.delino.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.logging.Logger;

import seedu.delino.commons.core.LogsCenter;

/**
 * Represents a Order's timeStamp in the order book.
 * Guarantees: immutable; is valid as declared in the method checkTimestamp.
 */
public class TimeStamp {
    public static final String MESSAGE_CONSTRAINTS =
            "Timestamp should have a valid date and time and it should have space in between date and time\n"
            + "Note: Time should be in 24hour format";
    public static final String ERROR_MESSAGE_TIMESTAMP_BEFORE_NOW = "Date and time cannot before current timestamp";
    public static final int VALID_TIMESTAMP = 0;
    public static final int PARSE_ERROR = 1;
    public static final int TIMESTAMP_BEFORE_NOW_ERROR = 2;
    public static final DateTimeFormatter FORMAT_CHECKER = DateTimeFormatter.ofPattern("uuuu-MM-dd HHmm");
    public static final boolean REQUIRE_CHECK_IF_TIMESTAMP_BEFORE_NOW = true;
    public static final boolean NO_CHECK_FOR_TIMESTAMP_BEFORE_NOW = false;
    private static final Logger logger = LogsCenter.getLogger(TimeStamp.class);
    public final LocalDateTime timeStamp;
    public final String value;

    /**
     * Constructs a {@code Timestamp}.
     *
     * @param timeStamp A valid date and time.
     */
    public TimeStamp(String timeStamp, boolean isBeforeCheckRequired) {
        requireNonNull(timeStamp);
        checkArgument(checkTimestamp(timeStamp, isBeforeCheckRequired), MESSAGE_CONSTRAINTS
                , ERROR_MESSAGE_TIMESTAMP_BEFORE_NOW);
        this.timeStamp = LocalDateTime.parse(timeStamp, FORMAT_CHECKER.withResolverStyle(ResolverStyle.STRICT));
        this.value = this.timeStamp.format(FORMAT_CHECKER);
    }


    /**
     * Returns true if a given string is a valid date and time.
     */
    public static int checkTimestamp(String test, boolean isBeforeCheckRequired) {
        logger.fine("Check whether it is a valid timestamp");
        try {
            LocalDateTime userInput = LocalDateTime.parse(test, FORMAT_CHECKER.withResolverStyle(ResolverStyle.STRICT));
            if (checkTimestampBeforeNow(test, isBeforeCheckRequired, userInput)) return TIMESTAMP_BEFORE_NOW_ERROR;
        } catch (DateTimeParseException e) {
            logger.info("Invalid timestamp format encountered: " + test);
            return PARSE_ERROR;
        }
        return VALID_TIMESTAMP;
    }

    /**
     * Check if the timestamp is before current timestamp.
     * @param data which the user input.
     * @param isBeforeCheckRequired determines whether the check is required.
     * @param userInput in LocalDateTime which used to be check.
     * @return true if the timestamp is before now, otherwise, return false.
     */
    private static boolean checkTimestampBeforeNow(String data, boolean isBeforeCheckRequired
            , LocalDateTime userInput) {
        if (isBeforeCheckRequired) {
            if (userInput.compareTo(LocalDateTime.now()) < 0) {
                logger.info("Input timestamp cannot before current date and time: " + data);
                return true;
            }
        }
        return false;
    }

    public LocalDateTime getOrderTimeStamp() {
        return this.timeStamp;
    }
    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TimeStamp // instanceof handles nulls
                && value.equals(((TimeStamp) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
