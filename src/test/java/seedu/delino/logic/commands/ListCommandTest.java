package seedu.delino.logic.commands;

import static seedu.delino.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.delino.logic.commands.CommandTestUtil.showOrderAtIndex;
import static seedu.delino.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.delino.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.delino.testutil.TypicalReturnOrders.getTypicalReturnOrderBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.delino.model.Model;
import seedu.delino.model.ModelManager;
import seedu.delino.model.UserPrefs;
import seedu.delino.model.parcel.order.Order;
import seedu.delino.model.parcel.returnorder.ReturnOrder;
import seedu.delino.testutil.OrderBuilder;

//@@author Amoscheong97
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 *
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private ListCommand listDone;
    private ListCommand listUndone;
    private ListCommand list;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getOrderBook(), model.getReturnOrderBook(), new UserPrefs());
        listDone = new ListCommand("done");
        listUndone = new ListCommand("undone");
        list = new ListCommand("");
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(""), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);
        assertCommandSuccess(new ListCommand(""), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listDone_showDoneList() {
        Order orderInFilteredList = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        Order editedOrder = new OrderBuilder(orderInFilteredList).buildDelivered();

        expectedModel.setOrder(model.getFilteredOrderList().get(0), editedOrder);
        expectedModel.updateFilteredOrderList(Order::isDelivered);
        expectedModel.updateFilteredReturnOrderList(ReturnOrder::isDelivered);
        model.setOrder(model.getFilteredOrderList().get(0), editedOrder);

        assertCommandSuccess(listDone, model, ListCommand.MESSAGE_SUCCESS_DONE, expectedModel);
    }

    @Test
    public void execute_listDone_showUndoneList() {
        assertCommandSuccess(listUndone, model, ListCommand.MESSAGE_SUCCESS_UNDONE, expectedModel);
    }
}
