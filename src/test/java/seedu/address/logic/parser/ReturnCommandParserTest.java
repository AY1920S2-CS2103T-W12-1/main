package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.COMMENT_DESC_INSTRUCTION;
import static seedu.address.logic.commands.CommandTestUtil.COMMENT_DESC_NIL;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COMMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RETURN_TIMESTAMP_DATE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RETURN_TIMESTAMP_DATE_ONLY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RETURN_TIMESTAMP_TIME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RETURN_TIMESTAMP_TIME_ONLY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WAREHOUSE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.RETURN_TIMESTAMP_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.RETURN_TIMESTAMP_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_GLASS;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_PLASTIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMENT_INSTRUCTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMESTAMP_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_GLASS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WAREHOUSE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.WAREHOUSE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.WAREHOUSE_DESC_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalReturnOrders.AMY_RETURN;
import static seedu.address.testutil.TypicalReturnOrders.BOB_RETURN;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ReturnCommand;
import seedu.address.model.comment.Comment;
import seedu.address.model.itemtype.TypeOfItem;
import seedu.address.model.order.Address;
import seedu.address.model.order.Email;
import seedu.address.model.order.Name;
import seedu.address.model.order.Phone;
import seedu.address.model.order.TimeStamp;
import seedu.address.model.order.TransactionId;
import seedu.address.model.order.Warehouse;
import seedu.address.model.order.returnorder.ReturnOrder;
import seedu.address.testutil.ReturnOrderBuilder;

