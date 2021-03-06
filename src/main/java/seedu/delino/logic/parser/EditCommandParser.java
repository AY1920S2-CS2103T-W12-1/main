package seedu.delino.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.delino.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.delino.commons.core.Messages.MESSAGE_INVALID_PREAMBLE;
import static seedu.delino.commons.core.Messages.MESSAGE_MISMATCH_FLAG_WITH_TIMESTAMP;
import static seedu.delino.commons.core.Messages.MESSAGE_MISSING_FLAG;
import static seedu.delino.commons.core.Messages.MESSAGE_MISSING_INDEX;
import static seedu.delino.commons.core.Messages.MESSAGE_NO_COD_FIELD_IN_RETURN_ORDER;
import static seedu.delino.commons.core.Messages.NEWLINE;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_COD;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_DELIVERY_TIMESTAMP;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_RETURN_TIMESTAMP;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_TID;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_WAREHOUSE;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.delino.commons.core.LogsCenter;
import seedu.delino.commons.core.index.Index;
import seedu.delino.logic.commands.EditCommand;
import seedu.delino.logic.commands.EditCommand.EditParcelDescriptor;
import seedu.delino.logic.parser.exceptions.ParseException;

//@@author khsc96

/**
 * Parses input arguments and creates a new EditCommand Object
 */
public class EditCommandParser implements Parser<EditCommand> {

    private static final Logger LOGGER = LogsCenter.getLogger(EditCommandParser.class.getName());


    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Flag flag = null;

        checkForEmptyArgsAfterEditCommand(args);

        if (areFlagsPresent(args)) {
            flag = extractFlag(args);
            args = removeFlags(args);
        } else {
            LOGGER.info("Invalid user input, parse failure: Missing flags.");
            throw new ParseException(MESSAGE_MISSING_FLAG + NEWLINE + EditCommand.MESSAGE_USAGE);
        }

