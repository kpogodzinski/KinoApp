package com.example.kinoapp;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;

public class MyAlert extends Alert {
    public enum MyAlertType {ERROR, CONFIRM, INFO, WARNING}
    public MyAlert(MyAlert.MyAlertType type, String message) {
        super(AlertType.NONE, message);
        this.getButtonTypes().clear();
        this.getDialogPane().setStyle("-fx-font-family: Cambria");
        if (type == MyAlertType.ERROR) {
            this.getButtonTypes().add(new ButtonType("Zamknij"));
            this.setTitle("Błąd");
            this.getDialogPane().setGraphic(new ImageView(String.valueOf(MyAlert.class.getResource("images/error.png"))));
        }
        if (type == MyAlertType.CONFIRM) {
            this.getButtonTypes().add(new ButtonType("Tak"));
            this.getButtonTypes().add(new ButtonType("Nie"));
            this.setTitle("Potwierdzenie");
            this.getDialogPane().setGraphic(new ImageView(String.valueOf(MyAlert.class.getResource("images/confirm.png"))));
        }
        if (type == MyAlertType.INFO) {
            this.getButtonTypes().add(new ButtonType("OK"));
            this.setTitle("Informacja");
            this.getDialogPane().setGraphic(new ImageView(String.valueOf(MyAlert.class.getResource("images/info.png"))));
        }
        if (type == MyAlertType.WARNING) {
            this.getButtonTypes().add(new ButtonType("Zamknij"));
            this.setTitle("Ostrzeżenie");
            this.getDialogPane().setGraphic(new ImageView(String.valueOf(MyAlert.class.getResource("images/warning.png"))));
        }
        this.showAndWait();
    }
}
