/**
 * This class represents an exception where a transaction already exists in the general ledger.
 *
 * @author Charles Walford
 * Solar ID: 116237064
 * Email: charles.walford@stonybrook.edu
 * Assignment number: 1
 * Course: CSE 214
 * Recitation number: 1
 * TAs: Yvette Han, Vincent Zheng
 */
public class TransactionAlreadyExistsException extends Throwable {
    /**
     * This is a Constructor method that is used to create a new TransactionAlreadyExistsException object.
     *
     * @param message
     * The message to be shown when the exception is thrown
     */
    public TransactionAlreadyExistsException(String message) {
        super(message);
    }
}
