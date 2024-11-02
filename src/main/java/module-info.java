module org.example.controller.clothingstore {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires mysql.connector.j;


    opens edu.icet.Controller to javafx.fxml;
    opens edu.icet.View to javafx.fxml;
    opens edu.icet.Model to javafx.base;
    exports edu.icet.Controller;
    exports db;
    opens db to javafx.fxml;
}
