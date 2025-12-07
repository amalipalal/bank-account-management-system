package interfaces;

public interface AutoIdGenerator {
    String generateId();
    int getAccountCounter();
    int extractIndex(String idNumber);
}
