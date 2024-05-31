package com.example.kinoapp.controllers;

import com.example.kinoapp.database.DBManager;
import com.example.kinoapp.database.RezerwacjeDB;
import com.example.kinoapp.database.SeanseDB;
import com.example.kinoapp.tableview.Rezerwacje;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

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

    public BookingController(boolean editMode) {
        this.editMode = editMode;
    }

    public Rezerwacje getBooking() {
        return booking;
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
        DBManager.getClients().forEach(client -> {
            clients.add(client.getEmail());
        });
        clients.sort(String::compareToIgnoreCase);
        klient.setItems(clients);

        if (editMode) {
            id_rezerwacji.setText(Long.toString(booking.getId_rezerwacji()));
            seans.setValue(booking.getSeans());
            klient.setValue(booking.getKlient());
            fotel.getValueFactory().setValue(booking.getFotel());
        }
        else {
            long id = DBManager.getNextId("Rezerwacje");
            id_rezerwacji.setText(Long.toString(id));

            if (id_rezerwacji.getText().equals("-1")) {

            }
        }
    }


    @Override
    public void save(Event event) {
        RezerwacjeDB r = new RezerwacjeDB();
        r.setId_rezerwacji(Long.parseLong(id_rezerwacji.getText()));
        DBManager.getScreenings().forEach(screening -> {
            if (screening.getFilm().getTytul().equals(seans.getValue().substring(0, seans.getValue().indexOf("\n")))
            && screening.getData_godzina().equals(Timestamp.valueOf(seans.getValue().substring(seans.getValue().indexOf("\n")+1))))
                r.setSeans(screening);
        });
        DBManager.getClients().forEach(client -> {
            if (client.getEmail().equals(klient.getValue()))
                r.setKlient(client);
        });
        r.setNumer_fotela(fotel.getValue());
    }

    @Override
    public void cancel(Event event) {
        SmallController.super.cancel(event);
    }
}