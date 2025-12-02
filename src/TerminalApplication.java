import handlers.AccountFlowHandler;
import handlers.TransactionFlowHandler;
import models.*;
import services.AccountManager;
import services.TransactionManager;
import services.BankingService;
import utils.DataSeeder;
import utils.DisplayUtil;
import utils.InputReader;
import utils.ValidationUtil;

import java.util.Scanner;

public class TerminalApplication {
    private static final BankingService BANKING_SERVICE = new BankingService(new AccountManager(), new TransactionManager());
    private static final InputReader INPUT = new InputReader(new Scanner(System.in));
    private static final AccountFlowHandler ACCOUNT_FLOW = new AccountFlowHandler(BANKING_SERVICE, INPUT);
    private static final TransactionFlowHandler TRANSACTION_FLOW = new TransactionFlowHandler(BANKING_SERVICE, INPUT);

    public static void start() {
        // Populate the program with already existing customer accounts
        // defined within seed method
        DataSeeder seeder = new DataSeeder(BANKING_SERVICE);
        seeder.seed();

        boolean userIsActive = true;

        while(userIsActive) {
            try {
                DisplayUtil.displayMainMenu();

                int userSelection = INPUT.readInt("Select an option (1-5)", 1, 5);
                System.out.println();

                switch(userSelection) {
                    case 1:
                        ACCOUNT_FLOW.handleAccountCreationFlow();
                        break;
                    case 2:
                        ACCOUNT_FLOW.handleAccountListingFlow();
                        break;
                    case 3:
                        TRANSACTION_FLOW.handleTransactionFlow();
                        break;
                    case 4:
                        TRANSACTION_FLOW.handleTransactionListingFlow();
                        break;
                    case 5:
                        userIsActive = false;
                        break;
                    default:
                        DisplayUtil.displayNotice("Wrong number selection");
                }
            } catch (Exception e) {
                DisplayUtil.displayNotice(e.getMessage());
            }
        }
    }

    public static void handleTransactionFlow() {
        DisplayUtil.displayHeading("Process Transaction");

        System.out.println();

        Account customerAccount = handleAccountValidationFlow();

        Transaction newTransaction = handleTransactionTypeFlow(customerAccount);

        handleTransactionConfirmation(customerAccount, newTransaction);
    }

    private static Account handleAccountValidationFlow() {
        String accountNumber = INPUT.readNonEmptyString("Enter Account Number", ValidationUtil::validateAccountNumber);

        Account customerAccount = BANKING_SERVICE.getAccountByNumber(accountNumber);

        System.out.println("Account Details:");
        DisplayUtil.displayAccountDetails(customerAccount);

        System.out.println();

        return customerAccount;
    }

    private static Transaction handleTransactionTypeFlow(Account customerAccount) {
        System.out.println("Transaction type:");
        System.out.println("1. Deposit \n2. Withdrawal");
        System.out.println();

        int transactionType = INPUT.readInt("Select type (1-2)", 1, 2);

        double transactionAmount = INPUT.readDouble("Enter amount", 0);

        System.out.println();

        return transactionType == 1
                ? BANKING_SERVICE.processDeposit(customerAccount, transactionAmount)
                : BANKING_SERVICE.processWithdrawal(customerAccount, transactionAmount);
    }

    private static void handleTransactionConfirmation(Account customerAccount, Transaction newTransaction) {
        DisplayUtil.displayHeading("Transaction Confirmation");

        DisplayUtil.displayTransaction(newTransaction);

        System.out.println();

        boolean isConfirmed = INPUT.readYesOrNo("Confirm transaction? (Y/N)");

        if (isConfirmed) {
            boolean isSuccessful = BANKING_SERVICE.confirmTransaction(customerAccount, newTransaction);

            if (isSuccessful) {
                System.out.println("Transaction completed successful!");
            } else {
                System.out.println("Transaction failed. Please try again.");
            }
        } else {
            System.out.println("Transaction not confirmed. Aborting.");
        }
    }

    public static void handleTransactionListingFlow() {
        DisplayUtil.displayHeading("View Transaction history");

        String accountNumber = INPUT.readNonEmptyString(
                "Enter Account Number", ValidationUtil::validateAccountNumber);

        Account customerAccount = BANKING_SERVICE.getAccountByNumber(accountNumber);

        DisplayUtil.displayAccountDetails(customerAccount);

        Transaction[] customerTransactions = BANKING_SERVICE.getTransactionsByAccount(accountNumber);

        if (customerTransactions.length == 0) {
            DisplayUtil.displayNotice("No transactions recorded for this account.");
        } else {
            DisplayUtil.displayMultipleTransactions(customerTransactions);
            displayTransactionTotals(customerTransactions);
        }
    }

    private static void displayTransactionTotals(Transaction[] transactions) {
        String accountNumber = transactions[0].getAccountNumber();

        double totalDeposits = BANKING_SERVICE.getTotalDeposit(accountNumber);
        double totalWithdrawals = BANKING_SERVICE.getTotalWithdrawals(accountNumber);

        double netChange = totalDeposits - totalWithdrawals;

        System.out.println("Total Transactions: " + transactions.length);
        System.out.println("Total Deposits: " + DisplayUtil.displayAmount(totalDeposits));
        System.out.println("Total Withdrawals: " + DisplayUtil.displayAmount(totalWithdrawals));
        System.out.println("Net Change: " + DisplayUtil.displayAmount(netChange));
    }
}
