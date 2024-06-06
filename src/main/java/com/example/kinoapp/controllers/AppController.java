package com.example.kinoapp.controllers;

import com.example.kinoapp.Application;
import com.example.kinoapp.MyAlert;
import com.example.kinoapp.database.*;
import com.example.kinoapp.tableview.*;
import jakarta.persistence.RollbackException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private Button addClientBtn;
    @FXML
    private Button editClientBtn;
    @FXML
    private Button addScreeningBtn;
    @FXML
    private Button editScreeningBtn;
    @FXML
    private Button addBookingBtn;
    @FXML
    private Button editBookingBtn;

    @FXML
    private TableView<Filmy> filmTable;
    @FXML
    private TableView<Sale> roomTable;
    @FXML
    private TableView<Klienci> clientTable;
    @FXML
    private TableView<Seanse> screeningTable;
    @FXML
    private TableView<Rezerwacje> bookingTable;

    @FXML
    private void initialize() {
        // Przygotowanie wspólnych ustawień dla okienek edycji
        stage.setResizable(false);
        stage.getIcons().add(new Image(String.valueOf(Application.class.getResource("images/favicon.png"))));
        stage.initModality(Modality.APPLICATION_MODAL);

        // Pobranie danych z bazy do tabel TableView
        fetchFilms();
        fetchScreenings();
        fetchBookings();
        fetchRooms();
        fetchClients();
    }

    // Funkcje fetch dla tabel
    @FXML
    private void fetchFilms() {
        ObservableList<Filmy> filmy = FXCollections.observableArrayList();
        filmTable.setItems(filmy);
        DBManager.getFilms().forEach(film -> filmy.add(new Filmy(film)));
    }

    @FXML
    private void fetchRooms() {
        ObservableList<Sale> sale = FXCollections.observableArrayList();
        roomTable.setItems(sale);
        DBManager.getRooms().forEach(room -> sale.add(new Sale(room)));
    }
    @FXML
    private void fetchClients() {
        ObservableList<Klienci> klienci = FXCollections.observableArrayList();
        clientTable.setItems(klienci);
        DBManager.getClients().forEach(client -> klienci.add(new Klienci(client)));
    }
    @FXML
    private void fetchScreenings() {
        ObservableList<Seanse> seanse = FXCollections.observableArrayList();
        screeningTable.setItems(seanse);
        DBManager.getScreenings().forEach(screening -> seanse.add(new Seanse(screening)));
    }
    @FXML
    private void fetchBookings() {
        ObservableList<Rezerwacje> rezerwacje = FXCollections.observableArrayList();
        bookingTable.setItems(rezerwacje);
        DBManager.getBookings().forEach(booking -> rezerwacje.add(new Rezerwacje(booking)));
    }

    // Funkcje edycji/usuwania wpisów tabel
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
            fxmlLoader.setLocation(Application.class.getResource("views/film.fxml"));
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
            MyAlert confirm = new MyAlert(MyAlert.MyAlertType.CONFIRM, "Czy na pewno chcesz usunąć film " +
                    "\"" + filmTable.getSelectionModel().getSelectedItem().getTytul() + "\"?");
            if (confirm.getResult().getText().equals("Tak")) {
                try {
                    DBManager.delete(FilmyDB.class, filmTable.getSelectionModel().getSelectedItem().getId_filmu());
                    fetchFilms();
                } catch (RollbackException e) {
                    new MyAlert(MyAlert.MyAlertType.ERROR, "Nie można usunąć filmu, " +
                            "ponieważ zaplanowane są jego seanse.");
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
            fxmlLoader.setLocation(Application.class.getResource("views/sala.fxml"));
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
            MyAlert confirm = new MyAlert(MyAlert.MyAlertType.CONFIRM, "Czy na pewno chcesz usunąć salę nr "
                    + roomTable.getSelectionModel().getSelectedItem().getNumer_sali() + "?");
            if (confirm.getResult().getText().equals("Tak")) {
                try {
                    DBManager.delete(SaleDB.class, roomTable.getSelectionModel().getSelectedItem().getNumer_sali());
                    fetchRooms();
                } catch (RollbackException e) {
                    new MyAlert(MyAlert.MyAlertType.ERROR, "Nie można usunąć sali," +
                            " ponieważ przypisane są do niej seanse.");
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
            fxmlLoader.setLocation(Application.class.getResource("views/klient.fxml"));
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
            MyAlert confirm = new MyAlert(MyAlert.MyAlertType.CONFIRM, "Czy na pewno chcesz usunąć klienta "
                    + clientTable.getSelectionModel().getSelectedItem().getImie() + " "
                    + clientTable.getSelectionModel().getSelectedItem().getNazwisko() + "?");
            if (confirm.getResult().getText().equals("Tak")) {
                try {
                    DBManager.delete(KlienciDB.class, clientTable.getSelectionModel().getSelectedItem().getId_klienta());
                    fetchClients();
                } catch (RollbackException e) {
                    new MyAlert(MyAlert.MyAlertType.ERROR, "Nie można usunąć klienta," +
                            " ponieważ ma on wpisaną rezerwację.");
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
            fxmlLoader.setLocation(Application.class.getResource("views/seans.fxml"));
            fxmlLoader.setController(screeningController);
            stage.setScene(new Scene(fxmlLoader.load(), 360, 280));
            stage.setTitle("Edytuj informacje o seansie");
            stage.showAndWait();
            fetchScreenings();
        }
    }
    @FXML
    private void deleteScreening() {
        if (screeningTable.getSelectionModel().getSelectedItem() != null) {
            MyAlert confirm = new MyAlert(MyAlert.MyAlertType.CONFIRM, "Czy na pewno chcesz usunąć wybrany seans?");
            if (confirm.getResult().getText().equals("Tak")) {
                try {
                    DBManager.delete(SeanseDB.class, screeningTable.getSelectionModel().getSelectedItem().getId_seansu());
                    fetchScreenings();
                } catch (RollbackException e) {
                    new MyAlert(MyAlert.MyAlertType.ERROR, "Nie można usunąć wybranego seansu, " +
                            "ponieważ jest on przypisany do rezerwacji.");
                }
            }
        }
    }

    @FXML
    private void editBooking(Event event) throws IOException {
        BookingController bookingController;
        if (event.getSource().equals(editBookingBtn)) {
            bookingController = new BookingController(true);
            bookingController.setBooking(bookingTable.getSelectionModel().getSelectedItem());
        }
        else
            bookingController = new BookingController(false);
        if (event.getSource().equals(addBookingBtn) || bookingTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Application.class.getResource("views/rezerwacja.fxml"));
            fxmlLoader.setController(bookingController);
            stage.setScene(new Scene(fxmlLoader.load(), 360, 280));
            stage.setTitle("Edytuj informacje o rezerwacji");
            stage.showAndWait();
            fetchBookings();
        }
    }
    @FXML
    private void deleteBooking() {
        if (bookingTable.getSelectionModel().getSelectedItem() != null) {
            MyAlert confirm = new MyAlert(MyAlert.MyAlertType.CONFIRM, "Czy na pewno chcesz usunąć wybraną rezerwację?");
            if (confirm.getResult().getText().equals("Tak")) {
                DBManager.delete(RezerwacjeDB.class, bookingTable.getSelectionModel().getSelectedItem().getId_rezerwacji());
                fetchBookings();
            }
        }
    }
}