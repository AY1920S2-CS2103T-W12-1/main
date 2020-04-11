package seedu.delino.logic.parser;

import static seedu.delino.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.delino.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.delino.logic.commands.ClearCommand;
import seedu.delino.logic.commands.Command;
import seedu.delino.logic.commands.DeleteCommand;
import seedu.delino.logic.commands.DeliveredCommand;
import seedu.delino.logic.commands.EditCommand;
import seedu.delino.logic.commands.ExitCommand;
import seedu.delino.logic.commands.HelpCommand;
import seedu.delino.logic.commands.ImportCommand;
import seedu.delino.logic.commands.InsertCommand;
import seedu.delino.logic.commands.ListCommand;
import seedu.delino.logic.commands.NearbyCommand;
import seedu.delino.logic.commands.ReturnCommand;
import seedu.delino.logic.commands.SearchCommand;

import seedu.delino.logic.commands.ShowCommand;
import seedu.delino.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class DelinoParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {
        case InsertCommand.COMMAND_WORD:
            return new InsertCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DeliveredCommand.COMMAND_WORD:
            return new DeliveredCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommandParser().parse(arguments);

        case ImportCommand.COMMAND_WORD:
            return new ImportCommandParser().parse(arguments);

        case SearchCommand.COMMAND_WORD:
            return new SearchCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand(arguments);

        case NearbyCommand.COMMAND_WORD:
            return new NearbyCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommandParser().parse(arguments);

        case ShowCommand.COMMAND_WORD:
            return new ShowCommand(arguments);

        case ReturnCommand.COMMAND_WORD:
            return new ReturnCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
