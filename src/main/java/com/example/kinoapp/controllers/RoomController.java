package com.example.kinoapp.controllers;

import com.example.kinoapp.database.DBManager;
import com.example.kinoapp.database.SaleDB;
import com.example.kinoapp.tableview.Sale;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.crypto.spec.PSource;

public class RoomController {
    @FXML
    private TextField numer_sali;
    @FXML
    private TextField pojemnosc;

    private final boolean editMode;

    private Sale room;

    public RoomController(boolean editMode) {
        this.editMode = editMode;
    }

    public Sale getRoom() {
        return room;
    }

    public void setRoom(Sale room) {
        this.room = room;
    }

    @FXML
    private void initialize() {
        if (editMode) {
            numer_sali.setText(Long.toString(room.getNumer_sali()));
            pojemnosc.setText(Integer.toString(room.getPojemnosc()));
        }
        else {
            long id = DBManager.getNextId("Sale");
            numer_sali.setText(Long.toString(id));

            if (numer_sali.getText().equals("-1")) {

            }
        }
    }

    @FXML
    private void save(Event event) {
        SaleDB s = new SaleDB();
        s.setNumer_sali(Long.parseLong(numer_sali.getText()));
        try {
            s.setPojemnosc(Integer.parseInt(pojemnosc.getText()));
            if (s.getPojemnosc() < 0)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            s.setPojemnosc(0);
        }
        DBManager.update(s);
        cancel(event);
    }
    @FXML
    private void cancel(Event event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
}
