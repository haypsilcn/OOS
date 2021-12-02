package bank.exceptions;

public class TransactionAlreadyExistException extends Exception{

    public TransactionAlreadyExistException(String errorMsg) {
        super(errorMsg);
    }
}
