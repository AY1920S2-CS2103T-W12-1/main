package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RETURN_TIMESTAMP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WAREHOUSE;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.parcel.order.Order;
import seedu.address.model.parcel.parcelattributes.TransactionId;
import seedu.address.model.parcel.returnorder.ReturnOrder;

/**
 * Adds a order to the order book.
 */
public class ReturnCommand extends Command {

    public static final String COMMAND_WORD = "return";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Returns an order and adds it to the returns book. "
            + "Parameters: "
            + PREFIX_TID + "TRANSACTION_ID "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_RETURN_TIMESTAMP + "Delivery_DATE_&_TIME "
            + PREFIX_WAREHOUSE + "WAREHOUSE_LOCATION "
            + "[" + PREFIX_COMMENT + "COMMENT] "
            + "[" + PREFIX_TYPE + "TYPE_OF_ITEM] "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TID + "A999999 "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_ADDRESS + "311 Clementi Ave 2 #02-25 "
            + PREFIX_EMAIL + "johndoe@gmail.com "
            + PREFIX_RETURN_TIMESTAMP + "2020-05-05 1500 "
            + PREFIX_WAREHOUSE + "5 Toh Guan Rd E #02-30 S608831 "
            + PREFIX_COMMENT + "NIL "
            + PREFIX_TYPE + "glass";

    public static final String MESSAGE_SUCCESS = "This return order has been created: %1$s";
    public static final String MESSAGE_DUPLICATE_RETURN = "This return order already exists in the returns book";
    public static final String MESSAGE_ORDER_NOT_DELIVERED = "This order was not delivered. Return Order cannot be"
            + " created";
    public static final String MESSAGE_ORDER_TRANSACTION_ID_NOT_VALID = "The input Transaction ID is not valid.";

    private ReturnOrder toBeCreated;
    private final TransactionId tid;

    /**
     * Creates an ReturnCommand to add the specified {@code Order}
     */
    public ReturnCommand(ReturnOrder returnOrder, TransactionId tid) {
        requireNonNull(tid);
        toBeCreated = returnOrder;
        this.tid = tid;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (isReturnOrderNotPresent()) {
            Order orderToBeReturned = getOrderByTransactionId(model);
            if (!orderToBeReturned.isDelivered()) {
                throw new CommandException(MESSAGE_ORDER_NOT_DELIVERED);
            }
            model.deleteOrder(orderToBeReturned);
            toBeCreated = new ReturnOrder(orderToBeReturned);
        }
        if (model.hasParcel(toBeCreated)) {
            throw new CommandException(MESSAGE_DUPLICATE_RETURN);
        }
        model.addReturnOrder(toBeCreated);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toBeCreated));
    }

    /**
     * Checks if the return order is null. Returns true if return order is not present.
     * @return
     */
    private boolean isReturnOrderNotPresent() {
        return toBeCreated == null;
    }

    /**
     * Gets the order from the model based on its Transaction ID.
     * @param model The current Model.
     * @return The order taken from the order book based on the transaction id input by user.
     * @throws CommandException
     */
    private Order getOrderByTransactionId(Model model) throws CommandException {
        List<Order> ordersToBeReturned = model.getOrderBook()
                .getOrderList()
                .stream()
                .filter(order -> order.getTid().equals(tid))
                .collect(Collectors.toList());
        if (ordersToBeReturned.isEmpty()) {
            throw new CommandException(MESSAGE_ORDER_TRANSACTION_ID_NOT_VALID);
        }
        assert(ordersToBeReturned.size() <= 1);
        Order orderToBeReturned = ordersToBeReturned.get(0);
        return orderToBeReturned;
    }

    @Override
    public boolean equals(Object other) {
        boolean shortCircuitCheck = other == this; // short circuit if same object
        if (shortCircuitCheck) {
            return true;
        }
        if (toBeCreated == null) {
            return ((other instanceof ReturnCommand && ((ReturnCommand) other).toBeCreated == null)
                    && tid.equals(((ReturnCommand) other).tid));
        } else {
            return (other instanceof ReturnCommand
                    && (toBeCreated.equals(((ReturnCommand) other).toBeCreated))
                    && tid.equals(((ReturnCommand) other).tid));
        }
    }
}
