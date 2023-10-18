package com.mycompany.proyecto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Crear e iniciar dos instancias de MultiClient
        MultiClient client1 = new MultiClient();
        MultiClient client2 = new MultiClient();
        client1.start(primaryStage);
        client2.start(new Stage());
    }

    public static void main(String[] args) {
        launch(args);
    }
}