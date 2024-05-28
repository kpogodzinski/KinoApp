package com.example.kinoapp.tableview;

import com.example.kinoapp.database.FilmyDB;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Filmy {
    private final SimpleLongProperty id_filmu = new SimpleLongProperty();
    private final SimpleStringProperty tytul = new SimpleStringProperty();
    private final SimpleStringProperty gatunek = new SimpleStringProperty();
    private final SimpleIntegerProperty czas_trwania = new SimpleIntegerProperty();

    public Filmy() {
    }

    public Filmy(FilmyDB f) {
        setId_filmu(f.getId_filmu());
        setTytul(f.getTytul());
        setGatunek(f.getGatunek());
        setCzas_trwania(f.getCzas_trwania());
    }

    public long getId_filmu() {
        return id_filmu.get();
    }

    public SimpleLongProperty id_filmuProperty() {
        return id_filmu;
    }

    public void setId_filmu(long id_filmu) {
        this.id_filmu.set(id_filmu);
    }

    public String getTytul() {
        return tytul.get();
    }

    public SimpleStringProperty tytulProperty() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul.set(tytul);
    }

    public String getGatunek() {
        return gatunek.get();
    }

    public SimpleStringProperty gatunekProperty() {
        return gatunek;
    }

    public void setGatunek(String gatunek) {
        this.gatunek.set(gatunek);
    }

    public int getCzas_trwania() {
        return czas_trwania.get();
    }

    public SimpleIntegerProperty czas_trwaniaProperty() {
        return czas_trwania;
    }

    public void setCzas_trwania(int czas_trwania) {
        this.czas_trwania.set(czas_trwania);
    }
}
