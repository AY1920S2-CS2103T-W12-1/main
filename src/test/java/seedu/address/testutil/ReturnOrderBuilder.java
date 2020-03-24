package seedu.address.testutil;

import seedu.address.model.comment.Comment;
import seedu.address.model.itemtype.TypeOfItem;
import seedu.address.model.order.Address;
import seedu.address.model.order.CashOnDelivery;
import seedu.address.model.order.Email;
import seedu.address.model.order.Name;
import seedu.address.model.order.Order;
import seedu.address.model.order.Phone;
import seedu.address.model.order.TimeStamp;
import seedu.address.model.order.TransactionId;
import seedu.address.model.order.Warehouse;

/**
 * A utility class to help with building Return Order objects.
 */
public class ReturnOrderBuilder {

    public static final String DEFAULT_TID = "A98765431";
    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@example.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_TIMESTAMP = "2020-02-20 1500";
    public static final String DEFAULT_WAREHOUSE = "5 Toh Guan Rd E, #02-30 S608831";
    public static final String DEFAULT_COD = "$3";
    public static final String DEFAULT_COMMENT = "NIL";
    public static final String DEFAULT_TYPE = "NIL";

    private TransactionId tid;
    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private TimeStamp timeStamp;
    private Warehouse warehouse;
    private CashOnDelivery cod;
    private Comment comment;
    private TypeOfItem itemType;
    private boolean deliveryStatus;

    public ReturnOrderBuilder() {
        tid = new TransactionId(DEFAULT_TID);
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        timeStamp = new TimeStamp(DEFAULT_TIMESTAMP);
        warehouse = new Warehouse(DEFAULT_WAREHOUSE);
        cod = new CashOnDelivery(DEFAULT_COD);
        comment = new Comment(DEFAULT_COMMENT);
        itemType = new TypeOfItem(DEFAULT_TYPE);
        deliveryStatus = false;
    }

    /**
     * Initializes the OrderBuilder with the data of {@code orderToCopy}.
     */
    public ReturnOrderBuilder(Order orderToCopy) {
        tid = orderToCopy.getTid();
        name = orderToCopy.getName();
        phone = orderToCopy.getPhone();
        email = orderToCopy.getEmail();
        address = orderToCopy.getAddress();
        timeStamp = orderToCopy.getTimestamp();
        warehouse = orderToCopy.getWarehouse();
        cod = orderToCopy.getCash();
        comment = orderToCopy.getComment();
        itemType = orderToCopy.getItemType();
        deliveryStatus = orderToCopy.isDelivered();
    }

    /**
     * Sets the {@code TransactionId} of the {@code Order} that we are building.
     */
    public ReturnOrderBuilder withCash(String cod) {
        this.cod = new CashOnDelivery(cod);
        return this;
    }

    /**
     * Sets the {@code TransactionId} of the {@code Order} that we are building.
     */
    public ReturnOrderBuilder withTid(String tid) {
        this.tid = new TransactionId(tid);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Order} that we are building.
     */
    public ReturnOrderBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code TypeOfItem} of the {@code Order} that we are building.
     */
    public ReturnOrderBuilder withItemType(String itemType) {
        this.itemType = new TypeOfItem(itemType);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Order} that we are building.
     */
    public ReturnOrderBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code TimeStamp} of the {@code Order} that we are building.
     */
    public ReturnOrderBuilder withTimeStamp(String timeStamp) {
        this.timeStamp = new TimeStamp(timeStamp);
        return this;
    }

    /**
     * Sets the {@code Warehouse} of the {@code Order} that we are building.
     */
    public ReturnOrderBuilder withWarehouse(String warehouseLocation) {
        this.warehouse = new Warehouse(warehouseLocation);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Order} that we are building.
     */
    public ReturnOrderBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Order} that we are building.
     */
    public ReturnOrderBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Comment} of the {@code Order} that we are building.
     */
    public ReturnOrderBuilder withComment(String comment) {
        this.comment = new Comment(comment);
        return this;
    }

    /**
     * Sets the {@code boolean} of the {@code Order} that we are building.
     */
    public ReturnOrderBuilder withDeliveryStatus(boolean status) {
        this.deliveryStatus = status;
        return this;
    }

    /**
     * Builds a delivered order based on attributes given.
     * @return A delivered {@Code Order} with the given attributes
     */
    public Order buildDelivered() {
        Order toBuild = new Order(tid, name, phone, email, address, timeStamp, warehouse, cod, comment, itemType);
        toBuild.setDeliveryStatus(true);
        return toBuild;
    }

    /**
     * Builds a default order based on attributes given.
     * @return A default {@Code Order} with the given attributes
     */
    public Order build() {
        Order toBuild = new Order(tid, name, phone, email, address, timeStamp, warehouse, cod, comment, itemType);
        toBuild.setDeliveryStatus(false);
        return toBuild;
    }

}