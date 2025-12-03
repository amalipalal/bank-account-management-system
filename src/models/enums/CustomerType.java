package models.enums;

public enum CustomerType {
    PREMIUM("Premium"),
    REGULAR("Regular");

    private final String displayName;

    CustomerType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
