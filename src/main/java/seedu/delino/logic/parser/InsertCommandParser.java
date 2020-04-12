package seedu.delino.logic.parser;

import static seedu.delino.commons.core.Messages.MESSAGE_EMPTY_PREFIXES;
import static seedu.delino.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.delino.logic.commands.InsertCommand;
import seedu.delino.logic.parser.exceptions.ParseException;
import seedu.delino.model.parcel.optionalparcelattributes.Comment;
import seedu.delino.model.parcel.optionalparcelattributes.TypeOfItem;
import seedu.delino.model.parcel.order.CashOnDelivery;
import seedu.delino.model.parcel.order.Order;
import seedu.delino.model.parcel.parcelattributes.Address;
import seedu.delino.model.parcel.parcelattributes.Email;
import seedu.delino.model.parcel.parcelattributes.Name;
import seedu.delino.model.parcel.parcelattributes.Phone;
import seedu.delino.model.parcel.parcelattributes.TimeStamp;
import seedu.delino.model.parcel.parcelattributes.TransactionId;
import seedu.delino.model.parcel.parcelattributes.Warehouse;

//@@author Amoscheong97
/**
 * Parses input arguments and creates a new InsertCommand object
 *
 */
public class InsertCommandParser implements Parser<InsertCommand> {

    private static final Logger logger = Logger.getLogger(InsertCommandParser.class.getName());

    /**
     * Parses the given {@code String} of arguments in the context of the InsertCommand
     * and returns an InsertCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public InsertCommand parse(String args) throws ParseException {
        String missingMessage = MESSAGE_EMPTY_PREFIXES;

        List<Prefix> prefixes = new ArrayList<>();
        prefixes.add(PREFIX_TID);
        prefixes.add(PREFIX_NAME);
        prefixes.add(PREFIX_PHONE);
        prefixes.add(PREFIX_EMAIL);
        prefixes.add(PREFIX_ADDRESS);
        prefixes.add(PREFIX_DELIVERY_TIMESTAMP);
        prefixes.add(PREFIX_WAREHOUSE);
        prefixes.add(PREFIX_COD);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TID, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_DELIVERY_TIMESTAMP, PREFIX_WAREHOUSE, PREFIX_COD, PREFIX_TYPE,
                        PREFIX_COMMENT);
        if (!arePrefixesPresent(argMultimap, PREFIX_TID, PREFIX_NAME, PREFIX_ADDRESS,
                PREFIX_DELIVERY_TIMESTAMP, PREFIX_WAREHOUSE,
                PREFIX_PHONE, PREFIX_EMAIL, PREFIX_COD)
                || !argMultimap.getPreamble().isEmpty()) {

            if (!arePrefixesPresent(argMultimap, PREFIX_TID, PREFIX_NAME, PREFIX_ADDRESS,
                    PREFIX_DELIVERY_TIMESTAMP, PREFIX_WAREHOUSE,
                    PREFIX_PHONE, PREFIX_EMAIL, PREFIX_COD)) {
                logger.info("There are missing prefixes in the input");
                for (Prefix p : prefixes) {
                    if (!arePrefixesPresent(argMultimap, p)) {
                        logger.info("Prefix " + p + " is missing");
                        missingMessage = missingMessage + p + "\n";
                    }
                }

                missingMessage = missingMessage + "%1$s";

                throw new ParseException(String.format(missingMessage, InsertCommand.MESSAGE_USAGE));

            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, InsertCommand.MESSAGE_USAGE));
            }
        }

        ParserUtil.parse(prefixes, argMultimap);

        TransactionId tid = ParserUtil.parseTid(argMultimap.getValue(PREFIX_TID).get());
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        TimeStamp timeStamp = ParserUtil.parseTimeStamp(argMultimap.getValue(PREFIX_DELIVERY_TIMESTAMP).get());
        Warehouse warehouse = ParserUtil.parseWarehouse(argMultimap.getValue(PREFIX_WAREHOUSE).get());
        CashOnDelivery cash = ParserUtil.parseCash(argMultimap.getValue(PREFIX_COD).get());
        Comment comment = ParserUtil.parseComment(argMultimap.getValue(PREFIX_COMMENT).isEmpty()
                ? "NIL"
                : argMultimap.getValue(PREFIX_COMMENT).get());
        TypeOfItem type = ParserUtil.parseItemType(argMultimap.getValue(PREFIX_TYPE).isEmpty()
                ? "NIL"
                : argMultimap.getValue(PREFIX_TYPE).get());

        Order order = new Order(tid, name, phone, email, address, timeStamp, warehouse, cash, comment, type);
        logger.fine("All Order attributes are valid");
        logger.fine("New Order created");
        return new InsertCommand(order);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
