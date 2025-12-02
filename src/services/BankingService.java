package services;

import models.Account;
import models.Customer;
import models.CheckingAccount;
import models.PremiumCustomer;
import models.SavingsAccount;
import models.Transaction;
import models.exceptions.InsufficientFundsException;
import models.exceptions.OverdraftExceededException;
import services.exceptions.AccountNotFoundException;

public class BankingService {
    private final AccountManager accountManager;
    private final TransactionManager transactionManager;

    public BankingService(AccountManager accountManager, TransactionManager transactionManager) {
        this.accountManager = accountManager;
        this.transactionManager = transactionManager;
    }

    public Transaction processDeposit(Account account, double amount) {
        double balanceAfterTransaction = account.getBalance() + amount;
        return new Transaction("deposit", account.getAccountNumber(), amount, balanceAfterTransaction);
    }

    public Transaction processWithdrawal(Account account, double amount) {
        double balanceAfterTransaction = account.getBalance() - amount;
        return new Transaction("withdraw", account.getAccountNumber(), amount, balanceAfterTransaction);
    }

    ///  Updates the bank account balance and adds transaction to transaction store
    public void confirmTransaction(Account account, Transaction transaction) throws OverdraftExceededException,
            InsufficientFundsException {
        if ("withdraw".equals(transaction.getTransactionType())) {
            account.withdraw(transaction.getAmount());
        } else {
            account.deposit(transaction.getAmount());
        }

        // Record the transaction only after successful account update
        this.transactionManager.addTransaction(transaction);
    }

    public Account createSavingsAccount(Customer customer) {
        // Account object's initial deposit will be processed as a transaction
        SavingsAccount newAccount = new SavingsAccount(customer, 0, "active");
        accountManager.addAccount(newAccount);
        return newAccount;
    }

    public Account createCheckingAccount(Customer customer) {
        // Account object's initial deposit will be processed as a transaction
        CheckingAccount newAccount = new CheckingAccount(customer, 0, "active");

        if(customer instanceof PremiumCustomer) newAccount.setMonthlyFee(0);

        accountManager.addAccount(newAccount);

        return newAccount;
    }

    public Account[] viewAllAccounts() {
        return accountManager.getAllAccounts();
    }

    public Transaction[] getTransactionsByAccount(String accountNumber) {
        return transactionManager.viewTransactionsByAccount(accountNumber);
    }

    public double getTotalDeposit(String accountNumber) {
        return transactionManager.calculateTotalDeposits(accountNumber);
    }

    public double getTotalWithdrawals(String accountNumber) {
        return transactionManager.calculateTotalWithdrawals(accountNumber);
    }

    public double getTotalBankBalance() {
        return accountManager.getTotalBalance();
    }

    public int getAccountCount() {
        return accountManager.getAccountCount();
    }

    public Account getAccountByNumber(String accountNumber) throws AccountNotFoundException {
        return accountManager.findAccount(accountNumber);
    }
}
