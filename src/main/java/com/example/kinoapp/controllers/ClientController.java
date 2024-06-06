package com.example.kinoapp.controllers;

import com.example.kinoapp.MyAlert;
import com.example.kinoapp.database.DBManager;
import com.example.kinoapp.database.KlienciDB;
import com.example.kinoapp.tableview.Klienci;
import jakarta.persistence.RollbackException;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

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
        }
    }

    @FXML
    public void save(Event event) {
        if (checkIdError(id_klienta))
            return;
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
            new MyAlert(MyAlert.MyAlertType.WARNING, "Pole Telefon zawiera niedozwolone znaki.");
        } catch (IllegalArgumentException e) {
            new MyAlert(MyAlert.MyAlertType.WARNING, "Pola nie mogą być puste.");
        } catch (RollbackException e) {
            if (e.getCause().getMessage().contains("unikalnyemail"))
                new MyAlert(MyAlert.MyAlertType.INFO, "Adres email jest już zarejestrowany w bazie.");
        }
    }

    @Override
    public void cancel(Event event) {
        SmallController.super.cancel(event);
    }
}
