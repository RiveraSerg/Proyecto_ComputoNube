package com.mycompany.proyecto;

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
import javafx.application.Application;

public class MultiClient extends Application {
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private MultiClientController controller;
    private int clientId;
    String directionRemote;
    int targetClientId;
   

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

            
            
            // Configurar el controlador de teclado para las flechas direccionales
            scene.addEventHandler(KeyEvent.KEY_PRESSED, (keyEvent) -> {
                String direction = "";
                if (keyEvent.getCode() == KeyCode.UP) {
                    controller.setClientId(clientId);
                    direction = "UP";
                    controller.moveUp();
                } else if (keyEvent.getCode() == KeyCode.DOWN) {
                    controller.setClientId(clientId);
                    direction = "DOWN";
                    controller.moveDown();
                } else if (keyEvent.getCode() == KeyCode.LEFT) {
                    controller.setClientId(clientId);
                    direction = "LEFT";
                    controller.moveLeft();
                } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                    controller.setClientId(clientId);
                    direction = "RIGHT";
                    controller.moveRight();
                }

                if (!direction.isEmpty()) {
                    try {
                        dos.writeUTF(clientId + " " + direction);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            
            Thread receiveThread = new Thread(() -> {
                try {
                    while (true) {
                        String serverMessage = dis.readUTF();
                        processServerMessage(serverMessage);
                    }
                } catch (Exception e) {
                    System.out.println("Error receiving messages from server: " + e);
                }
            });
            receiveThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void processServerMessage(String message) {
    System.out.println("Received message from server: " + message);
    String[] parts = message.split(" ");
    if (parts.length >= 5) {
        try {
            int targetClientId = Integer.parseInt(parts[3]);
            String directionRemote = parts[4];
            
            System.out.println("directionRemote: " + directionRemote);
            System.out.println("targetClientId: " + targetClientId);
            
            if(targetClientId != clientId)
            {
            controller.setClientId(targetClientId);
                switch (directionRemote) {
                    case "UP":
                        controller.moveUp();
                        break;
                    case "DOWN":
                        controller.moveDown();
                        break;
                    case "LEFT":
                        controller.moveLeft();
                        break;
                    case "RIGHT":
                        controller.moveRight();
                        break;
                    default:
                        break;
                }
            }
            // Verificar si el targetClientId coincide con el cliente actual
           /*if(targetClientId != clientId){
              if (directionRemote == "UP") {
                    controller.moveUpR(targetClientId);
                } else if (directionRemote == "DOWN") {
                    controller.moveDownR(targetClientId);
                } else if (directionRemote == "LEFT") {
                    controller.moveLeftR(targetClientId);
                } else if (directionRemote == "RIGHT") {
                    controller.moveRightR(targetClientId);
                }
           }
            */
            } catch (NumberFormatException e) {
                // Manejar errores de conversión de números
                System.out.println("Error parsing target client ID: " + e);
            }
        }
    }

}
