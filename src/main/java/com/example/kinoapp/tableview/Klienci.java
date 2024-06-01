package com.example.kinoapp.tableview;

import com.example.kinoapp.database.KlienciDB;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Klienci {
    private final SimpleLongProperty id_klienta = new SimpleLongProperty();
    private final SimpleStringProperty imie = new SimpleStringProperty();
    private final SimpleStringProperty nazwisko = new SimpleStringProperty();
    private final SimpleStringProperty email = new SimpleStringProperty();
    private final SimpleStringProperty telefon = new SimpleStringProperty();

    public Klienci() {
    }

    public Klienci(KlienciDB k) {
        setId_klienta(k.getId_klienta());
        setImie(k.getImie());
        setNazwisko(k.getNazwisko());
        setEmail(k.getEmail());
        setTelefon(k.getTelefon());
    }

    public long getId_klienta() {
        return id_klienta.get();
    }

    public SimpleLongProperty id_klientaProperty() {
        return id_klienta;
    }

    public void setId_klienta(long id_klienta) {
        this.id_klienta.set(id_klienta);
    }

    public String getImie() {
        return imie.get();
    }

    public SimpleStringProperty imieProperty() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie.set(imie);
    }

    public String getNazwisko() {
        return nazwisko.get();
    }

    public SimpleStringProperty nazwiskoProperty() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko.set(nazwisko);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getTelefon() {
        return telefon.get();
    }

    public SimpleStringProperty telefonProperty() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon.set(telefon);
    }
}
