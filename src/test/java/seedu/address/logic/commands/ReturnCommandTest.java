package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyOrderBook;
import seedu.address.model.ReadOnlyReturnOrderBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.ReturnOrderBook;
import seedu.address.model.order.Order;
import seedu.address.model.order.TransactionId;
import seedu.address.model.order.returnorder.ReturnOrder;
import seedu.address.testutil.ReturnOrderBuilder;

public class ReturnCommandTest {

    @Test
    public void constructor_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ReturnCommand(null, null));
    }

    @Test
    public void execute_returnAcceptedByModel_returnSuccessful() throws Exception {
        ModelStubAcceptingReturnOrderAdded modelStub = new ModelStubAcceptingReturnOrderAdded();
        ReturnOrder validReturnOrder = new ReturnOrderBuilder().build();
        TransactionId validTid = validReturnOrder.getTid();

        CommandResult commandResult = new ReturnCommand(validReturnOrder, validTid).execute(modelStub);

        assertEquals(String.format(ReturnCommand.MESSAGE_SUCCESS, validReturnOrder), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validReturnOrder), modelStub.returnOrdersAdded);
    }

    @Test
    public void execute_duplicateReturn_throwsCommandException() {
        ReturnOrder validReturnOrder = new ReturnOrderBuilder().build();
        TransactionId validTid = validReturnOrder.getTid();
        ReturnCommand returnCommand = new ReturnCommand(validReturnOrder, validTid);
        ModelStub modelStub = new ModelStubWithReturnOrder(validReturnOrder);

        assertThrows(CommandException.class,
                ReturnCommand.MESSAGE_DUPLICATE_RETURN, () -> returnCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        ReturnOrder alice = new ReturnOrderBuilder().withName("Alice").build();
        ReturnOrder bob = new ReturnOrderBuilder().withName("Bob").build();
        TransactionId aliceTid = alice.getTid();
        TransactionId bobTid = bob.getTid();
        ReturnCommand returnAliceCommand = new ReturnCommand(alice, aliceTid);
        ReturnCommand returnBobCommand = new ReturnCommand(bob, bobTid);
        // same object -> returns true
        assertTrue(returnAliceCommand.equals(returnAliceCommand));

        // same values -> returns true
        ReturnCommand returnAliceCommandCopy = new ReturnCommand(alice, aliceTid);
        assertTrue(returnAliceCommand.equals(returnAliceCommandCopy));

        // different types -> returns false
        assertFalse(returnAliceCommand.equals(1));

        // null -> returns false
        assertFalse(returnAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(returnAliceCommand.equals(returnBobCommand));

        // same return order but different tid -> returns false
        ReturnCommand returnAliceCommandWithOtherTid = new ReturnCommand(alice,
                new TransactionId("abc123"));
        assertFalse(returnAliceCommand.equals(returnAliceCommandWithOtherTid));

        // different return order but same tid -> returns false
        ReturnCommand returnBobCommandWithAliceTid = new ReturnCommand(bob, aliceTid);
        assertFalse(returnAliceCommand.equals(returnBobCommandWithAliceTid));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getOrderBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setOrderBookFilePath(Path orderBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addOrder(Order order) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setOrderBook(ReadOnlyOrderBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyOrderBook getOrderBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasOrder(Order order) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteOrder(Order target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setOrder(Order target, Order editedOrder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deliverOrder(Order target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void renewDeliveryStatus(Order target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getReturnOrderBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setReturnOrderBookFilePath(Path orderBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addReturnOrder(ReturnOrder returnOrder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setReturnOrderBook(ReadOnlyReturnOrderBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyReturnOrderBook getReturnOrderBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasReturnOrder(ReturnOrder returnOrder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteReturnOrder(ReturnOrder target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deliverReturnOrder(ReturnOrder target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setReturnOrder(ReturnOrder target, ReturnOrder editedOrder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Order> getFilteredOrderList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<ReturnOrder> getFilteredReturnOrderList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredOrderList(Predicate<Order> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredReturnOrderList(Predicate<ReturnOrder> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single return order.
     */
    private class ModelStubWithReturnOrder extends ModelStub {
        private final ReturnOrder returnOrder;

        ModelStubWithReturnOrder(ReturnOrder returnOrder) {
            requireNonNull(returnOrder);
            this.returnOrder = returnOrder;
        }

        @Override
        public boolean hasReturnOrder(ReturnOrder returnOrder) {
            requireNonNull(returnOrder);
            return this.returnOrder.isSameReturn(returnOrder);
        }
    }

    /**
     * A Model stub that always accept the order being added.
     */
    private class ModelStubAcceptingReturnOrderAdded extends ModelStub {
        final ArrayList<ReturnOrder> returnOrdersAdded = new ArrayList<>();

        @Override
        public boolean hasReturnOrder(ReturnOrder returnOrder) {
            requireNonNull(returnOrder);
            return returnOrdersAdded.stream().anyMatch(returnOrder::isSameReturn);
        }

        @Override
        public void addReturnOrder(ReturnOrder returnOrder) {
            requireNonNull(returnOrder);
            returnOrdersAdded.add(returnOrder);
        }

        @Override
        public ReadOnlyReturnOrderBook getReturnOrderBook() {
            return new ReturnOrderBook();
        }
    }

}
