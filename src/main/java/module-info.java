module com.mycompany.proyecto {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.base;

    opens com.mycompany.proyecto to javafx.fxml;
    exports com.mycompany.proyecto;
}
