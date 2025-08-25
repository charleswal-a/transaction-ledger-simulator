import java.util.Scanner;

/**
 * This class represents a system that the user can interact with to manipulate the ledger(s)
 *
 * @author Charles Walford
 * Solar ID: 116237064
 * Email: charles.walford@stonybrook.edu
 * Assignment number: 1
 * Course: CSE 214
 * Recitation number: 1
 * TAs: Yvette Han, Vincent Zheng
 */
public class GeneralLedgerManager {
    /**
     * This a main method that runs the program for handling user inputs.
     *
     * @param args
     * The command line arguments
     */
    public static void main(String[] args) {
        GeneralLedger ledger = new GeneralLedger();
        GeneralLedger backup = null;
        Scanner s = new Scanner(System.in);
        boolean quitPressed = false;
        String[] menuOptions = {"A", "G", "R", "P", "F", "L", "S", "B", "PB", "RB", "CB", "PF", "Q"};

        while (!quitPressed) {
            System.out.print("(A) Add Transaction \n" +
                    "(G) Get Transaction\n" +
                    "(R) Remove Transaction\n" +
                    "(P) Print Transactions in General Ledger\n" +
                    "(F) Filter by Date\n" +
                    "(L) Look for Transaction\n" +
                    "(S) Size\n" +
                    "(B) Backup\n" +
                    "(PB) Print Transactions in Backup\n" +
                    "(RB) Revert to Backup\n" +
                    "(CB) Compare Backup with Current\n" +
                    "(PF) Print Financial Information\n" +
                    "(Q) Quit\n\n" +
                    "Enter a selection: ");
            String selection = s.nextLine();
            selection = selection.toUpperCase();
            boolean selectionAvailable = false;
            for (String o : menuOptions) {
                if (selection.equals(o)) {
                    selectionAvailable = true;
                    break;
                }
            }
            while (!selectionAvailable) {
                System.out.print("Selection not available.\n\nEnter a selection: ");
                selection = s.nextLine();
                selection = selection.toUpperCase();

                // Checks to see if input is an available menu option
                for (String o : menuOptions) {
                    if (selection.equals(o)) {
                        selectionAvailable = true;
                        break;
                    }
                }
            }

            if (selection.equals("A")) {
                int previousSize = ledger.size();
                System.out.print("\nEnter date: ");
                String date = s.nextLine();
                System.out.print("Enter amount: ");
                String amount = s.nextLine();
                System.out.print("Enter description: ");
                String description = s.nextLine();

                if (isDouble(amount, "Please enter a valid number for amount.")) {
                    ledger.addTransaction(new Transaction(date, Double.parseDouble(amount), description));
                }
                int newSize = ledger.size();

                if (previousSize != newSize) {
                    System.out.println("\nTransaction successfully added to the general ledger.\n");
                }
            }
            else if (selection.equals("G")) {
                System.out.print("\nEnter a position: ");
                String position = s.nextLine();

                Transaction t = null;
                if (isInteger(position, "Please enter a valid position number.")) {
                    t = ledger.getTransaction(Integer.parseInt(position));
                }

                if (t != null) {
                    String table = "\nNo.    Date          Debit    Credit    Description\n---------------------------------------------------------------------------------------------------\n";
                    table += t.toString(Integer.parseInt(position)) + "\n";
                    System.out.println(table);
                } else {
                    System.out.println("\nNo such transaction.\n");
                }
            }
            else if (selection.equals("R")) {
                System.out.print("\nEnter position: ");
                String position = s.nextLine();

                int oldSize = ledger.size();

                if (isInteger(position, "Please enter a valid position number.")) {
                    ledger.removeTransaction(Integer.parseInt(position));
                }

                if (oldSize != ledger.size()) {
                    System.out.println("\nTransaction successfully removed from the general ledger.\n");
                } else {
                    System.out.print("\n");
                }
            }
            else if (selection.equals("P")) {
                if (ledger.size() == 0) {
                    System.out.println("\nNo transactions currently in the general ledger.\n");
                } else {
                    System.out.print("\n");
                    ledger.printAllTransactions();
                }
            }
            else if (selection.equals("F")) {
                System.out.print("\nEnter a date: ");
                String date = s.nextLine();

                GeneralLedger.filter(ledger, date);
            }
            else if (selection.equals("L")) {
                System.out.print("\nEnter date: ");
                String date = s.nextLine();
                System.out.print("Enter amount: ");
                String amount = s.nextLine();
                System.out.print("Enter description: ");
                String description = s.nextLine();

                Transaction t = new Transaction(date, Double.parseDouble(amount), description);
                boolean exists = ledger.exists(t);
                int position = 0;
                if (exists) {
                    // Finds position of the transaction in the ledger
                    for (int i = 0; i < ledger.size(); i++) {
                        if (ledger.getLedger()[i].equals(t)) {
                            position = i+1;
                            i = ledger.size();
                        }
                    }
                    String table = "\nNo.    Date          Debit    Credit    Description\n--------------------------------------------------------------\n";
                    table += t.toString(position) + "\n";
                    System.out.println(table);
                } else {
                    System.out.println("\nTransaction not found in the general ledger.\n");
                }
            }
            else if (selection.equals("S")) {
                System.out.println("\nThere are " + ledger.size() + " transactions currently in the general ledger.\n");
            }
            else if (selection.equals("B")) {
                backup = new GeneralLedger();
                for (int i = 0; i < ledger.size(); i++) {
                    backup.addTransaction(ledger.getTransaction(i+1));
                }
                System.out.println("\nCreated a backup of the current general ledger.\n");
            }
            else if (selection.equals("PB")) {
                if (backup == null) {
                    System.out.println("\nNo backup exists.\n");
                } else {
                    backup.printAllTransactions();
                }
            }
            else if (selection.equals("RB")) {
                if (backup == null) {
                    System.out.println("\nNo backup exists.\n");
                } else {
                    // Resets ledger and iterates through backup to add each backup transaction
                    ledger = new GeneralLedger();
                    for (int i = 0; i < backup.size(); i++) {
                        ledger.addTransaction((Transaction) backup.getTransaction(i + 1).clone());
                    }
                    System.out.println("\nGeneral ledger successfully reverted to the backup copy.\n");
                }
            }
            else if (selection.equals("CB")) {
                if (backup == null) {
                    System.out.println("\nNo backup exists.\n");
                } else {
                    boolean ledgersEqual = true;

                    // Compares corresponding entries in ledger and backup
                    for (int i = 0; i < ledger.size(); i++) {
                        if (!ledger.getTransaction(i + 1).equals(backup.getTransaction(i + 1))) {
                            ledgersEqual = false;
                        }
                    }

                    if (ledgersEqual) {
                        System.out.println("\nThe current general ledger is the same as the backup copy.\n");
                    } else {
                        System.out.println("\nThe current general ledger is NOT the same as the backup copy.\n");
                    }
                }
            }
            else if (selection.equals("PF")) {
                System.out.println("\nFinancial Data"
                        + "\n---------------------------------------------------------------------------------------------------\n"
                        + "     Assets: $" + String.format("%.2f", ledger.getTotalDebitAmount())
                        + "\nLiabilities: $" + String.format("%.2f", ledger.getTotalCreditAmount())
                        + "\n  Net Worth: $" + String.format("%.2f", (ledger.getTotalDebitAmount() - ledger.getTotalCreditAmount())) + "\n");
            }
            else if (selection.equals("Q")) {
                quitPressed = true;
                System.out.println("\nProgram terminating successfully...");
                s.close();
            }
        }
    }

    /**
     * This method determines if an input is a valid integer
     *
     * @param input
     * The input to determine if it is an integer
     *
     * @param error
     * The error message to print if input is not valid
     *
     * @return
     * A boolean that represents if input is a valid integer
     *
     * @throws NumberFormatException
     * when the input is not a valid integer
     */
    public static boolean isInteger(String input, String error) {
        try {
            int x = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("\n" + error + "\n");
            return false;
        }
        return true;
    }

    /**
     * This method determines if an input is a valid double
     *
     * @param input
     * The input to determine if it is a double
     *
     * @param error
     * The error message to print if input is not valid
     *
     * @return
     * A boolean that represents if input is a valid double
     *
     * @throws NumberFormatException
     * when the input is not a valid double
     */
    public static boolean isDouble(String input, String error) {
        try {
            double x = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("\n" + error + "\n");
            return false;
        }
        return true;
    }
}
