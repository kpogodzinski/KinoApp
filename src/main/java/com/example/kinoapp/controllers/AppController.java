package com.example.kinoapp.controllers;

import com.example.kinoapp.Application;
import com.example.kinoapp.database.*;
import com.example.kinoapp.tableview.*;
import jakarta.persistence.RollbackException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
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
import org.hibernate.exception.ConstraintViolationException;

import java.io.IOException;

public class AppController {
    private final Stage stage = new Stage();
    @FXML
    private Button addFilmBtn;
    @FXML
    private Button editFilmBtn;
    @FXML
    private Button addRoomBtn;
    @FXML
    private Button editRoomBtn;

    @FXML
    private TableView<Filmy> filmTable;
    @FXML
    private TableView<Sale> roomTable;

    @FXML
    private void initialize() {
        // Przygotowanie wspólnych ustawień dla okienek edycji
        stage.setResizable(false);
        stage.getIcons().add(new Image("file:src/main/resources/com/example/kinoapp/favicon.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    @FXML
    private void fetchFilms() {
        ObservableList<Filmy> filmy = FXCollections.observableArrayList();
        filmTable.setItems(filmy);
        DBManager.getFilms().forEach(o -> filmy.add(new Filmy(o)));
    }

    @FXML
    private void fetchRooms() {
        ObservableList<Sale> sale = FXCollections.observableArrayList();
        roomTable.setItems(sale);
        DBManager.getRooms().forEach(o -> sale.add(new Sale(o)));
    }

    @FXML
    private void editFilm(Event event) throws IOException {
        FilmController filmController;
        if (event.getSource().equals(editFilmBtn)) {
            filmController = new FilmController(true);
            filmController.setFilm(filmTable.getSelectionModel().getSelectedItem());
        }
        else
            filmController = new FilmController(false);
        if (event.getSource().equals(addFilmBtn) || filmTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Application.class.getResource("film.fxml"));
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
                DBManager.delete(FilmyDB.class, filmTable.getSelectionModel().getSelectedItem().getId_filmu());
                fetchFilms();
            }
        }
    }

    @FXML
    private void editRoom(Event event) throws IOException {
        RoomController roomController;
        if (event.getSource().equals(editRoomBtn)) {
            roomController = new RoomController(true);
            roomController.setRoom(roomTable.getSelectionModel().getSelectedItem());
        }
        else
            roomController = new RoomController(false);
        if (event.getSource().equals(addRoomBtn) || roomTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Application.class.getResource("sala.fxml"));
            fxmlLoader.setController(roomController);
            stage.setScene(new Scene(fxmlLoader.load(), 360, 280));
            stage.setTitle("Edytuj informacje o sali");
            stage.showAndWait();
            fetchRooms();
        }
    }
    @FXML
    private void deleteRoom() {
        if (roomTable.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Czy na pewno chcesz usunąć salę nr "
                    + roomTable.getSelectionModel().getSelectedItem().getNumer_sali()
                    + "?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Potwierdzenie");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                try {
                    DBManager.delete(SaleDB.class, roomTable.getSelectionModel().getSelectedItem().getNumer_sali());
                    fetchRooms();
                } catch (RollbackException e) {
                    Alert error = new Alert(Alert.AlertType.NONE, "Nie można usunąć sali," +
                            " ponieważ przypisane są do niej seanse.", ButtonType.OK);
                    error.showAndWait();
                }
            }
        }
    }
}