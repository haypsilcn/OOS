import bank.IncomingTransfer;
import bank.OutgoingTransfer;
import bank.Payment;
import bank.PrivateBank;
import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.TransactionAlreadyExistException;
import bank.exceptions.TransactionDoesNotExistException;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PrivateBankTest {

    static PrivateBank privateBank;

    @BeforeAll
    public static void setUp() throws AccountAlreadyExistsException, IOException, TransactionAlreadyExistException, AccountDoesNotExistException {
        privateBank = new PrivateBank("JUnit 5", "junit5", 0, 0.12);
        privateBank.createAccount("Hagen");
        privateBank.createAccount("Tim");
        privateBank.addTransaction("Hagen", new Payment("19.01.2011", "Payment", -789, 0.9, 0.25));
        privateBank.addTransaction("Tim", new IncomingTransfer("03.03.2000", "IncomingTransfer from Molziles to Elixir; 80", 80, "Adam", "Tim"));
    }

    @Test
    public void constructorTest() {
        assertEquals("JUnit 5", privateBank.getName());
        assertEquals("junit5", privateBank.getDirectoryName());
        assertEquals(0, privateBank.getIncomingInterest());
        assertEquals(0.12, privateBank.getOutgoingInterest());
    }

    @Test
    public void createAccountSuccessTest() {
        assertDoesNotThrow(
                () -> privateBank.createAccount("Dinesh")
        );
    }

    @Test
    public void createAccountFailTest() {
        assertThrows(AccountAlreadyExistsException.class,
                () -> privateBank.createAccount("Hagen"));
        System.out.println("Account already exist");
    }

    @Test
    public void createAccountWithTransactionsListSuccessTest() {
        assertDoesNotThrow(
                () -> privateBank.createAccount("Klaus", List.of(
                        new Payment("23.09.1897", "Payment 02", -2500, 0.8, 0.5),
                        new OutgoingTransfer("30.07.2020", "OutgoingTransfer to Hagen", 1890, "Elixir", "Hagen")
                ))
        );
    }

    @Test
    public void createAccountWithTransactionsListFailTest() {
        assertThrows(AccountAlreadyExistsException.class,
                () -> privateBank.createAccount("Hagen", List.of(
                        new Payment("23.09.1897", "Payment 02", -2500, 0.8, 0.5)
                ))
        );
    }

    @Test
    public void addTransactionValidAccountValidTransactionTest() {
        assertDoesNotThrow(
                () -> privateBank.addTransaction("Hagen", new OutgoingTransfer("30.07.2020", "OutgoingTransfer to Hagen", 1890, "Elixir", "Hagen"))
        );
    }

    @Test
    public void addTransactionInvalidTransactionTest() {
        assertThrows(TransactionAlreadyExistException.class,
                () ->  privateBank.addTransaction("Tim", new IncomingTransfer("03.03.2000", "IncomingTransfer from Molziles to Elixir; 80", 80, "Adam", "Tim"))
        );
    }

  @Test
    public void addTransactionInvalidAccountTest() {
        assertThrows(AccountDoesNotExistException.class,
                () -> privateBank.addTransaction("Gina", new Payment("19.01.2011", "Payment", -789, 0.9, 0.25))
                );
    }

    @Test
    public void removeTransactionValidTransactionTest() {
        assertDoesNotThrow(
                () -> privateBank.removeTransaction("Hagen", new Payment("19.01.2011", "Payment", -789, 0.9, 0.25))
        );
    }

    @Test
    public void removeTransactionInvalidTest() {
        assertThrows(TransactionDoesNotExistException.class,
                () -> privateBank.removeTransaction("Hagen", new IncomingTransfer("03.03.2000", "IncomingTransfer from Molziles to Elixir; 80", 80))
                );
    }

    @Test
    public void containsTransactionTrueTest() {
        assertTrue(privateBank.containsTransaction("Tim", new IncomingTransfer("03.03.2000", "IncomingTransfer from Molziles to Elixir; 80", 80, "Adam", "Tim")));
    }

    @Test
    public void containsTransactionFalseTest() {
        assertFalse(privateBank.containsTransaction("Tim", new Payment("19.01.2011", "Payment", -789, 0.9, 0.25)));
    }

    @Test
    public void getAccountBalanceTest() {
        assertEquals(80, privateBank.getAccountBalance("Tim"));
    }

    @Test
    public void getTransactionTest() {
        assertEquals(List.of(
                new IncomingTransfer("03.03.2000", "IncomingTransfer from Molziles to Elixir; 80", 80, "Adam", "Tim"))
                , privateBank.getTransactions("Tim"));
    }

    @Test
    public void getTransactionsByTypeTest() {
        assertEquals(List.of(), privateBank.getTransactionsByType("Tim", false));
    }

    @Test
    public void getTransactionsSortedTest() {
        assertEquals(List.of(
                        new IncomingTransfer("03.03.2000", "IncomingTransfer from Molziles to Elixir; 80", 80, "Adam", "Tim"))
                , privateBank.getTransactionsSorted("Tim", true));

    }


}
