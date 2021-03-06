package seedu.delino.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.delino.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path orderBookFilePath = Paths.get("data", "OrderBook.json");
    private Path returnOrderBookFilePath = Paths.get("data", "ReturnBook.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setOrderBookFilePath(newUserPrefs.getOrderBookFilePath());
        setReturnOrderBookFilePath(newUserPrefs.getReturnOrderBookFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getOrderBookFilePath() {
        return orderBookFilePath;
    }

    public Path getReturnOrderBookFilePath() {
        return returnOrderBookFilePath;
    }

    public void setOrderBookFilePath(Path orderBookFilePath) {
        requireNonNull(orderBookFilePath);
        this.orderBookFilePath = orderBookFilePath;
    }

    public void setReturnOrderBookFilePath(Path returnOrderBookFilePath) {
        requireNonNull(returnOrderBookFilePath);
        this.returnOrderBookFilePath = returnOrderBookFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && orderBookFilePath.equals(o.orderBookFilePath)
                && returnOrderBookFilePath.equals((o.returnOrderBookFilePath));
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, orderBookFilePath, returnOrderBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nDelivery order data file location : " + orderBookFilePath);
        sb.append("\nReturn order data file location : " + returnOrderBookFilePath);
        return sb.toString();
    }

}
