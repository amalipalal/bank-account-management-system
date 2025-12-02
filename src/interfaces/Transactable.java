package interfaces;

public interface Transactable {
    public boolean processTransaction(double amount, String type);
}
