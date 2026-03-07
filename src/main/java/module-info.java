module com.sevro.escriturarapida2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;


    opens com.sevro.escriturarapida2 to javafx.fxml;
    opens com.sevro.escriturarapida2.controller to javafx.fxml;
    opens com.sevro.escriturarapida2.model to javafx.fxml;

    exports com.sevro.escriturarapida2;
}