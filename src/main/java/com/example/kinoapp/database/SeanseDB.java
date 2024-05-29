package com.example.kinoapp.database;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.sql.Timestamp;

@Entity(name = "Seanse")
public class SeanseDB {
    @Id
    private long id_seansu;
    @ManyToOne
    @JoinColumn(name = "id_filmu")
    private FilmyDB film;
    private Timestamp data_godzina;
    @ManyToOne
    @JoinColumn(name = "numer_sali")
    private SaleDB sala;

    public SeanseDB() {
    }

    public long getId_seansu() {
        return id_seansu;
    }

    public void setId_seansu(long id_seansu) {
        this.id_seansu = id_seansu;
    }

    public Timestamp getData_godzina() {
        return data_godzina;
    }

    public void setData_godzina(Timestamp data_godzina) {
        this.data_godzina = data_godzina;
    }


    public FilmyDB getFilm() {
        return film;
    }

    public void setFilm(FilmyDB film) {
        this.film = film;
    }

    public SaleDB getSala() {
        return sala;
    }

    public void setSala(SaleDB sala) {
        this.sala = sala;
    }
}
