/**
 * This class represents an exception where the general ledger is full.
 *
 * @author Charles Walford
 * Solar ID: 116237064
 * Email: charles.walford@stonybrook.edu
 * Assignment number: 1
 * Course: CSE 214
 * Recitation number: 1
 * TAs: Yvette Han, Vincent Zheng
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
