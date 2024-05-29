package com.example.kinoapp.controllers;

import com.example.kinoapp.database.DBManager;
import com.example.kinoapp.database.SeanseDB;
import com.example.kinoapp.tableview.Seanse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScreeningController implements SmallController {
    @FXML
    private TextField id_seansu;
    @FXML
    private ChoiceBox<String> film;
    @FXML
    private DatePicker data;
    @FXML
    private Spinner<Integer> godzina;
    @FXML
    private Spinner<Integer> minuta;
    @FXML
    private ChoiceBox<Long> sala;
    private final boolean editMode;
    private Seanse screening;

    public ScreeningController(boolean editMode) {
        this.editMode = editMode;
    }

    public Seanse getScreening() {
        return screening;
    }

    public void setScreening(Seanse screening) {
        this.screening = screening;
    }

    public void initialize() {
        // Ustawienia spinnerów
        godzina.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 12, 1));
        minuta.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 30, 10));
        godzina.getValueFactory().setWrapAround(true);
        minuta.getValueFactory().setWrapAround(true);

        // Dodanie tytułów filmów do listy combo
        ObservableList<String> titles = FXCollections.observableArrayList();
        DBManager.getFilms().forEach(film -> titles.add(film.getTytul()));
        film.setItems(titles);

        // Dodanie numerów sal do listy combo
        ObservableList<Long> roomNumbers = FXCollections.observableArrayList();
        DBManager.getRooms().forEach(room -> roomNumbers.add(room.getNumer_sali()));
        sala.setItems(roomNumbers);

        if (editMode) {
            id_seansu.setText(Long.toString(screening.getId_seansu()));
            film.setValue(screening.getFilm());
            LocalDateTime dateTime = Timestamp.valueOf(screening.getData_godzina() + ":00").toLocalDateTime();
            data.setValue(dateTime.toLocalDate());
            godzina.getValueFactory().setValue(dateTime.getHour());
            minuta.getValueFactory().setValue(dateTime.getMinute());
            sala.setValue(screening.getSala());
        }
        else {
            long id = DBManager.getNextId("Seanse");
            id_seansu.setText(Long.toString(id));

            if (id_seansu.getText().equals("-1")) {

            }
        }
    }

    public void save(Event event) {

    }

    @Override
    public void cancel(Event event) {
        SmallController.super.cancel(event);
    }
}