import models.Transaction;
import models.enums.TransactionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.TransactionManager;

public class TransactionManagerTest {

    private TransactionManager transactionManager;

    @BeforeEach
    void setup()
    {
        transactionManager = new TransactionManager();
    }

    @Test
    @DisplayName("Should have 0 transactions on initialization")
    void testTransactionCountEmpty() {
        int expected = 0;
        int actual = transactionManager.getTransactionCount();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should increase transaction count on transactions")
    void testTransactionCountMultipleTransactions() {
        Transaction deposit  = new Transaction(
                TransactionType.DEPOSIT, "ACC001", 1000, 2000);
        Transaction withdrawal = new Transaction(TransactionType.WITHDRAWAL, "ACC001", 50, 1500);

        transactionManager.addTransaction(deposit);
        transactionManager.addTransaction(withdrawal);

        int expected = 2;
        int actual = transactionManager.getTransactionCount();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should have 0 total deposits on initialization")
    void testTotalDepositsEmpty() {
        double expected = 0;
        double actual = transactionManager.calculateTotalDeposits("ACC001");

        Assertions.assertEquals(expected, actual);
    }
}
