package seedu.delino.ui;

import static seedu.delino.logic.commands.ClearCommand.MESSAGE_ENQUIRY_ORDER_BOOK;
import static seedu.delino.logic.commands.ClearCommand.MESSAGE_ENQUIRY_RETURN_BOOK;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.delino.commons.core.LogsCenter;
import seedu.delino.logic.Logic;
import seedu.delino.logic.commands.CommandResult;
import seedu.delino.logic.commands.exceptions.CommandException;
import seedu.delino.logic.parser.exceptions.ParseException;

//@@author Exeexe93
/**
 * Controller for a clear warning page
 */
public class ClearWindow extends UiPart<Stage> {
    public static final String WARNING_MESSAGE = "Are you sure you want to clear the order book list ?";

    private static final Logger logger = LogsCenter.getLogger(ClearWindow.class);
    private static final String FXML = "ClearWindow.fxml";
    private static ResultDisplay resultDisplay;

    private Logic logic;

    @FXML
    private Button yesButton;

    @FXML
    private Button noButton;

    @FXML
    private Label warningMessage;

    /**
     * Creates a new ClearWindow.
     *
     * @param root Stage to use as the root of the ClearWindow.
     */
    public ClearWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new ClearWindow.
     */
    public ClearWindow(Logic logic) {
        this(new Stage());
        this.logic = logic;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage.setText(warningMessage);
    }

    /**
     * Show the Clear Window Stage in the centre of the Screen.
     */
    public void show() {
        logger.fine("Showing confirmation message to the user");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the clear window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the clear window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the clear window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    public void setComponent(ResultDisplay resultDisplay) {
        this.resultDisplay = resultDisplay;
    }

    /**
     * User press Yes for clearing the order book list. Clear the whole order book list
     * and display success message to user.
     */
    @FXML
    private void agreeToClearOrderBookList() {
        String commandText = setCommandText();
        logger.fine("User confirm to clear");
        try {
            CommandResult commandResult = logic.execute(commandText);
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
        } catch (CommandException | ParseException e) {
            resultDisplay.setFeedbackToUser(e.getMessage());
        }
        hide();
    }

    /**
     * Set commmand text according to the warning message.
     * @return command string back.
     */
    private String setCommandText() {
        switch (warningMessage.getText()) {
        case MESSAGE_ENQUIRY_RETURN_BOOK:
            return "clear -r -f";
        case MESSAGE_ENQUIRY_ORDER_BOOK:
            return "clear -o -f";
        default:
            return "clear -f";
        }
    }

    /**
     * User press No for clearing the order book list.
     */
    @FXML
    private void disagreeToClearOrderBookList() {
        resultDisplay.setFeedbackToUser("");
        hide();
    }
}
