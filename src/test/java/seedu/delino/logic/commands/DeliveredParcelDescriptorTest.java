package seedu.delino.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.delino.logic.commands.CommandTestUtil.AMY_DESC;
import static seedu.delino.logic.commands.CommandTestUtil.BOB_DESC;
import static seedu.delino.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.delino.logic.commands.CommandTestUtil.VALID_COMMENT_NIL;
import static seedu.delino.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.delino.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.delino.logic.commands.CommandTestUtil.VALID_TID_BOB;
import static seedu.delino.logic.commands.CommandTestUtil.VALID_TIMESTAMP_BOB;
import static seedu.delino.logic.commands.CommandTestUtil.VALID_TYPE_PLASTIC;

import org.junit.jupiter.api.Test;

import seedu.delino.logic.commands.DeliveredCommand.DeliveredParcelDescriptor;
import seedu.delino.testutil.DeliveredParcelDescriptorBuilder;

//@@author cherweijie
public class DeliveredParcelDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        DeliveredParcelDescriptor descriptorWithSameValues = new DeliveredParcelDescriptor(AMY_DESC);
        assertTrue(AMY_DESC.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(AMY_DESC.equals(AMY_DESC));

        // null -> returns false
        assertFalse(AMY_DESC.equals(null));

        // different types -> returns false
        assertFalse(AMY_DESC.equals(5));

        // different values -> returns false
        assertFalse(AMY_DESC.equals(BOB_DESC));

        // different name -> returns false
        DeliveredParcelDescriptor editedAmy = new DeliveredParcelDescriptorBuilder(AMY_DESC)
                .withName(VALID_NAME_BOB).build();
        assertFalse(AMY_DESC.equals(editedAmy));

        // different TID -> returns false
        editedAmy = new DeliveredParcelDescriptorBuilder(AMY_DESC).withTid(VALID_TID_BOB).build();
        assertFalse(AMY_DESC.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new DeliveredParcelDescriptorBuilder(AMY_DESC).withPhone(VALID_PHONE_BOB).build();
        assertFalse(AMY_DESC.equals(editedAmy));

        // different address -> returns false
        editedAmy = new DeliveredParcelDescriptorBuilder(AMY_DESC).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(AMY_DESC.equals(editedAmy));

        // different delivery timestamp -> returns false
        editedAmy = new DeliveredParcelDescriptorBuilder(AMY_DESC).withTimeStamp(VALID_TIMESTAMP_BOB).build();
        assertFalse(AMY_DESC.equals(editedAmy));

        // different comment -> returns false
        editedAmy = new DeliveredParcelDescriptorBuilder(AMY_DESC).withComment(VALID_COMMENT_NIL).build();
        assertFalse(AMY_DESC.equals(editedAmy));

        // different Item Types -> returns false
        editedAmy = new DeliveredParcelDescriptorBuilder(AMY_DESC).withItemType(VALID_TYPE_PLASTIC).build();
        assertFalse(AMY_DESC.equals(editedAmy));
    }
}
