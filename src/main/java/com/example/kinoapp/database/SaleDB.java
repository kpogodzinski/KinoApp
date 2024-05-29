package com.example.kinoapp.database;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity(name = "Sale")
public class SaleDB {
    @Id
    private long numer_sali;
    private int pojemnosc;
    @OneToMany
    private List<SeanseDB> seanse;

    public SaleDB() {
    }

    public long getNumer_sali() {
        return numer_sali;
    }

    public void setNumer_sali(long numer_sali) {
        this.numer_sali = numer_sali;
    }

    public int getPojemnosc() {
        return pojemnosc;
    }

    public void setPojemnosc(int pojemnosc) {
        this.pojemnosc = pojemnosc;
    }

    public List<SeanseDB> getSeanse() {
        return seanse;
    }

    public void setSeanse(List<SeanseDB> seanse) {
        this.seanse = seanse;
    }
}
