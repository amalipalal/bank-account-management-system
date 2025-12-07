package services;

import config.AppConfig;
import interfaces.AutoIdGenerator;
import models.Account;
import models.Transaction;
import models.enums.TransactionType;
import services.exceptions.TransactionLimitExceededException;

import java.util.Arrays;
import java.util.Objects;

public class TransactionManager {
    private final AutoIdGenerator idGenerator;
    private final Transaction[] transactions;
    // keeps track of successful transactions since unsuccessful
    // transactions still increase idGenerator transaction count
    private int transactionCount;

    public TransactionManager(AutoIdGenerator idGenerator) {
        this.idGenerator = idGenerator;
        this.transactions = new Transaction[AppConfig.MAX_TRANSACTIONS];
        this.transactionCount = 0;
    }

    public Transaction createTransaction(
            TransactionType transactionType, Account account, double amount, double balanceAfterTransaction) {
        String transactionId = idGenerator.generateId();
        return new Transaction(
                transactionId, transactionType, account.getAccountNumber(), amount, balanceAfterTransaction);
    }

    public void addTransaction(Transaction transaction) {
        if(this.transactionCount == transactions.length)
            throw new TransactionLimitExceededException("Addition not allowed: maximum number of transactions have been made");

        transactions[this.transactionCount] = transaction;
        this.transactionCount++;
    }

    public Transaction[] viewTransactionsByAccount(String accountNumber) {
        return Arrays.stream(transactions)
                .filter(Objects::nonNull)
                .filter(transaction -> Objects.equals(transaction.getAccountNumber(), accountNumber))
                .toArray(Transaction[]::new);
    }

    public double calculateTotalDeposits(String accountNumber) {
        TransactionType transactionType = TransactionType.DEPOSIT;
        return Arrays.stream(transactions, 0, this.transactionCount)
                .filter(Objects::nonNull)
                .filter(transaction -> Objects.equals(transaction.getAccountNumber(), accountNumber))
                .filter(transaction -> transaction.getTransactionType() == transactionType)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double calculateTotalWithdrawals(String accountNumber) {
        TransactionType transactionType = TransactionType.WITHDRAWAL;
        return Arrays.stream(transactions, 0, this.transactionCount)
                .filter(Objects::nonNull)
                .filter(transaction -> Objects.equals(transaction.getAccountNumber(), accountNumber))
                .filter(transaction -> transaction.getTransactionType() == transactionType)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public int getTransactionCount() {
        return this.transactionCount;
    }
}