        assert (flag != null);

        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_TID, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_DELIVERY_TIMESTAMP, PREFIX_RETURN_TIMESTAMP, PREFIX_WAREHOUSE,
                PREFIX_COD, PREFIX_COMMENT, PREFIX_TYPE);

        Index index;

        try {
            checkForEmptyIndex(argMultimap.getPreamble());
            checkForValidPreamble(argMultimap.getPreamble());
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format("%s" + NEWLINE + "%s",
                pe.getMessage(), EditCommand.MESSAGE_USAGE));
        }

        checkPrefixMatchesFlag(argMultimap, flag);

        EditCommand.EditParcelDescriptor editParcelDescriptor = new EditParcelDescriptor();

        editTid(editParcelDescriptor, argMultimap);
        editName(editParcelDescriptor, argMultimap);
        editPhone(editParcelDescriptor, argMultimap);
        editEmail(editParcelDescriptor, argMultimap);
        editAddress(editParcelDescriptor, argMultimap);
        editTimeStamp(editParcelDescriptor, argMultimap);
        editWarehouse(editParcelDescriptor, argMultimap);
        editComment(editParcelDescriptor, argMultimap);
        editType(editParcelDescriptor, argMultimap);

        if (flag.equals(CliSyntax.FLAG_ORDER_BOOK)) {
            editCod(editParcelDescriptor, argMultimap);
        }

        if (!editParcelDescriptor.isAnyFieldEdited()) {
            LOGGER.info("Invalid user input, parse failure: No field edited.");
            throw new ParseException(String.format(EditCommand.MESSAGE_NOT_EDITED, EditCommand.MESSAGE_USAGE));
        }

        LOGGER.fine(
            String.format("Parse success: EditCommand class returned with %d index and %s flag.",
                index.getOneBased(), flag.getFlag()));
        return new EditCommand(index, editParcelDescriptor, flag);
    }

    /**
     * Checks for presence of any flag given by user, return true if there is any.
     *
     * @param string String object representing the user input.
     * @return Return boolean value representing the presence of any flags.
     */
    private boolean areFlagsPresent(String string) {
        List<String> listOfStr = Arrays.asList(string.split("\\s"));
        return Stream
            .of(CliSyntax.FLAG_RETURN_BOOK, CliSyntax.FLAG_ORDER_BOOK)
            .anyMatch(flag -> listOfStr.contains(flag.getFlag()));
    }

    /**
     * Returns a {@code Flag} object according to the flag user provided in.
     *
     * @param args String object representing the user input.
     * @return {@code Flag} object representing the flag given.
     * @throws ParseException ParseException is thrown when multiple different flags are detected.
     */
    private Flag extractFlag(String args) throws ParseException {
        List<String> argArr = Arrays.asList(args.trim().split("\\s"));
        if (argArr.contains(CliSyntax.FLAG_ORDER_BOOK.getFlag())
            && argArr.contains(CliSyntax.FLAG_RETURN_BOOK.getFlag())) {
            LOGGER.info("Invalid user input, parse failure: Multiple flag provided");
            throw new ParseException(EditCommand.MULTIPLE_FLAGS_DETECTED);
        }

        if (argArr.contains(CliSyntax.FLAG_RETURN_BOOK.getFlag())) {
            LOGGER.finest("Return flag found");
            return CliSyntax.FLAG_RETURN_BOOK;
        } else {
            LOGGER.finest("Order flag found");
            return CliSyntax.FLAG_ORDER_BOOK;
        }
    }

    /**
     * Returns a String object with all flags removed from it.
     *
     * @param args String object representing the input given by the user.
     * @return a String object with all flags removed from it.
     */
    private String removeFlags(String args) {
        List<String> listOfArgs = Arrays.asList(args.split("\\s"));
        String returnString = listOfArgs
            .stream()
            .filter(each -> Stream
                .of(CliSyntax.FLAG_RETURN_BOOK, CliSyntax.FLAG_ORDER_BOOK)
                .noneMatch(flag -> each.equals(flag.getFlag())))
            .map(each -> each + " ")
            .collect(Collectors.joining());
        LOGGER.finest(String.format("User input without flags: %s", returnString));
        return returnString;
    }

    /**
     * Method checks for invalid prefixes with flag provided. Throws {@code ParseException} if valiation occurs.
     */
    private void checkPrefixMatchesFlag(ArgumentMultimap argumentMultimap, Flag flag) throws ParseException {
        if (flag.equals(CliSyntax.FLAG_RETURN_BOOK) && argumentMultimap.getValue(PREFIX_COD).isPresent()) {
            throw new ParseException(MESSAGE_NO_COD_FIELD_IN_RETURN_ORDER);
        }
        if ((argumentMultimap.getValue(PREFIX_DELIVERY_TIMESTAMP).isPresent()
            && flag.equals(CliSyntax.FLAG_RETURN_BOOK))
            || (argumentMultimap.getValue(PREFIX_RETURN_TIMESTAMP).isPresent()
            && flag.equals(CliSyntax.FLAG_ORDER_BOOK))
            || (argumentMultimap.getValue(PREFIX_RETURN_TIMESTAMP).isPresent()
            && argumentMultimap.getValue(PREFIX_DELIVERY_TIMESTAMP).isPresent())) {
            LOGGER.info("Invalid user input, parse failure: Mismatch flag with timestamp");
            throw new ParseException(MESSAGE_MISMATCH_FLAG_WITH_TIMESTAMP);
        }
    }

    /**
     * Edits the {@code EditParcelDescriptor} if {@code TransactionId} prefix is present.
     */
    private EditParcelDescriptor editTid(EditParcelDescriptor editParcelDescriptor, ArgumentMultimap argumentMultimap)
        throws ParseException {
        if (argumentMultimap.getValue(PREFIX_TID).isPresent()) {
            editParcelDescriptor.setTid(ParserUtil.parseTid(argumentMultimap.getValue(PREFIX_TID).get()));
            LOGGER.finest(String.format("Edited Tid: %s", argumentMultimap.getValue(PREFIX_TID).get()));
        }
        return editParcelDescriptor;
    }

    /**
     * Edits the {@code EditParcelDescriptor} if {@code Name} prefix is present.
     */
    private EditParcelDescriptor editName(EditParcelDescriptor editParcelDescriptor, ArgumentMultimap argumentMultimap)
        throws ParseException {
        if (argumentMultimap.getValue(PREFIX_NAME).isPresent()) {
            editParcelDescriptor.setName(ParserUtil.parseName(argumentMultimap.getValue(PREFIX_NAME).get()));
            LOGGER.finest(String.format("Edited Name: %s", argumentMultimap.getValue(PREFIX_NAME).get()));
        }
        return editParcelDescriptor;
    }

    /**
     * Edits the {@code EditParcelDescriptor} if {@code Phone} prefix is present.
     */
    private EditParcelDescriptor editPhone(EditParcelDescriptor editParcelDescriptor, ArgumentMultimap argumentMultimap)
        throws ParseException {
        if (argumentMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editParcelDescriptor.setPhone(ParserUtil.parsePhone(argumentMultimap.getValue(PREFIX_PHONE).get()));
            LOGGER.finest(String.format("Edited Phone: %s", argumentMultimap.getValue(PREFIX_PHONE).get()));
        }
        return editParcelDescriptor;
    }

    /**
     * Edits the {@code EditParcelDescriptor} if {@code Email} prefix is present.
     */
    private EditParcelDescriptor editEmail(EditParcelDescriptor editParcelDescriptor, ArgumentMultimap argumentMultimap)
        throws ParseException {
        if (argumentMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editParcelDescriptor.setEmail(ParserUtil.parseEmail(argumentMultimap.getValue(PREFIX_EMAIL).get()));
            LOGGER.finest(String.format("Edited Email: %s", argumentMultimap.getValue(PREFIX_EMAIL).get()));
        }
        return editParcelDescriptor;
    }

    /**
     * Edits the {@code EditParcelDescriptor} if {@code Address} prefix is present.
     */
    private EditParcelDescriptor editAddress(EditParcelDescriptor editParcelDescriptor,
                                             ArgumentMultimap argumentMultimap) throws ParseException {
        if (argumentMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editParcelDescriptor.setAddress(ParserUtil.parseAddress(argumentMultimap.getValue(PREFIX_ADDRESS).get()));
            LOGGER.finest(String.format("Edited Address: %s", argumentMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        return editParcelDescriptor;
    }

    /**
     * Edits the {@code EditParcelDescriptor} if {@code Timestamp} prefix is present.
     */
    private EditParcelDescriptor editTimeStamp(EditParcelDescriptor editParcelDescriptor,
                                               ArgumentMultimap argumentMultimap) throws ParseException {
        if (argumentMultimap.getValue(PREFIX_DELIVERY_TIMESTAMP).isPresent()) {
            editParcelDescriptor
                .setTimeStamp(
                    ParserUtil.parseTimeStamp(argumentMultimap.getValue(PREFIX_DELIVERY_TIMESTAMP).get()));
            LOGGER.finest(
                String.format(
                    "Edited Delivery TimeStamp: %s", argumentMultimap.getValue(PREFIX_DELIVERY_TIMESTAMP).get()));
        } else if (argumentMultimap.getValue(PREFIX_RETURN_TIMESTAMP).isPresent()) {
            editParcelDescriptor
                .setTimeStamp(ParserUtil.parseTimeStamp(argumentMultimap.getValue(PREFIX_RETURN_TIMESTAMP).get()));
            LOGGER.finest(
                String.format(
                    "Edited Return TimeStamp: %s", argumentMultimap.getValue(PREFIX_RETURN_TIMESTAMP).get()));
        }

        return editParcelDescriptor;
    }

    /**
     * Edits the {@code EditParcelDescriptor} if {@code Warehouse} prefix is present.
     */
    private EditParcelDescriptor editWarehouse(EditParcelDescriptor editParcelDescriptor,
                                               ArgumentMultimap argumentMultimap) throws ParseException {
        if (argumentMultimap.getValue(PREFIX_WAREHOUSE).isPresent()) {
            editParcelDescriptor.setWarehouse(
                ParserUtil.parseWarehouse(argumentMultimap.getValue(PREFIX_WAREHOUSE).get()));
            LOGGER.finest(
                String.format("Edited Warehouse Address: %s", argumentMultimap.getValue(PREFIX_WAREHOUSE).get()));
        }

        return editParcelDescriptor;
    }

    /**
     * Edits the {@code EditParcelDescriptor} if {@code CashOnDelivery} prefix is present.
     */
    private EditParcelDescriptor editCod(EditParcelDescriptor editParcelDescriptor, ArgumentMultimap argumentMultimap)
        throws ParseException {
        if (argumentMultimap.getValue(PREFIX_COD).isPresent()) {
            editParcelDescriptor.setCash(ParserUtil.parseCash(argumentMultimap.getValue(PREFIX_COD).get()));
            LOGGER.finest(String.format("Edited Cash: %s", argumentMultimap.getValue(PREFIX_COD).get()));
        }
        return editParcelDescriptor;
    }

    /**
     * Edits the {@code EditParcelDescriptor} if {@code Comment} prefix is present.
     */
    private EditParcelDescriptor editComment(EditParcelDescriptor editParcelDescriptor,
                                             ArgumentMultimap argumentMultimap) throws ParseException {
        if (argumentMultimap.getValue(PREFIX_COMMENT).isPresent()) {
            editParcelDescriptor.setComment(ParserUtil.parseComment(argumentMultimap.getValue(PREFIX_COMMENT).get()));
            LOGGER.finest(String.format("Edited Comment: %s", argumentMultimap.getValue(PREFIX_COMMENT).get()));
        }
        return editParcelDescriptor;
    }

    /**
     * Edits the {@code EditParcelDescriptor} if {@code ItemType} prefix is present.
     */
    private EditParcelDescriptor editType(EditParcelDescriptor editParcelDescriptor,
                                          ArgumentMultimap argumentMultimap) throws ParseException {
        if (argumentMultimap.getValue(PREFIX_TYPE).isPresent()) {
            editParcelDescriptor.setItemType(ParserUtil.parseItemType(argumentMultimap.getValue(PREFIX_TYPE).get()));
            LOGGER.finest(String.format("Edited Type: %s", argumentMultimap.getValue(PREFIX_TYPE).get()));
        }
        return editParcelDescriptor;
    }

    /**
     * A checker method to see if user type only command word.
     *
     * @param args user input
     * @throws ParseException throws exception of message usage to user if only command word is provided.
     */
    private void checkForEmptyArgsAfterEditCommand(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (args.split("\\s+").length == 1) {
            LOGGER.info("Invalid user input, parse failure: No user input provided after Edit keyword");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
    }

    /**
     * A checker method to see if user did not give any index
     *
     * @param string preamble of argmultimap
     * @throws ParseException throws exception when index is empty.
     */
    private void checkForEmptyIndex(String string) throws ParseException {
        if (string.isEmpty()) {
            LOGGER.info("Invalid user input, parse failure: Missing index.");
            throw new ParseException(MESSAGE_MISSING_INDEX);
        }
    }

    /**
     * checks for valid index given.
     *
     * @param string preamble.
     * @throws ParseException throws exception whenever user give more than just index in between the index and prefix
     */
    private void checkForValidPreamble(String string) throws ParseException {
        if (string.split("\\s+").length > 1) {
            LOGGER.info("Invalid user input, parse failure: Invalid index format.");
            throw new ParseException(MESSAGE_INVALID_PREAMBLE);
        }
    }
}
