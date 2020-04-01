package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_PLASTIC;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showOrderAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalReturnOrders.getTypicalReturnOrderBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand.EditParcelDescriptor;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.Flag;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.OrderBook;
import seedu.address.model.ReturnOrderBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.Parcel.order.Order;
import seedu.address.model.Parcel.returnorder.ReturnOrder;
import seedu.address.testutil.EditParcelDescriptorBuilder;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.ReturnOrderBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private static final Flag ORDER_FLAG = CliSyntax.FLAG_ORDER_BOOK;
    private static final Flag RETURN_FLAG = CliSyntax.FLAG_RETURN_BOOK;

    private Model model = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {

        // Order Testing
        Order editedOrder = new OrderBuilder().build();
        EditCommand.EditParcelDescriptor descriptor = new EditParcelDescriptorBuilder(editedOrder).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ORDER, descriptor, ORDER_FLAG);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS, editedOrder);

        Model expectedModel = new ModelManager(new OrderBook(model.getOrderBook()),
                new ReturnOrderBook(model.getReturnOrderBook()), new UserPrefs());
        expectedModel.setOrder(model.getFilteredOrderList().get(0), editedOrder);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);

        // Return testing
        Flag returnFlag = CliSyntax.FLAG_RETURN_BOOK;
        ReturnOrder editedReturn = new ReturnOrderBuilder().build();
        descriptor = new EditParcelDescriptorBuilder(editedReturn).build();
        editCommand = new EditCommand(INDEX_FIRST_ORDER, descriptor, returnFlag);

        expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RETURN_ORDER_SUCCESS, editedReturn);

        expectedModel = new ModelManager(new OrderBook(model.getOrderBook()),
            new ReturnOrderBook(model.getReturnOrderBook()), new UserPrefs());
        expectedModel.setReturnOrder(model.getFilteredReturnOrderList().get(0), editedReturn);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredOrderList().size());
        Order lastOrder = model.getFilteredOrderList().get(indexLastPerson.getZeroBased());

        OrderBuilder orderInList = new OrderBuilder(lastOrder);
        Order editedOrder = orderInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withItemType(VALID_TYPE_PLASTIC).build();

        EditCommand.EditParcelDescriptor descriptor = new EditParcelDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withItemType(VALID_TYPE_PLASTIC).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor, ORDER_FLAG);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS, editedOrder);

        Model expectedModel = new ModelManager(new OrderBook(model.getOrderBook()),
                new ReturnOrderBook(model.getReturnOrderBook()), new UserPrefs());
        expectedModel.setOrder(lastOrder, editedOrder);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ORDER, new EditParcelDescriptor(), ORDER_FLAG);
        Order editedOrder = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS, editedOrder);

        Model expectedModel = new ModelManager(new OrderBook(model.getOrderBook()),
                new ReturnOrderBook(model.getReturnOrderBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        Order orderInFilteredList = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        Order editedOrder = new OrderBuilder(orderInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ORDER,
                new EditParcelDescriptorBuilder().withName(VALID_NAME_BOB).build(), ORDER_FLAG);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ORDER_SUCCESS, editedOrder);

        Model expectedModel = new ModelManager(new OrderBook(model.getOrderBook()),
                new ReturnOrderBook(model.getReturnOrderBook()), new UserPrefs());
        expectedModel.setOrder(model.getFilteredOrderList().get(0), editedOrder);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateOrderUnfilteredList_failure() {
        Order firstOrder = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        EditCommand.EditParcelDescriptor descriptor = new EditParcelDescriptorBuilder(firstOrder).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_ORDER, descriptor, ORDER_FLAG);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PARCEL);
    }

    @Test
    public void execute_duplicateOrderFilteredList_failure() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        // edit person in filtered list into a duplicate in address book
        Order orderInList = model.getOrderBook().getOrderList().get(INDEX_SECOND_ORDER.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ORDER,
                new EditParcelDescriptorBuilder(orderInList).build(), ORDER_FLAG);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PARCEL);
    }

    @Test
    public void execute_invalidOrderIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        EditCommand.EditParcelDescriptor descriptor =
            new EditParcelDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor, ORDER_FLAG);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidOrderIndexFilteredList_failure() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);
        Index outOfBoundIndex = INDEX_SECOND_ORDER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getOrderBook().getOrderList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditParcelDescriptorBuilder().withName(VALID_NAME_BOB).build(), ORDER_FLAG);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_ORDER, DESC_AMY, ORDER_FLAG);

        // same values -> returns true
        EditCommand.EditParcelDescriptor copyDescriptor = new EditCommand.EditParcelDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_ORDER, copyDescriptor, ORDER_FLAG);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand(null)));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_ORDER, DESC_AMY, ORDER_FLAG)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_ORDER, DESC_BOB, ORDER_FLAG)));
    }

}
