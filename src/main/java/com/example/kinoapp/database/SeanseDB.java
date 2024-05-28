package com.example.kinoapp.database;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Timestamp;

@Entity(name = "Seanse")
public class SeanseDB {
    @Id
    private long id_seansu;
    private long id_filmu;
    private Timestamp data_godzina;
    private long numer_sali;

    public SeanseDB() {
    }

    public long getId_seansu() {
        return id_seansu;
    }

    public void setId_seansu(long id_seansu) {
        this.id_seansu = id_seansu;
    }

    public long getId_filmu() {
        return id_filmu;
    }

    public void setId_filmu(long id_filmu) {
        this.id_filmu = id_filmu;
    }

    public Timestamp getData_godzina() {
        return data_godzina;
    }

    public void setData_godzina(Timestamp data_godzina) {
        this.data_godzina = data_godzina;
    }

    public long getNumer_sali() {
        return numer_sali;
    }

    public void setNumer_sali(long numer_sali) {
        this.numer_sali = numer_sali;
    }
}
