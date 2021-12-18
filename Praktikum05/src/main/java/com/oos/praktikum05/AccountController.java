package com.oos.praktikum05;

import bank.*;
import bank.exceptions.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class AccountController implements Initializable {

    private final ObservableList<Transaction> transactionsList = FXCollections.observableArrayList();
    private PrivateBank bank;

    @FXML
    public Text text;
    @FXML
    public MenuButton addButton;
    @FXML
    public MenuItem payment;
    @FXML
    public MenuItem incoming;
    @FXML
    public MenuItem outgoing;
    @FXML
    public Parent root;
    @FXML
    public MenuButton optionsButton;
    @FXML
    public MenuItem allTransaction;
    @FXML
    public MenuItem ascending;
    @FXML
    public MenuItem descending;
    @FXML
    public MenuItem positive;
    @FXML
    public MenuItem negative;
    @FXML
    public ListView<Transaction> transactionsListView;
    @FXML
    public Text accountName;
    @FXML
    public Button backButton;

    private void updateListView(List<Transaction> listTransaction) {
        transactionsList.clear();
        transactionsList.addAll(listTransaction);
        transactionsListView.setItems(transactionsList);
    }

    private void setDialogAddTransaction(MenuItem menuItem, String name) {
        Dialog<Transaction> dialog = new Dialog<>();
        dialog.getDialogPane().setMinWidth(350);
        dialog.getDialogPane().setMinHeight(250);
        GridPane gridPane = new GridPane();

        Label date = new Label("Date: ");
        Label description = new Label("Description: ");
        Label amount = new Label("Amount: ");
        Label incomingInterest_sender = new Label();
        Label outgoingInterest_recipient = new Label();

        TextField dateText = new TextField();
        TextField descriptionText = new TextField();
        TextField amountText = new TextField();
        TextField incomingInterest_senderText = new TextField();
        TextField outgoingInterest_recipientText = new TextField();

        gridPane.add(date, 1, 1);
        gridPane.add(dateText, 2, 1);
        gridPane.add(description, 1, 2);
        gridPane.add(descriptionText, 2, 2);
        gridPane.add(amount, 1, 3);
        gridPane.add(amountText, 2, 3);
        gridPane.add(incomingInterest_sender, 1, 4);
        gridPane.add(incomingInterest_senderText, 2, 4);
        gridPane.add(outgoingInterest_recipient, 1, 5);
        gridPane.add(outgoingInterest_recipientText, 2, 5);

        ButtonType okButton = new ButtonType("ADD", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().setContent(gridPane);
        dialog.setResizable(true);
        dialog.getDialogPane().getButtonTypes().add(okButton);

        Alert invalid = new Alert(Alert.AlertType.ERROR);
        dialog.show();
        if (Objects.equals(menuItem.getId(), "payment")) {
            dialog.setTitle("Add new payment");
            dialog.setHeaderText("Add a new payment to account [" + name + "]");

            incomingInterest_sender.setText("Incoming interest: ");
            outgoingInterest_recipient.setText("Outgoing interest: ");

            dialog.setResultConverter(buttonType ->  {
                if (buttonType == okButton) {
                    if (Objects.equals(dateText.getText(), "") ||
                            Objects.equals(descriptionText.getText(),"") ||
                            Objects.equals(amountText.getText(), "") ||
                            Objects.equals(incomingInterest_senderText.getText(), "") ||
                            Objects.equals(outgoingInterest_recipientText.getText(), ""))
                    {
                        invalid.setContentText("Please insert valid value!");
                        Optional<ButtonType> result = invalid.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            text.setText("No new payment was added!");
                        }
                    } else {
                        Payment payment = new Payment(dateText.getText(),
                                descriptionText.getText(),
                                Double.parseDouble(amountText.getText()),
                                Double.parseDouble(incomingInterest_senderText.getText()),
                                Double.parseDouble(outgoingInterest_recipientText.getText()));
                        try {
                            bank.addTransaction(name, payment);
                            text.setText("A new payment is added");
                        } catch (TransactionAlreadyExistException e) {
                            invalid.setContentText("Duplicated payment!");
                            Optional<ButtonType> result = invalid.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                text.setText("This new payment is already in the bank!");
                            }
                            System.out.println(e.getMessage());
                        } catch (AccountDoesNotExistException e) {
                            System.out.println(e.getMessage());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        updateListView(bank.getTransactions(name));
                        accountName.setText(name + " [" + bank.getAccountBalance(name) + "€]");
                    }

                }
                return null;
            });
        }  else {
            incomingInterest_sender.setText("Sender: ");
            outgoingInterest_recipient.setText("Recipient: ");
            if (Objects.equals(menuItem.getId(), "incoming")) {

                dialog.setTitle("Add new incoming transfer");
                dialog.setHeaderText("Add a new incoming transfer to account [" + name + "]");

                dialog.setResultConverter(buttonType -> {
                    if (buttonType == okButton) {
                        if (Objects.equals(dateText.getText(), "") ||
                                Objects.equals(descriptionText.getText(),"") ||
                                Objects.equals(amountText.getText(), "") ||
                                Objects.equals(incomingInterest_senderText.getText(), "") ||
                                Objects.equals(outgoingInterest_recipientText.getText(), ""))
                        {
                            invalid.setContentText("Please insert valid value!");
                            Optional<ButtonType> result = invalid.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                text.setText("No new incoming transfer was added!");
                            }
                        } else {
                            IncomingTransfer incomingTransfer = new IncomingTransfer(dateText.getText(),
                                    descriptionText.getText(),
                                    Double.parseDouble(amountText.getText()),
                                    incomingInterest_senderText.getText(),
                                    outgoingInterest_recipientText.getText());
                            try {
                                bank.addTransaction(name, incomingTransfer);
                                text.setText("A new incoming transfer is added");
                            } catch (TransactionAlreadyExistException e) {
                                invalid.setContentText("Duplicated incoming transfer!");
                                Optional<ButtonType> result = invalid.showAndWait();
                                if (result.isPresent() && result.get() == ButtonType.OK) {
                                    text.setText("This new incoming transfer is already in the bank!");
                                }
                                System.out.println(e.getMessage());
                            } catch (AccountDoesNotExistException e) {
                                System.out.println(e.getMessage());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            updateListView(bank.getTransactions(name));
                            accountName.setText(name + " [" + bank.getAccountBalance(name) + "€]");

                        }

                    }
                    return null;
                });
            } else  {
                dialog.setTitle("Add new outgoing transfer");
                dialog.setHeaderText("Add a new outgoing transfer to account [" + name + "]");

                dialog.setResultConverter(buttonType -> {
                    if (buttonType == okButton) {
                        if (Objects.equals(dateText.getText(), "") ||
                                Objects.equals(descriptionText.getText(),"") ||
                                Objects.equals(amountText.getText(), "") ||
                                Objects.equals(incomingInterest_senderText.getText(), "") ||
                                Objects.equals(outgoingInterest_recipientText.getText(), ""))
                        {
                            invalid.setContentText("Please insert valid value!");
                            Optional<ButtonType> result = invalid.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                text.setText("No new outcoming transfer was added!");
                            }
                        } else {
                            OutgoingTransfer outgoingTransfer = new OutgoingTransfer(dateText.getText(),
                                    descriptionText.getText(),
                                    Double.parseDouble(amountText.getText()),
                                    incomingInterest_senderText.getText(),
                                    outgoingInterest_recipientText.getText());
                            try {
                                bank.addTransaction(name, outgoingTransfer);
                                text.setText("A new outgoing transfer is added");
                            } catch (TransactionAlreadyExistException e) {
                                invalid.setContentText("Duplicated outgoing transfer!");
                                Optional<ButtonType> result = invalid.showAndWait();
                                if (result.isPresent() && result.get() == ButtonType.OK) {
                                    text.setText("This new outgoing transfer is already in the bank!");
                                }
                                System.out.println(e.getMessage());
                            } catch (AccountDoesNotExistException e) {
                                System.out.println(e.getMessage());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            updateListView(bank.getTransactions(name));
                            accountName.setText(name + " [" + bank.getAccountBalance(name) + "€]");
                        }
                    }
                    return null;
                });
            }
        }
    }

    public void setupData(PrivateBank privateBank, String name) {
        bank = privateBank;
        accountName.setText(name + " [" + bank.getAccountBalance(name) + "€]");
        updateListView(bank.getTransactions(name));

        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteTransaction = new MenuItem("Delete transaction");

        contextMenu.getItems().addAll(deleteTransaction);
        transactionsListView.setContextMenu(contextMenu);

        AtomicReference<Transaction> selectedTransaction = new AtomicReference<>();

        transactionsListView.setOnMouseClicked(mouseEvent -> {
            selectedTransaction.set(transactionsListView.getSelectionModel().getSelectedItem());
            System.out.println("[" + selectedTransaction.toString().replace("\n", "]"));
        });
        deleteTransaction.setOnAction(event -> {

            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Delete transaction confirmation");
            confirmation.setContentText("Do you wanna delete this transaction?");
            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    bank.removeTransaction(name, selectedTransaction.get());
                } catch (TransactionDoesNotExistException | IOException e) {
                    e.printStackTrace();
                }
                System.out.println("[" + selectedTransaction.toString().replace("\n", "]") + " is deleted");
                text.setText(selectedTransaction.toString().replace("\n", "]") + " is deleted");
                updateListView(bank.getTransactions(name));
                accountName.setText(name + " [" + bank.getAccountBalance(name) + "€]");
            }
        });


        allTransaction.setOnAction(event -> updateListView(bank.getTransactions(name)));
        ascending.setOnAction(event -> updateListView(bank.getTransactionsSorted(name, true)));
        descending.setOnAction(event -> updateListView(bank.getTransactionsSorted(name, false)));
        positive.setOnAction(event -> updateListView(bank.getTransactionsByType(name, true)));
        negative.setOnAction(event -> updateListView(bank.getTransactionsByType(name, false)));

        payment.setOnAction(event -> setDialogAddTransaction(payment, name));
        incoming.setOnAction(event -> setDialogAddTransaction(incoming, name));
        outgoing.setOnAction(event -> setDialogAddTransaction(outgoing, name));



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnMouseClicked(mouseEvent -> {
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main-view.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });
    }
}
