package seedu.delino.logic.commands;

import static seedu.delino.logic.commands.ClearCommand.MESSAGE_ENQUIRY_BOTH_LIST;
import static seedu.delino.logic.commands.ClearCommand.MESSAGE_ENQUIRY_ORDER_LIST;
import static seedu.delino.logic.commands.ClearCommand.MESSAGE_ENQUIRY_RETURN_LIST;
import static seedu.delino.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.delino.logic.parser.CliSyntax.FLAG_FORCE_CLEAR;
import static seedu.delino.logic.parser.CliSyntax.FLAG_ORDER_BOOK;
import static seedu.delino.logic.parser.CliSyntax.FLAG_RETURN_BOOK;
import static seedu.delino.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.delino.testutil.TypicalReturnOrders.getTypicalReturnOrderBook;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.delino.model.Model;
import seedu.delino.model.ModelManager;
import seedu.delino.model.OrderBook;
import seedu.delino.model.ReturnOrderBook;
import seedu.delino.model.UserPrefs;
//@@author Exeexe93
public class ClearCommandTest {
    private HashSet<String> flags;

    @BeforeEach
    public void setup() {
        flags = new HashSet<>();
    }

    @Test
    public void executeWithEmptyBooks_forceClear_emptyBothLists() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        flags.add(FLAG_FORCE_CLEAR.toString());

        assertCommandSuccess(new ClearCommand(flags), model, ClearCommand.MESSAGE_SUCCESS_BOTH_LIST,
                expectedModel);
    }

    @Test
    public void executeWithEmptyBooks_forceClearWithOrderFlag_emptyOrderList() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        flags.add(FLAG_FORCE_CLEAR.toString());
        flags.add(FLAG_ORDER_BOOK.toString());

        assertCommandSuccess(new ClearCommand(flags), model, ClearCommand.MESSAGE_SUCCESS_ORDER_LIST,
                expectedModel);
    }

    @Test
    public void executeWithEmptyBooks_forceClearWithReturnFlag_emptyReturnOrderList() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        flags.add(FLAG_FORCE_CLEAR.toString());
        flags.add(FLAG_RETURN_BOOK.toString());

        assertCommandSuccess(new ClearCommand(flags), model, ClearCommand.MESSAGE_SUCCESS_RETURN_LIST,
                expectedModel);
    }

    @Test
    public void executeWithEmptyLists_withoutForceClear_replyCorrectEnquiryMessage() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(null), model,
                new CommandResult(MESSAGE_ENQUIRY_BOTH_LIST, false, false, true, false),
                expectedModel);
    }

    @Test
    public void executeWithEmptyLists_orderFlagOnly_replyCorrectEnquiryMessage() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        flags.add(FLAG_ORDER_BOOK.toString());

        assertCommandSuccess(new ClearCommand(flags), model,
                new CommandResult(MESSAGE_ENQUIRY_ORDER_LIST, false, false, true, false),
                expectedModel);
    }

    @Test
    public void executeWithEmptyLists_returnFlagOnly_replyCorrectEnquiryMessage() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        flags.add(FLAG_RETURN_BOOK.toString());

        assertCommandSuccess(new ClearCommand(flags), model,
                new CommandResult(MESSAGE_ENQUIRY_RETURN_LIST, false, false, true, false),
                expectedModel);
    }

    @Test
    public void execute_forceClear_emptyBothLists() {
        Model model = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
        expectedModel.setOrderBook(new OrderBook());
        expectedModel.setReturnOrderBook(new ReturnOrderBook());

        flags.add(FLAG_FORCE_CLEAR.toString());

        assertCommandSuccess(new ClearCommand(flags), model, ClearCommand.MESSAGE_SUCCESS_BOTH_LIST,
                expectedModel);
    }

    @Test
    public void execute_forceClearWithOrderFlag_emptyOrderList() {
        Model model = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
        expectedModel.setOrderBook(new OrderBook());

        flags.add(FLAG_FORCE_CLEAR.toString());
        flags.add(FLAG_ORDER_BOOK.toString());

        assertCommandSuccess(new ClearCommand(flags), model, ClearCommand.MESSAGE_SUCCESS_ORDER_LIST,
                expectedModel);
    }

    @Test
    public void execute_forceClearWithReturnFlag_emptyReturnOrderList() {
        Model model = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
        expectedModel.setReturnOrderBook(new ReturnOrderBook());

        flags.add(FLAG_FORCE_CLEAR.toString());
        flags.add(FLAG_RETURN_BOOK.toString());

        assertCommandSuccess(new ClearCommand(flags), model, ClearCommand.MESSAGE_SUCCESS_RETURN_LIST,
                expectedModel);
    }

    @Test
    public void execute_withoutForceClear_replyCorrectEnquiryMessage() {
        Model model = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());

        assertCommandSuccess(new ClearCommand(null), model,
                new CommandResult(MESSAGE_ENQUIRY_BOTH_LIST, false, false, true, false),
                expectedModel);
    }

    @Test
    public void execute_orderFlagOnly_replyCorrectEnquiryMessage() {
        Model model = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
        flags.add(FLAG_ORDER_BOOK.toString());

        assertCommandSuccess(new ClearCommand(flags), model,
                new CommandResult(MESSAGE_ENQUIRY_ORDER_LIST, false, false, true, false),
                expectedModel);
    }

    @Test
    public void execute_returnFlagOnly_replyCorrectEnquiryMessage() {
        Model model = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
        flags.add(FLAG_RETURN_BOOK.toString());

        assertCommandSuccess(new ClearCommand(flags), model,
                new CommandResult(MESSAGE_ENQUIRY_RETURN_LIST, false, false, true, false),
                expectedModel);
    }
}
