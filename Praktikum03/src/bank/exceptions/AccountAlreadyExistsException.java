package bank.exceptions;

public class AccountAlreadyExistsException extends Exception {

    public AccountAlreadyExistsException() {
        System.out.println("Error: Account already exists!");
    }

    public AccountAlreadyExistsException(String errorMsg) {
        super(errorMsg);
    }
}
