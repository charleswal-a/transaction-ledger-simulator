/**
 * This class represents a ledger containing a log of the transactions for an account.
 *
 * @author Charles Walford
 */
public class GeneralLedger {
    /** Represents the most amount of transactions a ledger can contain. */
    public static final int MAX_TRANSACTIONS = 50;
    /** Represents a ledger of transactions for an account. */
    private Transaction[] ledger;
    /** Represents the total debit for the account. */
    private double totalDebitAmount;
    /** Represents the total credit for the account. */
    private double totalCreditAmount;
    /** Represents the total amount of transactions on this ledger */
    private int transactionCount;

    /**
     * This is a Constructor method that is used to create a new GeneralLedger object.
     */
    public GeneralLedger() {
        ledger = new Transaction[MAX_TRANSACTIONS];
        totalDebitAmount = 0;
        totalCreditAmount = 0;
        transactionCount = 0;
    }

    /**
     * This method adds a new transaction to this ledger.
     *
     * @param newTransaction
     * The Transaction object to be added to this ledger
     *
     * @throws FullGeneralLedgerException
     * when this ledger is too full to add more transactions
     * @throws InvalidLedgerPositionException
     * when the new transaction has an invalid date or amount of 0
     * @throws TransactionAlreadyExistsException
     * when the transaction is already contained in this ledger
     */
    public void addTransaction(Transaction newTransaction) {
        try {
            if (ledger[MAX_TRANSACTIONS - 1] != null) {
                throw new FullGeneralLedgerException("General Ledger is full.");
            }

            if (newTransaction.getAmount() == 0) {
                throw new InvalidTransactionException("Transaction amount can not be 0.");
            }

            // This segment of code will throw exceptions for incorrect date formatting
            String[] dateArr = newTransaction.getDate().split("/");
            if (dateArr.length != 3) {
                throw new InvalidTransactionException("Transaction date should be in the format of yyyy/mm/dd.");
            }
            if (Integer.parseInt(dateArr[0]) < 1900 || Integer.parseInt(dateArr[0]) > 2050) {
                throw new InvalidTransactionException("Transaction year must be between 1900 and 2050.");
            }
            if (Integer.parseInt(dateArr[1]) < 1 || Integer.parseInt(dateArr[1]) > 12) {
                throw new InvalidTransactionException("Transaction month must be between 1 and 12.");
            }
            if (Integer.parseInt(dateArr[2]) < 1 || Integer.parseInt(dateArr[2]) > 30) {
                throw new InvalidTransactionException("Transaction day must be between 1 and 30.");
            }
            if (dateArr[0].length() != 4 || dateArr[1].length() != 2 || dateArr[2].length() != 2) {
                throw new InvalidTransactionException("Transaction date should be in the format of yyyy/mm/dd.");
            }

            // Checks to make sure that transaction is not in ledger already
            for (Transaction t : ledger) {
                if (t != null && t.equals(newTransaction)) {
                    throw new TransactionAlreadyExistsException("Transaction already exists in ledger.");
                }
            }

            // Checks to create the new position for transaction based on chronological order
            String[] newTransDateArr = newTransaction.getDate().split("/");
            int newPos = 0;
            for (int i = 0; i < MAX_TRANSACTIONS; i++) {
                if (ledger[i] != null) {
                    String[] transDateArr = ledger[i].getDate().split("/");
                    if (Integer.parseInt(newTransDateArr[0]) < Integer.parseInt(transDateArr[0])) {
                        newPos = i;
                        i = MAX_TRANSACTIONS;
                    } else if (Integer.parseInt(newTransDateArr[0]) == Integer.parseInt(transDateArr[0])) {
                        if (Integer.parseInt(newTransDateArr[1]) < Integer.parseInt(transDateArr[1])) {
                            newPos = i;
                            i = MAX_TRANSACTIONS;
                        } else if (Integer.parseInt(newTransDateArr[1]) == Integer.parseInt(transDateArr[1])) {
                            if (Integer.parseInt(newTransDateArr[2]) < Integer.parseInt(transDateArr[2])) {
                                newPos = i;
                                i = MAX_TRANSACTIONS;
                            }
                        }
                    }
                }
                else {
                    newPos = i;
                    i = MAX_TRANSACTIONS;
                }
            }

            // Moves other transactions back to make space for new transaction
            for (int i = MAX_TRANSACTIONS - 2; i >= newPos; i--) {
                ledger[i+1] = ledger[i];
            }
            ledger[newPos] = newTransaction;
            transactionCount++;

            // Modifies credit/debit amounts based on transaction
            if (newTransaction.getAmount() > 0) {
                totalDebitAmount += newTransaction.getAmount();
            } else {
                totalCreditAmount += -1 * newTransaction.getAmount();
            }
        } catch (FullGeneralLedgerException | InvalidTransactionException | TransactionAlreadyExistsException e) {
            System.out.println("\n" + e.getMessage());
        }
    }

