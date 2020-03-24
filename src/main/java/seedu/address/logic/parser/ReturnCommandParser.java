package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RETURN_TIMESTAMP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WAREHOUSE;

import java.util.stream.Stream;

import seedu.address.logic.commands.ReturnCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.comment.Comment;
import seedu.address.model.itemtype.TypeOfItem;
import seedu.address.model.order.Address;
import seedu.address.model.order.Email;
import seedu.address.model.order.Name;
import seedu.address.model.order.Phone;
import seedu.address.model.order.TimeStamp;
import seedu.address.model.order.TransactionId;
import seedu.address.model.order.Warehouse;
import seedu.address.model.order.returnorder.ReturnOrder;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class ReturnCommandParser implements Parser<ReturnCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReturnCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TID, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_RETURN_TIMESTAMP, PREFIX_WAREHOUSE, PREFIX_TYPE,
                        PREFIX_COMMENT);
        if (!arePrefixesPresent(argMultimap, PREFIX_TID, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_RETURN_TIMESTAMP, PREFIX_WAREHOUSE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReturnCommand.MESSAGE_USAGE));
        }

        if (arePrefixesPresent(argMultimap, PREFIX_TID) && !arePrefixesPresent(argMultimap, PREFIX_NAME,
                PREFIX_ADDRESS, PREFIX_RETURN_TIMESTAMP, PREFIX_WAREHOUSE, PREFIX_EMAIL)) {

        }
        TransactionId tid = ParserUtil.parseTid(argMultimap.getValue(PREFIX_TID).get());
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        TimeStamp timeStamp = ParserUtil.parseTimeStamp(argMultimap.getValue(PREFIX_RETURN_TIMESTAMP).get());
        Warehouse warehouse = ParserUtil.parseWarehouse(argMultimap.getValue(PREFIX_WAREHOUSE).get());
        Comment comment = ParserUtil.parseComment(argMultimap.getValue(PREFIX_COMMENT).isEmpty()
                ? "NIL"
                : argMultimap.getValue(PREFIX_COMMENT).get());
        TypeOfItem type = ParserUtil.parseItemType(argMultimap.getValue(PREFIX_TYPE).isEmpty()
                ? "NIL"
                : argMultimap.getValue(PREFIX_TYPE).get());

        ReturnOrder returnOrder = new ReturnOrder(tid, name, phone, email, address,
                timeStamp, warehouse, comment, type);

        return new ReturnCommand(returnOrder);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}