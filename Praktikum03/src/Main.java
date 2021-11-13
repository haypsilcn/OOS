import bank.Payment;
import bank.PrivateBank;
import bank.Transfer;
import bank.exceptions.AccountAlreadyExistsException;

import java.util.List;


public class Main {
    public static void main (String[] args) throws AccountAlreadyExistsException {
        PrivateBank privateBank = new PrivateBank("VnBank", 1, 1);

        privateBank.createAccount("testingAcc1", List.of(
                new Payment("12.03.2008", "Salary", 321),
                new Payment("23.09.1897", "Withdrawal", -2500, 0.8, 0.5),
                new Transfer("03.03.2000", "Transfer", 80, "testingAcc1", "Elixir")
        ));
        privateBank.createAccount("testingAcc2", List.of(
                new Payment("30.09.2008", "Deposit", 3930, 0.3, 0.1)
        ));

        privateBank.printAll();
    }
}
