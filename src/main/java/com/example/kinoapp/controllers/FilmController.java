package com.example.kinoapp.controllers;

import com.example.kinoapp.MyAlert;
import com.example.kinoapp.database.DBManager;
import com.example.kinoapp.database.FilmyDB;
import com.example.kinoapp.tableview.Filmy;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FilmController implements SmallController {
    @FXML
    private TextField id_filmu;
    @FXML
    private TextField tytul;
    @FXML
    private TextField gatunek;
    @FXML
    private TextField czas_trwania;
    private final boolean editMode;
    private Filmy film;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;

    public FilmController(boolean editMode) {
        this.editMode = editMode;
    }

    public void setFilm(Filmy film) {
        this.film = film;
    }

    @FXML
    public void initialize() {
        saveBtn.setOnAction(this::save);
        cancelBtn.setOnAction(this::cancel);
        if (editMode) {
            id_filmu.setText(Long.toString(film.getId_filmu()));
            tytul.setText(film.getTytul());
            gatunek.setText(film.getGatunek());
            czas_trwania.setText(Integer.toString(film.getCzas_trwania()));
        }
        else {
            long id = DBManager.getNextId("Filmy");
            id_filmu.setText(Long.toString(id));
        }
    }

    @FXML
    public void save(Event event) {
        if (checkIdError(id_filmu))
            return;
        try {
            FilmyDB f = new FilmyDB();
            f.setId_filmu(Long.parseLong(id_filmu.getText()));
            if (tytul.getText().isEmpty() || gatunek.getText().isEmpty() || czas_trwania.getText().isEmpty())
                throw new IllegalArgumentException();
            f.setTytul(tytul.getText());
            f.setGatunek(gatunek.getText());
            f.setCzas_trwania(Integer.parseInt(czas_trwania.getText()));
            if (f.getCzas_trwania() <= 0)
                throw new NumberFormatException();
            DBManager.update(f);
            cancel(event);
        } catch (NumberFormatException e) {
            new MyAlert(MyAlert.MyAlertType.WARNING, "Pole Czas trwania zawiera niedozwolone znaki.");
        } catch (IllegalArgumentException e) {
            new MyAlert(MyAlert.MyAlertType.WARNING, "Pola nie mogą być puste.");
        }
    }
}
