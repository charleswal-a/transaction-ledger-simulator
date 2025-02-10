/**
 * This class represents an exception where the transaction object is not valid.
 *
 * @author Charles Walford
 * Solar ID: 116237064
 * Email: charles.walford@stonybrook.edu
 * Assignment number: 1
 * Course: CSE 214
 * Recitation number: 1
 * TAs: Yvette Han, Vincent Zheng
 */
public class InvalidTransactionException extends Throwable {
    /**
     * This is a Constructor method that is used to create a new InvalidTransactionException method.
     *
     * @param message
     * The message to be shown when the exception is thrown
     */
    public InvalidTransactionException(String message) {
        super(message);
    }
}
