package com.example.kinoapp.controllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public interface SmallController {
    @FXML
    void initialize();

    @FXML
    void save(Event event);

    @FXML
    default void cancel(Event event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
}
