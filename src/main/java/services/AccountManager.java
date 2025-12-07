package services;

import config.AppConfig;
import interfaces.AutoIdGenerator;
import models.Account;
import models.CheckingAccount;
import models.Customer;
import models.SavingsAccount;
import services.exceptions.AccountLimitExceededException;
import services.exceptions.AccountNotFoundException;

import java.util.Arrays;
import java.util.Objects;

public class AccountManager {
    private final AutoIdGenerator idGenerator;
    private final Account[] accounts;

    public AccountManager(AutoIdGenerator idGenerator) {
        this.idGenerator = idGenerator;
        this.accounts = new Account[AppConfig.MAX_ACCOUNTS];
    }

    public SavingsAccount createSavingsAccount(Customer customer, double balance) {
        String accountNumber = idGenerator.generateId();
        return new SavingsAccount(accountNumber, customer, balance, "active");
    }

    public CheckingAccount createCheckingAccount(Customer customer, double balance) {
        String accountNumber = idGenerator.generateId();
        return new CheckingAccount(accountNumber, customer, balance, "active");
    }

    public void addAccount(Account account) {
        int accountCount =  this.idGenerator.getCounter();

        if(accountCount == accounts.length) {
            throw new AccountLimitExceededException("Cannot add account: maximum number of accounts reached.");
        }
        // accountCounter is a counter but array is appended
        // based on zero index
        accounts[accountCount - 1] = account;
    }

    public Account findAccount(String accountNumber) throws AccountNotFoundException {
        int accountIndex = this.idGenerator.extractIndex(accountNumber);
        if( accountIndex < 0 || accountIndex >= accounts.length || accounts[accountIndex] == null) {
            throw new AccountNotFoundException("Cannot find account: account doesn't exist");
        }

        return accounts[accountIndex];
    }

    public Account[] getAllAccounts() {
        int accountCount = this.idGenerator.getCounter();
        return Arrays.copyOf(accounts, accountCount);
    }

    public double getTotalBalance() {
        return Arrays.stream(accounts)
                    .filter(Objects::nonNull)
                    .mapToDouble(Account::getBalance)
                    .sum();
    }

    public int getAccountCount() {
        return this.idGenerator.getCounter();
    }
}
