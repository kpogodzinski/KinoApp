package com.example.kinoapp.controllers;

import com.example.kinoapp.database.DBManager;
import com.example.kinoapp.database.KlienciDB;
import com.example.kinoapp.tableview.Klienci;
import jakarta.persistence.RollbackException;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.util.Calendar;

public class ClientController implements SmallController {
    @FXML
    private TextField id_klienta;
    @FXML
    private TextField imie;
    @FXML
    private TextField nazwisko;
    @FXML
    private TextField email;
    @FXML
    private TextField telefon;
    private final boolean editMode;
    private Klienci client;

    public ClientController(boolean editMode) {
        this.editMode = editMode;
    }

    public Klienci getClient() {
        return client;
    }

    public void setClient(Klienci client) {
        this.client = client;
    }

    @FXML
    public void initialize() {
        if (editMode) {
            id_klienta.setText(Long.toString(client.getId_klienta()));
            imie.setText(client.getImie());
            nazwisko.setText(client.getNazwisko());
            email.setText(client.getEmail());
            telefon.setText(client.getTelefon());
        }
        else {
            long id = DBManager.getNextId("Klienci");
            id_klienta.setText(Long.toString(id));

            if (id_klienta.getText().equals("-1")) {

            }
        }
    }

    @FXML
    public void save(Event event) {
        try {
            KlienciDB k = new KlienciDB();
            k.setId_klienta(Long.parseLong(id_klienta.getText()));
            if (imie.getText().isEmpty() || nazwisko.getText().isEmpty() || email.getText().isEmpty() || telefon.getText().isEmpty())
                throw new IllegalArgumentException();
            k.setImie(imie.getText());
            k.setNazwisko(nazwisko.getText());
            k.setEmail(email.getText());
            k.setTelefon(telefon.getText());
            if (Long.parseLong(telefon.getText()) < 0)
                throw new NumberFormatException();
            DBManager.update(k);
            cancel(event);
        } catch (NumberFormatException e) {
            Alert error = new Alert(Alert.AlertType.NONE, "Pole Telefon zawiera niedozwolone znaki.", ButtonType.OK);
            error.showAndWait();
        } catch (IllegalArgumentException e) {
            Alert error = new Alert(Alert.AlertType.NONE, "Pola nie mogą być puste.", ButtonType.OK);
            error.showAndWait();
        } catch (RollbackException e) {
            if (e.getCause().getMessage().contains("unikalnyemail")) {
                Alert error = new Alert(Alert.AlertType.NONE, "Adres email jest już zarejestrowany w bazie.", ButtonType.OK);
                error.showAndWait();
            }
        }
    }

    @Override
    public void cancel(Event event) {
        SmallController.super.cancel(event);
    }
}
