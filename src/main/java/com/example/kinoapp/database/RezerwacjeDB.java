package com.example.kinoapp.database;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class RezerwacjeDB {
    @Id
    private long id_rezerwacji;
    private long id_seansu;
    private long id_klienta;
    private int numer_fotela;
}
