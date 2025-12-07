package interfaces;

public interface AutoIdGenerator {
    String generateId();
    int getCounter();
    int extractIndex(String idNumber);
}
