package com.example.kinoapp.database;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity(name = "Klienci")
public class KlienciDB {
    @Id
    private long id_klienta;
    private String imie;
    private String nazwisko;
    private String email;
    private String telefon;
    @OneToMany(mappedBy = "klient")
    private List<RezerwacjeDB> rezerwacje;

    public KlienciDB() {
    }

    public long getId_klienta() {
        return id_klienta;
    }

    public void setId_klienta(long id_klienta) {
        this.id_klienta = id_klienta;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
