package models;

import models.enums.AccountType;
import models.exceptions.InsufficientFundsException;
import models.exceptions.OverdraftExceededException;

import java.text.DecimalFormat;

public abstract class Account {
    private final String accountNumber;
    private final Customer customer;
    private double balance;
    private final String status;

    public Account(String accountNumber, Customer customer, double balance, String status) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.balance = balance;
        this.status = status;
    }

    public abstract String displayAccountDetails();

    public abstract String displayNewAccountDetails();

    public abstract AccountType getAccountType();

    public abstract void withdraw(double amount) throws OverdraftExceededException, InsufficientFundsException;

    @Override
    public boolean processTransaction(double amount, TransactionType type) throws OverdraftExceededException,
            InsufficientFundsException {
        if(amount <= 0) {
            throw new InvalidAmountException("Transaction amount has to be positive and greater 0");
        }

        switch (type) {
            case TransactionType.DEPOSIT:
                deposit(amount);
                break;
            case TransactionType.WITHDRAWAL:
                withdraw(amount);
                break;
            default:
                return false;
        }

        return true;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getBalance() {
        return balance;
    }

    public String getStatus() {
        return status;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
