package com.example.kinoapp.controllers;

import com.example.kinoapp.MyAlert;
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
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;

    public ScreeningController(boolean editMode) {
        this.editMode = editMode;
    }

    public void setScreening(Seanse screening) {
        this.screening = screening;
    }

    public void initialize() {
        // Ustawienie domyślnej daty na dzisiejszą
        data.setValue(LocalDate.now());

        // Ustawienia spinnerów
        godzina.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 12, 1));
        minuta.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 30, 10));
        godzina.getValueFactory().setWrapAround(true);
        minuta.getValueFactory().setWrapAround(true);

        // Dodanie posortowanych tytułów filmów do listy combo
        ObservableList<String> filmTitles = FXCollections.observableArrayList();
        DBManager.getFilms().forEach(film -> filmTitles.add(film.getTytul()));
        filmTitles.sort(String::compareToIgnoreCase);
        film.setItems(filmTitles);

        // Dodanie posortowanych numerów sal do listy combo
        ObservableList<Long> roomNumbers = FXCollections.observableArrayList();
        DBManager.getRooms().forEach(room -> roomNumbers.add(room.getNumer_sali()));
        roomNumbers.sort(Long::compare);
        sala.setItems(roomNumbers);

        saveBtn.setOnAction(this::save);
        cancelBtn.setOnAction(this::cancel);
        if (editMode) {
            saveBtn.setOnAction(this::save);
            cancelBtn.setOnAction(this::cancel);
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
        }
    }

    public void save(Event event)  {
        if (checkIdError(id_seansu))
            return;
        try {
            SeanseDB s = new SeanseDB();
            s.setId_seansu(Long.parseLong(id_seansu.getText()));
            s.setData_godzina(Timestamp.valueOf(data.getValue().toString() + " " + godzina.getValue()
                    + ":" + minuta.getValue() + ":00"));
            if (film.getValue().isEmpty())
                throw new NullPointerException();
            DBManager.getFilms().forEach(film -> {
                if (film.getTytul().equals(this.film.getValue()))
                    s.setFilm(film);
            });
            DBManager.getRooms().forEach(room -> {
                if (room.getNumer_sali() == this.sala.getValue())
                    s.setSala(room);
            });
            DBManager.update(s);
            cancel(event);
        } catch (NullPointerException e) {
            new MyAlert(MyAlert.MyAlertType.WARNING, "Pola nie mogą być puste.");
        }
    }
}
