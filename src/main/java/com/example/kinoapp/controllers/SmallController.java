package com.example.kinoapp.controllers;

import com.example.kinoapp.MyAlert;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public interface SmallController {
    @FXML
    void initialize();

    default boolean checkIdError(TextField field) {
        if (field.getText().equals("-1")) {
            new MyAlert(MyAlert.MyAlertType.ERROR, "Błąd pobierania numeru ID. Spróbuj ponownie " +
                    "lub skontaktuj się z administratorem bazy.");
            cancel(new Event(field, null, Event.ANY));
            return true;
        }
        return false;
    }

    @FXML
    void save(Event event);

    @FXML
    default void cancel(Event event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
}
