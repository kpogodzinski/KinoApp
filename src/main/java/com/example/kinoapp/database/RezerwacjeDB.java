package com.example.kinoapp.database;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "Rezerwacje")
public class RezerwacjeDB {
    @Id
    private long id_rezerwacji;
    @ManyToOne
    @JoinColumn(name = "id_seansu")
    private SeanseDB seans;
    @ManyToOne
    @JoinColumn(name = "id_klienta")
    private KlienciDB klient;
    private int numer_fotela;

    public RezerwacjeDB() {
    }

    public long getId_rezerwacji() {
        return id_rezerwacji;
    }

    public void setId_rezerwacji(long id_rezerwacji) {
        this.id_rezerwacji = id_rezerwacji;
    }

    public SeanseDB getSeans() {
        return seans;
    }

    public void setSeans(SeanseDB seans) {
        this.seans = seans;
    }

    public KlienciDB getKlient() {
        return klient;
    }

    public void setKlient(KlienciDB klient) {
        this.klient = klient;
    }

    public int getNumer_fotela() {
        return numer_fotela;
    }

    public void setNumer_fotela(int numer_fotela) {
        this.numer_fotela = numer_fotela;
    }
}
