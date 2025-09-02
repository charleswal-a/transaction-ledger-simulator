/**
 * This class represents an exception where the general ledger is full.
 *
 * @author Charles Walford
 */
public class FullGeneralLedgerException extends Throwable {
    /**
     * This is a Constructor method that is used to create a new FullGeneralLedgerException object.
     *
     * @param message
     * The message to be shown when the exception is thrown
     */
    public FullGeneralLedgerException(String message) {
        super(message);
    }
}
