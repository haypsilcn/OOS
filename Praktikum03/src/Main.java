import bank.PrivateBank;
import bank.exceptions.AccountAlreadyExistsException;


public class Main {
    public static void main (String[] args) throws AccountAlreadyExistsException {
        PrivateBank privateBank = new PrivateBank("VnBank", 0, 0);

        privateBank.createAccount("acc 1");
        privateBank.createAccount("testing acc");


        System.out.println(privateBank);
    }
}
