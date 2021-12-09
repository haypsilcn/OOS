import bank.*;
import bank.exceptions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test methods for PrivateBank")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PrivateBankTest {

    static PrivateBank privateBank;
    static PrivateBank copyPrivateBank;

    @DisplayName("Set up a PrivateBank")
    @BeforeAll
    public static void setUp() throws AccountAlreadyExistsException, IOException, TransactionAlreadyExistException, AccountDoesNotExistException {

        final File folder = new File("data/junit5");
        if (folder.exists()) {
            final File[] listOfFiles = folder.listFiles();
            assert listOfFiles != null;
            for (File file : listOfFiles)
                file.delete();
            Files.deleteIfExists(Path.of("data/junit5"));
        }

        privateBank = new PrivateBank("JUnit 5", "junit5", 0, 0.12);

        privateBank.createAccount("Hagen");
        privateBank.createAccount("Tim");
        privateBank.addTransaction("Hagen", new Payment("19.01.2011", "Payment", -789, 0.9, 0.25));
        privateBank.addTransaction("Tim", new IncomingTransfer("03.03.2000", "IncomingTransfer from Molziles to Elixir; 80", 80, "Adam", "Tim"));
        copyPrivateBank = new PrivateBank(privateBank);

    }

    @DisplayName("Testing constructor")
    @Test
    @Order(0)
    public void constructorTest() {
        assertEquals("JUnit 5", privateBank.getName());
        assertEquals("junit5", privateBank.getDirectoryName());
        assertEquals(0, privateBank.getIncomingInterest());
        assertEquals(0.12, privateBank.getOutgoingInterest());
    }

    @DisplayName("Testing copy constructor")
    @Test @Order(1)
    public void copyConstructorTest() {
        assertEquals(privateBank.getName(), copyPrivateBank.getName());
        assertEquals(privateBank.getDirectoryName(), copyPrivateBank.getDirectoryName());
        assertEquals(privateBank.getIncomingInterest(), copyPrivateBank.getIncomingInterest());
        assertEquals(privateBank.getOutgoingInterest(), copyPrivateBank.getOutgoingInterest());
    }

    @DisplayName("Create a duplicate account")
    @ParameterizedTest
    @Order(2)
    @ValueSource(strings = {"Hagen", "Tim"})
    public void createDuplicateAccountTest(String account) {
        Exception e = assertThrows(AccountAlreadyExistsException.class,
                () -> privateBank.createAccount(account));
        System.out.println(e.getMessage());
    }

    @DisplayName("Create a valid account")
    @ParameterizedTest
    @Order(3)
    @ValueSource(strings = {"Dinesh", "Bob", "Narsha"})
    public void createValidAccountTest(String account) {
        assertDoesNotThrow(
                () -> privateBank.createAccount(account)
        );
    }

    @DisplayName("Create a valid account with a transactions list")
    @ParameterizedTest
    @Order(4)
    @ValueSource(strings = {"Klaus", "Harsh", "Rastla"})
    public void createValidAccountWithTransactionsListTest(String account) {
        assertDoesNotThrow(
                () -> privateBank.createAccount(account, List.of(
                        new Payment("23.09.1897", "Payment 02", -2500, 0.8, 0.5),
                        new OutgoingTransfer("30.07.2020", "OutgoingTransfer to Hagen", 1890, "Elixir", "Hagen")
                ))
        );
    }

    @DisplayName("Create a duplicate account with a transactions list")
    @ParameterizedTest
    @Order(5)
    @ValueSource(strings = {"Hagen", "Klaus", "Tim", "Bob", "Dinesh", "Narsha", "Harsh", "Rastla"})
    public void createInvalidAccountWithTransactionsListTest(String account) {
        Exception e = assertThrows(AccountAlreadyExistsException.class,
                () -> privateBank.createAccount(account, List.of(
                        new Payment("23.09.1897", "Payment 02", -2500, 0.8, 0.5)
                ))
        );
        System.out.println(e.getMessage());
    }

    @DisplayName("Add a valid transaction to a valid account")
    @ParameterizedTest
    @Order(6)
    @ValueSource(strings = {"Hagen", "Bob", "Narsha", "Dinesh"})
    public void addValidTransactionValidAccountTest(String account) {
        assertDoesNotThrow(
                () -> privateBank.addTransaction(account, new Payment("30.07.2020", "OutgoingTransfer to Hagen", 1890, 0.2, 0.05))
        );
    }

    @DisplayName("Add a duplicate transaction to a valid account")
    @Test
    @Order(7)
    public void addDuplicateTransactionTest() {
        Exception e = assertThrows(TransactionAlreadyExistException.class,
                () -> privateBank.addTransaction("Tim", new IncomingTransfer("03.03.2000", "IncomingTransfer from Molziles to Elixir; 80", 80, "Adam", "Tim"))
        );
        System.out.println(e.getMessage());
    }

    @DisplayName("Add a valid transaction to an invalid account")
    @ParameterizedTest
    @Order(8)
    @ValueSource(strings = {"Gina", "Bona", "Yang"})
    public void addTransactionInvalidAccountTest(String account) {
        Exception e = assertThrows(AccountDoesNotExistException.class,
                () -> privateBank.addTransaction(account, new Payment("19.01.2011", "Payment", -789, 0.9, 0.25))
        );
        System.out.println(e.getMessage());
    }

    @DisplayName("Remove a valid transaction")
    @Test
    @Order(9)
    public void removeValidTransactionTest() {
        assertDoesNotThrow(
                () -> privateBank.removeTransaction("Hagen", new Payment("19.01.2011", "Payment", -789, 0.9, 0.25))
        );
    }

    @DisplayName("Remove an invalid transaction")
    @Test
    @Order(10)
    public void removeInvalidTransactionTest() {
        Exception e = assertThrows(TransactionDoesNotExistException.class,
                () -> privateBank.removeTransaction("Hagen", new Payment("19.01.2011", "Payment", -789, 0.9, 0.25))
        );
        System.out.println(e.getMessage());
    }

    @DisplayName("Contains a transaction is true")
    @ParameterizedTest
    @Order(11)
    @ValueSource(strings = {"Hagen", "Bob", "Narsha", "Dinesh"})
    public void containsTransactionTrueTest(String account) {
        System.out.println("Expected TRUE: ");
        assertTrue(privateBank.containsTransaction(account, new Payment("30.07.2020", "OutgoingTransfer to Hagen", 1890, 0.2, 0.05)));
    }

    @DisplayName("Contains a transaction is false")
    @ParameterizedTest
    @Order(12)
    @ValueSource(strings = {"Hagen", "Bob", "Narsha", "Dinesh", "Tim"})
    public void containsTransactionFalseTest(String account) {
        System.out.println("Expected FALSE: ");
        assertFalse(privateBank.containsTransaction(account, new Payment("19.01.2011", "Payment", -789, 0.9, 0.25)), "false");
    }

    @DisplayName("Get account balance")
    @ParameterizedTest
    @Order(14)
    @CsvSource({"Klaus, -4690", "Tim, 80", "Hagen, 1890"})
    public void getAccountBalanceTest(String account, double balance) {
        System.out.println("Expected <" + balance + "> in account <" + account + ">");
        assertEquals(balance, privateBank.getAccountBalance(account));
    }

    @DisplayName("Get transactions list")
    @Test  @Order(15)
    public void getTransactionTest() {
        List<Transaction> transactionList = List.of(
                new Payment("23.09.1897", "Payment 02", -2500, 0, 0.12),
                new OutgoingTransfer("30.07.2020", "OutgoingTransfer to Hagen", 1890, "Elixir", "Hagen"));
        System.out.println("Transactions list expected: \n" + transactionList);
        assertEquals(transactionList, privateBank.getTransactions("Klaus"));
    }

    @DisplayName("Get transactions list by type")
    @Test @Order(16)
    public void getTransactionsByTypeTest() {
        List<Transaction> transactionList = List.of(
                new Payment("23.09.1897", "Payment 02", -2500, 0, 0.12),
                new OutgoingTransfer("30.07.2020", "OutgoingTransfer to Hagen", 1890, "Elixir", "Hagen"));
        System.out.println("Transactions list expected: \n" + transactionList);
        assertEquals(transactionList, privateBank.getTransactionsByType("Klaus", false));
    }

    @Test @Order(17)
    @DisplayName("Get sorted transactions list")
    public void getTransactionsSortedTest() {
        assertEquals(List.of(
                        new IncomingTransfer("03.03.2000", "IncomingTransfer from Molziles to Elixir; 80", 80, "Adam", "Tim"))
                , privateBank.getTransactionsSorted("Tim", true));

    }
}
