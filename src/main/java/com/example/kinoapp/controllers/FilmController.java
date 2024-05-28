package com.example.kinoapp.controllers;

import com.example.kinoapp.database.DBManager;
import com.example.kinoapp.database.FilmyDB;
import com.example.kinoapp.tableview.Filmy;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FilmController {
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
    private void initialize() {
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
    private void save(Event event) {
        FilmyDB f = new FilmyDB();
        f.setId_filmu(Long.parseLong(id_filmu.getText()));
        f.setTytul(tytul.getText());
        f.setGatunek(gatunek.getText());
        try {
            f.setCzas_trwania(Integer.parseInt(czas_trwania.getText()));
            if (f.getCzas_trwania() < 0)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            f.setCzas_trwania(0);
        }
        DBManager.update(f);
        cancel(event);
    }

    @FXML
    private void cancel(Event event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
}
