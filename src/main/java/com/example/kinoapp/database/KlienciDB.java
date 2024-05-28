package com.example.kinoapp.database;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class KlienciDB {
    @Id
    private long id_klienta;
    private String imie;
    private String nazwisko;
    private String email;
    private String telefon;
}
