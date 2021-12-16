package bank;

import com.google.gson.*;
import bank.exceptions.*;

import java.io.*;
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
    private double outgoingInterest;

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


    public void setOutgoingInterest(double outgoingInterest) {
        this.outgoingInterest = outgoingInterest;
    }

    public double getOutgoingInterest() {
        return outgoingInterest;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setFullPath(String directoryName, boolean copiedBankPath) {
        if (copiedBankPath)
            fullPath = "data/_CopiedPrivateBanks/" + directoryName;
        else
            fullPath = "data/" + directoryName;
    }

    public String getFullPath() {
        return fullPath;
    }


    /**
     * Constructor with four attributes
     */
    public PrivateBank(String newName, String newDirectoryName, double newIncomingInterest, double newOutgoingInterest) {
        this.name = newName;
        this.directoryName = newDirectoryName;
        this.incomingInterest = newIncomingInterest;
        this.outgoingInterest = newOutgoingInterest;

        setFullPath(newDirectoryName, false);

        try {
            Path path = Paths.get(fullPath);

            if (Files.notExists(path)) {
                Files.createDirectories(path);
                System.out.println("\nDirectory for " + PrivateBank.this.getName() + " is created!");
            }
            else {
                System.out.println("\nDirectory for " + PrivateBank.this.getName() + " is already exist!");
                System.out.println("=> Start reading account(s) from directory to " + PrivateBank.this.getName() + ":");
                readAccounts();
                System.out.println("FINISHED reading account(s) for " + PrivateBank.this.getName() + "\n");
            }
        } catch (IOException | AccountAlreadyExistsException e) {
            System.out.println("Failed to create directory for " + PrivateBank.this.getName() + "!");
        }


    }

    /**
     * Copy Constructor
     */
    public PrivateBank(PrivateBank newPrivateBank) throws AccountAlreadyExistsException {
        this(newPrivateBank.name, newPrivateBank.directoryName, newPrivateBank.incomingInterest, newPrivateBank.outgoingInterest);
        this.accountsToTransactions = newPrivateBank.accountsToTransactions;

        setFullPath(newPrivateBank.directoryName, true);

        try {
            Path path = Paths.get(fullPath);

            if (Files.notExists(path)) {
                Files.createDirectories(path);
                System.out.println("\nDirectory for copied " + newPrivateBank.getName() + " is created!");
            }
            else {
                System.out.println("\nDirectory for copied " + newPrivateBank.getName() + " is already exist!");
                System.out.println("=> Start adding account(s) from directory to copied " + newPrivateBank.getName() + ":");
                readAccounts();
                System.out.println("FINISHED reading account(s) for copied " + newPrivateBank.getName() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Failed to create directory for copied " + newPrivateBank.getName() + "!");
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
        return "Name: " + name + "\nIncoming Interest: " + incomingInterest + "\nOutgoing Interest: " + outgoingInterest + "\n" + str;
    }

    public boolean equals(Object obj) {
        if (obj instanceof PrivateBank privateBank)
            return (name.equals(privateBank.name) && incomingInterest == privateBank.incomingInterest && outgoingInterest == privateBank.outgoingInterest && accountsToTransactions.equals(privateBank.accountsToTransactions));
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
        Path path = Path.of(PrivateBank.this.getFullPath() + "/" + account + ".json");

        if (Files.exists(path)) {
            System.out.print("\nAdding <" + account + "> from the data system to bank <" + name + "> " );
            if (accountsToTransactions.containsKey(account))
                throw new AccountAlreadyExistsException("=> FAILED! ACCOUNT <" + account + "> ALREADY EXISTS!\n");
            else {
                accountsToTransactions.put(account, List.of());
                System.out.println("=> SUCCESS!");
            }
        }
        else {
            System.out.print("\nCreating new account <" + account + "> to bank <" + name + "> ");
            accountsToTransactions.put(account, List.of());
            writeAccount(account);
            System.out.println("=> SUCCESS!");
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
        Path path = Path.of(PrivateBank.this.getFullPath() + "/" + account + ".json");
        String transactionsString = transactions.toString().replaceAll("[]]|[\\[]", "").replace("\n, ", "\n\t\t");

        if (Files.exists(path)) {
            System.out.print("\nAdding <" + account + "> from the data system to bank <" + name + "> with transactions list: \n\t\t" + transactionsString);
            if (accountsToTransactions.containsKey(account))
                throw new AccountAlreadyExistsException("=> FAILED! ACCOUNT <" + account + "> ALREADY EXISTS!\n");
            else {
                for (Transaction valueOfTransactions : transactions)
                    if (valueOfTransactions instanceof Payment payment) {
                        payment.setIncomingInterest(PrivateBank.this.incomingInterest);
                        payment.setOutgoingInterest(PrivateBank.this.outgoingInterest);
                    }
                accountsToTransactions.put(account, transactions);
                System.out.println("=> SUCCESS!");
            }
        } else {
            System.out.print("\nCreating new account <" + account + "> to bank <" + name + "> with transactions list: \n\t\t" + transactionsString);
            for (Transaction valueOfTransactions : transactions)
                if (valueOfTransactions instanceof Payment payment) {
                    payment.setIncomingInterest(PrivateBank.this.incomingInterest);
                    payment.setOutgoingInterest(PrivateBank.this.outgoingInterest);
                }
            accountsToTransactions.put(account, transactions);
            writeAccount(account);
            System.out.println("=> SUCCESS!");
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
    public void addTransaction(String account, Transaction transaction) throws TransactionAlreadyExistException, AccountDoesNotExistException, IOException {
        System.out.println("Adding new transaction <" + transaction.toString().replace("\n", "") + "> to account <" + account + "> in bank <" + name + ">");
        if (!accountsToTransactions.containsKey(account))
            throw new AccountDoesNotExistException("=> FAILED! ACCOUNT <" + account + "> DOES NOT EXISTS!\n");
        else {
            if (containsTransaction(account, transaction))
                throw new TransactionAlreadyExistException("=> FAILED! THIS TRANSACTION ALREADY EXISTS!\n");
            else {
                List<Transaction> transactionsList = new ArrayList<>(accountsToTransactions.get(account));
                transactionsList.add(transaction);
                accountsToTransactions.put(account, transactionsList);
                writeAccount(account);
                System.out.println("=> SUCCESS!");
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
    public void removeTransaction(String account, Transaction transaction) throws TransactionDoesNotExistException, IOException {
        System.out.println("Removing transaction <" + transaction.toString().replace("\n", "") + "> from account <" + account + "> in bank <" + name + ">");
        if (!containsTransaction(account, transaction))
            throw new TransactionDoesNotExistException("=> FAILED! THIS TRANSACTION DOES NOT EXISTS!\n");
        else {
            List<Transaction> transactionsList = new ArrayList<>(accountsToTransactions.get(account));
            transactionsList.remove(transaction);
            accountsToTransactions.put(account, transactionsList);
            writeAccount(account);
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
            payment.setOutgoingInterest(PrivateBank.this.outgoingInterest);
        }
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
    private void writeAccount(String account) throws IOException {
        FileWriter file = new FileWriter(getFullPath() + "/" + account + ".json");
        try  {
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
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        file.close();
    }

    /**
     * read all existing accounts from data system and make them available in PrivateBank object
     * @throws AccountAlreadyExistsException
     * @throws IOException
     */
    private void readAccounts() throws AccountAlreadyExistsException, IOException {

        final File folder = new File(PrivateBank.this.getFullPath());
        final File[] listOfFiles = Objects.requireNonNull(folder.listFiles());

        for (File file : listOfFiles) {
            String accountName = file.getName().replace(".json", "");
            String accountNameFile = file.getName();
            PrivateBank.this.createAccount(accountName);

            try {

                Reader reader = new FileReader(PrivateBank.this.getFullPath() + "/" + accountNameFile);
                JsonArray parser = JsonParser.parseReader(reader).getAsJsonArray();
                for (JsonElement jsonElement : parser.getAsJsonArray()) {

                    JsonObject jsonObject = jsonElement.getAsJsonObject();

                    Gson customGson = new GsonBuilder()
                            .registerTypeAdapter(Transaction.class, new CustomDe_Serializer())
                            .create();

                    String str = customGson.toJson(jsonObject.get("INSTANCE"));

                    if (jsonObject.get("CLASSNAME").getAsString().equals("Payment")) {
                        Payment payment = customGson.fromJson(str, Payment.class);
                        PrivateBank.this.addTransaction(accountName, payment);
                    }
                    else if (jsonObject.get("CLASSNAME").getAsString().equals("IncomingTransfer")) {
                        IncomingTransfer incomingTransfer = customGson.fromJson(str, IncomingTransfer.class);
                        PrivateBank.this.addTransaction(accountName, incomingTransfer);
                    }
                    else {
                        OutgoingTransfer outgoingTransfer = customGson.fromJson(str, OutgoingTransfer.class);
                        PrivateBank.this.addTransaction(accountName, outgoingTransfer);
                    }

                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TransactionAlreadyExistException | AccountDoesNotExistException e) {
                System.out.println(e);
            }

        }
    }

    /**
     * delete an account in accountsToTransactions and in data system
     * @param account account to be deleted
     * @throws AccountDoesNotExistException when account is not valid
     */
    public void deleteAccount(String account) throws AccountDoesNotExistException, IOException {
        System.out.print("\nDelete account <" + account + "> from bank <" + this.getName() + "> ");
        if (!accountsToTransactions.containsKey(account))
            throw new AccountDoesNotExistException("=> FAILED! ACCOUNT <" + account + "> DOES NOT EXISTS!\n");
        else {
            accountsToTransactions.remove(account);
            Path path = Path.of(this.getFullPath() + "/" + account + ".json");
            Files.deleteIfExists(path);
            System.out.println("=> SUCCESS!");
        }
    }

    /**
     * show all accounts in accountsToTransactions
     * @return a list of all accounts
     */
    public List<String> getAllAccounts() {
        Set<String> setKey = accountsToTransactions.keySet();
        return new ArrayList<>(setKey);
    }

}