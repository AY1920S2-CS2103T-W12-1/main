package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_ORDER_BOOK;
import static seedu.address.logic.parser.CliSyntax.FLAG_RETURN_BOOK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeliveredCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeliveredCommandParser implements Parser<DeliveredCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DoneCommand
     * and returns a DoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeliveredCommand parse(String args) throws ParseException {
        String[] trimmedArgs = args.trim().split("\\s");
        ArrayList<String> splitInputList = new ArrayList<>(Arrays.asList(trimmedArgs));
        DeliveredCommand.DeliveredOrderDescriptor deliveredOrderDescriptor =
                new DeliveredCommand.DeliveredOrderDescriptor();
        if (onlyReturnFlagPresent(splitInputList)) {
            Index index = indexOfReturnList(splitInputList);
            return new DeliveredCommand(index, FLAG_RETURN_BOOK, deliveredOrderDescriptor);
        } else if (onlyOrderFlagPresent(splitInputList)) {
            Index index = indexOfOrderList(splitInputList);
            return new DeliveredCommand(index, FLAG_ORDER_BOOK, deliveredOrderDescriptor);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeliveredCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if only the return flag '-r' is present in user input.
     * @param splitInputList
     * @return
     */
    private boolean onlyReturnFlagPresent(List<String> splitInputList) {
        return splitInputList.contains("-r") && !splitInputList.contains("-o");
    }

    /**
     * Returns the Index of the return list based on user input.
     * @param splitInputList
     * @return
     */
    private Index indexOfReturnList(List<String> splitInputList) {
        splitInputList.remove("-r");
        int indexAsInt = Integer.parseInt(splitInputList.get(0));
        Index index = Index.fromOneBased(indexAsInt);
        return index;
    }

    /**
     * Returns true if only the order flag '-o' is present in user input.
     * @param splitInputList
     * @return
     */
    private boolean onlyOrderFlagPresent(List<String> splitInputList) {
        return splitInputList.contains("-o") && !splitInputList.contains("-r");
    }

    /**
     * Returns the Index of the order list based on user input.
     * @param splitInputList
     * @return
     */
    private Index indexOfOrderList(List<String> splitInputList) {
        splitInputList.remove("-o");
        int indexAsInt = Integer.parseInt(splitInputList.get(0));
        Index index = Index.fromOneBased(indexAsInt);
        return index;
    }
}

