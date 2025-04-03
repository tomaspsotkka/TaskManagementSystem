module dk.via.taskmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.postgresql.jdbc;
    requires java.rmi;
    requires java.desktop;
    requires java.sql;
    requires remoteobserver;
    requires org.controlsfx.controls;

    opens dk.via.taskmanagement to javafx.fxml;
    opens dk.via.taskmanagement.view to javafx.fxml;

    opens dk.via.taskmanagement.model to javafx.base;

    exports dk.via.taskmanagement;
    exports dk.via.taskmanagement.shared to java.rmi;
    exports dk.via.taskmanagement.view to javafx.fxml;
}