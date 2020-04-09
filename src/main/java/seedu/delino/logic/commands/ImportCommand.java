package seedu.delino.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import seedu.delino.commons.core.LogsCenter;
import seedu.delino.logic.commands.exceptions.CommandException;
import seedu.delino.logic.parser.InsertCommandParser;
import seedu.delino.logic.parser.ReturnCommandParser;
import seedu.delino.logic.parser.exceptions.ParseException;
import seedu.delino.model.Model;

/**
 * Import the csv file in order book.
 */
public class ImportCommand extends Command {

    public static final String OT_RETURN = "return";
    public static final String OT_ORDER = "order";

    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Import the data in .csv file into Delino\n"
            + "Parameters: fileName.csv\n"
            + "Example: " + COMMAND_WORD + " orders.csv";

    public static final String INVALID_MESSAGE = "Invalid order type encountered";
    public static final String DUPLICATE_ORDER_MESSAGE = "Duplicate order encountered: ";
    public static final String DUPLICATE_RETURN_MESSAGE = "Duplicate return order encountered: ";
    public static final String PROCESS_FAILED_MESSAGE = "Failed to process the data: ";
    public static final String MESSAGE_INVALID_CSV_FILEPATH = "The csv file is not found in the data folder: ";

    private static final Logger logger = LogsCenter.getLogger(ImportCommand.class);
    private static HashMap<Integer, String> errorMessages = new HashMap<>();

    private final List<String> ordersData;
    private int invalidCounter;
    private int processedOrderCounter;
    private int processedReturnOrderCounter;
    private int duplicateCounter;


    public ImportCommand(List<String> orderData) {
        requireNonNull(orderData);
        this.ordersData = orderData;
        this.invalidCounter = 0;
        this.processedOrderCounter = 0;
        this.processedReturnOrderCounter = 0;
        this.duplicateCounter = 0;
    }

    @Override
    public CommandResult execute(Model model) {

        requireNonNull(model);
        processData(model);
        return new CommandResult(printResult(processedOrderCounter, processedReturnOrderCounter, duplicateCounter,
                invalidCounter, errorMessages));
    }

    /**
     * Process the data, add either new order or return order into the model and increase the respective counter.
     * Store the error message into the hash map.
     * @param model model to be edited if encounter order and return for order type.
     */
    private void processData(Model model) {
        logger.fine("Processing the csv data into different orders...");
        int orderNumber = 1;
        for (String rawData : ordersData) {
            String data = rawData.replaceAll(",", " ").stripTrailing();
            try {
                if (data.startsWith(OT_ORDER)) {
                    logger.fine("Passing data to InsertCommandParser for adding a new order");
                    new InsertCommandParser().parse(data.substring(5)).execute(model);
                    processedOrderCounter++;
                } else if (data.startsWith(OT_RETURN)) {
                    logger.fine("Passing data to ReturnCommandParser for adding a new return order");
                    new ReturnCommandParser().parse(data.substring(6)).execute(model);
                    processedReturnOrderCounter++;
                } else {
                    logger.info(INVALID_MESSAGE + ": " + data);
                    // Invalid order type
                    throw new ParseException(INVALID_MESSAGE);
                }
            } catch (ParseException pe) {
                logger.info(PROCESS_FAILED_MESSAGE + rawData);
                errorMessages.put(orderNumber, PROCESS_FAILED_MESSAGE + rawData);
                invalidCounter++;
            } catch (CommandException ce) {
                if (data.startsWith(OT_RETURN)) {
                    logger.info(DUPLICATE_RETURN_MESSAGE + rawData);
                    errorMessages.put(orderNumber, DUPLICATE_RETURN_MESSAGE + rawData);
                } else {
                    logger.info(DUPLICATE_ORDER_MESSAGE + rawData);
                    errorMessages.put(orderNumber, DUPLICATE_ORDER_MESSAGE + rawData);
                }
                duplicateCounter++;
            }
            orderNumber++;
        }
    }

    /**
     * Print the result based on the various counters and the error message in the hash map given.
     * @return message to pass back to user.
     */
    public static String printResult(int processedOrderCounter, int processedReturnOrderCounter, int duplicateCounter,
                               int invalidCounter, HashMap<Integer, String> errorMessages) {
        logger.fine("Generating the message for the user...");
        String message = processedOrderCounter + " delivery order(s) and " + processedReturnOrderCounter
                + " return order(s) being imported.\n";
        if (duplicateCounter != 0) {
            message += duplicateCounter + " duplicate order(s) found!\n";
        }
        if (invalidCounter != 0) {
            message += invalidCounter + " invalid order(s) found!\n";
            message += "Please refer to the user guide for the correct format of the data in csv file.\n";
            message += "The following are those invalid or duplicate: ";
            for (String i : errorMessages.values()) {
                message += "\n" + i;
            }
        }
        logger.fine("Result of importing the file: \n" + message);
        return message;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && ordersData.equals(((ImportCommand) other).ordersData));
    }
}
