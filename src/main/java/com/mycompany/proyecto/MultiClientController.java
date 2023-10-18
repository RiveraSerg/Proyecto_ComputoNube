package com.mycompany.proyecto;

import javafx.fxml.FXML;
import javafx.scene.shape.Circle;

public class MultiClientController {
    @FXML
    private Circle Circle1;
    @FXML
    private Circle Circle2;
    @FXML
    private Circle Circle3;
    @FXML
    private Circle Circle4;
    private int clientId; // ID del cliente actual

    public void moveUp() {
        if (clientId == 1) {
            Circle1.setLayoutY(Circle1.getLayoutY() - 10);
        } else if (clientId == 2) {
            Circle2.setLayoutY(Circle2.getLayoutY() - 10);
        } else if (clientId == 3) {
            Circle3.setLayoutY(Circle3.getLayoutY() - 10);
        } else if (clientId == 4) {
            Circle4.setLayoutY(Circle4.getLayoutY() - 10);
        }
    }

    public void moveDown() {
        if (clientId == 1) {
            Circle1.setLayoutY(Circle1.getLayoutY() + 10);
        } else if (clientId == 2) {
            Circle2.setLayoutY(Circle2.getLayoutY() + 10);
        } else if (clientId == 3) {
            Circle3.setLayoutY(Circle3.getLayoutY() + 10);
        } else if (clientId == 4) {
            Circle4.setLayoutY(Circle4.getLayoutY() + 10);
        }
    }

    public void moveLeft() {
        if (clientId == 1) {
            Circle1.setLayoutX(Circle1.getLayoutX() - 10);
        } else if (clientId == 2) {
            Circle2.setLayoutX(Circle2.getLayoutX() - 10);
        } else if (clientId == 3) {
            Circle3.setLayoutX(Circle3.getLayoutX() - 10);
        } else if (clientId == 4) {
            Circle4.setLayoutX(Circle4.getLayoutX() - 10);
        }
    }

    public void moveRight() {
        if (clientId == 1) {
            Circle1.setLayoutX(Circle1.getLayoutX() + 10);
        } else if (clientId == 2) {
            Circle2.setLayoutX(Circle2.getLayoutX() + 10);
        } else if (clientId == 3) {
            Circle3.setLayoutX(Circle3.getLayoutX() + 10);
        } else if (clientId == 4) {
            Circle4.setLayoutX(Circle4.getLayoutX() + 10);
        }
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void moveRemote(int clientId, String direction) {
        // Aplicar el movimiento remoto a la figura correspondiente
        if (this.clientId == clientId) {
            if (direction.equals("UP")) {
                Circle1.setLayoutY(Circle1.getLayoutY() - 10);
            } else if (direction.equals("DOWN")) {
                Circle1.setLayoutY(Circle1.getLayoutY() + 10);
            } else if (direction.equals("LEFT")) {
                Circle1.setLayoutX(Circle1.getLayoutX() - 10);
            } else if (direction.equals("RIGHT")) {
                Circle1.setLayoutX(Circle1.getLayoutX() + 10);
            }
        }
    }
}
