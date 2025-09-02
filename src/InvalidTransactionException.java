/**
 * This class represents an exception where the transaction object is not valid.
 *
 * @author Charles Walford
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
