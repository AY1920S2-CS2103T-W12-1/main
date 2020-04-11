package seedu.delino.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.delino.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.delino.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.delino.logic.parser.CliSyntax.FLAG_ORDER_BOOK;
import static seedu.delino.logic.parser.CliSyntax.FLAG_RETURN_BOOK;
import static seedu.delino.testutil.Assert.assertThrows;
import static seedu.delino.testutil.TypicalIndexes.INDEX_FIRST_ORDER;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.delino.logic.commands.ClearCommand;
import seedu.delino.logic.commands.DeleteCommand;
import seedu.delino.logic.commands.DeliveredCommand;
import seedu.delino.logic.commands.EditCommand;
import seedu.delino.logic.commands.ExitCommand;
import seedu.delino.logic.commands.HelpCommand;
import seedu.delino.logic.commands.InsertCommand;
import seedu.delino.logic.commands.ListCommand;
import seedu.delino.logic.commands.NearbyCommand;
import seedu.delino.logic.commands.ReturnCommand;
import seedu.delino.logic.commands.SearchCommand;
import seedu.delino.logic.parser.exceptions.ParseException;
import seedu.delino.model.ModelManager;
import seedu.delino.model.parcel.OrderContainsKeywordsPredicate;
import seedu.delino.model.parcel.ReturnOrderContainsKeywordsPredicate;
import seedu.delino.model.parcel.order.Order;
import seedu.delino.model.parcel.parcelattributes.TimeStamp;
import seedu.delino.model.parcel.parcelattributes.TransactionId;
import seedu.delino.model.parcel.returnorder.ReturnOrder;
import seedu.delino.testutil.EditParcelDescriptorBuilder;
import seedu.delino.testutil.OrderBuilder;
import seedu.delino.testutil.OrderUtil;
import seedu.delino.testutil.ReturnOrderBuilder;
import seedu.delino.testutil.ReturnUtil;

public class DelinoParserTest {

    private final DelinoParser parser = new DelinoParser();

    @Test
    public void parseCommand_add() throws Exception {
        Order order = new OrderBuilder().build();
        InsertCommand command = (InsertCommand) parser.parseCommand(OrderUtil.getInsertCommand(order));
        assertEquals(new InsertCommand(order), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " -f") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + FLAG_ORDER_BOOK.toString() + " "
                        + INDEX_FIRST_ORDER.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_ORDER, FLAG_ORDER_BOOK), command);

        DeleteCommand command1 = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + FLAG_RETURN_BOOK.toString() + " "
                        + INDEX_FIRST_ORDER.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_ORDER, FLAG_RETURN_BOOK), command1);
    }

    @Test
    public void parseCommand_delivered() throws Exception {
        DeliveredCommand commandForOrder = (DeliveredCommand) parser.parseCommand(
                DeliveredCommand.COMMAND_WORD + " -o " + INDEX_FIRST_ORDER.getOneBased());
        assertEquals(new DeliveredCommand(INDEX_FIRST_ORDER, new Flag("o"),
                new DeliveredCommand.DeliveredParcelDescriptor()), commandForOrder);
        DeliveredCommand commandforReturnOrder = (DeliveredCommand) parser.parseCommand(
                DeliveredCommand.COMMAND_WORD + " -r " + INDEX_FIRST_ORDER.getOneBased());
        assertEquals(new DeliveredCommand(INDEX_FIRST_ORDER, new Flag("r"),
                new DeliveredCommand.DeliveredParcelDescriptor()), commandforReturnOrder);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Flag orderFlag = CliSyntax.FLAG_ORDER_BOOK;
        Flag returnFlag = CliSyntax.FLAG_RETURN_BOOK;
        String orderFlagInput = orderFlag.getFlag() + " ";
        String returnFlagInput = returnFlag.getFlag() + " ";

        // Parsing order flag
        Order order = new OrderBuilder().build();
        EditCommand.EditParcelDescriptor descriptor = new EditParcelDescriptorBuilder(order).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
            + orderFlagInput + INDEX_FIRST_ORDER.getOneBased() + " "
            + OrderUtil.getEditOrderDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_ORDER, descriptor, orderFlag), command);

        // Parsing return flag
        ReturnOrder returnOrder = new ReturnOrderBuilder().build();
        descriptor = new EditParcelDescriptorBuilder(returnOrder).build();
        command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
            + returnFlagInput + INDEX_FIRST_ORDER.getOneBased() + " "
            + ReturnUtil.getEditReturnOrderDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_ORDER, descriptor, returnFlag), command);
    }

    @Test
    public void parseCommand_return() throws Exception {
        Order order = new OrderBuilder().buildDelivered();
        TransactionId transactionId = order.getTid();
        TimeStamp timeStamp = order.getTimestamp();
        ModelManager modelManager = new ModelManager();
        modelManager.addOrder(order);

        ReturnCommand commandForExistingOrderToBeReturned = (ReturnCommand) parser.parseCommand(
                ReturnCommand.COMMAND_WORD + " tid/" + transactionId + " rts/" + timeStamp);
        assertEquals(new ReturnCommand(null, transactionId, timeStamp),
                commandForExistingOrderToBeReturned);

        ReturnCommand commandForNewReturn = (ReturnCommand) parser.parseCommand(OrderUtil.getReturnCommand(order));
        ReturnOrder returnOrder = new ReturnOrder(order);
        assertEquals(new ReturnCommand(returnOrder, transactionId, timeStamp),
                commandForNewReturn);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_search() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        SearchCommand command = (SearchCommand) parser.parseCommand(
                SearchCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new SearchCommand(new OrderContainsKeywordsPredicate(keywords),
                new ReturnOrderContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        HelpCommand helpCommand = new HelpCommand();
        helpCommand.setValidity(true);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD).equals(helpCommand));
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_nearby() throws Exception {
        NearbyCommand nearbyCommand = (NearbyCommand) parser.parseCommand(
                NearbyCommand.COMMAND_WORD + " -o " + "1");
        assertEquals(new NearbyCommand(" -o 1"), nearbyCommand);

        NearbyCommand nearbyCommand1 = (NearbyCommand) parser.parseCommand(
                NearbyCommand.COMMAND_WORD + " -r 1");
        assertEquals(new NearbyCommand(" -r 1"), nearbyCommand1);

        NearbyCommand nearbyCommand2 = (NearbyCommand) parser.parseCommand(
                NearbyCommand.COMMAND_WORD + " -o central");
        assertEquals(new NearbyCommand(" -o central"), nearbyCommand2);

        NearbyCommand nearbyCommand3 = (NearbyCommand) parser.parseCommand(
                NearbyCommand.COMMAND_WORD + " -r central");
        assertEquals(new NearbyCommand(" -r central"), nearbyCommand3);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
