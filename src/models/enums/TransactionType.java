package models.enums;

public enum TransactionType {
    WITHDRAWAL("Withdrawal"),
    DEPOSIT("Deposit");

    private final String displayName;

    TransactionType(String displayName) {
        this.displayName = displayName;
    }

    String getDisplayName() {
        return this.displayName;
    }
}
