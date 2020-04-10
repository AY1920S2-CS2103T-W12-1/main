package seedu.delino.model.parcel.returnorder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.delino.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.delino.logic.commands.CommandTestUtil.VALID_TYPE_PLASTIC;
import static seedu.delino.testutil.Assert.assertThrows;
import static seedu.delino.testutil.TypicalReturnOrders.ALICE_RETURN;
import static seedu.delino.testutil.TypicalReturnOrders.getTypicalReturnOrderBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.delino.model.ReadOnlyReturnOrderBook;
import seedu.delino.model.ReturnOrderBook;
import seedu.delino.model.parcel.exceptions.DuplicateReturnOrderException;
import seedu.delino.testutil.ReturnOrderBuilder;

class ReturnOrderBookTest {
    private final ReturnOrderBook returnOrderBook = new ReturnOrderBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), returnOrderBook.getReturnOrderList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> returnOrderBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyOrderBook_replacesData() {
        ReturnOrderBook newData = getTypicalReturnOrderBook();
        returnOrderBook.resetData(newData);
        assertEquals(newData, returnOrderBook);
    }

    @Test
    public void resetData_withDuplicateReturnOrders_throwsDuplicateReturnOrderException() {
        // Two orders with the same identity fields
        ReturnOrder editedAlice = new ReturnOrderBuilder(ALICE_RETURN)
                .withAddress(VALID_ADDRESS_BOB)
                .withItemType(VALID_TYPE_PLASTIC)
                .build();
        List<ReturnOrder> newReturnOrders = Arrays.asList(ALICE_RETURN, editedAlice);
        ReturnOrderBookTest.ReturnOrderBookStub newData = new ReturnOrderBookTest.ReturnOrderBookStub(newReturnOrders);

        assertThrows(DuplicateReturnOrderException.class, () -> returnOrderBook.resetData(newData));
    }

    @Test
    public void hasOrder_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> returnOrderBook.hasReturnOrder(null));
    }

    @Test
    public void hasOrder_orderNotInOrderBook_returnsFalse() {
        assertFalse(returnOrderBook.hasReturnOrder(ALICE_RETURN));
    }

    @Test
    public void hasOrder_orderInOrderBook_returnsTrue() {
        returnOrderBook.addReturnOrder(ALICE_RETURN);
        assertTrue(returnOrderBook.hasReturnOrder(ALICE_RETURN));
    }

    @Test
    public void hasOrder_orderWithSameIdentityFieldsInOrderBook_returnsTrue() {
        returnOrderBook.addReturnOrder(ALICE_RETURN);
        ReturnOrder editedAlice = new ReturnOrderBuilder(ALICE_RETURN)
                .withAddress(VALID_ADDRESS_BOB).withItemType(VALID_TYPE_PLASTIC).build();
        assertTrue(returnOrderBook.hasReturnOrder(editedAlice));
    }

    @Test
    public void getOrderList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> returnOrderBook.getReturnOrderList().remove(0));
    }

    /**
     * A stub ReadOnlyReturnOrderBook whose orders list can violate interface constraints.
     */
    private static class ReturnOrderBookStub implements ReadOnlyReturnOrderBook {
        private final ObservableList<ReturnOrder> returnOrders = FXCollections.observableArrayList();

        ReturnOrderBookStub(Collection<ReturnOrder> returnOrders) {
            this.returnOrders.setAll(returnOrders);
        }

        @Override
        public ObservableList<ReturnOrder> getReturnOrderList() {
            return returnOrders;
        }
    }

}
