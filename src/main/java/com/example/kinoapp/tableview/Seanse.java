package com.example.kinoapp.tableview;

import com.example.kinoapp.database.SeanseDB;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;

public class Seanse {
    private final SimpleLongProperty id_seansu = new SimpleLongProperty();
    private final SimpleStringProperty film = new SimpleStringProperty();
    private final SimpleStringProperty data_godzina = new SimpleStringProperty();
    private final SimpleLongProperty sala = new SimpleLongProperty();

    public Seanse() {
    }
    public Seanse(SeanseDB s) {
        setId_seansu(s.getId_seansu());
        setFilm(s.getFilm().getTytul());
        LocalDateTime dateTime = s.getData_godzina().toLocalDateTime();
        setData_godzina(dateTime.toLocalDate().toString() + " " + dateTime.toLocalTime().toString());
        setSala(s.getSala().getNumer_sali());
    }

    public long getId_seansu() {
        return id_seansu.get();
    }

    public SimpleLongProperty id_seansuProperty() {
        return id_seansu;
    }

    public void setId_seansu(long id_seansu) {
        this.id_seansu.set(id_seansu);
    }

    public String getFilm() {
        return film.get();
    }

    public SimpleStringProperty filmProperty() {
        return film;
    }

    public void setFilm(String film) {
        this.film.set(film);
    }

    public String getData_godzina() {
        return data_godzina.get();
    }

    public SimpleStringProperty data_godzinaProperty() {
        return data_godzina;
    }

    public void setData_godzina(String data_godzina) {
        this.data_godzina.set(data_godzina);
    }

    public long getSala() {
        return sala.get();
    }

    public SimpleLongProperty salaProperty() {
        return sala;
    }

    public void setSala(long sala) {
        this.sala.set(sala);
    }

}
