package com.example.kinoapp.database;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "Filmy")
public class FilmyDB {
    @Id
    private long id_filmu;
    private String tytul;
    private String gatunek;
    private int czas_trwania;
    @OneToMany(mappedBy = "id_seansu")
    private List<SeanseDB> seanse;

    public FilmyDB() {
    }

    public long getId_filmu() {
        return id_filmu;
    }

    public void setId_filmu(long id_filmu) {
        this.id_filmu = id_filmu;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public String getGatunek() {
        return gatunek;
    }

    public void setGatunek(String gatunek) {
        this.gatunek = gatunek;
    }

    public int getCzas_trwania() {
        return czas_trwania;
    }

    public void setCzas_trwania(int czas_trwania) {
        this.czas_trwania = czas_trwania;
    }

    public List<SeanseDB> getSeanse() {
        return seanse;
    }

    public void setSeanse(List<SeanseDB> seanse) {
        this.seanse = seanse;
    }
}
