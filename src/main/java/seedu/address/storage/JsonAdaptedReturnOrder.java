package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.comment.Comment;
import seedu.address.model.itemtype.TypeOfItem;
import seedu.address.model.order.Address;
import seedu.address.model.order.Email;
import seedu.address.model.order.Name;
import seedu.address.model.order.Order;
import seedu.address.model.order.Phone;
import seedu.address.model.order.TimeStamp;
import seedu.address.model.order.TransactionId;
import seedu.address.model.order.Warehouse;
import seedu.address.model.order.returnorder.ReturnOrder;

/**
 * Jackson-friendly version of {@link Order}.
 */
class JsonAdaptedReturnOrder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Return Order's %s field is missing!";

    private final String tid;
    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String timeStamp;
    private final String warehouse;
    private final String comment;
    private final String itemType;
    private final boolean deliveryStatus;
    private final boolean isReturn;

    /**
     * Constructs a {@code JsonAdaptedReturnOrder} with the given return order details.
     */
    @JsonCreator
    public JsonAdaptedReturnOrder(@JsonProperty("tid") String tid,
                                  @JsonProperty("name") String name,
                                  @JsonProperty("phone") String phone,
                                  @JsonProperty("email") String email,
                                  @JsonProperty("address") String address,
                                  @JsonProperty("timestamp") String timeStamp,
                                  @JsonProperty("warehouse") String warehouse,
                                  @JsonProperty("comment") String comment,
                                  @JsonProperty("itemType") String itemType,
                                  @JsonProperty("deliveryStatus") boolean deliveryStatus,
                                  @JsonProperty("isReturn") boolean isReturn) {
        this.tid = tid;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.timeStamp = timeStamp;
        this.warehouse = warehouse;
        this.comment = comment;
        this.itemType = itemType;
        this.deliveryStatus = deliveryStatus;
        this.isReturn = isReturn;
    }

    /**
     * Converts a given {@code ReturnOrder} into this class for Jackson use.
     */
    public JsonAdaptedReturnOrder(ReturnOrder source) {
        tid = source.getTid().tid;
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        timeStamp = source.getTimestamp().value;
        warehouse = source.getWarehouse().address;
        comment = source.getComment().commentMade;
        itemType = source.getItemType().itemType;
        deliveryStatus = source.isDelivered();
        isReturn = source.isReturn();
    }

    /**
     * Converts this Jackson-friendly adapted order object into the model's {@code ReturnOrder} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted order.
     */
    public ReturnOrder toModelType() throws IllegalValueException {
        if (tid == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TransactionId.class.getSimpleName()));
        }
        if (!TransactionId.isValidTid(tid)) {
            throw new IllegalValueException(TransactionId.MESSAGE_CONSTRAINTS);
        }
        final TransactionId modelTid = new TransactionId(tid);

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (timeStamp == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TimeStamp.class.getSimpleName()));
        }
        if (!TimeStamp.isValidTimeStamp(timeStamp)) {
            throw new IllegalValueException(TimeStamp.MESSAGE_CONSTRAINTS);
        }
        final TimeStamp modelTimeStamp = new TimeStamp(timeStamp);

        if (warehouse == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Warehouse.class.getSimpleName()));
        }
        if (!Warehouse.isValidAddress(warehouse)) {
            throw new IllegalValueException(Warehouse.MESSAGE_CONSTRAINTS);
        }
        final Warehouse modelWarehouse = new Warehouse(warehouse);

        final Comment modelComment;
        if (comment == null) {
            modelComment = new Comment("NIL");
        } else {
            if (!Comment.isValidComment(comment)) {
                throw new IllegalValueException(Comment.MESSAGE_CONSTRAINTS);
            }
            modelComment = new Comment(comment);
        }

        final TypeOfItem modelItem;
        if (itemType == null) {
            modelItem = new TypeOfItem("NIL");
        } else {
            if (!TypeOfItem.isValidItemType(itemType)) {
                throw new IllegalValueException(TypeOfItem.MESSAGE_CONSTRAINTS);
            }
            modelItem = new TypeOfItem(itemType);
        }

        ReturnOrder finalReturnOrder = new ReturnOrder(modelTid, modelName, modelPhone, modelEmail, modelAddress,
                modelTimeStamp, modelWarehouse, modelComment, modelItem);
        return finalReturnOrder;
    }

}
