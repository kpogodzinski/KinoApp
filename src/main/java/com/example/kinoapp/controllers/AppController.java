package com.example.kinoapp.controllers;

import com.example.kinoapp.Application;
import com.example.kinoapp.database.*;
import com.example.kinoapp.tableview.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AppController {
    private final Stage stage = new Stage();

    @FXML
    private TableView<Filmy> filmTable;

    @FXML
    private void initialize() {
        // Przygotowanie wspólnych ustawień dla okienek edycji
        stage.setResizable(false);
        stage.getIcons().add(new Image("file:src/main/resources/com/example/kinoapp/favicon.png"));
        stage.initModality(Modality.APPLICATION_MODAL);

        fetchFilms();
    }

    @FXML
    private void tabChanged() {
        fetchFilms();
    }

    private void fetchFilms() {
        ObservableList<Filmy> filmy = FXCollections.observableArrayList();
        filmTable.setItems(filmy);
        DBManager.getFilms().forEach(o -> filmy.add(new Filmy(o)));
    }

    @FXML
    private void addFilm() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Application.class.getResource("film.fxml"));
        FilmController filmController = new FilmController(false);
        fxmlLoader.setController(filmController);
        stage.setScene(new Scene(fxmlLoader.load(), 360, 280));
        stage.setTitle("Edytuj informacje o filmie");
        stage.showAndWait();
        fetchFilms();
    }
    @FXML
    private void editFilm() throws IOException {
        if (filmTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Application.class.getResource("film.fxml"));
            FilmController filmController = new FilmController(true);
            filmController.setFilm(filmTable.getSelectionModel().getSelectedItem());
            fxmlLoader.setController(filmController);
            stage.setScene(new Scene(fxmlLoader.load(), 360, 280));
            stage.setTitle("Edytuj informacje o filmie");
            stage.showAndWait();
            fetchFilms();

        }
    }

    @FXML
    private void deleteFilm() {
        if (filmTable.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Czy na pewno chcesz usunąć film \""
                    + filmTable.getSelectionModel().getSelectedItem().getTytul()
                    + "\"?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Potwierdzenie");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                DBManager.deleteFilm(filmTable.getSelectionModel().getSelectedItem().getId_filmu());
                fetchFilms();
            }
        }
    }
}