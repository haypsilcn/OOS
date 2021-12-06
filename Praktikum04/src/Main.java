import bank.*;

import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.TransactionAlreadyExistException;
import bank.exceptions.TransactionDoesNotExistException;

import java.io.IOException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws AccountAlreadyExistsException, TransactionAlreadyExistException, AccountDoesNotExistException, TransactionDoesNotExistException, IOException {


        PrivateBank deutscheBank = new PrivateBank("Deutsche Bank", "Deutsche Bank", 0.25, 0.3);
        try {
            deutscheBank.createAccount("Molziles", List.of(
                    new Payment("12.03.2008", "Payment", 321),
                    new Payment("23.09.1897", "Payment", -2500, 0.8, 0.5),
                    new OutcomingTransfer("03.03.2000", "OutcomingTransfer", 80, "Molziles", "Elixir")
            ));
        } catch (AccountAlreadyExistsException e) {
            System.out.println(e);
        }

        try {
            deutscheBank.createAccount("Elixir", List.of(
                    new Payment("22.06.1998", "Payment", 435, 0., 0.),
                    new IncomingTransfer("03.03.2000", "IncomingTransfer", 80, "Molziles", "Elixir"),
                    new Payment("05.08.2022", "Payment", -118, 0., 0.),
                    new OutcomingTransfer("15.04.1990", "OutcomingTransfer", 185, "Elixir", "Vaio"),
                    new OutcomingTransfer("30.07.2020", "OutcomingTransfer", 1890, "Elixir", "Booth")
            ));
        } catch (AccountAlreadyExistsException e) {
            System.out.println(e);
        }

        try {
            deutscheBank.createAccount("Hagen");
        } catch (AccountAlreadyExistsException e) {
            System.out.println(e);
        }

        try {
            deutscheBank.removeTransaction("Hagen", new Payment("19.01.2011", "Payment", -789, 0.9, 0.25));
        } catch (TransactionDoesNotExistException e) {
            System.out.println(e);
        }

        try {
            deutscheBank.addTransaction("Hagen", new Payment("19.01.2011", "Payment", -789, 0.9, 0.25));
        } catch (TransactionAlreadyExistException e) {
            System.out.println(e);
        }

        System.out.println("\n" + deutscheBank);



        PrivateBank sparkasse = new PrivateBank("Sparkasse", "Sparkasse", 0.11, 0.05);
        try {
            sparkasse.addTransaction("Hagen", new Payment("19.01.2011", "Payment", -789, 0.9, 0.25));
        } catch (AccountDoesNotExistException e) {
            System.out.println(e);
        }

        try {
            sparkasse.createAccount("Rehman");
        } catch (AccountAlreadyExistsException e) {
            System.out.println(e);
        }

        System.out.println("\n" + sparkasse);


        PrivateBank aachenerBank = new PrivateBank("Aachener Bank", "Aachen", 0.11, 0.26);
        System.out.println(aachenerBank);
    }
}
