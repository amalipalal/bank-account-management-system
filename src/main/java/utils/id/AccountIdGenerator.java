package utils.id;

import interfaces.AutoIdGenerator;
import services.exceptions.InvalidAccountNumberException;

import java.text.DecimalFormat;

public class AccountIdGenerator implements AutoIdGenerator {
    private static final DecimalFormat FORMATTER = new DecimalFormat("000");
    private int accountCounter;

    public AccountIdGenerator() {
        this.accountCounter = 0;
    }

    @Override
    public String generateId() {
        this.accountCounter += 1;
        String idString = FORMATTER.format(accountCounter);
        return "ACC" + idString;
    }

    @Override
    public int getAccountCounter() {
        return this.accountCounter;
    }

    @Override
    public int extractIndex(String accountNumber) {
        int startIndex = accountNumber.length() - 3;
        String indexString = accountNumber.substring(startIndex);

        try {
            return Integer.parseInt(indexString) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidAccountNumberException("Account number format is invalid: " + accountNumber);
        }
    }
}
