package seedu.delino.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.delino.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.delino.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.delino.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.delino.logic.commands.CommandTestUtil.VALID_COMMENT_NIL;
import static seedu.delino.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.delino.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.delino.logic.commands.CommandTestUtil.VALID_TID_BOB;
import static seedu.delino.logic.commands.CommandTestUtil.VALID_TIMESTAMP_BOB;
import static seedu.delino.logic.commands.CommandTestUtil.VALID_TYPE_PLASTIC;

import org.junit.jupiter.api.Test;

import seedu.delino.logic.commands.EditCommand.EditParcelDescriptor;
import seedu.delino.testutil.EditParcelDescriptorBuilder;

public class EditParcelDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditParcelDescriptor descriptorWithSameValues = new EditCommand.EditParcelDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditParcelDescriptor editedAmy = new EditParcelDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different TID -> returns false
        editedAmy = new EditParcelDescriptorBuilder(DESC_AMY).withTid(VALID_TID_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditParcelDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditParcelDescriptorBuilder(DESC_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different delivery timestamp -> returns false
        editedAmy = new EditParcelDescriptorBuilder(DESC_AMY).withTimeStamp(VALID_TIMESTAMP_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different comment -> returns false
        editedAmy = new EditParcelDescriptorBuilder(DESC_AMY).withComment(VALID_COMMENT_NIL).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different Item Types -> returns false
        editedAmy = new EditParcelDescriptorBuilder(DESC_AMY).withItemType(VALID_TYPE_PLASTIC).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