    /**
     * This method removes the transaction at the given position.
     *
     * @param position
     * The position in this ledger that contains the transaction to be removed
     *
     * @throws InvalidLedgerPositionException
     * when the position given is not within this ledger's bounds
     */
    public void removeTransaction(int position) {
        try {
            if (position < 1 || position > size()) {
                throw new InvalidLedgerPositionException("Transaction not removed: No such transaction in the general ledger");
            }

            // Changes credit/debit amounts based on transaction
            if (ledger[position-1].getAmount() > 0) {
                totalDebitAmount += ledger[position-1].getAmount();
            } else {
                totalCreditAmount += -1 * ledger[position-1].getAmount();
            }

            // Moves transactions in the ledger to maintain chronological order
            for (int i = position; i < MAX_TRANSACTIONS; i++) {
                ledger[i - 1] = ledger[i];
            }

            ledger[MAX_TRANSACTIONS - 1] = null;
            transactionCount--;
        } catch (InvalidLedgerPositionException e) {
            System.out.println("\n" + e.getMessage());
        }
    }

    /**
     * This method retrieves the transaction at the given position in this ledger.
     *
     * @param position
     * The position of the transaction to retrieve
     *
     * @return
     * The transaction at the given position
     *
     * @throws InvalidLedgerPositionException
     * when the position given is not within this ledger's bounds
     */
    public Transaction getTransaction(int position) {
        try {
            if (position < 1 || position > MAX_TRANSACTIONS) {
                throw new InvalidLedgerPositionException("Transaction position must be within the bounds of the ledger.");
            }
            return ledger[position - 1];
        } catch (InvalidLedgerPositionException e) {
            System.out.println("\n" + e.getMessage());
        }
        return null;
    }

    /**
     * This method filters out and prints all transactions that happened on a given date.
     *
     * @param generalLedger
     * The ledger to be filtered
     * @param date
     * The date that this ledger will be filtered by
     */
    public static void filter(GeneralLedger generalLedger, String date) {
        String table = "\nNo.    Date          Debit    Credit    Description\n---------------------------------------------------------------------------------------------------\n";

        // Iterates through ledger to find transactions that fit the filter
        for (int i = 0; i < MAX_TRANSACTIONS; i++) {
            if (generalLedger.ledger[i] != null && generalLedger.ledger[i].getDate().equals(date)) {
                table += generalLedger.ledger[i].toString(i+1) + "\n";
            }
        }
        if (table.equals("\nNo.    Date          Debit    Credit    Description\n---------------------------------------------------------------------------------------------------\n")) {
            System.out.println("\nNo transactions found for " + date + ".\n");
        } else {
            System.out.println(table);
        }
    }

    /**
     * This method creates a deep copy this GeneralLedger object
     *
     * @return
     * A deep copy of this GeneralTransaction object
     */
    public Object clone() {
        GeneralLedger newLedger = new GeneralLedger();
        for (int i = 0; i < this.size(); i++) {
            newLedger.addTransaction((Transaction) ledger[i].clone());
        }
        return newLedger;
    }

    /**
     * This method determines if a given transaction exists in this ledger.
     *
     * @param transaction
     * The transaction to look for in this ledger
     *
     * @return
     * A boolean representing if the given transaction is in this ledger.
     *
     * @throws IllegalArgumentException
     * when transaction given is not a valid transaction
     */
    public boolean exists(Transaction transaction) {
        try {
            for (Transaction t : ledger) {
                if (t != null && t.equals(transaction)) {
                    return true;
                }
            }
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("\nTransaction chosen is not a valid transaction.");
            return false;
        }
    }

    /**
     * This method retrieves the amount of transactions in this ledger.
     *
     * @return
     * the amount of transactions in this ledger
     */
    public int size() {
        return transactionCount;
    }

    /**
     * This method prints all the transactions in this ledger.
     */
    public void printAllTransactions() {
       System.out.println(this);
    }

    /**
     * This method constructs a formatted String containing a table of the transactions in this ledger.
     *
     * @return
     * A String containing the table of transactions
     */
    public String toString() {
        String tableString = "No.    Date          Debit    Credit    Description\n---------------------------------------------------------------------------------------------------\n";
        for (int i = 0; i < MAX_TRANSACTIONS; i++) {
            if (ledger[i] != null) {
                tableString += ledger[i].toString(i+1) + "\n";
            }
        }
        return tableString;
    }

    /**
     * This is a Getter method used to get the total account debit.
     *
     * @return
     * The total debit amount for the account
     */
    public double getTotalDebitAmount() {
        return totalDebitAmount;
    }

    /**
     * This is a Getter method used to get the total account credit.
     *
     * @return
     * The total credit amount for the account
     */
    public double getTotalCreditAmount() {
        return totalCreditAmount;
    }

    /**
     * This is a Getter method used to get the ledger of transactions.
     *
     * @return
     * The array of Transaction objects that represents the ledger
     */
    public Transaction[] getLedger() {
        return ledger;
    }
}
