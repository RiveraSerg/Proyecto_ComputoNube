package com.mycompany.proyecto;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class MultiClient extends Application {
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    @Override
    public void start(Stage primaryStage) {
        try {
            InetAddress ip = InetAddress.getByName("localhost");
            socket = new Socket(ip, 2555);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            primaryStage.setTitle("MultiClient");

            StackPane root = new StackPane();
            Scene scene = new Scene(root, 300, 200);

            scene.addEventHandler(KeyEvent.KEY_PRESSED, (keyEvent) -> {
                if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.DOWN ||
                        keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.RIGHT) {
                    try {
                        dos.writeUTF("Arrow Key Pressed: " + keyEvent.getCode());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}