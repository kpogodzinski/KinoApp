package com.example.kinoapp.database;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SaleDB {
    @Id
    private long numer_sali;
    private int pojemnosc;
}
