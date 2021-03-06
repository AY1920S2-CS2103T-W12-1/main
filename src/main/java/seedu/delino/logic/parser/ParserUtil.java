package seedu.delino.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.delino.logic.commands.ImportCommand.MESSAGE_INVALID_CSV_FILEPATH;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_COD;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_DELIVERY_TIMESTAMP;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_TID;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_WAREHOUSE;
import static seedu.delino.model.parcel.parcelattributes.TimeStamp.REQUIRE_CHECK_IF_TIMESTAMP_BEFORE_NOW;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import seedu.delino.commons.core.LogsCenter;
import seedu.delino.commons.core.index.Index;
import seedu.delino.commons.util.FileUtil;
import seedu.delino.commons.util.StringUtil;
import seedu.delino.logic.parser.exceptions.ParseException;
import seedu.delino.model.parcel.optionalparcelattributes.Comment;
import seedu.delino.model.parcel.optionalparcelattributes.TypeOfItem;
import seedu.delino.model.parcel.order.CashOnDelivery;
import seedu.delino.model.parcel.parcelattributes.Address;
import seedu.delino.model.parcel.parcelattributes.Email;
import seedu.delino.model.parcel.parcelattributes.Name;
import seedu.delino.model.parcel.parcelattributes.Phone;
import seedu.delino.model.parcel.parcelattributes.TimeStamp;
import seedu.delino.model.parcel.parcelattributes.TransactionId;
import seedu.delino.model.parcel.parcelattributes.Warehouse;
import seedu.delino.storage.CsvProcessor;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    private static final Logger logger = LogsCenter.getLogger(ParserUtil.class);

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            logger.info("Invalid user input, parse failure: Index is negative or 0.");
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses using {@code argMultimap} with {@code prefixList} to validate every single
     * prefix values
     *
     * @param prefixList A list of prefixes
     * @param argMultimap Used to get the value from a prefix
     * @throws ParseException A message that includes the combination of all the errors
     */
    public static void parse(List<Prefix> prefixList, ArgumentMultimap argMultimap) throws ParseException {
        prefixList.add(PREFIX_COMMENT);
        prefixList.add(PREFIX_TYPE);
        String errorMessage = "";

        for (Prefix prefix : prefixList) {
            String valueValidation = "";
            switch(prefix.toString()) {
            case "tid/":
                valueValidation = argMultimap.getValue(PREFIX_TID).get();
                requireNonNull(valueValidation);
                valueValidation = valueValidation.trim();
                if (!TransactionId.isValidTid(valueValidation)) {
                    logger.info("Invalid TID encountered" + valueValidation);
                    errorMessage = errorMessage + TransactionId.MESSAGE_CONSTRAINTS + "\n";
                }
                break;

            case "n/":
                valueValidation = argMultimap.getValue(PREFIX_NAME).get();
                requireNonNull(valueValidation);
                valueValidation = valueValidation.trim();
                if (!Name.isValidName(valueValidation)) {
                    logger.info("Invalid Name encountered" + valueValidation);
                    errorMessage = errorMessage + Name.MESSAGE_CONSTRAINTS + "\n";
                }
                break;

            case "p/":
                valueValidation = argMultimap.getValue(PREFIX_PHONE).get();
                requireNonNull(valueValidation);
                valueValidation = valueValidation.trim();
                if (!Phone.isValidPhone(valueValidation)) {
                    logger.info("Invalid Phone encountered" + valueValidation);
                    errorMessage = errorMessage + Phone.MESSAGE_CONSTRAINTS + "\n";
                }
                break;

            case "a/":
                valueValidation = argMultimap.getValue(PREFIX_ADDRESS).get();
                requireNonNull(valueValidation);
                valueValidation = valueValidation.trim();
                if (!Address.isValidAddress(valueValidation)) {
                    logger.info("Invalid Address encountered" + valueValidation);
                    errorMessage = errorMessage + Address.MESSAGE_CONSTRAINTS + "\n";
                }
                break;

            case "e/":
                valueValidation = argMultimap.getValue(PREFIX_EMAIL).get();
                requireNonNull(valueValidation);
                valueValidation = valueValidation.trim();
                if (!Email.isValidEmail(valueValidation)) {
                    logger.info("Invalid Email encountered" + valueValidation);
                    errorMessage = errorMessage + Email.MESSAGE_CONSTRAINTS + "\n";
                }
                break;

            case "dts/":
                valueValidation = argMultimap.getValue(PREFIX_DELIVERY_TIMESTAMP).get();
                requireNonNull(valueValidation);
                valueValidation = valueValidation.trim();
                int result = TimeStamp.checkTimestamp(valueValidation, REQUIRE_CHECK_IF_TIMESTAMP_BEFORE_NOW);
                if (result == TimeStamp.PARSE_ERROR) {
                    logger.info("Invalid timestamp: " + valueValidation);
                    errorMessage = errorMessage + TimeStamp.MESSAGE_CONSTRAINTS + "\n";
                } else if (result == TimeStamp.TIMESTAMP_BEFORE_NOW_ERROR) {
                    logger.info("Input date and time before current timestamp: " + valueValidation);
                    errorMessage = errorMessage + TimeStamp.ERROR_MESSAGE_TIMESTAMP_BEFORE_NOW + "\n";
                }
                break;

            case "w/":
                valueValidation = argMultimap.getValue(PREFIX_WAREHOUSE).get();
                requireNonNull(valueValidation);
                valueValidation = valueValidation.trim();
                if (!Warehouse.isValidAddress(valueValidation)) {
                    logger.info("Invalid Warehouse encountered" + valueValidation);
                    errorMessage = errorMessage + Warehouse.MESSAGE_CONSTRAINTS + "\n";
                }
                break;

            case "cod/":
                valueValidation = argMultimap.getValue(PREFIX_COD).get();
                requireNonNull(valueValidation);
                valueValidation = valueValidation.trim();
                if (!CashOnDelivery.isValidCashValue(valueValidation)) {
                    logger.info("Invalid CashOnDelivery encountered" + valueValidation);
                    errorMessage = errorMessage + CashOnDelivery.MESSAGE_CONSTRAINTS + "\n";
                }
                break;

            case "c/":
                valueValidation = argMultimap.getValue(PREFIX_COMMENT).isEmpty()
                        ? "NIL"
                        : argMultimap.getValue(PREFIX_COMMENT).get();

                requireNonNull(valueValidation);
                valueValidation = valueValidation.trim();
                if (!Comment.isValidComment(valueValidation)) {
                    logger.info("Invalid Comment encountered: " + valueValidation);
                    errorMessage = errorMessage + Comment.MESSAGE_CONSTRAINTS + "\n";
                }
                break;

            default:
                valueValidation = argMultimap.getValue(PREFIX_TYPE).isEmpty()
                        ? "NIL"
                        : argMultimap.getValue(PREFIX_TYPE).get();

                requireNonNull(valueValidation);
                valueValidation = valueValidation.trim();
                if (!TypeOfItem.isValidItemType(valueValidation)) {
                    logger.info("Invalid ItemType encountered" + valueValidation);
                    errorMessage = errorMessage + TypeOfItem.MESSAGE_CONSTRAINTS + "\n";
                }
                break;
            }
        }

        if (!errorMessage.isEmpty()) {
            throw new ParseException(errorMessage);
        }
    }
    //@@author Amoscheong97
    /**
     * Parses a {@code String tid} into a {@code TransactionId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tid} is invalid.
     */
    public static TransactionId parseTid(String tid) throws ParseException {
        requireNonNull(tid);
        String trimmedTid = tid.trim();
        if (!TransactionId.isValidTid(trimmedTid)) {
            throw new ParseException(TransactionId.MESSAGE_CONSTRAINTS);
        }
        return new TransactionId(trimmedTid);
    }
    //@@author
    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }
    //@@author Exeexe93
    /**
     * Parses a {@code String timeStamp} into an {@code TimeStamp}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code timeStamp} is invalid.
     */
    public static TimeStamp parseTimeStamp(String timeStamp) throws ParseException {
        requireNonNull(timeStamp);
        String trimmedTimeStamp = timeStamp.trim();
        logger.fine("Checking whether it is valid timestamp");
        int result = TimeStamp.checkTimestamp(trimmedTimeStamp, REQUIRE_CHECK_IF_TIMESTAMP_BEFORE_NOW);
        if (result == TimeStamp.PARSE_ERROR) {
            logger.info("Invalid timestamp: " + trimmedTimeStamp);
            throw new ParseException(TimeStamp.MESSAGE_CONSTRAINTS);
        } else if (result == TimeStamp.TIMESTAMP_BEFORE_NOW_ERROR) {
            logger.info("Input date and time before current timestamp: " + trimmedTimeStamp);
            throw new ParseException((TimeStamp.ERROR_MESSAGE_TIMESTAMP_BEFORE_NOW));
        }

        return new TimeStamp(trimmedTimeStamp, REQUIRE_CHECK_IF_TIMESTAMP_BEFORE_NOW);
    }
    //@@author JeremyLoh
    /**
     * Parses a {@code String address} into an {@code Warehouse}.
     * Leading and trailing whitespace will be removed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Warehouse parseWarehouse(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Warehouse.isValidAddress(trimmedAddress)) {
            throw new ParseException(Warehouse.MESSAGE_CONSTRAINTS);
        }
        return new Warehouse(trimmedAddress);
    }
    //@@author Amoscheong97
    /**
     * Parses a {@code String cod} into a {@code CashOnDelivery}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code cod} is invalid.
     */
    public static CashOnDelivery parseCash(String cod) throws ParseException {
        requireNonNull(cod);
        String trimmedCash = cod.trim();
        if (!CashOnDelivery.isValidCashValue(trimmedCash)) {
            throw new ParseException(CashOnDelivery.MESSAGE_CONSTRAINTS);
        }
        return new CashOnDelivery(trimmedCash);
    }
    //@@author Exeexe93
    /**
     * Parses a {@code String comment} into a {@code Comment}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code comment} is invalid.
     */
    public static Comment parseComment(String comment) throws ParseException {
        requireNonNull(comment);
        String trimmedComment = comment.trim();
        logger.fine("Check if it is a valid comment" + trimmedComment);
        if (!Comment.isValidComment(trimmedComment)) {
            logger.info("Invalid Comment encountered: " + trimmedComment);
            throw new ParseException(Comment.MESSAGE_CONSTRAINTS);
        }
        return new Comment(trimmedComment);
    }
    //@@author Amoscheong97
    /**
     * Parses a {@code String itemType} into a {@code TypeOfItem}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code itemType} is invalid.
     */
    public static TypeOfItem parseItemType(String itemType) throws ParseException {
        requireNonNull(itemType);
        String itemTypeTrimmed = itemType.trim();
        if (!TypeOfItem.isValidItemType(itemTypeTrimmed)) {
            throw new ParseException(TypeOfItem.MESSAGE_CONSTRAINTS);
        }

        return new TypeOfItem(itemTypeTrimmed);
    }


    //@@author Exeexe93
    /**
     * Parses a {@code String filePath} into a {@code List<String>}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code filePath} is invalid or the csv file unable to read.
     */
    public static List<String> parseCsvFile(String filePath) throws ParseException {
        requireNonNull(filePath);
        String filePathTrimmed = filePath.trim();

        logger.fine("Getting the path of the file: " + filePath);
        Path csvFilePath = Paths.get("data", filePathTrimmed);
        logger.fine("Checking whether the file exists: " + filePath);
        if (!FileUtil.isFileExists(csvFilePath)) {
            logger.info("File not exists: " + filePath);
            throw new ParseException(MESSAGE_INVALID_CSV_FILEPATH + filePath);
        }
        return CsvProcessor.retrieveData(csvFilePath);
    }

}
