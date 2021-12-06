package bank;

import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.TransactionAlreadyExistException;
import bank.exceptions.TransactionDoesNotExistException;
import com.google.gson.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class PrivateBank implements Bank {

    /**
     * Represents the name of private Bank
     */
    private String name;

    /**
     * Represent directory for all Json files for PrivateBank
     */
    private String directoryName;

    /**
     * full path of directory where stores all files
     */
    private String fullPath;

    /**
     * The interest (positive value in percent, 0 to 1) accrues on a deposit
     */
    private double incomingInterest;

    /**
     * The interest (positive value in percent, 0 to 1) accrues on a withdrawal
     */
    private double outcomingInterest;

    /**
     * Links accounts to transactions so that 0 to N transactions can be assigned to
     * each stored account
     */
    private Map<String, List<Transaction>> accountsToTransactions = new HashMap<>();


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }


    public void setIncomingInterest(double incomingInterest) {
        this.incomingInterest = incomingInterest;
    }

    public double getIncomingInterest() {
        return incomingInterest;
    }


    public void setOutcomingInterest(double outcomingInterest) {
        this.outcomingInterest = outcomingInterest;
    }

    public double getOutcomingInterest() {
        return outcomingInterest;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setFullPath(String directoryName, boolean copiedBankPath) {
        if (copiedBankPath) fullPath = "PrivateBanks/_CopiedPrivateBanks/" + directoryName;
        else fullPath = "PrivateBanks/" + directoryName;
    }

    public String getFullPath() {
        return fullPath;
    }


    /**
     * Constructor with four attribute
     */
    public PrivateBank(String newName, String newDirectoryName, double newIncomingInterest, double newOutcomingInterest) {
        this.name = newName;
        this.directoryName = newDirectoryName;
        this.incomingInterest = newIncomingInterest;
        this.outcomingInterest = newOutcomingInterest;

        setFullPath(newDirectoryName, false);

        try {
            Path path = Paths.get(fullPath);
            Files.createDirectories(path);
            System.out.println("Directory for " + PrivateBank.this.getName() + " is created!");
        } catch (IOException e) {
            System.out.println("Failed to create directory for " + PrivateBank.this.getName() + "!");
        }
    }

    /**
     * Copy Constructor
     */
    public PrivateBank(PrivateBank newPrivateBank) {
        this(newPrivateBank.name, newPrivateBank.directoryName, newPrivateBank.incomingInterest, newPrivateBank.outcomingInterest);
        this.accountsToTransactions = newPrivateBank.accountsToTransactions;

        setFullPath(newPrivateBank.directoryName, true);
        try {
            Path path = Paths.get(fullPath);
            System.out.println(path);
            Files.createDirectories(path);
            System.out.println("Directory for copied " + PrivateBank.this.getName() + " is created!");
        } catch (IOException e) {
            System.out.println("Failed to create directory for copied " + PrivateBank.this.getName() + "!");
        }
    }

    /**
     * @return contents of all attributes
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        Set<String> setKey = accountsToTransactions.keySet();
        for (String key : setKey) {
            str.append(key).append(" => \n");
            List<Transaction> transactionsList = accountsToTransactions.get(key);
            for (Transaction transaction : transactionsList)
                str.append("\t\t").append(transaction);
        }
        return "Name: " + name + "\nIncoming Interest: " + incomingInterest + "\nOutcoming Interest: " + outcomingInterest + "\n" + str;
    }

    public boolean equals(Object obj) {
        if (obj instanceof PrivateBank privateBank)
            return (name.equals(privateBank.name) && incomingInterest == privateBank.incomingInterest && outcomingInterest == privateBank.outcomingInterest && accountsToTransactions.equals(privateBank.accountsToTransactions));
        return false;
    }

    /**
     * Adds an account to the bank. If the account ALREADY EXISTS, an exception is thrown.
     *
     * @param account the account to be added
     * @throws AccountAlreadyExistsException if the account ALREADY EXISTS
     */
    @Override
    public void createAccount(String account) throws AccountAlreadyExistsException, IOException {
        System.out.println("Creating new account <" + account + "> to bank <" + name + ">");
        if (accountsToTransactions.containsKey(account))
            throw new AccountAlreadyExistsException("ACCOUNT <" + account + "> ALREADY EXISTS!\n");
        else {
            accountsToTransactions.put(account, List.of());
            writeAccount(account);
            System.out.println("=> SUCCESS!\n");
        }
    }

    /**
     * Adds an account (with all specified transactions) to the bank. If the account ALREADY EXISTS,
     * an exception is thrown.
     *
     * @param account      the account to be added
     * @param transactions
     * @throws AccountAlreadyExistsException if the account ALREADY EXISTS
     */
    @Override
    public void createAccount(String account, List<Transaction> transactions) throws AccountAlreadyExistsException, IOException {
        System.out.print("Creating new account <" + account + "> to bank <" + name + "> with transactions list: \n\t\t" + transactions.toString().replaceAll("[]]|[\\[]", "").replace("\n, ", "\n\t\t"));
        if ((accountsToTransactions.containsKey(account)) || (accountsToTransactions.containsKey(account) && accountsToTransactions.containsValue(transactions)))
            throw new AccountAlreadyExistsException("ACCOUNT <" + account + "> ALREADY EXISTS!\n");
        else {
            for (Transaction valueOfTransactions : transactions)
                if (valueOfTransactions instanceof Payment payment) {
                    payment.setIncomingInterest(PrivateBank.this.incomingInterest);
                    payment.setOutcomingInterest(PrivateBank.this.outcomingInterest);
                }
            accountsToTransactions.put(account, transactions);
            writeAccount(account);
            System.out.println("=> SUCCESS!\n");

        }
    }

    /**
     * Adds a transaction to an account. If the specified account does not exist, an exception is
     * thrown. If the transaction ALREADY EXISTS, an exception is thrown.
     *
     * @param account     the account to which the transaction is added
     * @param transaction the transaction which is added to the account
     * @throws TransactionAlreadyExistException if the transaction ALREADY EXISTS
     */
    @Override
    public void addTransaction(String account, Transaction transaction) throws TransactionAlreadyExistException, AccountDoesNotExistException {
        System.out.println("Adding new transaction <" + transaction.toString().replace("\n", "") + "> to account <" + account + "> in bank <" + name + ">");
        if (!accountsToTransactions.containsKey(account))
            throw new AccountDoesNotExistException("ACCOUNT <" + account + "> DOES NOT EXISTS!\n");
        else {
            if (accountsToTransactions.get(account).contains(transaction))
                throw new TransactionAlreadyExistException("THIS TRANSACTION ALREADY EXISTS!\n");
            else {
                if (transaction instanceof Payment payment) {
                    payment.setIncomingInterest(PrivateBank.this.incomingInterest);
                    payment.setOutcomingInterest(PrivateBank.this.outcomingInterest);
                }
                List<Transaction> transactionsList = new ArrayList<>(accountsToTransactions.get(account));
                transactionsList.add(transaction);
                accountsToTransactions.put(account, transactionsList);
                writeAccount(account);
                System.out.println("=> SUCCESS!\n");
            }
        }
    }

    /**
     * Removes a transaction from an account. If the transaction does not exist, an exception is
     * thrown.
     *
     * @param account     the account from which the transaction is removed
     * @param transaction the transaction which is added to the account
     * @throws TransactionDoesNotExistException if the transaction cannot be found
     */
    @Override
    public void removeTransaction(String account, Transaction transaction) throws TransactionDoesNotExistException {
        System.out.println("Removing transaction <" + transaction.toString().replace("\n", "") + "> from account <" + account + "> in bank <" + name + ">");
        if (!accountsToTransactions.get(account).contains(transaction))
            throw new TransactionDoesNotExistException("THIS TRANSACTION DOES NOT EXISTS!\n");
        else {
            List<Transaction> transactionsList = new ArrayList<>(accountsToTransactions.get(account));
            transactionsList.remove(transaction);
            accountsToTransactions.put(account, transactionsList);
            System.out.println("=> SUCCESS!\n");
        }

    }

    /**
     * Checks whether the specified transaction for a given account exists.
     *
     * @param account     the account from which the transaction is checked
     * @param transaction the transaction which is added to the account
     */
    @Override
    public boolean containsTransaction(String account, Transaction transaction) {
        if (transaction instanceof Payment payment) {
            payment.setIncomingInterest(PrivateBank.this.incomingInterest);
            payment.setOutcomingInterest(PrivateBank.this.outcomingInterest);
        }
        System.out.println("Checking account <" + account + "> contains the transaction <" + transaction.toString().replace("\n", "") + "> : " + accountsToTransactions.get(account).contains(transaction) + "\n");
        return accountsToTransactions.get(account).contains(transaction);
    }

    /**
     * Calculates and returns the current account balance.
     *
     * @param account the selected account
     * @return the current account balance
     */
    @Override
    public double getAccountBalance(String account) {
        double balance = 0;
        for (Transaction transaction : accountsToTransactions.get(account))
            balance = balance + transaction.calculate();
        System.out.println("Balance of account <" + account + "> in bank <" + name + "> : " + (double) Math.round(balance * 100) / 100 + "\n");
        return balance;
    }

    /**
     * Returns a list of transactions for an account.
     *
     * @param account the selected account
     * @return the list of transactions
     */
    @Override
    public List<Transaction> getTransactions(String account) {
        System.out.println("Transactions list of account <" + account + "> in bank <" + name + ">\n" + accountsToTransactions.get(account).toString().replace("[", "\t\t").replace("]", "").replace("\n, ", "\n\t\t"));
        return accountsToTransactions.get(account);
    }

    /**
     * Returns a sorted list (-> calculated amounts) of transactions for a specific account . Sorts the list either in ascending or descending order
     * (or empty).
     *
     * @param account the selected account
     * @param asc     selects if the transaction list is sorted ascending or descending
     * @return the list of transactions
     */
    @Override
    public List<Transaction> getTransactionsSorted(String account, boolean asc) {
        // create new list to store sorted list without affecting original list
        List<Transaction> sortedTransactionsList = new ArrayList<>(accountsToTransactions.get(account));
        if (asc) {
            sortedTransactionsList.sort(Comparator.comparingDouble(Transaction::calculate));
            System.out.println("Sorting transactions of account <" + account + "> by calculated amounts in ASCENDING order:\n" + sortedTransactionsList.toString().replace("[", "\t\t").replace("]", "").replace("\n, ", "\n\t\t"));
        } else {
            sortedTransactionsList.sort(Comparator.comparingDouble(Transaction::calculate).reversed());
            System.out.println("Sorting transactions of account <" + account + "> by calculated amounts in DESCENDING order:\n" + sortedTransactionsList.toString().replace("[", "\t\t").replace("]", "").replace("\n, ", "\n\t\t"));
        }
        return sortedTransactionsList;
    }

    /**
     * Returns a list of either positive or negative transactions (-> calculated amounts).
     *
     * @param account  the selected account
     * @param positive selects if positive  or negative transactions are listed
     * @return the list of transactions
     */
    @Override
    public List<Transaction> getTransactionsByType(String account, boolean positive) {
        List<Transaction> transactionsListByType = new ArrayList<>();
        if (positive) System.out.println("List of POSITIVE transactions of account <" + account + "> :");
        else System.out.println("List of NEGATIVE transactions of account <" + account + "> :");
        for (Transaction transaction : accountsToTransactions.get(account)) {
            if (positive && transaction.calculate() >= 0) transactionsListByType.add(transaction);
            else if (!positive && transaction.calculate() < 0) transactionsListByType.add(transaction);
        }
        System.out.println(transactionsListByType.toString().replace("[", "\t\t").replace("]", "").replace("\n, ", "\n\t\t"));

        return transactionsListByType;
    }

    /**
     * Persists the specified account in the file system (serialize then save into JSON)
     *
     * @param account the account to be written
     */
    private void writeAccount(String account) {

        try (FileWriter file = new FileWriter(getFullPath() + "/" + account + ".json")) {

            file.write("[");

            for (Transaction transaction : accountsToTransactions.get(account)) {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(transaction.getClass(), new CustomDe_Serializer())
                        .setPrettyPrinting()
                        .create();
                String json = gson.toJson(transaction);
                if (accountsToTransactions.get(account).indexOf(transaction) != 0)
                    file.write(",");
                file.write(json);
            }

            file.write("]");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readAccounts() {

    }
}