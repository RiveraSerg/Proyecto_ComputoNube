package com.mycompany.proyecto;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class MultiClientHandler extends Thread {
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket socket;
    final List<MultiClientHandler> clients;

    public MultiClientHandler(Socket socket, DataInputStream dis, DataOutputStream dos, List<MultiClientHandler> clients) {
        this.dis = dis;
        this.dos = dos;
        this.socket = socket;
        this.clients = clients;
    }

    public void run() {
        String received;
        String clientInfo = "Client " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort();

        try {
            dos.writeUTF("Send msg to Server (type Exit to terminate connection)");

            // Agregar este cliente a la lista de clientes
            synchronized (clients) {
                clients.add(this);
                // Notificar a todos los clientes sobre la cantidad actual de usuarios conectados
                notifyUsersConnected();
            }

            while (true) {
                received = dis.readUTF();
                if (received.equals("Exit")) {
                    break;
                }

                System.out.println(clientInfo + " says: " + received);

                // Transmitir el mensaje a todos los clientes
                broadcast(clientInfo + " says: " + received);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        } finally {
            try {
                // Eliminar este cliente de la lista de clientes
                synchronized (clients) {
                    clients.remove(this);
                    // Notificar a todos los clientes sobre la cantidad actualizada de usuarios conectados
                    notifyUsersConnected();
                }
                socket.close();
                System.out.println(clientInfo + " disconnected.");
            } catch (IOException e) {
                System.out.println("Error while closing the socket: " + e);
            }
        }
    }

    private void broadcast(String message) {
        // Transmitir un mensaje a todos los clientes conectados
        synchronized (clients) {
            for (MultiClientHandler client : clients) {
                try {
                    client.dos.writeUTF(message);
                } catch (IOException e) {
                    System.out.println("Error broadcasting message: " + e);
                }
            }
        }
    }

    private void notifyUsersConnected() {
        // Notificar a todos los clientes sobre la cantidad actual de usuarios conectados
        String userListMessage = "Usuarios Conectados: " + clients.size();
        for (MultiClientHandler client : clients) {
            try {
                client.dos.writeUTF(userListMessage);
            } catch (IOException e) {
                System.out.println("Error notifying users: " + e);
            }
        }
    }
}

public class EchoServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(2555);
            System.out.println("ServerSocket: " + serverSocket);
            List<MultiClientHandler> clients = new ArrayList<>();

            while (true) {
                Socket socket = null;
                try {
                    socket = serverSocket.accept();
                    System.out.println("A new Client is connected: " + socket);

                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                    System.out.println("Assigning new Thread for this Client.");
                    Thread t = new MultiClientHandler(socket, dis, dos, clients);
                    t.start();
                } catch (Exception e) {
                    if (socket != null) {
                        socket.close();
                    }
                    System.out.println("Server Error: " + e);
                }
            }
        } catch (IOException e) {
            System.out.println("Server Error: " + e);
        }
    }
}
