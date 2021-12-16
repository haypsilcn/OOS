package com.oos.praktikum05;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main-view.fxml")));
        Scene scene = new Scene(fxmlLoader);
        primaryStage.setTitle("MainViewApplication");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
