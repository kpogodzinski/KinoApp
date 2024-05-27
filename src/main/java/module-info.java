module com.example.rezerwacjabiletow {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;


    opens com.example.kinoapp to javafx.fxml;
    exports com.example.kinoapp;
    exports com.example.kinoapp.database;
    opens com.example.kinoapp.database to javafx.fxml;
}