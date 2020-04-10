package seedu.delino.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.delino.commons.exceptions.DataConversionException;
import seedu.delino.model.ReadOnlyOrderBook;
import seedu.delino.model.ReadOnlyReturnOrderBook;
import seedu.delino.model.ReadOnlyUserPrefs;
import seedu.delino.model.UserPrefs;


/**
 * API of the Storage component
 */
public interface Storage extends OrderBookStorage, ReturnOrderBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    //==================== Order Book methods ========================================

    @Override
    Path getOrderBookFilePath();

    @Override
    Optional<ReadOnlyOrderBook> readOrderBook() throws DataConversionException, IOException;

    @Override
    void saveOrderBook(ReadOnlyOrderBook orderBook) throws IOException;

    //==================== Return Order Book methods ========================================

    @Override
    Path getReturnOrderBookFilePath();

    @Override
    Optional<ReadOnlyReturnOrderBook> readReturnOrderBook() throws DataConversionException, IOException;

    @Override
    void saveReturnOrderBook(ReadOnlyReturnOrderBook returnOrderBook) throws IOException;
}
