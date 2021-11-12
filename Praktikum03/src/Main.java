import bank.PrivateBank;
import bank.exceptions.AccountAlreadyExistsException;


public class Main {
    public static void main (String[] args) throws AccountAlreadyExistsException {
        PrivateBank privateBank = new PrivateBank("somename", 0, 0);
       /* try {
            privateBank.createAccount("am");
        } catch (AccountAlreadyExistsException ex) {
            System.out.println(ex.getMessage());
        }*/

        privateBank.createAccount("am");

        System.out.println(privateBank);
    }
}
