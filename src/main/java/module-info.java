module com.example.rezerwacjabiletow {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.rezerwacjabiletow to javafx.fxml;
    exports com.example.rezerwacjabiletow;
}