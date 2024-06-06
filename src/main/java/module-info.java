module com.example.kinoapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires java.sql;
    requires org.hibernate.orm.core;


    opens com.example.kinoapp to javafx.fxml;
    exports com.example.kinoapp;
    exports com.example.kinoapp.database;
    exports com.example.kinoapp.tableview;
    exports com.example.kinoapp.controllers;
    opens com.example.kinoapp.database to javafx.base, org.hibernate.orm.core;
    opens com.example.kinoapp.tableview to javafx.base;
    opens com.example.kinoapp.controllers to javafx.fxml;
}