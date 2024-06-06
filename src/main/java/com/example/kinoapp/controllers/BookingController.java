package com.example.kinoapp.controllers;

import com.example.kinoapp.MyAlert;
import com.example.kinoapp.database.DBManager;
import com.example.kinoapp.database.RezerwacjeDB;
import com.example.kinoapp.tableview.Rezerwacje;
import jakarta.persistence.RollbackException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class BookingController implements SmallController {
    @FXML
    private TextField id_rezerwacji;
    @FXML
    private ChoiceBox<String> seans;
    @FXML
    private ChoiceBox<String> klient;
    @FXML
    private Spinner<Integer> fotel;
    private final boolean editMode;
    private Rezerwacje booking;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;

    public BookingController(boolean editMode) {
        this.editMode = editMode;
    }

    public void setBooking(Rezerwacje booking) {
        this.booking = booking;
    }

    @Override
    public void initialize() {
        // Ustawienia spinnera
        fotel.setDisable(true);
        fotel.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999));
        fotel.getValueFactory().setWrapAround(false);
        seans.valueProperty().addListener(observable -> {
            if (!observable.toString().isEmpty())
                fotel.setDisable(false);
        });
        fotel.getValueFactory().setConverter(new StringConverter<>() {
            @Override
            public String toString(Integer integer) {
                return integer.toString();
            }

            @Override
            public Integer fromString(String s) {
                try {
                    return Integer.parseInt(s);
                } catch (NumberFormatException e) {
                    fotel.cancelEdit();
                    return 1;
                }
            }
        });

        // Dodanie posortowanych seansów do listy combo
        ObservableList<String> screenings = FXCollections.observableArrayList();
        DBManager.getScreenings().forEach(screening -> {
            LocalDateTime datetime = screening.getData_godzina().toLocalDateTime();
            screenings.add(screening.getFilm().getTytul() + "\n"
                    + datetime.toLocalDate().toString() + " " + datetime.toLocalTime().toString());
        });
        screenings.sort(String::compareToIgnoreCase);
        seans.setItems(screenings);

        // Dodanie posortowanych klientów do listy combo
        ObservableList<String> clients = FXCollections.observableArrayList();
        DBManager.getClients().forEach(client -> clients.add(client.getEmail()));
        clients.sort(String::compareToIgnoreCase);
        klient.setItems(clients);

        saveBtn.setOnAction(this::save);
        cancelBtn.setOnAction(this::cancel);
        if (editMode) {
            id_rezerwacji.setText(Long.toString(booking.getId_rezerwacji()));
            seans.setValue(booking.getSeans());
            klient.setValue(booking.getKlient());
            fotel.getValueFactory().setValue(booking.getFotel());
        }
        else {
            long id = DBManager.getNextId("Rezerwacje");
            id_rezerwacji.setText(Long.toString(id));
        }
    }

    @Override
    public void save(Event event) {
        if (checkIdError(id_rezerwacji))
            return;
        try {
            RezerwacjeDB r = new RezerwacjeDB();
            r.setId_rezerwacji(Long.parseLong(id_rezerwacji.getText()));
            if (seans.getValue().isEmpty() || klient.getValue().isEmpty())
                throw new NullPointerException();
            DBManager.getScreenings().forEach(screening -> {
                if (screening.getFilm().getTytul().equals(seans.getValue().substring(0, seans.getValue().indexOf("\n")))
                        && screening.getData_godzina().equals(Timestamp.valueOf(seans.getValue().substring(seans.getValue().indexOf("\n") + 1) + ":00")))
                    r.setSeans(screening);
            });
            DBManager.getClients().forEach(client -> {
                if (client.getEmail().equals(klient.getValue()))
                    r.setKlient(client);
            });
            r.setNumer_fotela(fotel.getValue());
            DBManager.update(r);
            cancel(event);
        } catch (NullPointerException e) {
            new MyAlert(MyAlert.MyAlertType.WARNING, "Pola nie mogą być puste.");
        } catch (RollbackException e) {
            if (e.getCause().getMessage().contains("rezerwacje_id_seansu_numer_fotela_key"))
                new MyAlert(MyAlert.MyAlertType.INFO, "To miejsce jest już zajęte. Wybierz inne miejsce.");
            else if (e.getCause().getMessage().contains("sprawdzpojemnosc"))
                new MyAlert(MyAlert.MyAlertType.INFO, "Brak wolnych miejsc na wybrany seans.");
        }
    }
}