public class ReturnCommandParserTest {
    private ReturnCommandParser parser = new ReturnCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        ReturnOrder expectedReturnOrder = new ReturnOrderBuilder(BOB_RETURN).withItemType(VALID_TYPE_GLASS).build();
        TransactionId expectedTid = new TransactionId(VALID_TID_BOB);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + RETURN_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB
                + COMMENT_DESC_NIL + TYPE_DESC_GLASS, new ReturnCommand(expectedReturnOrder, expectedTid));

        // multiple TIDs - last TID accepted
        assertParseSuccess(parser, TID_DESC_AMY + TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + RETURN_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB
                + COMMENT_DESC_NIL + TYPE_DESC_GLASS, new ReturnCommand(expectedReturnOrder, expectedTid));

        // multiple names - last name accepted
        assertParseSuccess(parser, TID_DESC_BOB + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + RETURN_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB
                + COMMENT_DESC_NIL + TYPE_DESC_GLASS, new ReturnCommand(expectedReturnOrder, expectedTid));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + RETURN_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB
                        + COMMENT_DESC_NIL + TYPE_DESC_GLASS,
                new ReturnCommand(expectedReturnOrder, expectedTid));

        // multiple emails - last email accepted
        assertParseSuccess(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + RETURN_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB
                        + COMMENT_DESC_NIL + TYPE_DESC_GLASS,
                new ReturnCommand(expectedReturnOrder, expectedTid));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + RETURN_TIMESTAMP_DESC_BOB
                        + WAREHOUSE_DESC_BOB + COMMENT_DESC_NIL + TYPE_DESC_GLASS,
                new ReturnCommand(expectedReturnOrder, expectedTid));

        // multiple timeStamps - last timeStamp accepted
        assertParseSuccess(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + RETURN_TIMESTAMP_DESC_AMY + RETURN_TIMESTAMP_DESC_BOB
                        + WAREHOUSE_DESC_BOB + COMMENT_DESC_NIL + TYPE_DESC_GLASS,
                new ReturnCommand(expectedReturnOrder, expectedTid));

        // multiple warehouses - last warehouse accepted
        assertParseSuccess(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + COMMENT_DESC_NIL
                + WAREHOUSE_DESC_AMY + RETURN_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB
                + TYPE_DESC_GLASS, new ReturnCommand(expectedReturnOrder, expectedTid));

        // multiple comment - last comment accepted
        assertParseSuccess(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + RETURN_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB
                + COMMENT_DESC_INSTRUCTION
                + COMMENT_DESC_NIL + TYPE_DESC_GLASS, new ReturnCommand(expectedReturnOrder, expectedTid));

        // multiple item types - all accepted
        assertParseSuccess(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + RETURN_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COMMENT_DESC_NIL
                + TYPE_DESC_PLASTIC + TYPE_DESC_GLASS, new ReturnCommand(expectedReturnOrder, expectedTid));

        // only TID present
        assertParseSuccess(parser, TID_DESC_BOB, new ReturnCommand(null, expectedTid));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero item types and comment
        ReturnOrder expectedReturnOrder = new ReturnOrderBuilder(AMY_RETURN).build();
        TransactionId expectedTid = new TransactionId(VALID_TID_AMY);
        assertParseSuccess(parser, TID_DESC_AMY + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                        + ADDRESS_DESC_AMY + RETURN_TIMESTAMP_DESC_AMY + WAREHOUSE_DESC_AMY + COMMENT_DESC_NIL
                        + TYPE_DESC_PLASTIC,
                new ReturnCommand(expectedReturnOrder, expectedTid));

        ReturnOrder expectedReturnOrderWithType = new ReturnOrderBuilder(AMY_RETURN)
                .withItemType(VALID_TYPE_GLASS).build();
        assertParseSuccess(parser, TID_DESC_AMY + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                        + ADDRESS_DESC_AMY + RETURN_TIMESTAMP_DESC_AMY + WAREHOUSE_DESC_AMY + TYPE_DESC_GLASS,
                new ReturnCommand(expectedReturnOrderWithType, expectedTid));

        ReturnOrder expectedReturnOrderWithComment = new ReturnOrderBuilder(AMY_RETURN).withItemType("NIL")
                .withComment(VALID_COMMENT_INSTRUCTION).build();
        assertParseSuccess(parser, TID_DESC_AMY + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                        + ADDRESS_DESC_AMY + RETURN_TIMESTAMP_DESC_AMY + WAREHOUSE_DESC_AMY + COMMENT_DESC_INSTRUCTION,
                new ReturnCommand(expectedReturnOrderWithComment, expectedTid));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReturnCommand.MESSAGE_USAGE);

        // missing TID prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + RETURN_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB, expectedMessage);

        // missing name prefix
        assertParseFailure(parser, TID_DESC_BOB + VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + RETURN_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + RETURN_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB
                + ADDRESS_DESC_BOB + RETURN_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + VALID_ADDRESS_BOB + RETURN_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB, expectedMessage);

        // missing timeStamp prefix
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + VALID_ADDRESS_BOB + VALID_TIMESTAMP_BOB + WAREHOUSE_DESC_BOB, expectedMessage);

        // missing warehouse prefix
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + RETURN_TIMESTAMP_DESC_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TID_BOB + VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB
                + VALID_ADDRESS_BOB + VALID_TIMESTAMP_BOB + VALID_WAREHOUSE_BOB, expectedMessage);

        // additional field besides TID - cannot trigger conversion of existing order into a return order
        assertParseFailure(parser, VALID_TID_BOB + VALID_NAME_BOB, expectedMessage);

        // additional field besides TID - cannot trigger conversion of existing order into a return order
        assertParseFailure(parser, VALID_TID_BOB + VALID_ADDRESS_BOB + VALID_EMAIL_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid TID
        assertParseFailure(parser, INVALID_TID_DESC + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + RETURN_TIMESTAMP_DESC_BOB
                        + WAREHOUSE_DESC_BOB + COMMENT_DESC_NIL + TYPE_DESC_PLASTIC
                        + TYPE_DESC_GLASS,
                TransactionId.MESSAGE_CONSTRAINTS);

        // invalid name
        assertParseFailure(parser, TID_DESC_BOB + INVALID_NAME_DESC + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + RETURN_TIMESTAMP_DESC_BOB
                        + WAREHOUSE_DESC_BOB + COMMENT_DESC_NIL + TYPE_DESC_PLASTIC + TYPE_DESC_GLASS,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + RETURN_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB
                        + COMMENT_DESC_NIL + TYPE_DESC_PLASTIC + TYPE_DESC_GLASS,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                        + ADDRESS_DESC_BOB + RETURN_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB
                        + COMMENT_DESC_NIL + TYPE_DESC_PLASTIC + TYPE_DESC_GLASS,
                Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INVALID_ADDRESS_DESC + RETURN_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB
                        + COMMENT_DESC_NIL
                        + TYPE_DESC_PLASTIC + TYPE_DESC_GLASS,
                Address.MESSAGE_CONSTRAINTS);

        // invalid timeStamp
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_RETURN_TIMESTAMP_DATE + WAREHOUSE_DESC_BOB
                + COMMENT_DESC_NIL + TYPE_DESC_PLASTIC + TYPE_DESC_GLASS, TimeStamp.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_RETURN_TIMESTAMP_DATE_ONLY + WAREHOUSE_DESC_BOB
                + COMMENT_DESC_NIL + TYPE_DESC_PLASTIC + TYPE_DESC_GLASS, TimeStamp.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_RETURN_TIMESTAMP_TIME + WAREHOUSE_DESC_BOB
                + COMMENT_DESC_NIL + TYPE_DESC_PLASTIC
                + TYPE_DESC_GLASS, TimeStamp.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_RETURN_TIMESTAMP_TIME_ONLY + WAREHOUSE_DESC_BOB
                + COMMENT_DESC_NIL + TYPE_DESC_PLASTIC
                + TYPE_DESC_GLASS, TimeStamp.MESSAGE_CONSTRAINTS);

        // invalid warehouse
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + RETURN_TIMESTAMP_DESC_BOB + INVALID_WAREHOUSE_DESC
                        + TYPE_DESC_PLASTIC,
                Warehouse.MESSAGE_CONSTRAINTS);

        // invalid comment
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + INVALID_COMMENT_DESC + RETURN_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB
                        + TYPE_DESC_PLASTIC + TYPE_DESC_GLASS,
                Comment.MESSAGE_CONSTRAINTS);

        // invalid item type
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + RETURN_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COMMENT_DESC_NIL
                + INVALID_TYPE_DESC, TypeOfItem.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, TID_DESC_BOB + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC + RETURN_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + RETURN_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB
                        + COMMENT_DESC_NIL + TYPE_DESC_PLASTIC + TYPE_DESC_GLASS,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReturnCommand.MESSAGE_USAGE));
    }
}
