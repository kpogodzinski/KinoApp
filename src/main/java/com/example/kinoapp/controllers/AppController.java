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
import java.sql.Timestamp;

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
    private Button addClientBtn;
    @FXML
    private Button editClientBtn;
    @FXML
    private Button addScreeningBtn;
    @FXML
    private Button editScreeningBtn;

    @FXML
    private TableView<Filmy> filmTable;
    @FXML
    private TableView<Sale> roomTable;
    @FXML
    private TableView<Klienci> clientTable;
    @FXML
    private TableView<Seanse> screeningTable;

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
    private void fetchClients() {
        ObservableList<Klienci> klienci = FXCollections.observableArrayList();
        clientTable.setItems(klienci);
        DBManager.getClients().forEach(o -> klienci.add(new Klienci(o)));
    }
    @FXML
    private void fetchScreenings() {
        ObservableList<Seanse> seanse = FXCollections.observableArrayList();
        screeningTable.setItems(seanse);
        DBManager.getScreenings().forEach(o -> seanse.add(new Seanse(o)));
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
                try {
                    DBManager.delete(FilmyDB.class, filmTable.getSelectionModel().getSelectedItem().getId_filmu());
                    fetchFilms();
                } catch (RollbackException e) {
                    Alert error = new Alert(Alert.AlertType.NONE, "Nie można usunąć filmu, " +
                            "ponieważ zaplanowane są jego seanse.", ButtonType.OK);
                    error.showAndWait();
                }
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

    @FXML
    private void editClient(Event event) throws IOException {
        ClientController clientController;
        if (event.getSource().equals(editClientBtn)) {
            clientController = new ClientController(true);
            clientController.setClient(clientTable.getSelectionModel().getSelectedItem());
        }
        else
            clientController = new ClientController(false);
        if (event.getSource().equals(addClientBtn) || clientTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Application.class.getResource("klient.fxml"));
            fxmlLoader.setController(clientController);
            stage.setScene(new Scene(fxmlLoader.load(), 360, 280));
            stage.setTitle("Edytuj informacje o kliencie");
            stage.showAndWait();
            fetchClients();
        }
    }
    @FXML
    private void deleteClient() {
        if (clientTable.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Czy na pewno chcesz usunąć klienta "
                    + clientTable.getSelectionModel().getSelectedItem().getImie() + " "
                    + clientTable.getSelectionModel().getSelectedItem().getNazwisko()
                    + "?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Potwierdzenie");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                try {
                    DBManager.delete(KlienciDB.class, clientTable.getSelectionModel().getSelectedItem().getId_klienta());
                    fetchClients();
                } catch (RollbackException e) {
                    Alert error = new Alert(Alert.AlertType.NONE, "Nie można usunąć klienta," +
                            " ponieważ ma on wpisaną rezerwację.", ButtonType.OK);
                    error.showAndWait();
                }
            }
        }
    }

    @FXML
    private void editScreening(Event event) throws IOException {
        ScreeningController screeningController;
        if (event.getSource().equals(editScreeningBtn)) {
            screeningController = new ScreeningController(true);
            screeningController.setScreening(screeningTable.getSelectionModel().getSelectedItem());
        }
        else
            screeningController = new ScreeningController(false);
        if (event.getSource().equals(addScreeningBtn) || screeningTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Application.class.getResource("seans.fxml"));
            fxmlLoader.setController(screeningController);
            stage.setScene(new Scene(fxmlLoader.load(), 360, 280));
            stage.setTitle("Edytuj informacje o seansie");
            stage.showAndWait();
            fetchScreenings();
        }
    }
    @FXML
    private void deleteScreening() {
//        if (clientTable.getSelectionModel().getSelectedItem() != null) {
//            Alert alert = new Alert(Alert.AlertType.NONE, "Czy na pewno chcesz usunąć klienta "
//                    + clientTable.getSelectionModel().getSelectedItem().getImie() + " "
//                    + clientTable.getSelectionModel().getSelectedItem().getNazwisko()
//                    + "?", ButtonType.YES, ButtonType.NO);
//            alert.setTitle("Potwierdzenie");
//            alert.showAndWait();
//            if (alert.getResult() == ButtonType.YES) {
//                try {
//                    DBManager.delete(KlienciDB.class, clientTable.getSelectionModel().getSelectedItem().getId_klienta());
//                    fetchClients();
//                } catch (RollbackException e) {
//                    Alert error = new Alert(Alert.AlertType.NONE, "Nie można usunąć klienta," +
//                            " ponieważ ma on wpisaną rezerwację.", ButtonType.OK);
//                    error.showAndWait();
//                }
//            }
//        }
    }
}