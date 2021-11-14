import bank.Payment;
import bank.PrivateBank;
import bank.Transaction;
import bank.Transfer;
import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.TransactionAlreadyExistException;
import bank.exceptions.TransactionDoesNotExistException;

import java.util.List;



public class Main {
    public static void main (String[] args) throws AccountAlreadyExistsException, TransactionAlreadyExistException, AccountDoesNotExistException, TransactionDoesNotExistException {
        PrivateBank privateBank = new PrivateBank("VnBank", 0.25, 0.3);

        privateBank.createAccount("Molziles", List.of(
                new Payment("12.03.2008", "Salary", 321),
                new Payment("23.09.1897", "Withdrawal", -2500, 0.8, 0.5),
                new Transfer("03.03.2000", "Transfer", 80, "Molziles", "Elixir")
        ));
        privateBank.createAccount("Elixir", List.of(
                new Payment("30.09.2008", "Deposit", 3930, 0.3, 0.1)
        ));

        System.out.println(privateBank);


        try {
            privateBank.createAccount("Elixir");
        } catch (AccountAlreadyExistsException e){
            System.out.println(e);
        }

        try {
            privateBank.createAccount("Molziles", List.of(
                    new Transfer("03.03.2001", "Transfer", 80, "Molziles", "Elixir")
            ));
        } catch (AccountAlreadyExistsException e){
            System.out.println(e);
        }

        try {
            privateBank.createAccount("Anand");
        } catch (AccountAlreadyExistsException e) {
            System.out.println(e);
        }

        System.out.println(privateBank);

        try {
            privateBank.addTransaction("testAddTransaction", new Payment("11.11.2000", "Testing", 890));
//            privateBank.addTransaction("Molziles", new Transfer("03.03.2001", "Transfer", 80, "Molziles", "Elixir"));
        } catch (AccountDoesNotExistException | TransactionAlreadyExistException e) {
            System.out.println(e);
        }

        try {
            privateBank.addTransaction("Molziles", new Transfer("03.03.2000", "Transfer", 80, "Molziles", "Elixir"));
        } catch (AccountDoesNotExistException | TransactionAlreadyExistException e) {
            System.out.println(e);
        }


        privateBank.addTransaction("Molziles", new Payment("22.03.2003", "testAddTransaction", 90, 0.9, 0.75));
        privateBank.addTransaction("Molziles", new Transfer("19.04.2023", "testAddTransaction", 3890,"Molziles", "Elixir"));

        System.out.println(privateBank);
        try {
            privateBank.removeTransaction("Molziles", new Transfer("19.04.2023", "testAddTransaction", 3890,"Molziles", "Elixir"));
        } catch (TransactionDoesNotExistException e) {
            System.out.println(e);
        }

//        privateBank.removeTransaction("Molziles", new Transfer("01.04.2023", "testAddTransaction", 3890,"Molziles", "Elixir"));

       /* System.out.println(privateBank);
        System.out.println(privateBank.getTransactions("Molziles"));*/

       ;
//        System.out.println(.stream().map());
        for (Transaction transaction :  privateBank.getTransactionsSorted("Molziles", false))
            System.out.println(transaction);
    }
}
