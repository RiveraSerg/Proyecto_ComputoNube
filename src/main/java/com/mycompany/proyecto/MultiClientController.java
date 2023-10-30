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
        switch (clientId) {
            case 1:
                Circle1.setLayoutY(Circle1.getLayoutY() - 10);
                break;
            case 2:
                Circle2.setLayoutY(Circle2.getLayoutY() - 10);
                break;
            case 3:
                Circle3.setLayoutY(Circle3.getLayoutY() - 10);
                break;
            case 4:
                Circle4.setLayoutY(Circle4.getLayoutY() - 10);
                break;
            default:
                break;
        }
    }

    public void moveDown() {
        switch (clientId) {
            case 1:
                Circle1.setLayoutY(Circle1.getLayoutY() + 10);
                break;
            case 2:
                Circle2.setLayoutY(Circle2.getLayoutY() + 10);
                break;
            case 3:
                Circle3.setLayoutY(Circle3.getLayoutY() + 10);
                break;
            case 4:
                Circle4.setLayoutY(Circle4.getLayoutY() + 10);
                break;
            default:
                break;
        }
    }

    public void moveLeft() {
        switch (clientId) {
            case 1:
                Circle1.setLayoutX(Circle1.getLayoutX() - 10);
                break;
            case 2:
                Circle2.setLayoutX(Circle2.getLayoutX() - 10);
                break;
            case 3:
                Circle3.setLayoutX(Circle3.getLayoutX() - 10);
                break;
            case 4:
                Circle4.setLayoutX(Circle4.getLayoutX() - 10);
                break;
            default:
                break;
        }
    }

    public void moveRight() {
        switch (clientId) {
            case 1:
                Circle1.setLayoutX(Circle1.getLayoutX() + 10);
                break;
            case 2:
                Circle2.setLayoutX(Circle2.getLayoutX() + 10);
                break;
            case 3:
                Circle3.setLayoutX(Circle3.getLayoutX() + 10);
                break;
            case 4:
                Circle4.setLayoutX(Circle4.getLayoutX() + 10);
                break;
            default:
                break;
        }
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
