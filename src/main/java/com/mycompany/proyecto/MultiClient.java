package com.mycompany.proyecto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private MultiClientController controller;
    private int clientId;

    @Override
    public void start(Stage primaryStage) {
        try {
            InetAddress ip = InetAddress.getByName("localhost");
            socket = new Socket(ip, 2555);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            // Leer y descartar el primer mensaje del servidor
            String initialMessage = dis.readUTF();

            // Leer el ID del cliente asignado por el servidor
            String clientIdString = dis.readUTF();
            clientId = Integer.parseInt(clientIdString);
        
            // Cargar la ventana controlada por el usuario desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MultiClient.fxml"));
            Parent root = loader.load();
            controller = loader.getController();

            // Configurar la escena y mostrar la ventana
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setScene(scene);
            primaryStage.show();

            
            controller.setClientId(clientId);
            // Configurar el controlador de teclado para las flechas direccionales
            scene.addEventHandler(KeyEvent.KEY_PRESSED, (keyEvent) -> {
                String direction = "";
                if (keyEvent.getCode() == KeyCode.UP) {
                    direction = "UP";
                    controller.moveUp();
                } else if (keyEvent.getCode() == KeyCode.DOWN) {
                    direction = "DOWN";
                    controller.moveDown();
                } else if (keyEvent.getCode() == KeyCode.LEFT) {
                    direction = "LEFT";
                    controller.moveLeft();
                } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                    direction = "RIGHT";
                    controller.moveRight();
                }

                if (!direction.isEmpty() && clientId > 10) {
                    controller.moveRemote(clientId, direction); // Llama al método del controlador para mover la figura
                    try {
                        // Envia la dirección al servidor
                        dos.writeUTF(clientId+": "+direction);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
