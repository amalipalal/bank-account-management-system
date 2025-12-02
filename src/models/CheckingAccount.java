package models;

import config.AppConfig;
import interfaces.Transactable;
import models.exceptions.InvalidAmountException;
import models.exceptions.OverdraftExceededException;

public class CheckingAccount extends Account implements Transactable {
    private final double OVERDRAFT_LIMIT = AppConfig.OVERDRAFT_LIMIT_CHECKING_ACCOUNT;
    private double monthlyFee;

    public CheckingAccount(Customer customer, double balance, String status) {
        super(customer, balance, status);
        this.monthlyFee = 10;
    }

    @Override
    public String displayAccountDetails() {
        return "";
    }

    @Override
    public String getAccountType() {
        return "Checking";
    }

    @Override
    public boolean processTransaction(double amount, String type) throws OverdraftExceededException{
        if(amount <= 0) return false;

        switch (type.toLowerCase()) {
            case "deposit":
                super.deposit(amount);
                break;
            case "withdraw":
                withdraw(amount);
                break;
            default:
                return false;
        }

        return true;
    }

    @Override
    public void withdraw(double amount) throws OverdraftExceededException{
        if(amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive");
        }

        double currentAccountBalance = super.getBalance();
        double newAccountBalance = currentAccountBalance - amount;

        if(newAccountBalance < -this.OVERDRAFT_LIMIT) {
            throw new OverdraftExceededException("Withdrawal not allowed: overdraft limit is exceeded");
        }

        super.setBalance(newAccountBalance);
    }

    public void applyMonthlyFee() throws OverdraftExceededException{
        double newAccountBalance = super.getBalance() - this.monthlyFee;
        if(newAccountBalance < -this.OVERDRAFT_LIMIT) {
            throw new OverdraftExceededException("Monthly fee cannot be applied: overdraft limit exceeded");
        }
        super.setBalance(newAccountBalance);
    }

    public double getOVERDRAFT_LIMIT() {
        return this.OVERDRAFT_LIMIT;
    }

    public double getMonthlyFee() {
        return this.monthlyFee;
    }

    public void setMonthlyFee(double fee) {
        this.monthlyFee = fee;
    }
}
