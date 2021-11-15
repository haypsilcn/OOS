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
                new Payment("12.03.2008", "Payment", 321),
                new Payment("23.09.1897", "Payment", -2500, 0.8, 0.5),
                new Transfer("03.03.2000", "Transfer", 80, "Molziles", "Elixir")
        ));
        privateBank.createAccount("Elixir", List.of(
                new Payment("22.06.1998", "Payment", 435, 0., 0.),
                new Transfer("03.03.2000", "Transfer", 80, "Molziles", "Elixir"),
                new Payment("05.08.2022", "Payment", -118, 0., 0.),
                new Transfer("15.04.1990", "Transfer", 185, "Elixir", "Vaio"),
                new Transfer("30.07.2020", "Transfer", 1890, "Elixir", "Booth"),
                new Payment("19.01.2011", "Payment", -789, 0.9, 0.25)
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
            privateBank.addTransaction("Vaio", new Payment("11.11.2000", "Payment", 890));
        } catch (AccountDoesNotExistException | TransactionAlreadyExistException e) {
            System.out.println(e);
        }

        try {
            privateBank.addTransaction("Molziles", new Transfer("03.03.2000", "Transfer", 80, "Molziles", "Elixir"));
        } catch (AccountDoesNotExistException | TransactionAlreadyExistException e) {
            System.out.println(e);
        }

        try {
            privateBank.addTransaction("Anand", new Payment("22.03.2003", "Payment", 90, 0.9, 0.75));
        } catch (AccountDoesNotExistException | TransactionAlreadyExistException e) {
            System.out.println(e);
        }

        try {
            privateBank.addTransaction("Anand", new Transfer("19.04.2023", "Transfer", 3890,"Molziles", "Elixir"));
        } catch (AccountDoesNotExistException | TransactionAlreadyExistException e) {
            System.out.println(e);
        }

        System.out.println(privateBank);

        try {
            privateBank.removeTransaction("Anand", new Transfer("19.04.2023", "Transfer", 3890,"Molziles", "Elixir"));
        } catch (TransactionDoesNotExistException e) {
            System.out.println(e);
        }

        try {
            privateBank.removeTransaction("Elixir", new Transfer("15.04.1990", "Transfer", 185, "Elixir", "Vaio"));
        } catch (TransactionDoesNotExistException e) {
            System.out.println(e);
        }

        System.out.println(privateBank);
        privateBank.getTransactionsSorted("Elixir", true);
        privateBank.getTransactionsSorted("Elixir", false);
        privateBank.getTransactionsByType("Elixir", true);
        privateBank.getTransactionsByType("Elixir", false);
        privateBank.getTransactions("Elixir");
        privateBank.getAccountBalance("Elixir");
        privateBank.containsTransaction("Molziles", new Transfer("28.02.1908", "Transfer", 1095, "Molziles", "Elixir"));
        privateBank.containsTransaction("Molziles", new Transfer("03.03.2000", "Transfer", 80, "Molziles", "Elixir"));
        privateBank.containsTransaction("Anand", new Payment("22.03.2003", "Payment", 90, 0.9, 0.75));

        PrivateBank copiedBank = new PrivateBank(privateBank);
        System.out.println(copiedBank);
        System.out.println("Testing equals() method:\nprivateBank vs copiedBank expected <true> => " + privateBank.equals(copiedBank));
    }
}
