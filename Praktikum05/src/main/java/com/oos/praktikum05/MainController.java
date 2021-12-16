package com.oos.praktikum05;

import bank.PrivateBank;
import bank.Transaction;
import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.AccountDoesNotExistException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class MainController implements Initializable {

    private Stage stage;
    private Scene scene;
    private final ObservableList<String> accountList = FXCollections.observableArrayList();
    private final PrivateBank globalBank = new PrivateBank("Global Bank", "GlobalBank", 0.12, 0.09);

    @FXML
    private Text text;

    @FXML
    private Button addButton;

    @FXML
    private ListView<String> accountsListView;

    @FXML
    private Parent root;


    private void updateListView() {
        accountList.clear();
        accountList.addAll(globalBank.getAllAccounts());
        accountList.sort(Comparator.naturalOrder());
        accountsListView.setItems(accountList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        updateListView();

        ContextMenu contextMenu = new ContextMenu();
        MenuItem viewAccount = new MenuItem("View account");
        MenuItem deleteAccount = new MenuItem("Delete account");

        contextMenu.getItems().addAll(viewAccount, deleteAccount);
        accountsListView.setContextMenu(contextMenu);

        AtomicReference<String> selectedAccount = new AtomicReference<>();

        accountsListView.setOnMouseClicked(mouseEvent -> {
            selectedAccount.set(String.valueOf(accountsListView.getSelectionModel().getSelectedItems()));
            String account = selectedAccount.toString().replace("[", "").replace("]", "");
            System.out.println(selectedAccount + " is selected");
            text.setText("Account " + selectedAccount + " is selected");

            // goes to AccountView if double-click on item
            if (mouseEvent.getClickCount() == 2)
                try {
                    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("account-view.fxml")));
                    root = loader.load();

                    AccountController accountController = loader.getController();
                    accountController.setupData(globalBank, account);

                    stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        });


        deleteAccount.setOnAction(event -> {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Delete account confirmation");
            confirmation.setContentText("Do you wanna delete this account?");
            Optional<ButtonType> result = confirmation.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    globalBank.deleteAccount(selectedAccount.toString().replace("[", "").replace("]", ""));
                } catch (AccountDoesNotExistException e) {
                    System.out.println(e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println(selectedAccount + " is deleted");
                text.setText(selectedAccount + " is deleted");
                updateListView();
            }
        });

        viewAccount.setOnAction(event -> {
            stage = (Stage) root.getScene().getWindow();
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("account-view.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            scene = new Scene(root);
            stage.setTitle("AccountView");
            stage.setScene(scene);
            stage.show();
        });

        addButton.setOnMouseClicked(event -> {
            text.setText("");
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Add new account");
            dialog.setHeaderText("Add a new account to bank");
            dialog.getDialogPane().setMinWidth(300);


            Label nameLabel = new Label("Name: ");
            TextField nameTextFiel = new TextField();

            GridPane grid = new GridPane();
            grid.add(nameLabel, 2, 1);
            grid.add(nameTextFiel, 3, 1);
            dialog.getDialogPane().setContent(grid);
            dialog.setResizable(true);

            ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

            dialog.setResultConverter(buttonType -> {
                if (buttonType == buttonTypeOk) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);

                    if (!Objects.equals(nameTextFiel.getText(), "")) {

                        try {
                            globalBank.createAccount(nameTextFiel.getText());
                            text.setText("Account [" + nameTextFiel.getText() + "] is added to the bank");
                        } catch (AccountAlreadyExistsException | IOException e) {
                            alert.setContentText("Duplicated account!");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                text.setText("Account [" + nameTextFiel.getText() + "] is already in the bank!");
                            }
                            System.out.println(e.getMessage());
                        }
                       updateListView();
                    }
                    else {
                        alert.setContentText("Please insert a valid name!");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            text.setText("No new account was added!");
                        }
                    }

                }
                return null;
            });

            dialog.show();
        });
    }

}