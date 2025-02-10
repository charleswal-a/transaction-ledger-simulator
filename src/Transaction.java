/**
 * This class represents a transaction that has a date, amount, and description.
 *
 * @author Charles Walford
 * Solar ID: 116237064
 * Email: charles.walford@stonybrook.edu
 * Assignment number: 1
 * Course: CSE 214
 * Recitation number: 1
 * TAs: Yvette Han, Vincent Zheng
 */

public class Transaction {
    /** Represents that date that a transaction occurred. */
    private String date;
    /** Represents the amount of money involved in a transaction. */
    private double amount;
    /** Represents a description about a transaction. */
    private String description;

    /**
     * This is a Constructor method used to create a new Transaction object.
     *
     * @param date
     * The date of the transaction
     * @param amount
     * The amount of money in the transaction
     * @param description
     * The description about a transaction
     */
    public Transaction(String date, double amount, String description) {
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    /**
     * This is a Getter method used to get the transaction date
     *
     * @return
     * The date of the transaction
     */
    public String getDate() {
        return date;
    }

    /**
     * This is a Getter method used to get the transaction amount.
     *
     * @return
     * The amount of money in the transaction
     */
    public double getAmount() {
        return amount;
    }

    /**
     * This is a Getter method used to get the transaction description.
     *
     * @return
     * The description of the transaction
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method creates a deep clone of this Transaction object.
     *
     * @return
     * A deep copy of this Transaction object
     */
    public Object clone() {
        return new Transaction(this.date, this.amount, this.description);
    }

    /**
     * This method compared an object to this Transaction object.
     *
     * @param obj
     * The object to be compared to this Transaction object
     *
     * @return
     * A boolean representing whether obj is equal to this Transaction object
     */
    public boolean equals(Object obj) {
        if (obj instanceof Transaction) {
            Transaction t = (Transaction) obj;
            return (t.getDate().equals(this.date) && t.getAmount() == this.amount && t.getDescription().equals(this.description));
        }
        return false;
    }

    /**
     * This method creates a formatted String containing this Transaction object's information.
     *
     * @param position
     * The position of this Transaction object in the ledger
     *
     * @return
     * A formatted String containing information about this Transaction object
     */
    public String toString(int position) {
        int amountPos = 13;
        if (amount < 0) {
            amountPos += 10;
        }

        if (Double.toString(amount).length() <= 5) {
            amountPos ++;
        }
        if (Double.toString(amount).length() <= 4) {
            amountPos ++;
        }

        int descPos = 40 - 7 - amountPos;
        return String.format("%-7d%-" + amountPos + "s%-" + descPos + ".2f%s", position, date, Math.abs(amount), description);
    }
}
