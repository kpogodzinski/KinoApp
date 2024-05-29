package com.example.kinoapp.controllers;

import com.example.kinoapp.database.DBManager;
import com.example.kinoapp.database.SaleDB;
import com.example.kinoapp.tableview.Sale;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class RoomController implements SmallController {
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
    public void initialize() {
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
    public void save(Event event) {
        try {
            SaleDB s = new SaleDB();
            s.setNumer_sali(Long.parseLong(numer_sali.getText()));
            if (pojemnosc.getText().isEmpty())
                throw new IllegalArgumentException();
            s.setPojemnosc(Integer.parseInt(pojemnosc.getText()));
            if (s.getPojemnosc() <= 0)
                throw new NumberFormatException();
            DBManager.update(s);
            cancel(event);
        } catch (NumberFormatException e) {
            Alert error = new Alert(Alert.AlertType.NONE, "Pole Pojemność zawiera niedozwolone znaki.", ButtonType.OK);
            error.showAndWait();
        } catch (IllegalArgumentException e) {
            Alert error = new Alert(Alert.AlertType.NONE, "Pola nie mogą być puste.", ButtonType.OK);
            error.showAndWait();
        }
    }

    @Override
    public void cancel(Event event) {
        SmallController.super.cancel(event);
    }
}
