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
import java.util.ArrayList;
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
        privateBank.addTransaction("Tim", new IncomingTransfer("03.03.2000", "IncomingTransfer from Adam to Tim; 80", 80, "Adam", "Tim"));
        copyPrivateBank = new PrivateBank(privateBank);

    }

    @DisplayName("Testing constructor")
    @Test
    @Order(0)
    public void constructorTest() {
        assertAll("PrivateBank",
                () -> assertEquals("JUnit 5", privateBank.getName()),
                () -> assertEquals("junit5", privateBank.getDirectoryName()),
                () -> assertEquals(0, privateBank.getIncomingInterest()),
                () -> assertEquals(0.12, privateBank.getOutgoingInterest()));
    }

    @DisplayName("Testing copy constructor")
    @Test
    @Order(1)
    public void copyConstructorTest() {
        assertAll("CopyPrivateBank",
                () -> assertEquals(privateBank.getName(), copyPrivateBank.getName()),
                () -> assertEquals(privateBank.getDirectoryName(), copyPrivateBank.getDirectoryName()),
                () -> assertEquals(privateBank.getIncomingInterest(), copyPrivateBank.getIncomingInterest()),
                () -> assertEquals(privateBank.getOutgoingInterest(), copyPrivateBank.getOutgoingInterest()));
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
                        new OutgoingTransfer("30.07.2020", "OutgoingTransfer to Hagen", 1890, account, "Hagen")
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
    @ValueSource(strings = {"Hagen", "Bob", "Narsha", "Dinesh", "Klaus"})
    public void addValidTransactionValidAccountTest(String account) {
        assertDoesNotThrow(
                () -> privateBank.addTransaction(account, new IncomingTransfer("30.07.2020", "OutgoingTransfer to Hagen", 1890, "Tom", account))
        );
    }

    @DisplayName("Add a duplicate transaction to a valid account")
    @ParameterizedTest
    @Order(7)
    @ValueSource(strings = {"Klaus", "Harsh", "Rastla"})
    public void addDuplicateTransactionTest(String account) {
        Exception e = assertThrows(TransactionAlreadyExistException.class,
                () -> privateBank.addTransaction(account, new Payment("23.09.1897", "Payment 02", -2500, 0.8, 0.5))
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
    @ParameterizedTest
    @Order(9)
    @ValueSource(strings = {"Harsh", "Rastla", "Klaus"})
    public void removeValidTransactionTest(String account) {
        assertDoesNotThrow(
                () -> privateBank.removeTransaction(account, new Payment("23.09.1897", "Payment 02", -2500, 0.8, 0.5))
        );
    }

    @DisplayName("Remove an invalid transaction")
    @ParameterizedTest
    @Order(10)
    @ValueSource(strings = {"Harsh", "Rastla", "Klaus"})
    public void removeInvalidTransactionTest(String account) {
        Exception e = assertThrows(TransactionDoesNotExistException.class,
                () -> privateBank.removeTransaction(account, new Payment("19.01.2011", "Payment", -789, 0.9, 0.25))
        );
        System.out.println(e.getMessage());
    }

    @DisplayName("Contains a transaction is true")
    @ParameterizedTest
    @Order(11)
    @ValueSource(strings = {"Harsh", "Rastla", "Klaus"})
    public void containsTransactionTrueTest(String account) {
        assertTrue(privateBank.containsTransaction(account, new OutgoingTransfer("30.07.2020", "OutgoingTransfer to Hagen", 1890, account, "Hagen")));
        System.out.println("containsTransactionTrueTest in <" + account + "> is correct.");
    }

    @DisplayName("Contains a transaction is false")
    @ParameterizedTest
    @Order(12)
    @ValueSource(strings = {"Hagen", "Bob", "Narsha", "Dinesh", "Tim"})
    public void containsTransactionFalseTest(String account) {
        assertFalse(privateBank.containsTransaction(account, new OutgoingTransfer("30.07.2020", "OutgoingTransfer to Hagen", 1890, account, "Hagen")));
        System.out.println("containsTransactionFalseTest in <" + account + "> is correct.");
    }

    @DisplayName("Get account balance")
    @ParameterizedTest
    @Order(14)
    @CsvSource({"Klaus, 0", "Tim, 80", "Hagen, 1006.32"})
    public void getAccountBalanceTest(String account, double balance) {
        System.out.println("Expected <" + balance + "> in account <" + account + ">");
        assertEquals(balance, privateBank.getAccountBalance(account));
    }

    @DisplayName("Get transactions list")
    @Test  @Order(15)
    public void getTransactionTest() {
        List<Transaction> transactionList = List.of(
                new Payment("19.01.2011", "Payment", -789, 0, 0.12),
                new IncomingTransfer("30.07.2020", "OutgoingTransfer to Hagen", 1890, "Tom", "Hagen"));
        assertEquals(transactionList, privateBank.getTransactions("Hagen"));
        System.out.println("getTransactionTest in <Hagen> is correct.");
    }

    @DisplayName("Get transactions list by type")
    @Test @Order(16)
    public void getTransactionsByTypeTest() {
        List<Transaction> transactionList = List.of(
                new OutgoingTransfer("30.07.2020", "OutgoingTransfer to Hagen", 1890, "Klaus", "Hagen"));
        assertEquals(transactionList, privateBank.getTransactionsByType("Klaus", false));
        System.out.println("getTransactionByTypeTest in <Klaus> is correct.");
    }

    @Test @Order(17)
    @DisplayName("Get sorted transactions list")
    public void getTransactionsSortedTest() {
        assertEquals(List.of(
                        new IncomingTransfer("03.03.2000", "IncomingTransfer from Adam to Tim; 80", 80, "Adam", "Tim"))
                , privateBank.getTransactionsSorted("Tim", true));

    }

    @Test @Order(18)
    @DisplayName("Get a list of all accounts")
    public void getAllAccounts() {
        List<String> expected = new ArrayList<>();
        expected.add("Hagen");
        expected.add("Bob");
        expected.add("Dinesh");
        expected.add("Tim");
        expected.add("Harsh");
        expected.add("Narsha");
        expected.add("Klaus");
        expected.add("Rastla");

        assertEquals(expected, privateBank.getAllAccounts());
    }

    @ParameterizedTest
    @DisplayName("Delete a valid account")
    @Order(19)
    @ValueSource(strings = {"Bob", "Narsha", "Tim", "Hagen"})
    public void deleteValidAccount(String account) {
        assertDoesNotThrow(
                () -> privateBank.deleteAccount(account)
        );
    }

    @ParameterizedTest
    @DisplayName("Delete an invalid account")
    @Order(20)
    @ValueSource(strings = {"Gina", "Natasha", "Steve"})
    public void deleteInvalidAccount(String account) {
        Exception e = assertThrows(AccountDoesNotExistException.class,
                () -> privateBank.deleteAccount(account));
        System.out.print(e.getMessage());
    }
}
