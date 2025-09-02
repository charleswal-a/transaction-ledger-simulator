/**
 * This class represents an exception where the ledger position is not valid.
 *
 * @author Charles Walford
 */
public class InvalidLedgerPositionException extends Throwable {
    /**
     * This is a Constructor method that is used to create a new InvalidLedgerPositionException object.
     *
     * @param message
     * The message to be shown when the exception is thrown
     */
    public InvalidLedgerPositionException(String message) {
        super(message);
    }
}
