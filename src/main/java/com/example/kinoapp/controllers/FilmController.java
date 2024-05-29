package com.example.kinoapp.controllers;

import com.example.kinoapp.database.DBManager;
import com.example.kinoapp.database.FilmyDB;
import com.example.kinoapp.tableview.Filmy;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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

    public FilmController(boolean editMode) {
        this.editMode = editMode;
    }

    public Filmy getFilm() {
        return film;
    }

    public void setFilm(Filmy film) {
        this.film = film;
    }

    @FXML
    public void initialize() {
        if (editMode) {
            id_filmu.setText(Long.toString(film.getId_filmu()));
            tytul.setText(film.getTytul());
            gatunek.setText(film.getGatunek());
            czas_trwania.setText(Integer.toString(film.getCzas_trwania()));
        }
        else {
            long id = DBManager.getNextId("Filmy");
            id_filmu.setText(Long.toString(id));

            if (id_filmu.getText().equals("-1")) {

            }
        }
    }

    @FXML
    public void save(Event event) {
        FilmyDB f = new FilmyDB();
        f.setId_filmu(Long.parseLong(id_filmu.getText()));
        f.setTytul(tytul.getText());
        f.setGatunek(gatunek.getText());
        try {
            f.setCzas_trwania(Integer.parseInt(czas_trwania.getText()));
            if (f.getCzas_trwania() <= 0)
                throw new NumberFormatException();
            DBManager.update(f);
            cancel(event);
        } catch (NumberFormatException e) {
            Alert error = new Alert(Alert.AlertType.NONE, "Pole Czas trwania jest puste " +
                    "lub zawiera niedozwolone znaki.", ButtonType.OK);
            error.showAndWait();
        }
    }

    @Override
    public void cancel(Event event) {
        SmallController.super.cancel(event);
    }
}
