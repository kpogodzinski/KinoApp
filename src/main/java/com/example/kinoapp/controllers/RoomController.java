package com.example.kinoapp.controllers;

import com.example.kinoapp.MyAlert;
import com.example.kinoapp.database.DBManager;
import com.example.kinoapp.database.SaleDB;
import com.example.kinoapp.tableview.Sale;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RoomController implements SmallController {
    @FXML
    private TextField numer_sali;
    @FXML
    private TextField pojemnosc;

    private final boolean editMode;

    private Sale room;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;
    public RoomController(boolean editMode) {
        this.editMode = editMode;
    }

    public void setRoom(Sale room) {
        this.room = room;
    }

    @FXML
    public void initialize() {
        saveBtn.setOnAction(this::save);
        cancelBtn.setOnAction(this::cancel);
        if (editMode) {
            numer_sali.setText(Long.toString(room.getNumer_sali()));
            pojemnosc.setText(Integer.toString(room.getPojemnosc()));
        }
        else {
            long id = DBManager.getNextId("Sale");
            numer_sali.setText(Long.toString(id));
        }
    }

    @FXML
    public void save(Event event) {
        if (checkIdError(numer_sali))
            return;
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
            new MyAlert(MyAlert.MyAlertType.WARNING, "Pole Pojemność zawiera niedozwolone znaki.");
        } catch (IllegalArgumentException e) {
            new MyAlert(MyAlert.MyAlertType.WARNING, "Pola nie mogą być puste.");
        }
    }
}
