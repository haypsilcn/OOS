package bank;

import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.TransactionAlreadyExistException;
import bank.exceptions.TransactionDoesNotExistException;

import javax.swing.*;
import java.util.*;

public class PrivateBank implements Bank {

    /**
     * Represents the name of private Bank
     */
    private String name;

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
    private Map<String, List<Transaction>> accountsToTransaction =  new HashMap<String, List<Transaction>>();



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


    /**
     * Constructor with three attribute
     */
    public PrivateBank(String newName, double newIncomingInterest, double newOutcomingInterest) {
        this.name = newName;
        this.incomingInterest = newIncomingInterest;
        this.outcomingInterest = newOutcomingInterest;

        accountsToTransaction.put("", null);
        accountsToTransaction.put("testing acc 1", List.of(
                new Transaction("12.12.2000", "Des", 212) {
                },
                new Transaction("21.04.3244", "Des1", 33) {
                }
        ));
        accountsToTransaction.put("testing acc 2", null);
    }

    /**
     * Copy Constructor
     */
    public PrivateBank(PrivateBank newPrivateBank) {
        this(newPrivateBank.name, newPrivateBank.incomingInterest, newPrivateBank.outcomingInterest);
    }

    /**
     *
     * @return contents of all attributes
     */
    @Override
    public String toString() {
        return "Name: " + name + ", Incoming Interest: " + incomingInterest + ", Outcoming Interest: " + outcomingInterest + ", " + accountsToTransaction.toString();
    }


    public boolean equals(Object obj) {
        if (obj instanceof PrivateBank privateBank)
            return (super.equals(privateBank) && incomingInterest == privateBank.incomingInterest && outcomingInterest == privateBank.outcomingInterest && accountsToTransaction.equals(privateBank.accountsToTransaction));
        return false;
    }

    /**
     * Adds an account to the bank. If the account already exists, an exception is thrown.
     *
     * @param account the account to be added
     * @throws AccountAlreadyExistsException if the account already exists
     */
    @Override
    public void createAccount(String account) throws AccountAlreadyExistsException {
        if (accountsToTransaction.containsKey(account))
            throw new AccountAlreadyExistsException("Error: Account already exists!");
        else
            accountsToTransaction.put(account, null);
    }

    /**
     * Adds an account (with all specified transactions) to the bank. If the account already exists,
     * an exception is thrown.
     *
     * @param account      the account to be added
     * @param transactions
     * @throws AccountAlreadyExistsException if the account already exists
     */
    @Override
    public void createAccount(String account, List<Transaction> transactions) throws AccountAlreadyExistsException {

    }

    /**
     * Adds a transaction to an account. If the specified account does not exist, an exception is
     * thrown. If the transaction already exists, an exception is thrown.
     *
     * @param account     the account to which the transaction is added
     * @param transaction the transaction which is added to the account
     * @throws TransactionAlreadyExistException if the transaction already exists
     */
    @Override
    public void addTransaction(String account, Transaction transaction) throws TransactionAlreadyExistException, AccountDoesNotExistException {

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

    }

    /**
     * Checks whether the specified transaction for a given account exists.
     *
     * @param account     the account from which the transaction is checked
     * @param transaction the transaction which is added to the account
     */
    @Override
    public boolean containsTransaction(String account, Transaction transaction) {
        return false;
    }

    /**
     * Calculates and returns the current account balance.
     *
     * @param account the selected account
     * @return the current account balance
     */
    @Override
    public double getAccountBalance(String account) {
        return 0;
    }

    /**
     * Returns a list of transactions for an account.
     *
     * @param account the selected account
     * @return the list of transactions
     */
    @Override
    public List<Transaction> getTransactions(String account) {
        return null;
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
        return null;
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
        return null;
    }
}
