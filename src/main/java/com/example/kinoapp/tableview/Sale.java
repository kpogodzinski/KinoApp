package com.example.kinoapp.tableview;

import com.example.kinoapp.database.SaleDB;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;

public class Sale {
    private final SimpleLongProperty numer_sali = new SimpleLongProperty();
    private final SimpleIntegerProperty pojemnosc = new SimpleIntegerProperty();

    public Sale() {
    }

    public Sale(SaleDB s) {
        setNumer_sali(s.getNumer_sali());
        setPojemnosc(s.getPojemnosc());
    }

    public long getNumer_sali() {
        return numer_sali.get();
    }

    public SimpleLongProperty numer_saliProperty() {
        return numer_sali;
    }

    public void setNumer_sali(long numer_sali) {
        this.numer_sali.set(numer_sali);
    }

    public int getPojemnosc() {
        return pojemnosc.get();
    }

    public SimpleIntegerProperty pojemnoscProperty() {
        return pojemnosc;
    }

    public void setPojemnosc(int pojemnosc) {
        this.pojemnosc.set(pojemnosc);
    }
}
