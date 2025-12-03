package models.enums;

public enum AccountType {
    SAVINGS("Savings"),
    CHECKING("Checking");

    private final String displayName;

    AccountType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
