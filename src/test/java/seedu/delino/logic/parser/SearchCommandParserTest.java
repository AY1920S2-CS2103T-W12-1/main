package seedu.delino.logic.parser;

import static seedu.delino.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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
import static seedu.delino.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.delino.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import seedu.delino.logic.commands.SearchCommand;
import seedu.delino.model.parcel.OrderContainsKeywordsPredicate;
import seedu.delino.model.parcel.ReturnOrderContainsKeywordsPredicate;

//@@author khsc96
public class SearchCommandParserTest {

    private SearchCommandParser parser = new SearchCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleFlagArg_throwsParseException() {
        String input = SearchCommand.COMMAND_WORD + " " + CliSyntax.FLAG_RETURN_BOOK.getFlag() + " "
            + CliSyntax.FLAG_ORDER_BOOK.getFlag() + " abc";
        assertParseFailure(parser, input, SearchCommand.MULTIPLE_FLAGS_DETECTED);
    }

    @Test
    public void parse_validArgs_returnsSearchCommand() {
        // no leading and trailing whitespaces
        SearchCommand expectedSearchCommand =
            new SearchCommand(new OrderContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")),
                new ReturnOrderContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedSearchCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedSearchCommand);

        // test overloaded constructor
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        argumentMultimap.put(new Prefix(""), "");
        argumentMultimap.put(PREFIX_ADDRESS, "Alice Bob");
        expectedSearchCommand = new SearchCommand(
            new OrderContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), argumentMultimap),
            new ReturnOrderContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), argumentMultimap));
        assertParseSuccess(parser, " a/Alice Bob", expectedSearchCommand);

        //==============================flags testing==============================

        // order flag
        expectedSearchCommand = new SearchCommand(
            new OrderContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), argumentMultimap));
        assertParseSuccess(parser, " -o a/Alice Bob", expectedSearchCommand);

        // order flag with no prefix
        expectedSearchCommand = new SearchCommand(
            new OrderContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, " -o Alice Bob", expectedSearchCommand);

        // return flag
        expectedSearchCommand = new SearchCommand(
            new ReturnOrderContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), argumentMultimap));
        assertParseSuccess(parser, " -r a/Alice Bob", expectedSearchCommand);

        // return flag with no prefix
        expectedSearchCommand = new SearchCommand(
            new ReturnOrderContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, " -r Alice Bob", expectedSearchCommand);
    }

    @ParameterizedTest
    @MethodSource("generalisePrefix")
    public void parse_validDifferentPrefix_returnsSearchCommand(SearchCommand expectedCommand, String input) {
        assertParseSuccess(parser, input, expectedCommand);
    }

    /**
     * A function to return a {@code Stream<Arguments>} object containing all possible arguments to test
     * the functionality of search command with prefixes.
     *
     * @return {@code Stream<Arguments>} object containing all possible prefix arguments
     */
    private static Stream<Arguments> generalisePrefix() {
        ArgumentMultimap amAddress = new ArgumentMultimap();
        amAddress.put(new Prefix(""), "");
        amAddress.put(PREFIX_ADDRESS, "Geylang");

        ArgumentMultimap amTid = new ArgumentMultimap();
        amTid.put(new Prefix(""), "");
        amTid.put(PREFIX_TID, "12345abc");

        ArgumentMultimap amPhone = new ArgumentMultimap();
        amPhone.put(new Prefix(""), "");
        amPhone.put(PREFIX_PHONE, "87654321");

        ArgumentMultimap amName = new ArgumentMultimap();
        amName.put(new Prefix(""), "");
        amName.put(PREFIX_NAME, "Alice bob");

        ArgumentMultimap amEmail = new ArgumentMultimap();
        amEmail.put(new Prefix(""), "");
        amEmail.put(PREFIX_EMAIL, "example@example.com");

        ArgumentMultimap amCod = new ArgumentMultimap();
        amCod.put(new Prefix(""), "");
        amCod.put(PREFIX_COD, "$5");

        ArgumentMultimap amType = new ArgumentMultimap();
        amType.put(new Prefix(""), "");
        amType.put(PREFIX_TYPE, "Plastic gold");

        ArgumentMultimap amDts = new ArgumentMultimap();
        amDts.put(new Prefix(""), "");
        amDts.put(PREFIX_DELIVERY_TIMESTAMP, "2020-05-06 1500");

        ArgumentMultimap amRts = new ArgumentMultimap();
        amRts.put(new Prefix(""), "");
        amRts.put(PREFIX_RETURN_TIMESTAMP, "2020-05-06 1300");

        ArgumentMultimap amWarehouse = new ArgumentMultimap();
        amWarehouse.put(new Prefix(""), "");
        amWarehouse.put(PREFIX_WAREHOUSE, "Pulau ubin");

        ArgumentMultimap amComment = new ArgumentMultimap();
        amComment.put(new Prefix(""), "");
        amComment.put(PREFIX_COMMENT, "Hi bye");

        ArgumentMultimap amMix = new ArgumentMultimap();
        amMix.put(new Prefix(""), "");
        amMix.put(PREFIX_COMMENT, "Hi bye");
        amMix.put(PREFIX_WAREHOUSE, "Pulau ubin");
        amMix.put(PREFIX_DELIVERY_TIMESTAMP, "2020-05-06 1500");

        return Stream.of(
            // Address prefix test
            Arguments.of(new SearchCommand(
                    new OrderContainsKeywordsPredicate(Collections.singletonList("Geylang"), amAddress),
                    new ReturnOrderContainsKeywordsPredicate(Collections.singletonList("Geylang"), amAddress)),
                " a/Geylang"
            ),
            // Tid prefix test
            Arguments.of(new SearchCommand(
                    new OrderContainsKeywordsPredicate(Collections.singletonList("12345abc"), amTid),
                    new ReturnOrderContainsKeywordsPredicate(Collections.singletonList("12345abc"), amTid)),
                " tid/12345abc"
            ),
            // Phone prefix test
            Arguments.of(new SearchCommand(
                    new OrderContainsKeywordsPredicate(Collections.singletonList("87654321"), amPhone),
                    new ReturnOrderContainsKeywordsPredicate(Collections.singletonList("87654321"), amPhone)),
                " p/87654321"
            ),
            // Name prefix test
            Arguments.of(new SearchCommand(
                    new OrderContainsKeywordsPredicate(Arrays.asList("Alice", "bob"), amName),
                    new ReturnOrderContainsKeywordsPredicate(Arrays.asList("Alice", "bob"), amName)),
                " n/Alice bob"
            ),
            // Email prefix test
            Arguments.of(new SearchCommand(
                    new OrderContainsKeywordsPredicate(Collections.singletonList("example@example.com"), amEmail),
                    new ReturnOrderContainsKeywordsPredicate(
                        Collections.singletonList("example@example.com"), amEmail)),
                " e/example@example.com"
            ),
            // Cash on delivery prefix test
            Arguments.of(new SearchCommand(
                    new OrderContainsKeywordsPredicate(Collections.singletonList("$5"), amCod),
                    new ReturnOrderContainsKeywordsPredicate(Collections.singletonList("$5"), amCod)),
                " cod/$5"
            ),
            // Type prefix test
            Arguments.of(new SearchCommand(
                    new OrderContainsKeywordsPredicate(Arrays.asList("Plastic", "gold"), amType),
                    new ReturnOrderContainsKeywordsPredicate(Arrays.asList("Plastic", "gold"), amType)),
                " type/Plastic gold"
            ),
            // Delivery timestamp prefix test
            Arguments.of(new SearchCommand(
                    new OrderContainsKeywordsPredicate(Arrays.asList("2020-05-06", "1500"), amDts),
                    new ReturnOrderContainsKeywordsPredicate(Arrays.asList("2020-05-06", "1500"), amDts)),
                " dts/2020-05-06 1500"
            ),
            // Return timestamp prefix test
            Arguments.of(new SearchCommand(
                    new OrderContainsKeywordsPredicate(Arrays.asList("2020-05-06", "1300"), amRts),
                    new ReturnOrderContainsKeywordsPredicate(Arrays.asList("2020-05-06", "1300"), amRts)),
                " rts/2020-05-06 1300"
            ),
            // Warehouse prefix test
            Arguments.of(new SearchCommand(
                    new OrderContainsKeywordsPredicate(Arrays.asList("Pulau", "ubin"), amWarehouse),
                    new ReturnOrderContainsKeywordsPredicate(Arrays.asList("Pulau", "ubin"), amWarehouse)),
                " w/Pulau ubin"
            ),
            // Comment prefix test
            Arguments.of(new SearchCommand(
                    new OrderContainsKeywordsPredicate(Arrays.asList("Hi", "bye"), amComment),
                    new ReturnOrderContainsKeywordsPredicate(Arrays.asList("Hi", "bye"), amComment)),
                " c/Hi bye"
            ),
            // Mix prefix test
            Arguments.of(new SearchCommand(
                new OrderContainsKeywordsPredicate(
                    Arrays.asList("Hi", "bye", "Pulau", "ubin", "2020-05-06", "1500"), amMix),
                new ReturnOrderContainsKeywordsPredicate(
                    Arrays.asList("Hi", "bye", "Pulau", "ubin", "2020-05-06", "1500"), amMix)),
                " c/Hi bye w/ Pulau ubin dts/2020-05-06 1500"
            )
        );
    }
}
