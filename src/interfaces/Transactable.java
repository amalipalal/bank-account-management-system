package interfaces;

import models.exceptions.InsufficientFundsException;
import models.exceptions.OverdraftExceededException;

public interface Transactable {
    public boolean processTransaction(double amount, String type) throws OverdraftExceededException,
            InsufficientFundsException;
}
