import models.Account;
import models.CheckingAccount;
import models.Customer;
import models.Transaction;
import models.enums.TransactionType;
import models.exceptions.InsufficientFundsException;
import models.exceptions.OverdraftExceededException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import services.AccountManager;
import services.BankingService;
import services.TransactionManager;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BankServiceTest {

    @Mock
    private AccountManager accountManager;

    @Mock
    private TransactionManager transactionManager;

    @InjectMocks
    private BankingService bankingService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should deposit in account and add transaction")
    void testConfirmTransactionDeposit() throws OverdraftExceededException, InsufficientFundsException {
        Account account = mock(Account.class);
        Transaction deposit = new Transaction(
                TransactionType.DEPOSIT, "ACC001", 200, 700);

        bankingService.confirmTransaction(account, deposit);

        verify(account).deposit(200);
        verify(transactionManager).addTransaction(deposit);
    }

    @Test
    @DisplayName("Should withdraw from account and transaction")
    void testConfirmTransactionWithdrawal() throws Exception {
        Account account = mock(Account.class);
        Transaction withdrawal = new Transaction(
                TransactionType.WITHDRAWAL, "ACC001", 200, 700);

        bankingService.confirmTransaction(account, withdrawal);

        verify(account).withdraw(200);
        verify(transactionManager).addTransaction(withdrawal);
    }
}
