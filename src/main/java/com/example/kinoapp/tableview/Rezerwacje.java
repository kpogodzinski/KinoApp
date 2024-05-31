package com.example.kinoapp.tableview;

import com.example.kinoapp.database.RezerwacjeDB;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;

public class Rezerwacje {
    private final SimpleLongProperty id_rezerwacji = new SimpleLongProperty();
    private final SimpleStringProperty seans = new SimpleStringProperty();
    private final SimpleStringProperty klient = new SimpleStringProperty();
    private final SimpleIntegerProperty fotel = new SimpleIntegerProperty();

    public Rezerwacje() {
    }

    public Rezerwacje(RezerwacjeDB r) {
        setId_rezerwacji(r.getId_rezerwacji());
        LocalDateTime dateTime = r.getSeans().getData_godzina().toLocalDateTime();
        setSeans(r.getSeans().getFilm().getTytul() + "\n" + dateTime.toLocalDate().toString()
                + " " + dateTime.toLocalTime().toString());
        setKlient(r.getKlient().getEmail());
        setFotel(r.getNumer_fotela());
    }

    public long getId_rezerwacji() {
        return id_rezerwacji.get();
    }

    public SimpleLongProperty id_rezerwacjiProperty() {
        return id_rezerwacji;
    }

    public void setId_rezerwacji(long id_rezerwacji) {
        this.id_rezerwacji.set(id_rezerwacji);
    }

    public String getSeans() {
        return seans.get();
    }

    public SimpleStringProperty seansProperty() {
        return seans;
    }

    public void setSeans(String seans) {
        this.seans.set(seans);
    }

    public String getKlient() {
        return klient.get();
    }

    public SimpleStringProperty klientProperty() {
        return klient;
    }

    public void setKlient(String klient) {
        this.klient.set(klient);
    }

    public int getFotel() {
        return fotel.get();
    }

    public SimpleIntegerProperty fotelProperty() {
        return fotel;
    }

    public void setFotel(int fotel) {
        this.fotel.set(fotel);
    }
}
