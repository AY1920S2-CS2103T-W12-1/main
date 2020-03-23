package seedu.address.model.returnorder;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.comment.Comment;
import seedu.address.model.itemtype.TypeOfItem;
import seedu.address.model.order.Address;
import seedu.address.model.order.Email;
import seedu.address.model.order.Name;
import seedu.address.model.order.Order;
import seedu.address.model.order.Parcel;
import seedu.address.model.order.Phone;
import seedu.address.model.order.TimeStamp;
import seedu.address.model.order.TransactionId;
import seedu.address.model.order.Warehouse;

/**
 * Represents a Order in the order book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ReturnOrder implements Parcel {
    // Identity fields
    private final TransactionId tid;
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final TimeStamp timestamp;
    private final Warehouse warehouse;
    private final Comment comment;
    private final TypeOfItem itemType;
    private boolean deliveryStatus;

    /**
     * Every field must be present and not null.
     */
    public ReturnOrder(TransactionId tid, Name name, Phone phone, Email email, Address address, TimeStamp timestamp,
                 Warehouse warehouse, Comment comment, TypeOfItem itemType) {
        requireAllNonNull(tid, name, phone, email, address, timestamp, warehouse, comment, itemType);
        this.tid = tid;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.timestamp = timestamp;
        this.warehouse = warehouse;
        this.comment = comment;
        this.itemType = itemType;
        this.deliveryStatus = true;
    }

    /**
     * Every field must be present and not null.
     */
    public ReturnOrder(Order order) {
        requireAllNonNull(order);
        this.tid = order.getTid();
        this.name = order.getName();
        this.phone = order.getPhone();
        this.email = order.getEmail();
        this.address = order.getAddress();
        this.timestamp = order.getTimestamp();
        this.warehouse = order.getWarehouse();
        this.comment = order.getComment();
        this.itemType = order.getItemType();
        this.deliveryStatus = true;
    }

    public TransactionId getTid() {
        return tid;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public TimeStamp getTimestamp() {
        return timestamp;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Comment getComment() {
        return comment;
    }

    public TypeOfItem getItemType() {
        return itemType;
    }

    public boolean isDelivered() {
        return deliveryStatus;
    }

    /**
     * Returns true if both orders of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two orders.
     */
    public boolean isSameReturn(ReturnOrder otherOrder) {
        if (otherOrder == this) {
            return true;
        }

        return otherOrder != null
                && otherOrder.getTid().equals(getTid())
                && otherOrder.getName().equals(getName())
                && otherOrder.getPhone().equals(getPhone())
                && otherOrder.getEmail().equals(getEmail());
    }

    /**
     * Returns true if both orders have the same identity and data fields.
     * This defines a stronger notion of equality between two orders.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ReturnOrder)) {
            return false;
        }

        ReturnOrder otherOrder = (ReturnOrder) other;
        return otherOrder.getTid().equals(getTid())
                && otherOrder.getName().equals(getName())
                && otherOrder.getPhone().equals(getPhone())
                && otherOrder.getEmail().equals(getEmail())
                && otherOrder.getAddress().equals(getAddress())
                && otherOrder.getTimestamp().equals(getTimestamp())
                && otherOrder.getWarehouse().equals(getWarehouse())
                && otherOrder.getWarehouse().equals(getComment())
                && otherOrder.getItemType().equals(getItemType())
                && (otherOrder.isDelivered() == isDelivered());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tid, name, phone, email, address, timestamp, warehouse, comment, itemType);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Transaction ID: ")
                .append(getTid())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Delivery Date & Time: ")
                .append(getTimestamp())
                .append(" Warehouse: ")
                .append(getWarehouse())
                .append(" Comment: ")
                .append(getComment())
                .append(" Item Type: ")
                .append(getItemType());
        if (this.isDelivered()) {
            builder.append(" Return Possibility: ").append("Yes");
        } else {
            builder.append(" Return Possibility: ").append("No - this parcel has not been delivered");
        }
        return builder.toString();
    }

}
