package seedu.delino.testutil;

import static seedu.delino.model.parcel.parcelattributes.TimeStamp.REQUIRE_CHECK_IF_TIMESTAMP_BEFORE_NOW;

import seedu.delino.logic.commands.EditCommand;
import seedu.delino.logic.commands.EditCommand.EditParcelDescriptor;
import seedu.delino.model.parcel.Parcel;
import seedu.delino.model.parcel.optionalparcelattributes.Comment;
import seedu.delino.model.parcel.optionalparcelattributes.TypeOfItem;
import seedu.delino.model.parcel.order.CashOnDelivery;
import seedu.delino.model.parcel.order.Order;
import seedu.delino.model.parcel.parcelattributes.Address;
import seedu.delino.model.parcel.parcelattributes.Email;
import seedu.delino.model.parcel.parcelattributes.Name;
import seedu.delino.model.parcel.parcelattributes.Phone;
import seedu.delino.model.parcel.parcelattributes.TimeStamp;
import seedu.delino.model.parcel.parcelattributes.TransactionId;
import seedu.delino.model.parcel.parcelattributes.Warehouse;

/**
 * A utility class to help with building EditParcelDescriptor objects.
 */
public class EditParcelDescriptorBuilder {

    private EditCommand.EditParcelDescriptor descriptor;

    public EditParcelDescriptorBuilder() {
        descriptor = new EditParcelDescriptor();
    }

    public EditParcelDescriptorBuilder(EditParcelDescriptor descriptor) {
        this.descriptor = new EditCommand.EditParcelDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditParcelDescriptor} with fields containing {@code order}'s details
     * @param parcel
     */
    public EditParcelDescriptorBuilder(Parcel parcel) {
        descriptor = new EditCommand.EditParcelDescriptor();
        descriptor.setTid(parcel.getTid());
        descriptor.setName(parcel.getName());
        descriptor.setPhone(parcel.getPhone());
        descriptor.setEmail(parcel.getEmail());
        descriptor.setAddress(parcel.getAddress());
        descriptor.setTimeStamp(parcel.getTimestamp());
        descriptor.setWarehouse(parcel.getWarehouse());
        descriptor.setComment(parcel.getComment());
        descriptor.setItemType(parcel.getItemType());
        if (parcel instanceof Order) {
            descriptor.setCash(((Order) parcel).getCash());
        }
    }

    /**
     * Sets the {@code Comment} of the {@code EditParcelDescriptor} that we are building.
     */
    public EditParcelDescriptorBuilder withComment(String comment) {
        descriptor.setComment(new Comment(comment));
        return this;
    }

    /**
     * Sets the {@code TransactionId} of the {@code EditParcelDescriptor} that we are building.
     */
    public EditParcelDescriptorBuilder withTid(String tid) {
        descriptor.setTid(new TransactionId(tid));
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code EditParcelDescriptor} that we are building.
     */
    public EditParcelDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditParcelDescriptor} that we are building.
     */
    public EditParcelDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditParcelDescriptor} that we are building.
     */
    public EditParcelDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditParcelDescriptor} that we are building.
     */
    public EditParcelDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code TimeStamp} of the {@code EditParcelDescriptor} that we are building.
     */
    public EditParcelDescriptorBuilder withTimeStamp(String timeStamp) {
        descriptor.setTimeStamp(new TimeStamp(timeStamp, REQUIRE_CHECK_IF_TIMESTAMP_BEFORE_NOW));
        return this;
    }

    /**
     * Sets the {@code Warehouse} of the {@code EditParcelDescriptor} that we are building.
     */
    public EditParcelDescriptorBuilder withWarehouse(String warehouseLocation) {
        descriptor.setWarehouse(new Warehouse(warehouseLocation));
        return this;
    }

    /**
     * Sets the {@code CashOnDelivery} of the {@code EditParcelDescriptor} that we are building.
     */
    public EditParcelDescriptorBuilder withCash(String cash) {
        descriptor.setCash(new CashOnDelivery(cash));
        return this;
    }

    /**
     * Sets the {@code TypeOfItem} of the {@code EditParcelDescriptor} that we are building.
     *
     */
    public EditParcelDescriptorBuilder withItemType(String item) {
        descriptor.setItemType(new TypeOfItem(item));
        return this;
    }

    public EditParcelDescriptor build() {
        return descriptor;
    }
}
